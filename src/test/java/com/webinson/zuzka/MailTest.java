package com.webinson.zuzka;

import com.google.common.collect.Lists;
import it.ozimov.springboot.templating.mail.model.Email;
import it.ozimov.springboot.templating.mail.model.impl.EmailImpl;
import it.ozimov.springboot.templating.mail.service.EmailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.mail.internet.InternetAddress;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * Created by Slavo on 11/11/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ApplicationConfig.class)
@Repository
public class MailTest {

    @Autowired
    public EmailService emailService;

    @Test
    public void sendEmailWithoutTemplating() throws UnsupportedEncodingException {
       /* final Email email = EmailImpl.builder()
                .from(new InternetAddress("ceo@webinson.com", "Marco Tullio Cicerone "))
                .to(Lists.newArrayList(new InternetAddress("slavosmn@gmail.com", "asd")))
                .subject("Laelius de amicitia")
                .body("Firmamentum autem stabilitatis constantiaeque eius, quam in amicitia quaerimus, fides est.")
                .encoding(Charset.forName("UTF-8")).build();

        emailService.send(email);*/
    }

}
