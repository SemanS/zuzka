package com.webinson.clickablebudget.dao;

import com.webinson.clickablebudget.entity.Income;
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
public interface IncomeDao extends JpaRepository<Income, Long>, QueryDslPredicateExecutor<Income> {

    List<Income> findIncomesByCityId(long id);

    Income findById(long prevId);

    Income findPolozkaById(long id);

    @Query("select distinct i from Income i where i.polozka = :polozka order by i.polozka")
    List<Income> findPolozka(@Param("polozka") String polozka);


    @Query("select distinct i from Income i where substring(i.polozka,0 + :i,1) = :cisloSubstring order by i.polozka")
    List<Income> findIncomeByPolozkaString(@Param("i") int i, @Param("cisloSubstring") String cisloSubstring);

    @Query("select distinct i from Income i where substring(i.polozka,1, :i) = :cisloSubstring order by i.polozka")
    List<Income> findIncomeByPolozkaString2(@Param("i") int i, @Param("cisloSubstring") String cisloSubstring);

    @Query("select distinct substring(i.polozka, 1, 1) from Income i order by substring(i.polozka, 1, 1) asc")
    List<String> findDistinctVykazy();

}