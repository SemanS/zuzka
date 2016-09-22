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
    List<String> allYears = new ArrayList<String>();

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

    public TreeNode onYearChange(String year) {

        this.selectedYear = year;
        System.out.println(selectedCity);
        //selectedCity = "Nelahozeves";
        selectedMonth = incomeAndOutcomeService.getLastDateByCityAndYear("Nelahozeves", year).toString().substring(5, 7);
        root = null;
        //System.out.println(selectedCity + selectedMonth + selectedYear);
        root = incomeAndOutcomeService.createIncomesAndOutcomes(selectedMonth, selectedCity, year);
        //FacesContext.getCurrentInstance().addMessage(null, msg);
        return root;
    }

    public TreeNode onMonthChange(String month, String year) {

        MonthFormatterReverse monthFormatterReverse = new MonthFormatterReverse();
        //selectedYear = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("year");
//        FacesMessage msg = new FacesMessage("Tab Changed", "Active Tab: " + event.g);
        System.out.println(year);
        /*if (year == "") {
            year = incomeAndOutcomeService.getLastDateByCity("Nelahozeves").toString().substring(0, 4);
        }*/
        //System.out.println(selectedYear);
        selectedMonth = monthFormatterReverse.monthFormat(month);
        //selectedYear = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("year");
        selectedCity = "Nelahozeves";
        //System.out.println(selectedYear);

        root = null;
        root = incomeAndOutcomeService.createIncomesAndOutcomes(selectedMonth, selectedCity, year);
        return root;
    }

    public void onRowChange(VykazRadekDto vykaz) {
        //System.out.println(vykaz.getName());
    }

    @PostConstruct
    public void init() {

        //incomeAndOutcomeService.getLastDateByCity("Nelahozeves").toString().substring(0, 4);
        allYears = incomeAndOutcomeService.getAllYears("Nelahozeves");
        selectedYear = "2015";


        /*if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("year") != null) {
            System.out.println(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("year"));
        }*/

        selectedCity = "Nelahozeves";

        fiveIncomes = incomeAndOutcomeService.getFiveIncomes(incomeAndOutcomeService.getLastDateByCity("Nelahozeves").toString().substring(5, 7), selectedCity, selectedYear);
        //System.out.println(incomeAndOutcomeService.getFiveIncomes(selectedMonth, selectedCity, selectedYear).get(0).getApprovedBudget());
        root = incomeAndOutcomeService.createIncomesAndOutcomes(incomeAndOutcomeService.getLastDateByCity("Nelahozeves").toString().substring(5, 7), selectedCity, selectedYear);
        selectedVykaz = incomeAndOutcomeService.createFirstRoots();
        barChartModel = createBarModelExpand(selectedVykaz);
        //selectedYear = ;
    }

    public void onNodeExpand(VykazRadekDto vykaz) {
        /*FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Expanded", event.getTreeNode().toString());
        FacesContext.getCurrentInstance().addMessage(null, message);*/
        //vykaz = selectedVykaz;
        createBarModelExpand(vykaz);
        //System.out.println(vykaz.getName());

    }

    public void onNodeCollapse(VykazRadekDto vykaz) {
        vykaz = selectedVykaz;
        createBarModelExpand(vykaz);
    }

    /*public void onNodeUnselect(NodeUnselectEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Unselected", event.getTreeNode().toString());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }*/


    public String simular() {

        return "index.xhtml?foo=42&faces-redirect=true";
    }

}
