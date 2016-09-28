package com.webinson.clickablebudget;

import com.querydsl.jpa.impl.JPAQuery;
import com.webinson.clickablebudget.assembler.VykazRadekIncomeAssembler;
import com.webinson.clickablebudget.dao.IncomeDao;
import com.webinson.clickablebudget.dao.IncomeDecreeCzechDao;
import com.webinson.clickablebudget.dto.VykazRadekDto;
import com.webinson.clickablebudget.entity.Income;
import com.webinson.clickablebudget.entity.QIncome;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

/**
 * Created by Slavo on 14.09.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ApplicationConfig.class)
@Repository
public class IncomeDecreeTest {

    @Autowired
    IncomeDecreeCzechDao decreeCzechDao;

    @Autowired
    IncomeDao incomeDao;

    @Autowired
    VykazRadekIncomeAssembler vykazRadekIncomeAssembler;

    @PersistenceContext
    EntityManager entityManager;

    @Test
    public void test() throws TransformerException {
/*        TransformerFactory factory = TransformerFactory.newInstance();
        Source xslt = new StreamSource(new File("transform.xslt"));
        Transformer transformer = factory.newTransformer(xslt);

        Source text = new StreamSource(new File("input.xml"));
        transformer.transform(text, new StreamResult(new File("output.xml")));

        System.out.println();*/

    }

    @Test
    public void sad() {
        String s = "/Nelahozeves";
        System.out.println(s.substring(1));
    }

    @Test
    public void test2() {



            /*HashMap<String, VykazRadekDto> mapper = new HashMap();

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


            }*/

        //System.out.println(mapper.get("1").getPolozka());
    }

    @Test
    public void testoss() {
        final JPAQuery<Income> query = new JPAQuery<>(entityManager);
        QIncome income = QIncome.income;
        Date date = query.from(income).select(income.date.max()).fetchFirst();
        System.out.println(date);
    }


    @Test
    public void testik() {
        VykazRadekDto vyk1 = new VykazRadekDto();
        vyk1.setPolozka("1111");
        vyk1.setApprovedBudget(1.0);

        VykazRadekDto vyk2 = new VykazRadekDto();
        vyk2.setPolozka("1111");
        vyk2.setApprovedBudget(4.0);

        List<VykazRadekDto> incomes = new ArrayList<VykazRadekDto>();
        incomes.add(vyk1);
        incomes.add(vyk2);


        for (VykazRadekDto inc : incomes) {

            if (inc.getPolozka() == inc.getPolozka()) {
                inc.setApprovedBudget(inc.getApprovedBudget() + inc.getApprovedBudget());
                System.out.println(inc.getApprovedBudget());
            }

        }

       /* HashMap<String, Integer> map = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            String text = list.get(i).getPolozka();
            if (map.get(text) == null) {
                map.put(text, 1);
            } else {
                map.put(text, map.get(text) + 1);
            }
        }

        for (String text : map.keySet()) {
            System.out.println(text + " " + map.get(text));
        }*/
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    @Test
    public void daco() {

        VykazRadekDto vykaz1 = new VykazRadekDto();
        VykazRadekDto vykaz2 = new VykazRadekDto();
        VykazRadekDto vykaz3 = new VykazRadekDto();
        vykaz1.setPolozka("1111");
        vykaz2.setPolozka("1111");
        vykaz3.setPolozka("1112");

        List<VykazRadekDto> vykazy = new ArrayList<VykazRadekDto>();

        vykazy.add(vykaz1);
        vykazy.add(vykaz2);
        vykazy.add(vykaz3);


        //vykazy.stream().filter(distinctByKey(p -> p.getPolozka()));

        List<VykazRadekDto> unique = vykazy.stream()
                .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparing(VykazRadekDto::getPolozka))),
                        ArrayList::new));

        System.out.println(unique.get(0).getPolozka());
        System.out.println(unique.get(1).getPolozka());

    }

}
