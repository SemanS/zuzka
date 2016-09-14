package com.webinson.clickablebudget.bean;

import org.chartistjsf.model.chart.PieChartModel;
import org.primefaces.event.ItemSelectEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * Created by Slavo on 13.09.2016.
 */
@Component
@Scope("request")
public class ChartDataBean {

    private PieChartModel pieChartModel;

    public ChartDataBean() {
        createPieChart();
    }

    public void createPieChart() {
        pieChartModel = new PieChartModel();

        pieChartModel.addLabel("Bananas");
        pieChartModel.addLabel("Apples");
        pieChartModel.addLabel("Grapes");

        pieChartModel.set(20);
        pieChartModel.set(10);
        pieChartModel.set(30);

        pieChartModel.setShowTooltip(true);
    }

    public void pieItemSelect(ItemSelectEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item selected", "Item Value: "
                + pieChartModel.getData().get(event.getItemIndex()));

        FacesContext.getCurrentInstance().addMessage(event.getComponent().getClientId(), msg);
    }

    /**
     * @return the pieChartModel
     */
    public PieChartModel getPieChartModel() {
        return pieChartModel;
    }

    /**
     * @param pieChartModel the pieChartModel to set
     */
    public void setPieChartModel(PieChartModel pieChartModel) {
        this.pieChartModel = pieChartModel;
    }

}
