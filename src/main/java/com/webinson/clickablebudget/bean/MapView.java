package com.webinson.clickablebudget.bean;

import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created by Slavo on 10/4/2016.
 */
@Component
@ViewScoped
public class MapView implements Serializable {

    @Getter
    private MapModel simpleModel;

    @Getter
    private Marker marker;

    @Getter
    @Setter
    private String selectedCity;

    @PostConstruct
    public void init() {
        simpleModel = new DefaultMapModel();

        //Shared coordinates
        LatLng coord1 = new LatLng(50.261699, 14.298823);
        LatLng coord2 = new LatLng(36.883707, 30.689216);
        LatLng coord3 = new LatLng(36.879703, 30.706707);
        LatLng coord4 = new LatLng(36.885233, 30.702323);

        //Basic marker
        simpleModel.addOverlay(new Marker(coord1, "Nelahozeves"));
        simpleModel.addOverlay(new Marker(coord2, "Ataturk Parki"));
        simpleModel.addOverlay(new Marker(coord3, "Karaalioglu Parki"));
        simpleModel.addOverlay(new Marker(coord4, "Kaleici"));
    }

    public void onMarkerSelect(OverlaySelectEvent event) throws IOException {
        marker = (Marker) event.getOverlay();
        System.out.println(marker.getTitle());

        FacesContext.getCurrentInstance().getExternalContext().redirect("/city/" + marker.getTitle());
        //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Marker Selected", marker.getTitle()));
    }

}
