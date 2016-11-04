package com.webinson.clickablebudget.bean;

import com.ocpsoft.pretty.PrettyContext;
import com.webinson.clickablebudget.dto.CardDto;
import com.webinson.clickablebudget.service.CardService;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
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
    private String selectedCard;

    @Getter
    @Setter
    private List<CardDto> cards;

    @PostConstruct
    public void init() {

        selectedCard = PrettyContext.getCurrentInstance().getRequestURL().toURL().substring(6);
        cards = cardService.getAllCards();

    }

}
