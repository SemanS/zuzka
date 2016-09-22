package com.webinson.clickablebudget.bean;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedProperty;

/**
 * Created by Slavo on 22.09.2016.
 */
@Scope("request")
@Component
public class DummyBean {

    @ManagedProperty(value = "#{param.text}")
    private String text;

    @Setter
    @Getter
    private String mesto;

    public DummyBean() {
        this.mesto = "Nelahozeves";
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
