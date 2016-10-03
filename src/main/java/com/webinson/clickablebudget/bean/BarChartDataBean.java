package com.webinson.clickablebudget.bean;

import org.chartistjsf.model.chart.*;
import org.primefaces.event.ItemSelectEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.Random;

/**
 * Created by Slavo on 20.09.2016.
 */
@Component
@Scope("request")
public class BarChartDataBean {

    private BarChartModel barChartModel;

    public BarChartDataBean() {
        createBarModel();
    }

    public void createBarModel() {
        Random random = new Random();
        barChartModel = new BarChartModel();
        barChartModel.setAspectRatio(AspectRatio.DOUBLE_OCTAVE);

        barChartModel.addLabel("Monday");
        barChartModel.addLabel("Tuesday");
        barChartModel.addLabel("Wednesday");
        barChartModel.addLabel("Thursday");


        BarChartSeries series1 = new BarChartSeries();
        series1.setName("Series 1");
        series1.set(random.nextInt(10));
        series1.set(random.nextInt(10));
        series1.set(random.nextInt(10));
        series1.set(random.nextInt(10));


        BarChartSeries series2 = new BarChartSeries();
        series2.setName("Series 2");
        series2.set(random.nextInt(10));
        series2.set(random.nextInt(10));
        series2.set(random.nextInt(10));
        series2.set(random.nextInt(10));


        /*BarChartSeries series3 = new BarChartSeries();
        series3.setName("Series 3");
        series3.set(random.nextInt(10));
        series3.set(random.nextInt(10));
        series3.set(random.nextInt(10));
        series3.set(random.nextInt(10));*/


        Axis xAxis = barChartModel.getAxis(AxisType.X);
        xAxis.setShowGrid(false);

        barChartModel.addSeries(series1);
        barChartModel.addSeries(series2);

        barChartModel.setShowTooltip(true);
        barChartModel.setSeriesBarDistance(15);
        barChartModel.setStackBars(true);
        //barChartModelIncome.setAnimateAdvanced(true);
    }

    /**
     * @return the barChartModelIncome
     */
    public BarChartModel getBarChartModel() {
        return barChartModel;
    }

    /**
     * @param barChartModel the barChartModelIncome to set
     */
    public void setBarChartModel(BarChartModel barChartModel) {
        this.barChartModel = barChartModel;
    }

    public void barItemSelect(ItemSelectEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item selected", "Item Value: "
                + ((ChartSeries) barChartModel.getSeries().get(event.getSeriesIndex())).getData().get(
                event.getItemIndex()) + ", Series name:"
                + ((ChartSeries) barChartModel.getSeries().get(event.getSeriesIndex())).getName());

        FacesContext.getCurrentInstance().addMessage(event.getComponent().getClientId(), msg);
    }

}
