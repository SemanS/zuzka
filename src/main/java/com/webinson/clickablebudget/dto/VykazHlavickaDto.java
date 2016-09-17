package com.webinson.clickablebudget.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created by Slavo on 13.09.2016.
 */
@Data
@XmlRootElement(name = "VykazHlavicka")
@XmlAccessorType(XmlAccessType.FIELD)
public class VykazHlavickaDto {

    @XmlElement(name = "DatumVykaz")
    private String datumVykaz;

    @XmlElement(name = "SubjektICO")
    private String subjektIco;

    private Date datumSestaveni;
    private Integer financniCastkaRad;
    private String mena;

}

