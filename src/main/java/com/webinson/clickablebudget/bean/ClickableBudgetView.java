package com.webinson.clickablebudget.bean;

import com.webinson.clickablebudget.dao.IncomeDao;
import com.webinson.clickablebudget.dto.VykazRadekDto;
import com.webinson.clickablebudget.service.CityService;
import com.webinson.clickablebudget.service.IncomeAndOutcomeService;
import com.webinson.clickablebudget.utils.MonthFormatter;
import com.webinson.clickablebudget.utils.MonthFormatterReverse;
import lombok.Getter;
import lombok.Setter;
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

/**
 * Created by Slavo on 13.09.2016.
 */
@Component
@Scope("session")
public class ClickableBudgetView implements Serializable {

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

    @PostConstruct
    public void init() {

        selectedDate = "08";
        selectedCity = "Nelahozeves";
        root = incomeAndOutcomeService.createIncomesAndOutcomes(selectedDate, selectedCity);

    }

    public void onNodeExpand(NodeExpandEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Expanded", event.getTreeNode().toString());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void onNodeCollapse(NodeCollapseEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Collapsed", event.getTreeNode().toString());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void onNodeUnselect(NodeUnselectEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Unselected", event.getTreeNode().toString());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
