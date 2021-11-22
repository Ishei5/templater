package com.pankov.roadtosenior.templater.processor;

import com.pankov.roadtosenior.templater.reader.ClasspathTemplateReader;
import com.pankov.roadtosenior.templater.reader.TemplateReader;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IncludeTemplateProcessor implements TemplateProcessor {

    @Override
    public String process(String content, Map<String, Object> attribute) {
        String modifiedContent = handleInclude(content);

        return handleMacro(modifiedContent);
    }

    String handleInclude(String content) {
        String modifiedContent = content;
        Matcher matcher = Pattern.compile("<#include([^>]+)/>").matcher(content);
        while (matcher.find()) {
            String match = matcher.group(1).trim();
            String fileName = match.substring(1, match.length() - 1);
            modifiedContent = matcher.replaceFirst(loadBaseTemplate(fileName));
        }

        return modifiedContent;
    }

    String loadBaseTemplate(String fileName) {
        TemplateReader templateReader = new ClasspathTemplateReader();

        return templateReader.readTemplate(fileName);
    }


    String handleMacro(String content) {
        String modifiedContent = content;
        Pattern pattern = Pattern.compile("<#macro([^>]+)>(.*?)</#macro>", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(modifiedContent);
        while (matcher.find()) {
            String macroName = matcher.group(1).trim();
            String macroContent = matcher.group(2);
            modifiedContent = matcher.replaceFirst("");
            modifiedContent = modifiedContent.replace("<@" + macroName + "/>", macroContent);
            matcher = pattern.matcher(modifiedContent);
        }

        return modifiedContent.trim();
    }
}
