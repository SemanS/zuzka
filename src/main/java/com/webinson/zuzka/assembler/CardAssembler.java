package com.webinson.zuzka.assembler;

import com.webinson.zuzka.dto.CardDto;
import com.webinson.zuzka.entity.Card;
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

    public CardDto toDto(Card model) {
        CardDto card = new CardDto();
        card.setHeader(model.getHeader());
        card.setText(model.getText());
        card.setDate(model.getDate());
        card.setUrl(model.getUrl());
        return card;
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
