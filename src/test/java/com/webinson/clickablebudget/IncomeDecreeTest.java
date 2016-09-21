package com.webinson.clickablebudget;

import com.webinson.clickablebudget.assembler.VykazRadekIncomeAssembler;
import com.webinson.clickablebudget.dao.IncomeDao;
import com.webinson.clickablebudget.dao.IncomeDecreeCzechDao;
import com.webinson.clickablebudget.dto.VykazRadekDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    public void test2() {

        for (String i : incomeDao.findDistinctVykazy("Nelahozeves", "08")) {

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

}
