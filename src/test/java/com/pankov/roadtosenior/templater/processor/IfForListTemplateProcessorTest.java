package com.pankov.roadtosenior.templater.processor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class IfForListTemplateProcessorTest {
    IfForListTemplateProcessor ifForListTemplateProcessor = new IfForListTemplateProcessor();

    List<Integer> list = List.of(1, 2);
    String content = "Header\n" +
            "<#if list?has_content>\n" +
            "Text inside condition\n" +
            "${test}\n" +
            "</#if>\n" +
            "Footer";
    Map<String, Object> attribute = Map.of("list", list);

    @Test
    @DisplayName("Test check conditions")
    public void testCheckCondition() {
        assertTrue(ifForListTemplateProcessor.checkCondition("list?has_content", attribute));
    }

    @Test
    @DisplayName("Test process when condition is true")
    public void testProcess() {
        String actualContent = ifForListTemplateProcessor.process(content, attribute);

        String expectedContent = "Header\n\n" +
                "Text inside condition\n" +
                "${test}\n\n" +
                "Footer";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    @DisplayName("Test process when condition is false due to empty list")
    public void testProcessWhenFalseAndListEmpty() {
        String actualContent = ifForListTemplateProcessor.process(content, Map.of("list", Collections.emptyList()));

        String expectedContent = "Header\n\n" +
                "Footer";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    @DisplayName("Test process when condition is false due to to absent required parameter")
    public void testProcessWhenFalseAnd() {
        String actualContent = ifForListTemplateProcessor.process(content, Map.of("lists", list));

        String expectedContent = "Header\n\n" +
                "Footer";

        assertEquals(expectedContent, actualContent);
    }
}