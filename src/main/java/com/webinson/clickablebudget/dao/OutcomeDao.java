package com.webinson.clickablebudget.dao;

import com.webinson.clickablebudget.entity.Income;
import com.webinson.clickablebudget.entity.Outcome;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Slavo on 13.09.2016.
 */
@Repository
public interface OutcomeDao extends JpaRepository<Outcome, Long>, QueryDslPredicateExecutor<Outcome> {

    @Query("select distinct substring(o.paragraf, 1, 1) from Outcome o where o.city.name = :selectedCity and substring(cast (o.date as text), 6, 2) = :selectedMonth and substring(cast (o.date as text), 1, 4) = :selectedYear order by substring(o.paragraf, 1, 1) asc")
    List<String> findDistinctVykazy(@Param("selectedCity") String selectedCity, @Param("selectedMonth") String selectedMonth, @Param("selectedYear") String selectedYear);

    @Query("select distinct o from Outcome o where substring(o.paragraf,1, :o) = :cisloSubstring and o.city.name = :selectedCity and substring(cast (o.date as text), 6, 2) = :selectedMonth and substring(cast (o.date as text), 1, 4) = :selectedYear order by o.paragraf")
    List<Outcome> findOutcomeByPolozkaString(@Param("o") int o, @Param("cisloSubstring") String cisloSubstring, @Param("selectedCity") String selectedCity, @Param("selectedMonth") String selectedMonth, @Param("selectedYear") String selectedYear);

    @Query("select o from Outcome o where o.city.name = :selectedCity and substring(cast (o.date as text), 6, 2) = :selectedMonth and substring(cast (o.date as text), 1, 4) = :selectedYear")
    List<Outcome> findGeneralOutcome(@Param("selectedCity") String selectedCity, @Param("selectedMonth") String selectedMonth, @Param("selectedYear") String selectedYear);

    @Query("select o from Outcome o where o.city.name = :selectedCity and substring(cast (o.date as text), 6, 2) = :selectedMonth and substring(cast (o.date as text), 1, 4) = :selectedYear order by o.spentBudget desc")
    List<Outcome> findFiveOutcomes(@Param("selectedCity") String selectedCity, @Param("selectedMonth") String selectedMonth, @Param("selectedYear") String selectedYear, Pageable pageable);

}

