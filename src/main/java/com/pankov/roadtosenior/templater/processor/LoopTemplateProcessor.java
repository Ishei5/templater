package com.pankov.roadtosenior.templater.processor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoopTemplateProcessor implements TemplateProcessor {

    @Override
    public String process(String content, Map<String, Object> attribute) {
        String modifiedContent = content;
        Pattern pattern = Pattern.compile("<#list(.*?)</#list>", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(modifiedContent);

        while (matcher.find()) {
            Loop loop = splitLoop(matcher.group());
            modifiedContent = matcher.replaceFirst(generateLoop(loop, attribute));
            matcher = pattern.matcher(modifiedContent);
        }

        return modifiedContent;
    }

    Loop splitLoop(String loopWithContent) {
        String[] loopAttribute = StringUtils.substringBetween(
                loopWithContent, "<#list", ">").split("\\s+");
        String loopContent = StringUtils.substringBetween(loopWithContent, ">", "</#list>");

        return new Loop(loopAttribute[1], loopAttribute[3], loopContent);
    }

    @SneakyThrows
    String generateLoop(Loop loop, Map<String, Object> attributes) {
        List<?> list = (List<?>) attributes.get(loop.getCollection());
        String content = loop.getLoopContent();
        StringBuilder stringBuilder = new StringBuilder();
        TemplateProcessor injectValueTemplateProcessor = new InjectValueTemplateProcessor();
        for (Object listVariableName : list) {
            stringBuilder.append(injectValueTemplateProcessor
                    .process(content, Map.of(loop.getLoopVariableName(), listVariableName)));
        }
        return stringBuilder.toString();
    }

    @ToString
    @Data
    @AllArgsConstructor
    static class Loop {
        private String collection;
        private String loopVariableName;
        private String loopContent;
    }
}
