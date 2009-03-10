/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.platf.client.i18n;


public abstract class I18nTranslationService {
    protected static final String TRANSLATION_NOTE_REGEXP = " (\\[)%NT (.*)(\\])$";
    protected static final String NOTE_FOR_TRANSLATOR_TAG_BEGIN = " [%NT ";
    protected static final String NOTE_FOR_TRANSLATOR_TAG_END = "]";
    // Also in I18nTranslation
    protected static final String UNTRANSLATED_VALUE = null;

    public String decodeHtml(final String textToDecode) {
        String text = textToDecode;
        // text = text.replaceAll("&copy;", "©");
        return text;
    }

    public String removeNT(final String string) {
        return string.replaceAll(TRANSLATION_NOTE_REGEXP, "");
    }

    /**
     * In production, this method uses a hashmap. In development, if the text is
     * not in the hashmap, it makes a server petition (that stores the text
     * pending for translation in db).
     * 
     * Warning: text is escaped as html before insert in the db. Don't use html
     * here (o user this method with params).
     * 
     * @param text
     * @return text translated in the current language
     */
    public String t(final String text) {
        return null;
    }

    /**
     * Use [%d] to reference the Integer parameters
     * 
     */
    public String t(final String text, final Integer... args) {
        String translation = t(text);
        for (Integer arg : args) {
            translation = translation.replaceFirst("\\[%d\\]", arg.toString());
        }
        return decodeHtml(translation);
    }

    /**
     * Use [%d] to reference the Long parameter
     * 
     */
    public String t(final String text, final Long... args) {
        String translation = t(text);
        for (Long arg : args) {
            translation = translation.replaceFirst("\\[%d\\]", arg.toString());
        }
        return decodeHtml(translation);
    }

    /**
     * Use [%s] to reference the string parameter
     * 
     */
    public String t(final String text, final String... args) {
        String translation = t(text);
        for (String arg : args) {
            translation = translation.replaceFirst("\\[%s\\]", arg);
        }
        return decodeHtml(translation);
    }

    /**
     * Adds [%NT noteForTranslators] at the end of text. This tag is later
     * renderer in the translator panel to inform translator how to do this
     * translation
     * 
     */
    public String tWithNT(final String text, final String noteForTranslators) {
        return t(text + NOTE_FOR_TRANSLATOR_TAG_BEGIN + noteForTranslators + NOTE_FOR_TRANSLATOR_TAG_END);
    }

    /**
     * Use [%d] to reference the Integer parameter.
     * 
     * Also adds [%NT noteForTranslators] at the end of text. This tag is later
     * renderer in the translator panel to inform translator how to do this
     * translation
     * 
     */
    public String tWithNT(final String text, final String noteForTranslators, final Integer... args) {
        return t(text + NOTE_FOR_TRANSLATOR_TAG_BEGIN + noteForTranslators + NOTE_FOR_TRANSLATOR_TAG_END, args);
    }

    /**
     * Use [%d] to reference the Long parameter.
     * 
     * Also adds [%NT noteForTranslators] at the end of text. This tag is later
     * renderer in the translator panel to inform translator how to do this
     * translation
     * 
     */
    public String tWithNT(final String text, final String noteForTranslators, final Long... args) {
        return t(text + NOTE_FOR_TRANSLATOR_TAG_BEGIN + noteForTranslators + NOTE_FOR_TRANSLATOR_TAG_END, args);
    }

    /**
     * Use [%s] to reference the String parameter.
     * 
     * Also adds [%NT noteForTranslators] at the end of text. This tag is later
     * renderer in the translator panel to inform translator how to do this
     * translation
     * 
     */
    public String tWithNT(final String text, final String noteForTranslators, final String... args) {
        return t(text + NOTE_FOR_TRANSLATOR_TAG_BEGIN + noteForTranslators + NOTE_FOR_TRANSLATOR_TAG_END, args);
    }
}
