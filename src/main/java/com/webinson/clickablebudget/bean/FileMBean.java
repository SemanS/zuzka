package com.webinson.clickablebudget.bean;

import java.io.*;
import java.util.Collection;
import java.util.Scanner;
import java.util.stream.Collectors;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.webinson.clickablebudget.dto.IncomeAndOutcomeDto;
import com.webinson.clickablebudget.service.IncomeAndOutcomeService;
import com.webinson.clickablebudget.utils.Converter;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

@Component
@Scope("request")
public class FileMBean implements Serializable {

    @Autowired
    Converter converter;

    @Autowired
    IncomeAndOutcomeService incomeAndOutcomeService;

    @Getter
    @Setter
    private UploadedFile uploadedFile;

    @Getter
    @Setter
    private Part file;


    public static Collection<Part> getAllParts(Part part) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return request.getParts().stream().filter(p -> part.getName().equals(p.getName())).collect(Collectors.toList());
    }

    public void upload() throws ParserConfigurationException, SAXException, IOException, ServletException {

        for (Part part : getAllParts(file)) {

            try {
                parseIncomeAndOutcome();

            } catch (IOException e) {
                // Error handling
            }
        }
    }

    public InputStream simpleTransform(InputStream inputStream, String xsltPath) throws UnsupportedEncodingException {
        System.out.println("Marshaling performed");
        TransformerFactory tFactory = TransformerFactory.newInstance("net.sf.saxon.TransformerFactoryImpl", null);

        StringWriter xmlAsWriter = new StringWriter();
        StreamResult result = new StreamResult(xmlAsWriter);
        try {
            Transformer transformer =
                    tFactory.newTransformer(new StreamSource(new File(xsltPath)));

            transformer.transform(new StreamSource(inputStream),
                    result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ByteArrayInputStream in = new ByteArrayInputStream(xmlAsWriter.toString().getBytes("UTF-8"));
        return in;
    }

    public void parseIncomeAndOutcome() throws ParserConfigurationException, IOException, SAXException {
        IncomeAndOutcomeDto incomeAndOutcomes = new IncomeAndOutcomeDto();
        System.out.println("Marshaling performed");
        //Perform UnMarshaling
        try {
            incomeAndOutcomes = (IncomeAndOutcomeDto) converter.doUnMarshaling(simpleTransform(file.getInputStream(),
                    "jdm.xsl"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        incomeAndOutcomeService.saveIncomes(incomeAndOutcomes);
    }
}
