package com.pankov.roadtosenior.templater.processor;

import com.pankov.roadtosenior.templater.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static java.util.List.*;
import static org.junit.jupiter.api.Assertions.*;

class LoopTemplateProcessorTest {

    LoopTemplateProcessor templateProcessor = new LoopTemplateProcessor();
    String loopWithContent = "<#list   products as product>" +
            "<tr><td>${product}</td></tr>" +
            "</#list>";

    Map<String, Object> attribute = Map.of("products", of("Milk", "Water"));
    Map<String, Object> attributeWithProducts = Map.of(
            "products", of(
                    new Product(1L, "Milk", 27.0),
                    new Product(2L, "Water", 15.5),
                    new Product(3L, "Juice", 37.0)
            ));

    @Test
    @DisplayName("Test split loop")
    public void testSplitLoop() {
        LoopTemplateProcessor.Loop loop = templateProcessor.splitLoop(loopWithContent);

        assertEquals("products", loop.getCollection());
        assertEquals("product", loop.getLoopVariableName());
        assertEquals("<tr><td>${product}</td></tr>", loop.getLoopContent());
    }

    @Test
    @DisplayName("Test generate loop")
    public void testLoopGenerate() {
        LoopTemplateProcessor.Loop loop = new LoopTemplateProcessor.Loop(
                "products", "product", "<tr><td>${product}</td></tr>\n");

        String actualContent = templateProcessor.generateLoop(loop, attribute);
        String expectedContent = "<tr><td>Milk</td></tr>\n<tr><td>Water</td></tr>\n";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    @DisplayName("Test generate loop using object with invoke getters")
    public void testLoopGenerateWithInvokeGetters() {
        LoopTemplateProcessor.Loop loop = new LoopTemplateProcessor.Loop(
                "products", "product",
                "<tr><td>${product.name}</td></tr>" +
                        "<tr><td>${product.price}</td></tr>\n");
        String actualContent = templateProcessor.generateLoop(loop, attributeWithProducts);
        String expectedContent = "<tr><td>Milk</td></tr><tr><td>27.0</td></tr>\n" +
                "<tr><td>Water</td></tr><tr><td>15.5</td></tr>\n" +
                "<tr><td>Juice</td></tr><tr><td>37.0</td></tr>\n";

        assertEquals(actualContent, expectedContent);
    }

    @Test
    @DisplayName("Test process multiple loops")
    public void testProcessLoop() {
        String content = "Header\n" +
                "<#list   products as product>" +
                "<tr><td>${product.price}</td></tr>\n" +
                "</#list>" +
                "Footer\n" +
                "<#list   productNames as productName>" +
                "<tr><td>${productName}</td></tr>\n" +
                "</#list>";

        Map<String, Object> attribute = Map.of(
                "productNames", of("Milk", "Water"),
                "products", of(
                        new Product(1L, "Milk", 27.0),
                        new Product(2L, "Water", 15.5),
                        new Product(3L, "Juice", 37.0)));

        String actualContent = templateProcessor.process(content, attribute);
        String expectedContent = "Header\n" +
                "<tr><td>27.0</td></tr>\n" +
                "<tr><td>15.5</td></tr>\n" +
                "<tr><td>37.0</td></tr>\n" +
                "Footer\n" +
                "<tr><td>Milk</td></tr>\n" +
                "<tr><td>Water</td></tr>\n";

        assertEquals(expectedContent, actualContent);
    }
}