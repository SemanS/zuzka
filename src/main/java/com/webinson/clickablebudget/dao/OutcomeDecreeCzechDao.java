package com.webinson.clickablebudget.dao;

import com.webinson.clickablebudget.entity.IncomeDecreeCzech;
import com.webinson.clickablebudget.entity.OutcomeDecreeCzech;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * Created by Slavo on 10/3/2016.
 */
@Repository
public interface OutcomeDecreeCzechDao extends JpaRepository<OutcomeDecreeCzech, Long>, QueryDslPredicateExecutor<OutcomeDecreeCzech> {

    OutcomeDecreeCzech findOutcomeDecreeCzechByKlass(String klass);

}
