package cc.kune.common.shared.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cc.kune.common.shared.utils.I18nBasicUtils;

public class I18nBasicUtilsTest {

    @Test
    public void someBasicTests() {
        assertEquals("en", I18nBasicUtils.getLanguage("en_US"));
        assertEquals("en", I18nBasicUtils.getLanguage("en"));
        assertEquals("es", I18nBasicUtils.getLanguage("es_AR"));
    }

    @Test
    public void testJavaLocaleNormalize() {
        assertEquals("en_US", I18nBasicUtils.javaLocaleNormalize("en-US"));
        assertEquals("pt_BR", I18nBasicUtils.javaLocaleNormalize("pt-br"));
        assertEquals("pt_BR", I18nBasicUtils.javaLocaleNormalize("pt-BR"));
    }

    @Test
    public void shouldReturnDefault() {
        assertEquals("en", I18nBasicUtils.getLanguage("default"));
        assertEquals("en", I18nBasicUtils.getLanguage("someOtherThing"));
    }

    @Test
    public void shouldWorkWithNull() {
        assertEquals("en", I18nBasicUtils.getLanguage(null));
    }
}
