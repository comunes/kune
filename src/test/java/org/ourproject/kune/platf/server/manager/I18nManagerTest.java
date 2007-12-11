package org.ourproject.kune.platf.server.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.apache.commons.lang.StringEscapeUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.server.PersistenceTest;
import org.ourproject.kune.platf.server.domain.I18nCountry;
import org.ourproject.kune.platf.server.domain.I18nLanguage;
import org.ourproject.kune.platf.server.domain.I18nTranslation;

import com.google.inject.Inject;

public class I18nManagerTest extends PersistenceTest {
    @Inject
    I18nTranslationManager translationManager;
    @Inject
    I18nCountryManager countryManager;
    @Inject
    I18nLanguageManager languageManager;

    @Before
    public void insertData() {
        openTransaction();
        I18nLanguage english = new I18nLanguage(new Long(1819), "English", "English", "en");
        I18nLanguage spanish = new I18nLanguage(new Long(5889), "Spanish", "Español", "es");
        I18nLanguage afrikaans = new I18nLanguage(new Long(114), "Afrikaans", "Afrikaans", "af");
        I18nLanguage greek = new I18nLanguage(new Long(1793), "Greek", "Ελληνικά", "el");
        languageManager.persist(english);
        languageManager.persist(spanish);
        languageManager.persist(afrikaans);
        languageManager.persist(greek);
        translationManager.persist(new I18nTranslation("Sunday [weekday]", english, "Sunday"));
        translationManager.persist(new I18nTranslation("January [month]", english, "January"));
        translationManager.persist(new I18nTranslation("Sunday [weekday]", afrikaans, "Sondag"));
        translationManager.persist(new I18nTranslation("January [month]", greek, "Ιανουάριος"));
        translationManager.persist(new I18nTranslation(StringEscapeUtils.escapeHtml("[%s] users"), english,
                StringEscapeUtils.escapeHtml("[%s] users")));
        translationManager.persist(new I18nTranslation(StringEscapeUtils.escapeHtml("[%d] users"), english,
                StringEscapeUtils.escapeHtml("[%d] users")));
        I18nCountry gb = new I18nCountry(new Long(75), "GB", "United Kingdom", "", "£%n", "GBP", ",", ".", ".",
                "western");
        countryManager.merge(gb);
    }

    @Test
    public void testGetLexicon() {
        HashMap map = translationManager.getLexicon("af");
        assertTrue(map.size() > 0);
    }

    @Test
    public void getTranslation() {
        String translation = translationManager.getTranslation("af", "Sunday [weekday]");
        assertEquals("Sondag", translation);
    }

    @Test
    public void getTranslationUTF8() {
        String translation = translationManager.getTranslation("el", "January [month]");
        assertEquals("Ιανουάριος", translation);
    }

    @Test
    public void setTranslation() {
        translationManager.setTranslation("en", "Foo foo foo", "Foo foo foo translation");
        String translation = translationManager.getTranslation("en", "Foo foo foo");
        assertEquals("Foo foo foo translation", translation);
    }

    @Test
    public void byDefaultUseEnglish() {
        HashMap map = translationManager.getLexicon("en");
        HashMap map2 = translationManager.getLexicon("af");
        assertEquals(map.size(), map2.size());
    }

    @Test
    public void getNonExistentTranslationReturnsDefaultLanguage() {
        String translation = translationManager.getTranslation("af", "January [month]");
        assertEquals("January", translation);
    }

    @Test
    public void getNonExistentTranslationInAnyLangReturnsKey() {
        HashMap map = translationManager.getLexicon("en");
        HashMap map2 = translationManager.getLexicon("aa");
        int initialSize = map.size();
        int initialSize2 = map2.size();

        String translation = translationManager.getTranslation("es", "Foo foo foo");
        String translation2 = translationManager.getTranslation("aa", "Foo foo foo");

        assertEquals(I18nTranslation.UNTRANSLATED_VALUE, translation);
        assertEquals(I18nTranslation.UNTRANSLATED_VALUE, translation2);

        map = translationManager.getLexicon("en");
        map2 = translationManager.getLexicon("aa");
        int newSize = map.size();
        int newSize2 = map2.size();

        assertEquals(initialSize + 1, newSize);
        assertEquals(initialSize2 + 1, newSize2);
    }

    @Test
    public void getTranslationWithStringArg() {
        String translation = translationManager.getTranslation("en", "[%s] users", "Twenty");
        assertEquals("Twenty users", translation);
    }

    @Test
    public void getTranslationWithIntArg() {
        String translation = translationManager.getTranslation("en", "[%d] users", 20);
        assertEquals("20 users", translation);
    }

    @After
    public void close() {
        if (getTransaction().isActive()) {
            getTransaction().rollback();
        }
    }
}
