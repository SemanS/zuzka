package com.webinson.clickablebudget.assembler;

import com.webinson.clickablebudget.dto.CardDto;
import com.webinson.clickablebudget.entity.Card;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Slavo on 10/17/2016.
 */
@Component
public class CardAssembler {


    public CardDto convertToDto(Card model, CardDto dto) {
        dto.setHeader(model.getHeader());
        dto.setText(model.getText());
        dto.setDate(model.getDate());
        dto.setUrl(model.getUrl());
        return dto;
    }

    public List<CardDto> toDtos(final Collection<Card> models) {
        final List<CardDto> dtos = new ArrayList<>();
        if (isNotEmpty(models)) {
            for (final Card card : models) {
                dtos.add(convertToDto(card, new CardDto()));
            }
        }
        return dtos;
    }

    public boolean isNotEmpty(final Collection<?> col) {
        return !isEmpty(col);
    }

    public boolean isEmpty(final Collection<?> col) {
        return col == null || col.isEmpty();
    }

}
