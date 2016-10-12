package com.webinson.clickablebudget.service.impl;

import com.webinson.clickablebudget.dao.CityDao;
import com.webinson.clickablebudget.entity.City;
import com.webinson.clickablebudget.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Slavo on 13.09.2016.
 */
@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityDao cityDao;

    @Override
    public City getCity(String name, String country) {
        return null;
    }

    @Override
    public List<City> getCities() {
        return cityDao.findAll();
    }

    @Override
    public City getCity(long id) {
        return cityDao.findOne(id);
    }

    @Override
    public List<String> queryByName(String name) {
        List<String> queried = new ArrayList<String>();

        for (City city : cityDao.findAll()) {
            if (city.getName().startsWith(name)) {
                queried.add(city.getName());
            }
        }
        return queried;
    }

}
