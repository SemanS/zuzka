package com.webinson.zuzka.service.impl;

import com.webinson.zuzka.assembler.CardAssembler;
import com.webinson.zuzka.dao.CardDao;
import com.webinson.zuzka.dto.CardDto;
import com.webinson.zuzka.entity.Card;
import com.webinson.zuzka.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Slavo on 10/17/2016.
 */
@Service
public class CardServiceImpl implements CardService {

    @Autowired
    CardDao cardDao;

    @Autowired
    CardAssembler cardAssembler;

    public List<CardDto> getAllCards() {

        List<CardDto> cards = new ArrayList<CardDto>();
        cards = cardAssembler.toDtos(cardDao.findAll());
        return cards;

    }

    @Override
    public String getTextOfCardByUrl(String url) {
        return cardDao.findByUrl(url).getText();
    }

    @Override
    public CardDto getCardByUrl(String url) {
        CardDto cardDto = new CardDto();
        cardDto = cardAssembler.toDto(cardDao.findByUrl(url));
        return cardDto;
    }

    @Override
    public void saveCardByUrl(String url, String text) {
        Card card = cardDao.findByUrl(url);
        card.setText(text);
        cardDao.save(card);
    }

}
