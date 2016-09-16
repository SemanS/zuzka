package com.webinson.clickablebudget.view;

import java.io.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.webinson.clickablebudget.dto.Company;
import com.webinson.clickablebudget.dto.IncomeAndOutcomeDto;
import com.webinson.clickablebudget.service.IncomeAndOutcomeService;
import com.webinson.clickablebudget.utils.Converter;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
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

    private StreamedContent downloadFile;

    public StreamedContent getDownloadFile() {
        return downloadFile;
    }

    public void upload(FileUploadEvent e) throws IOException, ParserConfigurationException, SAXException {
        this.uploadedFile = e.getFile();
        if (uploadedFile != null) {
            downloadFile = new DefaultStreamedContent(uploadedFile.getInputstream()
                    , uploadedFile.getContentType(), uploadedFile.getFileName());
        }
        parseIncomeAndOutcome();
    }

    public InputStream simpleTransform(InputStream inputStream, String xsltPath) throws UnsupportedEncodingException {

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
            incomeAndOutcomes = (IncomeAndOutcomeDto) converter.doUnMarshaling(simpleTransform(uploadedFile.getInputstream(),
                    "jdm.xsl"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(incomeAndOutcomes.getVykazHlavicka().getSubjektIco());

        //incomeAndOutcomeService.saveIncomes(incomeAndOutcomes);
    }
}
