/*
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.platf.client.services;

public abstract class I18nTranslationService {
    protected static final String TRANSLATION_NOTE_REGEXP = " (\\[)%NT (.*)(\\])$";
    protected static final String NOTE_FOR_TRANSLATOR_TAG_BEGIN = " [%NT ";
    protected static final String NOTE_FOR_TRANSLATOR_TAG_END = "]";
    // Also in I18nTranslation
    protected static final String UNTRANSLATED_VALUE = null;

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

    /*
     * Use [%s] to reference the string parameter
     * 
     */
    public String t(final String text, final String arg) {
        String translation = t(text);
        translation = translation.replaceFirst("\\[%s\\]", arg);
        return decodeHtml(translation);
    }

    /*
     * Use [%d] to reference the Integer parameter
     * 
     */
    public String t(final String text, final Integer arg) {
        String translation = t(text);
        translation = translation.replaceFirst("\\[%d\\]", arg.toString());
        return decodeHtml(translation);

    }

    /*
     * Adds [%NT noteForTranslators] at the end of text. This tag is later
     * renderer in the translator panel to inform translator how to do this
     * translation
     * 
     * 
     */
    public String tWithNT(final String text, final String noteForTranslators) {
        return t(text + NOTE_FOR_TRANSLATOR_TAG_BEGIN + noteForTranslators + NOTE_FOR_TRANSLATOR_TAG_END);
    }

    /*
     * Use [%s] to reference the String parameter.
     * 
     * Also adds [%NT noteForTranslators] at the end of text. This tag is later
     * renderer in the translator panel to inform translator how to do this
     * translation
     * 
     * 
     */
    public String tWithNT(final String text, final String noteForTranslators, final String arg) {
        return t(text + NOTE_FOR_TRANSLATOR_TAG_BEGIN + noteForTranslators + NOTE_FOR_TRANSLATOR_TAG_END, arg);
    }

    /*
     * Use [%d] to reference the Integer parameter.
     * 
     * Also adds [%NT noteForTranslators] at the end of text. This tag is later
     * renderer in the translator panel to inform translator how to do this
     * translation
     * 
     * 
     */
    public String tWithNT(final String text, final String noteForTranslators, final Integer arg) {
        return t(text + NOTE_FOR_TRANSLATOR_TAG_BEGIN + noteForTranslators + NOTE_FOR_TRANSLATOR_TAG_END, arg);
    }

    public String decodeHtml(final String textToDecode) {
        String text = textToDecode;
        // text = text.replaceAll("&copy;", "©");
        return text;
    }

    public String removeNT(final String string) {
        return string.replaceAll(TRANSLATION_NOTE_REGEXP, "");
    }

}
