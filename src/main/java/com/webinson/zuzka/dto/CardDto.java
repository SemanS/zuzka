package com.webinson.zuzka.dto;

import lombok.Data;

import java.util.Date;

/**
 * Created by Slavo on 10/17/2016.
 */
@Data
public class CardDto {

    private Long id;
    private String header;
    private String text;
    private Date date;
    private String url;

}
