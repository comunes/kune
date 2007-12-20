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
import org.ourproject.kune.platf.client.ui.KuneStringUtils;
import org.ourproject.kune.platf.client.ui.Location;
import org.ourproject.kune.platf.client.ui.WindowUtils;
import org.ourproject.kune.workspace.client.WorkspaceEvents;
import org.ourproject.kune.workspace.client.i18n.I18nChangeListener;
import org.ourproject.kune.workspace.client.i18n.I18nChangeListenerCollection;

import to.tipit.gwtlib.FireLog;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class I18nUITranslation {
    private static final String NOTE_FOR_TRANSLATOR_TAG_BEGIN = " [%NT ";
    private static final String NOTE_FOR_TRANSLATOR_TAG_END = "]";
    // Also in I18nTranslation
    private static final String UNTRANSLATED_VALUE = null;

    private static I18nUITranslation instance;
    private HashMap lexicon;
    private String currentLanguage;
    private I18nChangeListenerCollection i18nChangeListeners;

    public void getInitialLanguage(final AsyncCallback callback) {
        Location loc = WindowUtils.getLocation();
        String locale = loc.getParameter("locale");
        if (locale != null) {
            // If locale parameter exist, use it
            String[] localeSplitted = locale.split("_");
            currentLanguage = localeSplitted[0];
            callback.onSuccess(currentLanguage);
        } else {
            I18nServiceAsync server = I18nService.App.getInstance();
            server.getInitialLanguage(callback);
        }
    }

    public void getInitialLexicon(final String initLanguage, final AsyncCallback callback) {
        currentLanguage = initLanguage;
        I18nServiceAsync server = I18nService.App.getInstance();
        server.getLexicon(currentLanguage, callback);
    }

    public static I18nUITranslation getInstance() {
        if (instance == null) {
            instance = new I18nUITranslation();
        }
        return instance;
    }

    public void setLexicon(final HashMap lexicon) {
        this.lexicon = lexicon;
        fireI18nLanguageChange();
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
        String encodeText = KuneStringUtils.escapeHtmlLight(text);
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

    public void setTranslationAfterSave(final String text, final String translation) {
        lexicon.put(text, translation);
        fireI18nLanguageChange();
    }

    public String decodeHtml(final String textToDecode) {
        String text = textToDecode;
        // text = text.replaceAll("&copy;", "©");
        return text;
    }

    /*
     * If a UI element need to be fired when (for instance) the language changes
     * use this. Useful if you widget have to take in account text language
     * direction, for instance.
     */
    public void addI18nChangeListener(final I18nChangeListener listener) {
        if (i18nChangeListeners == null) {
            i18nChangeListeners = new I18nChangeListenerCollection();
        }
        i18nChangeListeners.add(listener);
    }

    public void removeI18nChangeListener(final I18nChangeListener listener) {
        if (i18nChangeListeners != null) {
            i18nChangeListeners.remove(listener);
        }
    }

    private void fireI18nLanguageChange() {
        if (i18nChangeListeners != null) {
            i18nChangeListeners.fireI18nLanguageChange();
        }
    }

}
