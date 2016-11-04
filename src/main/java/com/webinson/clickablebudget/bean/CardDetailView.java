package com.webinson.clickablebudget.bean;

import com.ocpsoft.pretty.PrettyContext;
import com.webinson.clickablebudget.dto.CardDto;
import lombok.Getter;
import lombok.Setter;
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
    public String selectedCard;

    @PostConstruct
    public void init() {

        selectedCard = PrettyContext.getCurrentInstance().getRequestURL().toURL().substring(11);
        System.out.println(selectedCard);

    }


}
