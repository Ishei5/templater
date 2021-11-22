package com.pankov.roadtosenior.templater.reader;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClasspathTemplateReaderTest {

    @Test
    public void testReadTemplate() {
        ClasspathTemplateReader classpathTemplateReader = new ClasspathTemplateReader();

        String actualStringTemplate = classpathTemplateReader.readTemplate("base.ftl");
        String expectedStringTemplate = "<#macro base>\n" +
                "Content from include\n" +
                "<@content/>\n" +
                "</#macro>";

        assertNotNull(actualStringTemplate);
        assertEquals(expectedStringTemplate, actualStringTemplate);
    }
}