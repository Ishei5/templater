package com.pankov.roadtosenior.templater.reader;

import com.pankov.roadtosenior.templater.Configuration;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ClasspathTemplateReader implements TemplateReader {

    private String directoryPath;

    public ClasspathTemplateReader() {
        this.directoryPath = Configuration.getConfiguration().getPathToDirectory();
    }

    @Override
    public String readTemplate(String fileName)  {
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStream inputStream = this.getClass().getClassLoader()
                .getResourceAsStream(String.valueOf(new File(directoryPath, fileName)));
             BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream)) {
            int inByte;
            while((inByte = bufferedInputStream.read()) != -1) {
                stringBuilder.append((char) inByte);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return stringBuilder.toString();
    }
}