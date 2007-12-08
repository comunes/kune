package org.ourproject.kune.platf.client.services;

import java.util.HashMap;

import org.ourproject.kune.platf.client.dispatch.DefaultDispatcher;
import org.ourproject.kune.platf.client.rpc.I18nService;
import org.ourproject.kune.platf.client.rpc.I18nServiceAsync;
import org.ourproject.kune.platf.client.ui.Location;
import org.ourproject.kune.platf.client.ui.WindowUtils;
import org.ourproject.kune.workspace.client.WorkspaceEvents;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class I18nUITranslation {
    // Also in I18nTranslation
    private static final String DEFAULT_LANG = "en";
    private static final String UNTRANSLATED_VALUE = null;

    private static I18nUITranslation instance;
    private HashMap lexicon;
    private String language;

    public void init(final AsyncCallback callback) {
        language = DEFAULT_LANG;
        Location loc = WindowUtils.getLocation();
        String locale = loc.getParameter("locale");
        if (locale != null) {
            String[] localeSplitted = locale.split("_");
            language = localeSplitted[0];
        }
        I18nServiceAsync server = I18nService.App.getInstance();
        server.getLexicon(language, callback);
    }

    public static I18nUITranslation getInstance() {
        if (instance == null) {
            instance = new I18nUITranslation();
        }
        return instance;
    }

    public void setLexicon(final String language, final HashMap lexicon) {
        this.language = language;
        this.lexicon = lexicon;
    }

    public HashMap getLexicon() {
        return lexicon;
    }

    public String t(final String text) {
        String translation = (String) lexicon.get(text);
        if (lexicon.containsKey(text)) {
            if (translation == UNTRANSLATED_VALUE) {
                // Not translated but in db, return text
                translation = text;
            }
        } else {
            // Not translated and not in db, make a petition for translation
            DefaultDispatcher.getInstance().fireDeferred(WorkspaceEvents.GET_TRANSLATION, this.language, text);
            translation = text;
            lexicon.put(text, translation);
        }
        return translation;
    }

    public String t(final String text, final String arg) {
        String translation = t(text);
        translation = translation.replaceFirst("<tt>%s</tt>", arg);
        return translation;
    }

    public String t(final String text, final Integer arg) {
        String translation = t(text);
        translation = translation.replaceFirst("<tt>%d</tt>", arg.toString());
        return translation;

    }

    public void setLexicon(final HashMap lexicon) {
        this.lexicon = lexicon;
    }

}
