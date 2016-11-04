package com.webinson.clickablebudget.bean;

import com.ocpsoft.pretty.PrettyContext;
import com.webinson.clickablebudget.dto.CardDto;
import com.webinson.clickablebudget.service.CardService;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.Marker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.List;

/**
 * Created by Slavo on 10/18/2016.
 */
@Component
@ViewScoped
public class DashboardView {

    @Getter
    @Setter
    public String selectedCard;

    @Autowired
    CardService cardService;

    @Getter
    @Setter
    public List<CardDto> cards;


    @PostConstruct
    public void init() {

        cards = cardService.getAllCards();

    }

    public void onEditCard(CardDto cardDto) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/dashboard/" + cardDto.getUrl());
        System.out.println(cardDto.getUrl());
    }

}
