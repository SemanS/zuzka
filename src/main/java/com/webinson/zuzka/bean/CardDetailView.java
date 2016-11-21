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
import java.io.Serializable;

/**
 * Created by Slavo on 10/18/2016.
 */
@Component
@ViewScoped
public class CardDetailView implements Serializable {

    @Getter
    @Setter
    @ManagedProperty(value = "#{param.selectedCard}")
    public String selectedCardDashboard;

    @Getter
    @Setter
    @ManagedProperty(value = "#{param.selectedCard}")
    public String selectedCard;

    @Getter
    @Setter
    private CardDto cardDto;

    @Autowired
    CardService cardService;

    @PostConstruct
    public void init() {

        cardDto = cardService.getCardByUrl(showText());
       /* selectedCard = PrettyContext.getCurrentInstance().getRequestURL().toURL().substring(10);*/
        selectedCardDashboard = PrettyContext.getCurrentInstance().getRequestURL().toURL().substring(11);

    }

    public String showText() {

        String path = PrettyContext.getCurrentInstance().getRequestURL().toURL();
        String segments[] = path.split("/");
        String resultUrl = segments[segments.length - 1];

        /*System.out.println(resultUrl);
        System.out.println(cardService.getTextOfCardByUrl(resultUrl));*/
        System.out.println(resultUrl);
        return resultUrl;
        /*System.out.println(cardService.getTextOfCardByUrl(selectedCard));*/
    }

}
