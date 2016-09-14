package com.webinson.clickablebudget.dto;

import lombok.Data;

import java.util.Date;

/**
 * Created by Slavo on 13.09.2016.
 */
@Data
public class VykazHlavickaDto {

    private Date datumVykaz;
    private Integer subjektIco;
    private Date datumSestaveni;
    private Integer financniCastkaRad;
    private String mena;

}

