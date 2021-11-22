package com.pankov.roadtosenior.templater;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static java.util.List.of;
import static org.junit.jupiter.api.Assertions.*;

class TemplateITest {

    Map<String, Object> attribute = Map.of(
            "products", of(
                    new Product(1L, "Milk", 37.0),
                    new Product(2L, "Water", 17.0)),
            "title", "Test BaseTemplateProcessor");


    @Test
    @DisplayName("Integration test for load template")
    public void test() {
        Configuration configuration = Configuration.getConfiguration();
        configuration.setPathToDirectory("templ");
        Template template = new Template();
        String expectedContent = "Content from include\n" +
                "\n" +
                "<h1>Test BaseTemplateProcessor</h1>\n" +
                "\n" +
                "\n" +
                "<div>\n" +
                "<p>1</p>\n" +
                "<p>Milk</p>\n" +
                "</div>\n" +
                "\n" +
                "<div>\n" +
                "<p>2</p>\n" +
                "<p>Water</p>\n" +
                "</div>";

        String actualContent = template.process("test.ftl", attribute);
        assertNotNull(actualContent);
        System.out.println(actualContent);
//        assertEquals(expectedContent, actualContent);
    }

    @Test
    @DisplayName("Integration test for load template when processors is not needed")
    public void testWhenProcessorsIsNotNeeded() {
        Template template = new Template();
        String actualContent = template.process("templateWithoutProcessing.ftl", attribute);
        String expectedContent = "Nothing should be done";

        assertEquals(expectedContent, actualContent);
    }

}