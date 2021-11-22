package com.pankov.roadtosenior.templater.processor;

import java.util.List;
import java.util.Map;

public class BaseTemplateProcessor implements TemplateProcessor {
    List<TemplateProcessor> templateProcessorList = List.of(
            new IfForListTemplateProcessor(),
            new LoopTemplateProcessor(),
            new InjectValueTemplateProcessor(),
            new IncludeTemplateProcessor()
    );

    @Override
    public String process(String content, Map<String, Object> attribute) {
        String modifiedContent = content;

        for (TemplateProcessor templateProcessor : templateProcessorList) {
            modifiedContent = templateProcessor.process(modifiedContent, attribute);
        }

        return modifiedContent;
    }
}
