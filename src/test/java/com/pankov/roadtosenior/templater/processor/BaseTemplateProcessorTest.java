package com.pankov.roadtosenior.templater.processor;

import com.pankov.roadtosenior.templater.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static java.util.List.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

class BaseTemplateProcessorTest {
    BaseTemplateProcessor templateProcessor = new BaseTemplateProcessor();

    String content =
            "<#include \"templ/base.ftl\"/>\n" +
                    "<#macro content>\n" +
                    "<h1>${title}</h1>\n" +
                    "<#if drinks?has_content>" +
                    "<#list drinks as drink>\n" +
                    "<div>\n" +
                    "<p>${drink.id}</p>\n" +
                    "<p>${drink.name}</p>\n" +
                    "</div>\n" +
                    "</#list>\n" +
                    "</#if>" +
                    "</#macro>\n" +
                    "<@base/>";

    Map<String, Object> attribute = Map.of(
            "drinks", of(
                    new Product(1L, "Milk", 37.0),
                    new Product(2L, "Water", 17.0)),
            "title", "Test BaseTemplateProcessor");

    @Test
    @DisplayName("Test process")
    public void testProcess() {
        String actualContent = templateProcessor.process(content, attribute);
        String expectedContent = "Content from include\n" +
                "\n<h1>Test BaseTemplateProcessor</h1>\n" +
                "\n<div>\n" +
                "<p>1</p>\n" +
                "<p>Milk</p>\n" +
                "</div>\n" +
                "\n<div>\n" +
                "<p>2</p>\n" +
                "<p>Water</p>\n" +
                "</div>";

        assertEquals(expectedContent, actualContent);
    }

}