package com.pankov.roadtosenior.templater.processor;

import java.util.Map;

public interface TemplateProcessor {

    String process(String content, Map<String, Object> attribute);
}
