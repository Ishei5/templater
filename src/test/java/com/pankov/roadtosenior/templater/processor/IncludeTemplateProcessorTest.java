package com.pankov.roadtosenior.templater.processor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IncludeTemplateProcessorTest {
    IncludeTemplateProcessor includeTemplateProcessor = new IncludeTemplateProcessor();

    String content = "<#include \"base.ftl\"/>\n" +
            "<#macro content>\n" +
            "<table></table>\n" +
            "</#macro>\n" +
            "<@base/>";

    @Test
    @DisplayName("Test include template")
    public void testHandleInclude() {
        String actualContent = includeTemplateProcessor.handleInclude(content);
        String expectedContent = "<#macro base>\n" +
                "Content from include\n" +
                "<@content/>\n" +
                "</#macro>\n" +
                "<#macro content>\n" +
                "<table></table>\n" +
                "</#macro>\n" +
                "<@base/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    @DisplayName("Test handle macro")
    public void testHandleMacro() {
        String content = "<#macro base>\n" +
                "Content from include\n" +
                "</#macro>\n" +
                "<@base/>";

        String actualContent = includeTemplateProcessor.handleMacro(content);

        String expectedContent = "Content from include";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    @DisplayName("Test handle multiple macro")
    public void testHandleMultipleMacro() {
        String content = "<#macro base>\n" +
                "Content from include\n" +
                "<@content/>\n" +
                "</#macro>\n" +
                "<#macro content>\n" +
                "<table></table>\n" +
                "</#macro>\n" +
                "<@base/>";

        String actualContent = includeTemplateProcessor.handleMacro(content);

        String expectedContent = "Content from include\n\n" +
                "<table></table>";

        assertEquals(expectedContent, actualContent);
    }

}