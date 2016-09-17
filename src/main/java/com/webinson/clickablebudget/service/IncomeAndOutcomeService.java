package com.webinson.clickablebudget.service;

import com.webinson.clickablebudget.dto.IncomeAndOutcomeDto;
import com.webinson.clickablebudget.entity.City;
import org.primefaces.model.TreeNode;

import java.util.Date;
import java.util.List;

/**
 * Created by Slavo on 13.09.2016.
 */
public interface IncomeAndOutcomeService {

    void saveIncomes(IncomeAndOutcomeDto incomeAndOutcome);

    void saveOutcomes(IncomeAndOutcomeDto incomeAndOutcome);

    List<Date> findAllDatesByCity(City city);

    List<IncomeAndOutcomeDto> findAllByDate(Date date);

    public TreeNode createIncomesAndOutcomes(String selectedDate, String selectedCity);

}
