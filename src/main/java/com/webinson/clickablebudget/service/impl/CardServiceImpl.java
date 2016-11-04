package com.webinson.clickablebudget.service.impl;

import com.webinson.clickablebudget.assembler.CardAssembler;
import com.webinson.clickablebudget.dao.CardDao;
import com.webinson.clickablebudget.dto.CardDto;
import com.webinson.clickablebudget.service.CardService;
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

}
