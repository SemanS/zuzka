package com.webinson.clickablebudget.bean;


import com.ocpsoft.pretty.PrettyContext;
import com.webinson.clickablebudget.dao.IncomeDao;
import com.webinson.clickablebudget.dto.VykazRadekDto;
import com.webinson.clickablebudget.service.CityService;
import com.webinson.clickablebudget.service.IncomeAndOutcomeService;
import com.webinson.clickablebudget.utils.MonthFormatter;
import com.webinson.clickablebudget.utils.MonthFormatterReverse;
import lombok.Getter;
import lombok.Setter;
import org.chartistjsf.model.chart.*;
import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UrlPathHelper;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Slavo on 13.09.2016.
 */
@Component
@ViewScoped
public class ClickableBudgetView implements Serializable {

    @Getter
    @Setter
    VykazRadekDto generalIncome;

    @Getter
    @Setter
    private PieChartModel pieChartModel;

    @Getter
    @Setter
    List<VykazRadekDto> fiveIncomes;

    @Getter
    @Setter
    private BarChartModel barChartModel;

    @Getter
    @Setter
    VykazRadekDto selectedVykaz;

    @Autowired
    @Setter
    private IncomeAndOutcomeService incomeAndOutcomeService;

    @Autowired
    private CityService cityService;

    @Autowired
    private IncomeDao incomeDao;

    @Getter
    @Setter
    private String selectedMonth;

    @Getter
    @Setter
    public String selectedYear;

    @Getter
    @Setter
    @ManagedProperty(value = "#{param.selectedCity}")
    private String selectedCity;

    @Getter
    @Setter
    private TreeNode root;

    @Getter
    @Setter
    private VykazRadekDto selectedVykazRadek;

    @Getter
    @Setter
    private TreeNode selectedNode;

    public List<String> getMonths(String thisYear) {

        MonthFormatter monthFormatter = new MonthFormatter();
        List<String> months = new ArrayList<String>();

        for (String dat : incomeDao.findAllMonthsByYear(selectedCity, selectedYear)) {
            months.add(monthFormatter.monthFormat(dat));
        }
        return months;
    }

    public List<String> getYears() {

        List<String> years = new ArrayList<String>();

        for (String dat : incomeDao.findAllYearsByCity(selectedCity)) {
            years.add(dat);
        }
        return years;
    }

    public String onYearChange(String year) {

        selectedMonth = incomeAndOutcomeService.getLastDateByCityAndYear(selectedCity, year).toString().substring(5, 7);
        root = null;
        root = incomeAndOutcomeService.createIncomesAndOutcomes(selectedMonth, selectedCity, year);
        selectedYear = year;
        return year;
    }

    public String onMonthChange(String month, String year) {

        if (year == "") {
            year = selectedYear;
        }

        MonthFormatterReverse monthFormatterReverse = new MonthFormatterReverse();
        selectedMonth = monthFormatterReverse.monthFormat(month);
        selectedCity = "Nelahozeves";
        root = null;
        root = incomeAndOutcomeService.createIncomesAndOutcomes(selectedMonth, selectedCity, year);
        generalIncome = incomeAndOutcomeService.getAllPrijmy(selectedCity, year, selectedMonth);
        createPieChart();
        return month;
    }

    private boolean responseRendered = false;

    @PostConstruct
    public void init() {

        selectedCity = PrettyContext.getCurrentInstance().getRequestURL().toURL().substring(6);
        selectedYear = incomeAndOutcomeService.getLastDateByCity(selectedCity).toString().substring(0, 4);
        fiveIncomes = incomeAndOutcomeService.getFiveIncomes(incomeAndOutcomeService.getLastDateByCity(selectedCity).toString().substring(5, 7), selectedCity, selectedYear);
        root = incomeAndOutcomeService.createIncomesAndOutcomes(incomeAndOutcomeService.getLastDateByCity(selectedCity).toString().substring(5, 7), selectedCity, selectedYear);

        selectedVykaz = incomeAndOutcomeService.createFirstRoots();
        barChartModel = createBarModelExpand(selectedVykaz);
        generalIncome = incomeAndOutcomeService.getAllPrijmy(selectedCity, selectedYear, incomeAndOutcomeService.getLastDateByCity(selectedCity).toString().substring(5, 7));
        createPieChart();
        selectedMonth = incomeAndOutcomeService.getLastDateByCity(selectedCity).toString().substring(5, 7);
    }

    public void onNodeExpand(VykazRadekDto vykaz) {
        createBarModelExpand(vykaz);
    }

    public void onNodeCollapse(VykazRadekDto vykaz) {
        vykaz = selectedVykaz;
        createBarModelExpand(vykaz);
    }

    public BarChartModel createBarModelExpand(VykazRadekDto selectedVykaz) {
        //Random random = new Random();
        barChartModel = new BarChartModel();
        barChartModel.setAspectRatio(AspectRatio.DOUBLE_OCTAVE);

        BarChartSeries series1 = new BarChartSeries();
        BarChartSeries series2 = new BarChartSeries();
        series1.setName("Prijate");
        series2.setName("Prijate");

        for (VykazRadekDto vyk : selectedVykaz.getChildren()) {
            barChartModel.addLabel(vyk.getName());
            series1.set(vyk.getApprovedBudget());
            series2.set(vyk.getAdjustedBudget());
        }

        barChartModel.addSeries(series1);
        barChartModel.addSeries(series2);

        Axis xAxis = barChartModel.getAxis(AxisType.X);
        xAxis.setShowGrid(false);

        barChartModel.setShowTooltip(true);
        barChartModel.setSeriesBarDistance(15);
        barChartModel.setStackBars(true);
        //barChartModel.setAnimateAdvanced(true);
        return barChartModel;
    }

    public BarChartModel createBarModelCollapse(VykazRadekDto selectedVykaz) {

        barChartModel = new BarChartModel();
        barChartModel.setAspectRatio(AspectRatio.DOUBLE_OCTAVE);

        BarChartSeries series1 = new BarChartSeries();
        BarChartSeries series2 = new BarChartSeries();
        series1.setName("Prijate");
        series2.setName("Prijate");

        for (VykazRadekDto vyk : selectedVykaz.getParent().getChildren()) {
            barChartModel.addLabel(vyk.getName());
            series1.set(vyk.getApprovedBudget());
            series2.set(vyk.getAdjustedBudget());
        }

        barChartModel.addSeries(series1);
        barChartModel.addSeries(series2);

        Axis xAxis = barChartModel.getAxis(AxisType.X);
        xAxis.setShowGrid(false);

        barChartModel.setShowTooltip(true);
        barChartModel.setSeriesBarDistance(15);
        barChartModel.setStackBars(true);

        return barChartModel;
    }

    public void createPieChart() {
        pieChartModel = new PieChartModel();

        pieChartModel.addLabel("Upravený");
        pieChartModel.addLabel("Skutečnost");

        pieChartModel.set(generalIncome.getAdjustedBudget());
        pieChartModel.set(generalIncome.getAdjustedBudget() - generalIncome.getSpentBudget());

        //pieChartModel.setShowTooltip(true);
    }

    public void pieItemSelect(ItemSelectEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item selected", "Item Value: "
                + pieChartModel.getData().get(event.getItemIndex()));

        FacesContext.getCurrentInstance().addMessage(event.getComponent().getClientId(), msg);
    }

}
