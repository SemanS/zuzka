package com.webinson.clickablebudget;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by Slavo on 13.09.2016.
 */
@Configuration
@EnableAutoConfiguration
@EnableTransactionManagement
@SpringBootApplication
public class ApplicationConfig extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationConfig.class, args);
    }
}
