package com.pankov.roadtosenior.templater.processor;

import lombok.SneakyThrows;
import org.apache.commons.lang3.StringEscapeUtils;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InjectValueTemplateProcessor implements TemplateProcessor {

    @SneakyThrows
    @Override
    public String process(String content, Map<String, Object> attribute) {
        String modifiedContent = content;
        Pattern pattern = Pattern.compile("\\$\\{([^}]+)}");
        Matcher matcher = pattern.matcher(modifiedContent);

        while (matcher.find()) {
            String matchedValue = matcher.group(1);
            if (matchedValue.contains(".")) {
                String valueName = matchedValue.split("\\.")[0];
                String valueField = matchedValue.split("\\.")[1];
                Object value = attribute.get(valueName);

                if (value == null) {
                    throw new IllegalArgumentException(String.format("Value \"%s\" not found in passed attributes", valueName));
                }

                modifiedContent = matcher.replaceFirst(
                        StringEscapeUtils.escapeHtml4(
                                generateGetter(valueField, value.getClass())
                                        .invoke(value)
                                        .toString()));
            } else {
                modifiedContent = matcher.replaceFirst(
                        StringEscapeUtils.escapeHtml4(
                                attribute.get(matchedValue).toString()));
            }
            matcher = pattern.matcher(modifiedContent);
        }

        return modifiedContent;
    }

    Method generateGetter(String objectField, Class<?> clazz) {
        String methodName = "get" + objectField.substring(0, 1).toUpperCase() + objectField.substring(1);
        Method getter = null;
        try {
            getter = clazz.getMethod(methodName);
        } catch (NoSuchMethodException exception) {
            throw new RuntimeException(String.format(
                    "There is no public getter for field: %s in the class: %s", objectField, clazz),
                    exception);
        }
        return getter;
    }
}
