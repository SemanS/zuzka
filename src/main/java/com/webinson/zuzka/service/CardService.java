package com.webinson.zuzka.service;

import com.webinson.zuzka.dto.CardDto;

import java.util.List;

/**
 * Created by Slavo on 10/17/2016.
 */
public interface CardService {

    public List<CardDto> getAllCards();

    public String getTextOfCardByUrl(String url);

    public CardDto getCardByUrl(String url);

    public void saveCardByUrl(String url, String text);

}
