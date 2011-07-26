/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package cc.kune.core.server.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.apache.commons.lang.StringEscapeUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cc.kune.core.server.PersistenceTest;
import cc.kune.core.server.manager.I18nCountryManager;
import cc.kune.core.server.manager.I18nLanguageManager;
import cc.kune.core.server.manager.I18nTranslationManager;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.domain.I18nCountry;
import cc.kune.domain.I18nLanguage;
import cc.kune.domain.I18nTranslation;

import com.google.inject.Inject;

public class I18nManagerDefaultTest extends PersistenceTest {
    @Inject
    I18nTranslationManager translationManager;
    @Inject
    I18nCountryManager countryManager;
    @Inject
    I18nLanguageManager languageManager;
    @Inject
    I18nTranslationService translationService;

    @Test
    public void byDefaultUseEnglish() {
        HashMap<String, String> map = translationManager.getLexicon("en");
        HashMap<String, String> map2 = translationManager.getLexicon("af");
        assertEquals(map.size(), map2.size());
    }

    @After
    public void close() {
        if (getTransaction().isActive()) {
            getTransaction().rollback();
        }
    }

    @Test
    public void getNonExistentTranslationInAnyLangReturnsKey() {
        HashMap<String, String> map = translationManager.getLexicon("en");
        HashMap<String, String> map2 = translationManager.getLexicon("aa");
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
    public void getNonExistentTranslationReturnsDefaultLanguage() {
        String translation = translationManager.getTranslation("af", "January [month]");
        assertEquals("January", translation);
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
    public void getTranslationWithIntArg() {
        String translation = translationManager.getTranslation("en", "[%d] users", 20);
        assertEquals("20 users", translation);
    }

    @Test
    public void getTranslationWithIntArgFromService() {
        String translation = translationService.t("[%d] users", 20);
        assertEquals("20 users", translation);
    }

    @Test
    public void getTranslationWithIntArgFromWithNtService() {
        String translation = translationService.tWithNT("[%d] users", "foo foo", 20);
        assertEquals("20 users", translation);
    }

    @Test
    public void getTranslationWithStringArg() {
        String translation = translationManager.getTranslation("en", "[%s] users", "Twenty");
        assertEquals("Twenty users", translation);
    }

    @Test
    public void getTranslationWithStringArgFromService() {
        String translation = translationService.t("[%s] users", "Twenty");
        assertEquals("Twenty users", translation);
    }

    @Test
    public void getTranslationWithStringArgWithNtFromService() {
        String translation = translationService.tWithNT("[%s] users", "foo foo", "Twenty");
        assertEquals("Twenty users", translation);
    }

    @Before
    public void insertData() {
        openTransaction();
        I18nLanguage english = new I18nLanguage(Long.valueOf(1819), "English", "English", "en");
        I18nLanguage spanish = new I18nLanguage(Long.valueOf(5889), "Spanish", "Español", "es");
        I18nLanguage afrikaans = new I18nLanguage(Long.valueOf(114), "Afrikaans", "Afrikaans", "af");
        I18nLanguage greek = new I18nLanguage(Long.valueOf(1793), "Greek", "Ελληνικά", "el");
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
        I18nCountry gb = new I18nCountry(Long.valueOf(75), "GB", "GBP", ".", "£%n", "", ".", "United Kingdom",
                "western", ",");
        countryManager.persist(gb);
    }

    @Test
    public void setTranslation() {
        translationManager.setTranslation("en", "Foo foo foo", "Foo foo foo translation");
        String translation = translationManager.getTranslation("en", "Foo foo foo");
        assertEquals("Foo foo foo translation", translation);
    }

    @Test
    public void testGetLexicon() {
        HashMap<String, String> map = translationManager.getLexicon("af");
        assertTrue(map.size() > 0);
    }
}
