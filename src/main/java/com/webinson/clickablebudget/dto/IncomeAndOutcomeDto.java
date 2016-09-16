package com.webinson.clickablebudget.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Slavo on 13.09.2016.
 */
@Data
@XmlRootElement( name = "VykazFin212M" )
@XmlAccessorType(XmlAccessType.FIELD)
public class IncomeAndOutcomeDto {

    private Long id;
    private String name;
    private String klass;
    private int approvedBudget;
    private int adjustedBudget;
    private int spentBudget;
    private int state;
    @XmlElement( name = "VykazHlavicka" )
    private VykazHlavickaDto vykazHlavicka;
    private List<VykazRadekDto> incomes;
    private List<VykazRadekDto> outcomes;

}
