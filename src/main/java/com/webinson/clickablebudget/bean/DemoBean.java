package com.webinson.clickablebudget.bean;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.servlet.http.Part;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Slavo on 23.09.2016.
 */

@Component
@Scope("session")
public class DemoBean {

    @Getter
    @Setter
    private Part file1;

    @Getter
    @Setter
    private Part file2;

    // getters and setters for file1 and file2

    public String upload() throws IOException {
        InputStream inputStream = file1.getInputStream();
        FileOutputStream outputStream = new FileOutputStream(getFilename(file1));

        byte[] buffer = new byte[4096];
        int bytesRead = 0;
        while(true) {
            bytesRead = inputStream.read(buffer);
            if(bytesRead > 0) {
                outputStream.write(buffer, 0, bytesRead);
            }else {
                break;
            }
        }
        outputStream.close();
        inputStream.close();

        return "success";
    }

    private static String getFilename(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
            }
        }
        return null;
    }
}
