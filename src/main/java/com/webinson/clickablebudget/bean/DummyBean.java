package com.webinson.clickablebudget.bean;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

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

    public String initializeText() {
        //System.out.println(text);
        return text;
    }

    public DummyBean() {
        //System.out.println(text);
        /*FacesContext context = FacesContext.getCurrentInstance();
        System.out.println((String) context.getApplication().evaluateExpressionGet(context, "#{dummyBean.text}", String.class));
*/


        String uri = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestURI();

        String originalURI = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get(RequestDispatcher.FORWARD_REQUEST_URI);
        System.out.println(originalURI);

        //PrettyContext.getCurrentInstance().getRequestURL().toURL();
        //System.out.println(PrettyContext.getCurrentInstance().getRequestURL().toURL());
    }


    public String getRequestURL() {
        Object request = FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request instanceof HttpServletRequest) {
            //HttpServletRequest origRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            System.out.println(((HttpServletRequest) request).getRequestURL().toString());
            return ((HttpServletRequest) request).getRequestURL().toString();
        } else {
            return "";
        }
    }

    @PostConstruct
    public void init() {

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
