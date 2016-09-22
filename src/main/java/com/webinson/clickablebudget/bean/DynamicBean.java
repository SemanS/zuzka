package com.webinson.clickablebudget.bean;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by Slavo on 22.09.2016.
 */
@Component
@Scope("request")
public class DynamicBean {

    private String uglyLink;

    public DynamicBean() {
        this.uglyLink = "/pokus.xhtml?text=welcome!";
    }

    public String getUglyLink() {
        return uglyLink;
    }

    public void setUglyLink(String uglyLink) {
        this.uglyLink = uglyLink;
    }
}
