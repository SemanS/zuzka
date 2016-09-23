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
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by Slavo on 13.09.2016.
 */
@Component
@Scope("request")
public class ClickableBudgetView implements Serializable {
    
    @Getter
    @Setter
    List<VykazRadekDto> fiveIncomes = null;

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
    private TreeNode root = null;

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

    public TreeNode onMonthChange(String month, String year) {

        if (year == "") {
            year = selectedYear;
        }

        MonthFormatterReverse monthFormatterReverse = new MonthFormatterReverse();
        selectedMonth = monthFormatterReverse.monthFormat(month);
        selectedCity = "Nelahozeves";
        root = null;
        root = incomeAndOutcomeService.createIncomesAndOutcomes(selectedMonth, selectedCity, year);
        return root;
    }

    public void onRowChange(VykazRadekDto vykaz) {
        //System.out.println(vykaz.getName());
    }

    @PostConstruct
    public void init() {

        selectedCity = PrettyContext.getCurrentInstance().getRequestURL().toURL().substring(1);
        selectedYear = incomeAndOutcomeService.getLastDateByCity("Nelahozeves").toString().substring(0, 4);
        fiveIncomes = incomeAndOutcomeService.getFiveIncomes(incomeAndOutcomeService.getLastDateByCity(selectedCity).toString().substring(5, 7), selectedCity, selectedYear);
        root = incomeAndOutcomeService.createIncomesAndOutcomes(incomeAndOutcomeService.getLastDateByCity(selectedCity).toString().substring(5, 7), selectedCity, selectedYear);
        selectedVykaz = incomeAndOutcomeService.createFirstRoots();
        barChartModel = createBarModelExpand(selectedVykaz);

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

}
