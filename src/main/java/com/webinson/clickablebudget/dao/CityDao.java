package com.webinson.clickablebudget.dao;

import com.webinson.clickablebudget.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * Created by Slavo on 13.09.2016.
 */
@Repository
public interface CityDao extends JpaRepository<City, Long>, QueryDslPredicateExecutor<City> {

    City findIdByIco(String ico);

}

