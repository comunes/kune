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

import java.util.HashMap;

import org.ourproject.kune.platf.client.dispatch.DefaultDispatcher;
import org.ourproject.kune.platf.client.rpc.I18nService;
import org.ourproject.kune.platf.client.rpc.I18nServiceAsync;
import org.ourproject.kune.platf.client.ui.Location;
import org.ourproject.kune.platf.client.ui.WindowUtils;
import org.ourproject.kune.workspace.client.WorkspaceEvents;

import to.tipit.gwtlib.FireLog;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class I18nUITranslation {
    // Also in I18nTranslation
    private static final String DEFAULT_LANG = "en";
    private static final String UNTRANSLATED_VALUE = null;

    private static I18nUITranslation instance;
    private HashMap lexicon;
    private String currentLanguage;

    public void init(final AsyncCallback callback) {
        currentLanguage = DEFAULT_LANG;
        Location loc = WindowUtils.getLocation();
        String locale = loc.getParameter("locale");
        if (locale != null) {
            String[] localeSplitted = locale.split("_");
            currentLanguage = localeSplitted[0];
        }
        I18nServiceAsync server = I18nService.App.getInstance();
        server.getLexicon(currentLanguage, callback);
    }

    public static I18nUITranslation getInstance() {
        if (instance == null) {
            instance = new I18nUITranslation();
        }
        return instance;
    }

    public void setLexicon(final String language, final HashMap lexicon) {
        this.currentLanguage = language;
        this.lexicon = lexicon;
    }

    public void setLexicon(final HashMap lexicon) {
        this.lexicon = lexicon;
    }

    public void setCurrentLanguage(final String newLanguage) {
        if (!newLanguage.equals(this.currentLanguage)) {
            this.currentLanguage = newLanguage;
            DefaultDispatcher.getInstance().fire(WorkspaceEvents.GET_LEXICON, newLanguage, null);
        }
    }

    public HashMap getLexicon() {
        return lexicon;
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
        String encodeText = encodeHtml(text);
        String translation = (String) lexicon.get(encodeText);
        if (lexicon.containsKey(encodeText)) {
            if (translation == UNTRANSLATED_VALUE) {
                // Not translated but in db, return text
                translation = encodeText;
            }
        } else {
            // Not translated and not in db, make a petition for translation
            DefaultDispatcher.getInstance().fireDeferred(WorkspaceEvents.GET_TRANSLATION, this.currentLanguage, text);
            FireLog.debug("Registering in db '" + text + "' as pending translation");
            translation = encodeText;
            lexicon.put(encodeText, UNTRANSLATED_VALUE);
        }
        return decodeHtml(translation);
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

    public String encodeHtml(final String textToEncode) {
        String text = textToEncode;
        text = text.replaceAll("&", "&amp;");
        text = text.replaceAll("\"", "&quot;");
        text = text.replaceAll("<", "&lt;");
        text = text.replaceAll(">", "&gt;");
        text = text.replaceAll("©", "&copy;");
        return text;
    }

    public String decodeHtml(final String textToDecode) {
        String text = textToDecode;
        text = text.replaceAll("&copy;", "©");
        return text;
    }
}
