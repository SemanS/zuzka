package com.webinson.zuzka.bean;

import com.ocpsoft.pretty.PrettyContext;
import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
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
@URLMappings(mappings = {
        @URLMapping(
                id = "home",
                pattern = "/",
                viewId = "/index.xhtml"),
        /*@URLMapping(
                id = "category",
                pattern = "/#{ selectedCategory: cardView.selectedCategory}",
                viewId = "/category.xhtml"),*/
        /*@URLMapping(
                id = "item",
                pattern = "/#{ selectedItem: cardView.selectedItem}",
                viewId = "/item.xhtml"),*/
        @URLMapping(
                id = "dashboard",
                pattern = "/dashboard/#{ selectedCard : cardView.selectedCard}",
                viewId = "/dashboardcarddetail.xhtml")
})
public class CardView implements Serializable {

    @Autowired
    CardService cardService;

    @Getter
    @Setter
    private String selectedCard;

    @Getter
    @Setter
    private String selectedProject;

    @Getter
    @Setter
    private String selectedCategory;

    @Getter
    @Setter
    private String selectedItem;

    @Getter
    @Setter
    private List<CardDto> cards;

   /* @URLAction(mappingId = "item")*/
    public void loadItem() {

        this.selectedItem = "item1";
        this.selectedCategory = "category1";
        /*if ( itemId != null ) {
            this.item = items.findById(itemId);
            return null;*/

    }

    @PostConstruct
    public void init() {

        this.selectedCategory = "category1";
        this.selectedItem = "item1";
        //selectedCard = PrettyContext.getCurrentInstance().getRequestURL().toURL();
        //cardDto = cardService.getCardByUrl(selectedCard);
        //selectedCardDashboard = PrettyContext.getCurrentInstance().getRequestURL().toURL().substring(6);
        cards = cardService.getAllCards();
    }

    public void onEditCard(CardDto cardDto) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/projects/" + cardDto.getUrl());
        System.out.println(cardDto.getUrl());
    }

    public void saveText() {

        System.out.println(selectedCard);
        /*String path = PrettyContext.getCurrentInstance().getRequestURL().toURL();
        String segments[] = path.split("/");
        String resultUrl = segments[segments.length - 1];

        cardService.saveCardByUrl(resultUrl, text);*/
    }

}
