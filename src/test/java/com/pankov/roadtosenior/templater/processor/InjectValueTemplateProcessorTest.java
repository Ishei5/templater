package com.pankov.roadtosenior.templater.processor;

import com.pankov.roadtosenior.templater.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.lang.reflect.Method;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class InjectValueTemplateProcessorTest {

    InjectValueTemplateProcessor injectValueTemplateProcessor = new InjectValueTemplateProcessor();

    @Test
    @DisplayName("Test generate getter for name field")
    public void testGenerateGetter() throws NoSuchMethodException {
        Method expectedMethod = Product.class.getDeclaredMethod("getName");
        Method actualMethod = injectValueTemplateProcessor.generateGetter("name", Product.class);

        assertEquals(expectedMethod, actualMethod);
    }

    @Test
    @DisplayName("Test generate getter for not exist name should throw RuntimeException")
    public void testGenerateGetterWithException() {
        assertThrows(RuntimeException.class,
                () -> injectValueTemplateProcessor.generateGetter("height", Product.class)
        );
    }

    @Test
    @DisplayName("Test inject value")
    public void testInjectValue() {
        String content = "<tr><td>${product}</td></tr>" +
                "<tr><td>${product2}</td></tr>";
        Map<String, Object> attribute = Map.of(
                "product", "Test product",
                "product2", "Test product2");

        String actualContent = injectValueTemplateProcessor.process(content, attribute);

        String expectedContent = "<tr><td>Test product</td></tr>" +
                "<tr><td>Test product2</td></tr>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    @DisplayName("Test inject value invoke getter")
    public void testInjectValueInvokeGetter() {
        String content = "<tr><td>${product.id}</td>" +
                "<td>${product.name}</td>" +
                "<td>${product.price}</td></tr>";
        Map<String, Object> attribute = Map.of(
                "product", new Product(1L, "Water", 17.7));

        String actualContent = injectValueTemplateProcessor.process(content, attribute);

        String expectedContent = "<tr><td>1</td>" +
                "<td>Water</td>" +
                "<td>17.7</td></tr>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    @DisplayName("Test inject value should throw IllegalArgumentException")
    public void testInjectValueWithException() {
        String content = "<tr><td>${products.id}</td>" +
                "<td>${products.name}</td>" +
                "<td>${products.price}</td></tr>";
        Map<String, Object> attribute = Map.of(
                "product", new Product(1L, "Water", 17.7));

        assertThrows(IllegalArgumentException.class,
                () -> injectValueTemplateProcessor.process(content, attribute));
    }
}