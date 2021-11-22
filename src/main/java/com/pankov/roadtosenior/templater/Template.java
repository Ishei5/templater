package com.pankov.roadtosenior.templater;

import com.pankov.roadtosenior.templater.processor.BaseTemplateProcessor;
import com.pankov.roadtosenior.templater.processor.TemplateProcessor;
import com.pankov.roadtosenior.templater.reader.ClasspathTemplateReader;
import com.pankov.roadtosenior.templater.reader.TemplateReader;

import java.util.Map;

public class Template {

    private TemplateReader templateReader;
    private TemplateProcessor templateProcessor;

    public Template() {
        this.templateProcessor = new BaseTemplateProcessor();
        this.templateReader = new ClasspathTemplateReader();
    }

    public String process(String fileName, Map<String, Object> attribute) {
        return templateProcessor.process(templateReader.readTemplate(fileName), attribute);
    }
}
