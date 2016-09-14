package com.webinson.clickablebudget.dto;

import lombok.Data;

import java.util.List;

/**
 * Created by Slavo on 13.09.2016.
 */
@Data
public class IncomeAndOutcomeDto {

    private Long id;
    private String name;
    private String klass;
    private int approvedBudget;
    private int adjustedBudget;
    private int spentBudget;
    private int state;
    private VykazHlavickaDto vykazHlavicka;
    private List<VykazRadekDto> incomes;
    private List<VykazRadekDto> outcomes;
    private Long prevId;

}
