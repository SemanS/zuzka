package com.webinson.clickablebudget.bean;

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
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Slavo on 13.09.2016.
 */
@Component
@Scope("request")
public class ClickableBudgetView implements Serializable {

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
    private String selectedYear;

    @Getter
    @Setter
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

    public List<String> getMonths() {

        MonthFormatter monthFormatter = new MonthFormatter();
        List<String> months = new ArrayList<String>();

        for (String dat : incomeDao.findAllMonthsByYear(selectedCity, "2015")) {
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

    public TreeNode onYearChange(String year) {
        MonthFormatterReverse monthFormatterReverse = new MonthFormatterReverse();

//        FacesMessage msg = new FacesMessage("Tab Changed", "Active Tab: " + event.g);
        selectedYear = year;
        selectedCity = "Nelahozeves";
        root = null;
        root = incomeAndOutcomeService.createIncomesAndOutcomes(selectedMonth, selectedCity, selectedYear);
        //FacesContext.getCurrentInstance().addMessage(null, msg);
        return root;
    }

    public TreeNode onTabChange(String event) {
        MonthFormatterReverse monthFormatterReverse = new MonthFormatterReverse();

//        FacesMessage msg = new FacesMessage("Tab Changed", "Active Tab: " + event.g);
        selectedMonth = monthFormatterReverse.monthFormat(event);
        selectedCity = "Nelahozeves";
        root = null;
        root = incomeAndOutcomeService.createIncomesAndOutcomes(selectedMonth, selectedCity, selectedYear);
        //FacesContext.getCurrentInstance().addMessage(null, msg);
        return root;
    }

    public void onRowChange(VykazRadekDto vykaz) {
        System.out.println(vykaz.getName());
    }

    @PostConstruct
    public void init() {
        selectedYear = "2015";
        selectedMonth = "08";
        selectedCity = "Nelahozeves";

        fiveIncomes = incomeAndOutcomeService.getFiveIncomes(selectedMonth, selectedCity, selectedYear);
        //System.out.println(incomeAndOutcomeService.getFiveIncomes(selectedMonth, selectedCity, selectedYear).get(0).getApprovedBudget());
        root = incomeAndOutcomeService.createIncomesAndOutcomes(selectedMonth, selectedCity, selectedYear);
        selectedVykaz = incomeAndOutcomeService.createFirstRoots();
        barChartModel = createBarModelExpand(selectedVykaz);
    }

    public void onNodeExpand(VykazRadekDto vykaz) {
        /*FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Expanded", event.getTreeNode().toString());
        FacesContext.getCurrentInstance().addMessage(null, message);*/
        //vykaz = selectedVykaz;
        createBarModelExpand(vykaz);
        System.out.println(vykaz.getName());

    }

    public void onNodeCollapse(VykazRadekDto vykaz) {
        vykaz = selectedVykaz;
        createBarModelExpand(vykaz);
    }

    /*public void onNodeUnselect(NodeUnselectEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Unselected", event.getTreeNode().toString());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }*/


}
