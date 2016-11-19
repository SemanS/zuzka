package com.webinson.zuzka.dao;

import com.webinson.zuzka.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * Created by Slavo on 10/17/2016.
 */
@Repository
public interface CardDao extends JpaRepository<Card, Long>, QueryDslPredicateExecutor<Card> {


}
