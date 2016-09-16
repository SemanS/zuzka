package com.webinson.clickablebudget;

import com.webinson.clickablebudget.dao.IncomeDecreeCzechDao;
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

/**
 * Created by Slavo on 14.09.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ApplicationConfig.class)
@Repository
public class IncomeDecreeTest {

    @Autowired
    IncomeDecreeCzechDao incDao;

    @Test
    public void test() throws TransformerException {
/*        TransformerFactory factory = TransformerFactory.newInstance();
        Source xslt = new StreamSource(new File("transform.xslt"));
        Transformer transformer = factory.newTransformer(xslt);

        Source text = new StreamSource(new File("input.xml"));
        transformer.transform(text, new StreamResult(new File("output.xml")));

        System.out.println();*/

    }

}
