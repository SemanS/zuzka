package com.webinson.zuzka.bean;

import lombok.Getter;
import lombok.Setter;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;

import org.apache.commons.io.FilenameUtils;

/**
 * Created by Slavo on 11/17/2016.
 */
@Component
@ViewScoped
public class EditorController implements Serializable {

    @Getter
    @Setter
    private String text;

    @Getter
    @Setter
    private Part file;

    public void uploadListener() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

        String fileName = FilenameUtils.getName(file.getName());
        String fileNamePrefix = FilenameUtils.getBaseName(fileName) + "_";
        String fileNameSuffix = "." + "jpg";
        // String fileNameSuffix = "." + FilenameUtils.getExtension(fileName);

        File uploadFolder = new File("/var/project/images");

        try {
            File result = File.createTempFile(fileNamePrefix, fileNameSuffix, uploadFolder);

            FileOutputStream fileOutputStream = new FileOutputStream(result);
            byte[] buffer = new byte[1024];
            int bulk;

            InputStream inputStream = file.getInputStream();
            while (true) {
                bulk = inputStream.read(buffer);
                if (bulk < 0) {
                    break;
                }

                fileOutputStream.write(buffer, 0, bulk);
                fileOutputStream.flush();
            }

            fileOutputStream.close();
            inputStream.close();

            String value = FacesContext.getCurrentInstance().
                    getExternalContext().getRequestParameterMap().get("editor_input");
            setText(value + "<img src=\"/images/" + result.getName() + "\" />");

            RequestContext.getCurrentInstance().update("editor_input");

            FacesMessage msg = new FacesMessage("Succesful", file.getName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, msg);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            FacesMessage error = new FacesMessage("The files were not uploaded!");
            FacesContext.getCurrentInstance().addMessage(null, error);
        }

    }
}
