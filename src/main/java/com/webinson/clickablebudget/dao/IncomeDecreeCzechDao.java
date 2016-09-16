package com.webinson.clickablebudget.dao;

import com.webinson.clickablebudget.entity.IncomeDecreeCzech;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * Created by Slavo on 14.09.2016.
 */
@Repository
public interface IncomeDecreeCzechDao extends JpaRepository<IncomeDecreeCzech, Long>, QueryDslPredicateExecutor<IncomeDecreeCzech> {

    IncomeDecreeCzech findIncomeDecreeCzechByKlass(String klass);

}
