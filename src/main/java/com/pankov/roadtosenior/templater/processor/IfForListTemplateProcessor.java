package com.pankov.roadtosenior.templater.processor;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IfForListTemplateProcessor implements TemplateProcessor {

    @Override
    public String process(String content, Map<String, Object> attribute) {
        String modifiedContent = content;
        Pattern pattern = Pattern.compile("<#if([^>]+)>(.*?)</#if>", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(modifiedContent);
        while (matcher.find()) {
            if (checkCondition(matcher.group(1), attribute)) {
                String s = matcher.group(2);
                modifiedContent = matcher.replaceFirst("$2");
                matcher = pattern.matcher(modifiedContent);
            } else {
                modifiedContent = matcher.replaceFirst("");
            }
        }

        return modifiedContent;
    }

    boolean checkCondition(String line, Map<String, Object> attributes) {
        String[] parameters = line.split("\\?");
        List<?> attribute = (List<?>) attributes.get(parameters[0].trim());
        String function = parameters[1].trim();

        if (attribute == null) {
            return false;
        }

        if ("has_content".equals(function)) {
            return !attribute.isEmpty();
        }

        return false;
    }
}
