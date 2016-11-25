package com.webinson.zuzka.bean;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * Created by Slavo on 11/25/2016.
 */
@Component
@ViewScoped
public class loginBean {

    private String message ="Enter username and password.";
    private String username;
    private String password;
    public String login(){
        if("concretepage".equalsIgnoreCase(username) && "concretepage".equalsIgnoreCase(password)) {
            message ="Successfully logged-in.";
            return "success";
        } else {
            message ="Wrong credentials.";
            return "login";
        }
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}

