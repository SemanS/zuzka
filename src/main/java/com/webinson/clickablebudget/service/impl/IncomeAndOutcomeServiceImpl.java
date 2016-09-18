package com.webinson.clickablebudget.service.impl;

import com.querydsl.jpa.impl.JPAQuery;
import com.webinson.clickablebudget.assembler.VykazRadekIncomeAssembler;
import com.webinson.clickablebudget.dao.CityDao;
import com.webinson.clickablebudget.dao.IncomeDao;
import com.webinson.clickablebudget.dao.IncomeDecreeCzechDao;
import com.webinson.clickablebudget.dao.OutcomeDao;
import com.webinson.clickablebudget.dto.IncomeAndOutcomeDto;
import com.webinson.clickablebudget.dto.VykazRadekDto;
import com.webinson.clickablebudget.entity.City;
import com.webinson.clickablebudget.entity.Income;
import com.webinson.clickablebudget.entity.Outcome;
import com.webinson.clickablebudget.entity.QIncome;
import com.webinson.clickablebudget.service.CityService;
import com.webinson.clickablebudget.service.IncomeAndOutcomeService;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Slavo on 13.09.2016.
 */
@Scope("session")
@Service
public class IncomeAndOutcomeServiceImpl implements IncomeAndOutcomeService {

    @Autowired
    IncomeDao incomeDao;

    @Autowired
    VykazRadekIncomeAssembler vykazRadekIncomeAssembler;

    @Autowired
    CityDao cityDao;

    @Autowired
    IncomeDecreeCzechDao decreeCzechDao;

    @Autowired
    OutcomeDao outcomeDao;

    @Autowired
    CityService cityService;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    VykazRadekIncomeAssembler incomeAssembler;

    @Override
    public void saveIncomes(IncomeAndOutcomeDto incomeAndOutcomes) {

        List<Income> incomes = new ArrayList<Income>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (VykazRadekDto inc : incomeAndOutcomes.getIncomes()) {
            Income income = new Income();
            income.setAdjustedbudget(inc.getAdjustedBudget());
            income.setApprovedBudget(inc.getApprovedBudget());
            income.setSpentBudget(inc.getSpentBudget());
            income.setCity(cityDao.findIdByIco(incomeAndOutcomes.getVykazHlavicka().getSubjektIco()));
            income.setPolozka(inc.getPolozka());
            income.setDate(java.sql.Date.valueOf(LocalDate.parse(incomeAndOutcomes.getVykazHlavicka().getDatumVykaz(), formatter)));
            incomes.add(income);
            incomeDao.save(incomes);
        }
    }

    @Override
    public void saveOutcomes(IncomeAndOutcomeDto incomeAndOutcomes) {

        List<Outcome> outcomes = new ArrayList<Outcome>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (VykazRadekDto inc : incomeAndOutcomes.getOutcomes()) {
            Outcome outcome = new Outcome();
            outcome.setAdjustedbudget(inc.getAdjustedBudget());
            outcome.setApprovedBudget(inc.getApprovedBudget());
            outcome.setSpentBudget(inc.getSpentBudget());
            outcome.setCity(cityDao.findIdByIco(incomeAndOutcomes.getVykazHlavicka().getSubjektIco()));
            outcome.setParagraf(inc.getParagraf());
            outcome.setDate(java.sql.Date.valueOf(LocalDate.parse(incomeAndOutcomes.getVykazHlavicka().getDatumVykaz(), formatter)));
            outcomes.add(outcome);
            outcomeDao.save(outcomes);
        }
    }

    @Override
    public List<Date> findAllDatesByCity(City city) {
        final JPAQuery<Income> query = new JPAQuery<>(entityManager);
        QIncome income = QIncome.income;
        List<Date> dates = query.from(income).select(income.date).distinct().fetch();
        return dates;
    }


    @Override
    public List<IncomeAndOutcomeDto> findAllByDate(Date date) {
        final JPAQuery<Income> query = new JPAQuery<>(entityManager);
        QIncome income = QIncome.income;
        query.from(income).select(income).where(income.date.eq(date));
        return null;
    }

    public TreeNode createIncomesAndOutcomes(String selectedDate, String selectedcity) {

        TreeNode rootNode = new DefaultTreeNode(new VykazRadekDto("name", 1.0, 2.0, 3.0), null);

        List<VykazRadekDto> vykazRadekRootNodeList = getVykazRadekRoot(selectedDate, selectedcity);

        for (VykazRadekDto vykRad : vykazRadekRootNodeList) {
            TreeNode node = new DefaultTreeNode(vykRad, rootNode);
            createSubIncomesAndOutcomes(vykRad, node);
        }
        return rootNode;
    }


    private List<VykazRadekDto> createSubIncomesAndOutcomes(VykazRadekDto vykaz, TreeNode node) {
        List<VykazRadekDto> vykazList = createSubVykazRadek(vykaz);
        try {
            for (VykazRadekDto vyk : vykazList) {
                TreeNode subNode = new DefaultTreeNode(vyk, node);
                createSubIncomesAndOutcomes(vyk, subNode);
            }
            return vykazList;
        } finally {
            return vykazList;
        }
    }

    private List<VykazRadekDto> createSubVykazRadek(VykazRadekDto vykaz) {

        List<VykazRadekDto> vykazRadokNodeList = new ArrayList<>();

        if (vykaz.getChildren() != null) {
            for (VykazRadekDto vyk : vykaz.getChildren()) {
                vykazRadokNodeList.add(vyk);
            }
        }
        return vykazRadokNodeList;
    }


    private List<VykazRadekDto> getVykazRadekRoot(String selectedDate, String selectedCity) {

        List<VykazRadekDto> root = new ArrayList<VykazRadekDto>();

        for (String i : incomeDao.findDistinctVykazy(selectedCity, selectedDate)) {

            HashMap<String, VykazRadekDto> mapper = new HashMap();

            List<VykazRadekDto> vykazy = vykazRadekIncomeAssembler.toDtos(incomeDao.findIncomeByPolozkaString2(1, String.valueOf(i)));

            for (VykazRadekDto vyk : vykazy) {
                mapper.put(vyk.getPolozka().substring(0, 1), vykazRadekIncomeAssembler.dtosToDto(incomeDao.findIncomeByPolozkaString2(1, String.valueOf(vyk.getPolozka().substring(0, 1)))));
                mapper.put(vyk.getPolozka().substring(0, 2), vykazRadekIncomeAssembler.dtosToDto(incomeDao.findIncomeByPolozkaString2(2, String.valueOf(vyk.getPolozka().substring(0, 2)))));
                mapper.put(vyk.getPolozka().substring(0, 3), vykazRadekIncomeAssembler.dtosToDto(incomeDao.findIncomeByPolozkaString2(3, String.valueOf(vyk.getPolozka().substring(0, 3)))));
                mapper.get(vyk.getPolozka().substring(0, 3)).setChildren(vykazRadekIncomeAssembler.toDtos(incomeDao.findIncomeByPolozkaString2(3, String.valueOf(vyk.getPolozka().substring(0, 3)))));
                mapper.put(vyk.getPolozka().substring(0, 4), vykazRadekIncomeAssembler.dtosToDto(incomeDao.findIncomeByPolozkaString2(4, String.valueOf(vyk.getPolozka().substring(0, 4)))));
                try {
                    mapper.get(vyk.getPolozka().substring(0, 4)).setName(decreeCzechDao.findIncomeDecreeCzechByKlass(vyk.getPolozka()).getName());
                } catch (RuntimeException e) {
                    System.out.println("somarina");
                }

            }

            List<VykazRadekDto> vyks2 = new ArrayList<VykazRadekDto>();
            List<VykazRadekDto> vyks3 = new ArrayList<VykazRadekDto>();
            VykazRadekDto vyk2 = new VykazRadekDto();
            VykazRadekDto vyk3 = new VykazRadekDto();
            String d = "000";

            for (String s : mapper.keySet()) {
                try {
                    mapper.get(s).setName(decreeCzechDao.findIncomeDecreeCzechByKlass(s).getName());
                } catch (RuntimeException e) {
                    System.out.println("somarina");
                }

                if (s.startsWith(i)) {

                    if (s.length() == 2) {
                        vyk2 = mapper.get(s);
                        vyk2.setPolozka(s);
                        try {
                            vyk2.setName(decreeCzechDao.findIncomeDecreeCzechByKlass(s).getName());
                        } catch (RuntimeException e) {
                            System.out.println("somarina");
                        }
                        vyks2.add(vyk2);
                        vyk2 = new VykazRadekDto();
                        mapper.get(s.substring(0, 1)).setChildren(vyks2);
                    }

                    if (s.length() == 3) {

                        if (!s.substring(0, 2).equals(d.substring(0, 2))) {
                            vyks3 = new ArrayList<VykazRadekDto>();
                        }
                        vyk3 = mapper.get(s);
                        vyk3.setPolozka(s);
                        try {
                            vyk3.setName(decreeCzechDao.findIncomeDecreeCzechByKlass(s).getName());
                        } catch (RuntimeException e) {
                            System.out.println("somarina");
                        }
                        vyks3.add(vyk3);
                        mapper.get(s.substring(0, 2)).setChildren(vyks3);
                        vyk3 = new VykazRadekDto();
                        d = s;
                    }

                }
            }
            root.add(mapper.get(i));
        }
        return root;
    }

}