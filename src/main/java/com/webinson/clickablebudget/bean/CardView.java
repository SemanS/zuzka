package com.webinson.clickablebudget.bean;

import com.ocpsoft.pretty.PrettyContext;
import com.webinson.clickablebudget.dto.CardDto;
import com.webinson.clickablebudget.entity.City;
import com.webinson.clickablebudget.service.CardService;
import com.webinson.clickablebudget.service.CityService;
import com.webinson.clickablebudget.service.impl.CityServiceImpl;
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
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Slavo on 10/4/2016.
 */
@Component
@ViewScoped
public class CardView implements Serializable {

    @Getter
    private MapModel simpleModel;

    @Getter
    private Marker marker;

    @Getter
    @Setter
    private String selectedCity;

    @Getter
    @Setter
    private String selectedCitySubmit;

    @Autowired
    private CityService cityService;

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
        simpleModel = new DefaultMapModel();

        //Shared coordinates
        LatLng coord1 = new LatLng(50.261699, 14.298823);
        LatLng coord2 = new LatLng(50.489174, 15.038296);

        //Basic marker
        simpleModel.addOverlay(new Marker(coord1, "Nelahozeves"));
        simpleModel.addOverlay(new Marker(coord2, "Kněžmost"));
    }

    public void onMarkerSelect(OverlaySelectEvent event) throws IOException {
        marker = (Marker) event.getOverlay();
        System.out.println(marker.getTitle());

        FacesContext.getCurrentInstance().getExternalContext().redirect("/city/" + marker.getTitle());
        //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Marker Selected", marker.getTitle()));
    }

    public List<String> completeText(String query) {
        /*List<String> results = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            results.add(query + i);
        }*/
        return cityService.queryByName(query);
    }

    public void onSubmit(ActionEvent event) throws IOException {
        /*marker = (Marker) event.getOverlay();*/
        //System.out.println(this.getSelectedCitySubmit());

        int i = 0;
        for (City city : cityService.getCities()) {
            if (!city.getName().equals(this.getSelectedCitySubmit())) {
                i = 0;
            } else if (city.getName().equals(this.getSelectedCitySubmit())) {
                i = 1;
                FacesContext.getCurrentInstance().getExternalContext().redirect("/city/" + this.getSelectedCitySubmit());
            }
        }
        if (i == 0) {
            addMessage("Toto mesto nie je do programu zapojené. Pošli mail na ceo@webinson.com a prihlás svoje mesto!");
        }

    }

    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

}
