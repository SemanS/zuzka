package com.webinson.clickablebudget.dto;

import com.webinson.clickablebudget.dto.VykazRadekDto;
import lombok.Data;

import java.util.List;

/**
 * Created by Slavo on 14.09.2016.
 */
@Data
public class FinancovaniDto {
    private String nazev;
    private String ucetNazev;
    private Integer radekCislo;
    private Double stavPocatecni;
    private Double stavKonecObdobi;
    private Double zmenaStavu;
    private Integer znakUcelovy;
    private String polozka;
    private String paragraf;
    private Double approvedBudget;
    private double adjustedBudget;
    private double spentBudget;
    private VykazRadekDto financovani;
    private Integer nastroj;
    private Integer jednotkaProstorova;
}
