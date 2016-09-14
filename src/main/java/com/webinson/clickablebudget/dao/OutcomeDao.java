package com.webinson.clickablebudget.dao;

import com.webinson.clickablebudget.entity.Outcome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * Created by Slavo on 13.09.2016.
 */
@Repository
public interface OutcomeDao extends JpaRepository<Outcome, Long>, QueryDslPredicateExecutor<Outcome> {


}

