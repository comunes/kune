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

public class I18nUITranslationService extends I18nTranslationService {
    // Also in I18nTranslation
    private static final String UNTRANSLATED_VALUE = null;

    private static I18nUITranslationService instance;
    private HashMap lexicon;
    private String currentLanguage;
    private I18nChangeListenerCollection i18nChangeListeners;

    public void getInitialLanguage(final AsyncCallback callback) {
        Location loc = WindowUtils.getLocation();
        String locale = loc.getParameter("locale");
        if (locale != null) {
            // If locale parameter exist, use it
            if (locale.equals("pt-br") || locale.equals("zh-Hant") || locale.equals("zh-Hans")) {
                // initially only three rfc 3306 langs supported
                currentLanguage = locale;
            } else {
                String[] localeSplitted = locale.split("-");
                currentLanguage = localeSplitted[0];
            }
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

    public static I18nUITranslationService getInstance() {
        if (instance == null) {
            instance = new I18nUITranslationService();
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
            changeLocale(newLanguage);
            // DefaultDispatcher.getInstance().fire(WorkspaceEvents.GET_LEXICON,
            // newLanguage, null);
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
                translation = removeNT(encodeText);
            }
        } else {
            // Not translated and not in db, make a petition for translation
            DefaultDispatcher.getInstance().fireDeferred(WorkspaceEvents.GET_TRANSLATION, this.currentLanguage, text);
            FireLog.debug("Registering in db '" + text + "' as pending translation");
            translation = removeNT(encodeText);
            lexicon.put(encodeText, UNTRANSLATED_VALUE);
        }
        return decodeHtml(translation);
    }

    public void setTranslationAfterSave(final String text, final String translation) {
        lexicon.put(text, translation);
        fireI18nLanguageChange();
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

    public String getCurrentLanguage() {
        return currentLanguage;
    }

    private void fireI18nLanguageChange() {
        if (i18nChangeListeners != null) {
            i18nChangeListeners.fireI18nLanguageChange();
        }
    }

    /**
     * 
     * See in:
     * http://groups.google.com/group/Google-Web-Toolkit/browse_thread/thread/5e4e25050d3be984/7035ec39354d06aa?lnk=gst&q=get+locale&rnum=23
     * 
     * JSNI method to change the locale of the application - it effectively
     * parses the existing URL and creates a new one for the chosen locale.
     * 
     * It additionally launches any JavaScript debugger that might be attached
     * to the system (Windows only). To disable this functionality just remove
     * the "debugger" line.
     * 
     * @param newLocale
     *                String value of the new locale to go to.
     */
    private native void changeLocale(String newLocale)
    /*-{
       // Uncomment the "debugger;" line to see how to set debug statements in JSNI code
       // When in web mode, if your browser has a JavaScript debugger attached, it will
       // launch at this point in the code (when the user changes locale through the menu system).
       //debugger;

       // Get the current location
       var currLocation = $wnd.location.toString();
       // Get rid of any GWT History tokens that might be present
       var noHistoryCurrLocArray = currLocation.split("#");
       var noHistoryCurrLoc = noHistoryCurrLocArray[0];
       var currHistory = noHistoryCurrLocArray[1];
       // Get rid of any locale string
       var locArray = noHistoryCurrLoc.split("?");
       // Build the new href location and then send the browser there.
       // $wnd.location.href = locArray[0]+"?locale="+newLocale+"#"+currHistory;
       $wnd.location.href = locArray[0]+"?locale="+newLocale

       // extjs part:
       // commented because the error: "Ext is not defined"
       // we have to try other way
       //var head = document.getElementsByTagName("head")[0];
       //var script = document.createElement('script');
       //script.id = 'localScript';
       //script.type = 'text/javascript';
       //script.src = "js/ext/locale/ext-lang-"+newLocale+".js";
       //head.appendChild(script);
       }-*/;

}
