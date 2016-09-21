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
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Slavo on 13.09.2016.
 */
@Component
@Scope("request")
public class ClickableBudgetView implements Serializable {

    @Getter
    @Setter
    private BarChartModel barChartModel;

    @Getter
    @Setter
    VykazRadekDto selectedVykaz;

    public BarChartModel createBarModel(VykazRadekDto selectedVykaz) {
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

        //barChartModel.addLabel(selectedVykaz.getName());
        /*barChartModel.addLabel("Tuesday");
        barChartModel.addLabel("Wednesday");
        barChartModel.addLabel("Thursday");*/


        /*BarChartSeries series1 = new BarChartSeries();
        series1.setName(selectedVykaz.getName());

        series1.set(selectedVykaz.getApprovedBudget());*/
/*        series1.set(random.nextInt(10));
        series1.set(random.nextInt(10));
        series1.set(random.nextInt(10));*/


        /*BarChartSeries series2 = new BarChartSeries();
        series2.setName("Series 2");

        series2.set(random.nextInt(10));
        series2.set(random.nextInt(10));
        series2.set(random.nextInt(10));
        series2.set(random.nextInt(10));


        BarChartSeries series3 = new BarChartSeries();
        series3.setName("Series 3");

        series3.set(random.nextInt(10));
        series3.set(random.nextInt(10));
        series3.set(random.nextInt(10));
        series3.set(random.nextInt(10));*/


        Axis xAxis = barChartModel.getAxis(AxisType.X);
        xAxis.setShowGrid(false);

        //barChartModel.addSeries(series1);
/*        barChartModel.addSeries(series2);
        barChartModel.addSeries(series3);*/

        barChartModel.setShowTooltip(true);
        barChartModel.setSeriesBarDistance(15);
        barChartModel.setStackBars(true);
        //barChartModel.setAnimateAdvanced(true);
        return barChartModel;
    }


    @Autowired
    @Setter
    private IncomeAndOutcomeService incomeAndOutcomeService;

    @Autowired
    private CityService cityService;

    @Autowired
    private IncomeDao incomeDao;

    @Getter
    @Setter
    private String selectedDate;

    @Getter
    @Setter
    private String selectedYear;

    @Getter
    @Setter
    private String selectedMonth;

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

    public TreeNode onTabChange(String event) {
        MonthFormatterReverse monthFormatterReverse = new MonthFormatterReverse();

//        FacesMessage msg = new FacesMessage("Tab Changed", "Active Tab: " + event.g);
        selectedDate = monthFormatterReverse.monthFormat(event);
        selectedCity = "Nelahozeves";
        root = null;
        root = incomeAndOutcomeService.createIncomesAndOutcomes(selectedDate, selectedCity);
        //FacesContext.getCurrentInstance().addMessage(null, msg);
        return root;
    }

    public void onRowChange(VykazRadekDto vykaz) {
        System.out.println(vykaz.getName());
    }

    @PostConstruct
    public void init() {

        selectedDate = "08";
        selectedCity = "Nelahozeves";
        root = incomeAndOutcomeService.createIncomesAndOutcomes("04", selectedCity);
        selectedVykaz = incomeAndOutcomeService.createFirstRoots(selectedDate, selectedCity);
        barChartModel = createBarModel(selectedVykaz);
    }

    public void onNodeExpand(VykazRadekDto vykaz) {
        /*FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Expanded", event.getTreeNode().toString());
        FacesContext.getCurrentInstance().addMessage(null, message);*/
        //vykaz = selectedVykaz;
        createBarModel(vykaz);
        System.out.println(vykaz.getName());

    }

   /* public void onNodeExpand(NodeExpandEvent event) {

        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Expanded", event.getTreeNode().toString());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }*/

    public void onNodeCollapse(VykazRadekDto vykaz) {
        createBarModel(vykaz);
    }

    /*public void onNodeUnselect(NodeUnselectEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Unselected", event.getTreeNode().toString());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }*/
}
