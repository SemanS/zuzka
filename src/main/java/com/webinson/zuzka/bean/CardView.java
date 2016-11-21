package com.webinson.zuzka.bean;

import com.ocpsoft.pretty.PrettyContext;
import com.webinson.zuzka.dto.CardDto;
import com.webinson.zuzka.service.CardService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Slavo on 10/4/2016.
 */
@Component
@ViewScoped
public class CardView implements Serializable {

    @Autowired
    CardService cardService;

    @Getter
    @Setter
    @ManagedProperty(value = "#{param.selectedCard}")
    private String selectedCardDashboard;

    @Getter
    @Setter
    @ManagedProperty(value = "#{param.selectedCard}")
    private String selectedCard;

    @Getter
    @Setter
    private List<CardDto> cards;



    @PostConstruct
    public void init() {

        selectedCard = PrettyContext.getCurrentInstance().getRequestURL().toURL();
        //cardDto = cardService.getCardByUrl(selectedCard);
        selectedCardDashboard = PrettyContext.getCurrentInstance().getRequestURL().toURL().substring(6);
        cards = cardService.getAllCards();

    }

    public void onEditCard(CardDto cardDto) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/projects/" + cardDto.getUrl());
        System.out.println(cardDto.getUrl());
    }

}
