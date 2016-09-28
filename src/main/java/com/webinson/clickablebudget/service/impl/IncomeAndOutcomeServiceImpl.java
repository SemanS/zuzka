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
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

/**
 * Created by Slavo on 13.09.2016.
 */
@ApplicationScoped
@Service
public class IncomeAndOutcomeServiceImpl implements IncomeAndOutcomeService {

    @Getter
    @Setter
    HashMap<String, VykazRadekDto> mapper = new HashMap();

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
        }
        List<Income> unique = incomes.stream()
                .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparing(Income::getPolozka))),
                        ArrayList::new));
        incomeDao.save(unique);

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

    public TreeNode createIncomesAndOutcomes(String selectedMonth, String selectedCity, String selectedYear) {

        TreeNode rootNode = new DefaultTreeNode(new VykazRadekDto("name", 1.0, 2.0, 3.0), null);

        List<VykazRadekDto> vykazRadekRootNodeList = getVykazRadekRoot(selectedYear, selectedMonth, selectedCity);

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

    public List<VykazRadekDto> getFiveIncomes(String selectedMonth, String selectedCity, String selectedYear) {

        //vykazy = vykazRadekIncomeAssembler.toDtos(incomeDao.findFiveIncomes(selectedCity, selectedMonth, new PageRequest(0, 5)));
        //System.out.println(vykazy.get(0).getApprovedBudget());
        return vykazRadekIncomeAssembler.toDtos(incomeDao.findFiveIncomes(selectedCity, selectedMonth, selectedYear, new PageRequest(0, 5)));
    }

    @Override
    public Date getLastDateByCity(String city) {
        final JPAQuery<Income> query = new JPAQuery<>(entityManager);
        QIncome income = QIncome.income;
        return query.from(income).select(income.date.max()).where(income.city.name.eq(city)).fetchFirst();
    }

    @Override
    public Date getLastDateByCityAndYear(String city, String year) {

        final JPAQuery<Income> query = new JPAQuery<>(entityManager);
        QIncome income = QIncome.income;
        return query.from(income).select(income.date.max()).where(income.city.name.eq(city).and(income.date.year().eq(Integer.parseInt(year)))).fetchFirst();

    }

    @Override
    public List<String> getAllYears(String city) {
        final JPAQuery<Income> query = new JPAQuery<>(entityManager);
        QIncome income = QIncome.income;
        List<Date> dates = query.from(income).select(income.date).where(income.city.name.eq(city)).fetch();
        List<String> dats = new ArrayList<String>();

        for (Date dat : dates) {
            String datum = new String();
            datum = dat.toString().substring(0, 4);
            dats.add(datum);
        }

        return dats;
    }

    @Override
    public VykazRadekDto getAllPrijmy(String city, String year, String month) {

        VykazRadekDto prijmy = new VykazRadekDto();
        prijmy = vykazRadekIncomeAssembler.dtosToDto(incomeDao.findGeneralIncome(city, month, year));

        //System.out.println(prijmy.getApprovedBudget());

        return prijmy;
    }

    @Override
    public VykazRadekDto createFirstRoots() {

        List<VykazRadekDto> vykazy = new ArrayList<VykazRadekDto>();
        VykazRadekDto vykaz = new VykazRadekDto();
        for (String s : mapper.keySet()) {
            if (s.length() == 1) {
                vykazy.add(mapper.get(s));
            }
        }
        //List<VykazRadekDto> vykazy = vykazRadekIncomeAssembler.toDtos(incomeDao.findDistinctVykazy(selectedCity, selectedMonth));
        vykaz.setChildren(vykazy);
        return vykaz;
    }

    private List<VykazRadekDto> getVykazRadekRoot(String selectedYear, String selectedMonth, String selectedCity) {

        List<VykazRadekDto> root = new ArrayList<VykazRadekDto>();

        for (String i : incomeDao.findDistinctVykazy(selectedCity, selectedMonth, selectedYear)) {


            List<VykazRadekDto> vykazy = vykazRadekIncomeAssembler.toDtos(incomeDao.findIncomeByPolozkaString2(1, String.valueOf(i), selectedCity, selectedMonth, selectedYear));

            for (VykazRadekDto vyk : vykazy) {
                mapper.put(vyk.getPolozka().substring(0, 1), vykazRadekIncomeAssembler.dtosToDto(incomeDao.findIncomeByPolozkaString2(1, String.valueOf(vyk.getPolozka().substring(0, 1)), selectedCity, selectedMonth, selectedYear)));
                mapper.put(vyk.getPolozka().substring(0, 2), vykazRadekIncomeAssembler.dtosToDto(incomeDao.findIncomeByPolozkaString2(2, String.valueOf(vyk.getPolozka().substring(0, 2)), selectedCity, selectedMonth, selectedYear)));
                mapper.put(vyk.getPolozka().substring(0, 3), vykazRadekIncomeAssembler.dtosToDto(incomeDao.findIncomeByPolozkaString2(3, String.valueOf(vyk.getPolozka().substring(0, 3)), selectedCity, selectedMonth, selectedYear)));
                mapper.get(vyk.getPolozka().substring(0, 3)).setChildren(vykazRadekIncomeAssembler.toDtos(incomeDao.findIncomeByPolozkaString2(3, String.valueOf(vyk.getPolozka().substring(0, 3)), selectedCity, selectedMonth, selectedYear)));

                for (VykazRadekDto pomVykaz : mapper.get(vyk.getPolozka().substring(0, 3)).getChildren()) {
                    try {
                        pomVykaz.setName(decreeCzechDao.findIncomeDecreeCzechByKlass(pomVykaz.getPolozka()).getName());
                        mapper.get(vyk.getPolozka().substring(0, 3)).setParent(pomVykaz);
                    } catch (RuntimeException e) {
                        System.out.println("somarina");
                    }
                }
            }


            List<VykazRadekDto> vyks2 = new ArrayList<VykazRadekDto>();
            List<VykazRadekDto> vyks3 = new ArrayList<VykazRadekDto>();
            VykazRadekDto vyk2 = new VykazRadekDto();
            VykazRadekDto vyk3 = new VykazRadekDto();
            String d = "000";

            Map<String, VykazRadekDto> map = new TreeMap<String, VykazRadekDto>(mapper);

            for (String s : map.keySet()) {
                try {
                    mapper.get(s).setName(decreeCzechDao.findIncomeDecreeCzechByKlass(s).getName());
                } catch (RuntimeException e) {
                    System.out.println("somarina");
                }

                if (s.startsWith(i)) {

                    if (s.length() == 2) {
                        vyk2 = mapper.get(s);
                        vyk2.setPolozka(s);
                        vyk2.setParent(mapper.get(s.charAt(0)));
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
