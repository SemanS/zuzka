package com.webinson.clickablebudget.service;

import com.webinson.clickablebudget.entity.City;

import java.util.List;

/**
 * Created by Slavo on 13.09.2016.
 */
public interface CityService {


    City getCity(long id);

    City getCity(String name, String country);

    List<City> getCities();

}
