package com.webinson.clickablebudget.service;

import com.webinson.clickablebudget.dto.IncomeAndOutcomeDto;
import com.webinson.clickablebudget.dto.VykazRadekDto;
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

    public TreeNode createIncomes(String selectedYear, String selectedMonth, String selectedCity);

    public VykazRadekDto createFirstRootsIncome();

    public VykazRadekDto createFirstRootsOutcome();

    public List<VykazRadekDto> getFiveIncomes(String selectedMonth, String selectedCity, String selectedYear);

    public List<VykazRadekDto> getFiveOutcomes(String selectedMonth, String selectedCity, String selectedYear);

    public Date getLastDateByCity(String city);

    public Date getLastDateByCityAndYear(String city, String Year);

    public List<String> getAllYears(String city);

    public VykazRadekDto getAllPrijmy(String city, String year, String month);

    public VykazRadekDto getAllVydaje(String city, String year, String month);

    public VykazRadekDto getAllPrijmyForBar(String city, String year, String month);

    public VykazRadekDto getAllVydajeForBar(String city, String year, String month);

    TreeNode createOutcomes(String substring, String selectedCity, String selectedYear);
}
