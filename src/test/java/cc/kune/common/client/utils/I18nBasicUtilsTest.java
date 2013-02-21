package cc.kune.common.client.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class I18nBasicUtilsTest {

    @Test
    public void someBasicTests() {
        assertEquals("en", I18nBasicUtils.getLanguage("en_US"));
        assertEquals("en", I18nBasicUtils.getLanguage("en"));
        assertEquals("es", I18nBasicUtils.getLanguage("es_AR"));
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
