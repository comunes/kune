/*
 *
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

package org.ourproject.kune.platf.server.manager.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.EntityManager;

import org.ourproject.kune.platf.client.ui.KuneStringUtils;
import org.ourproject.kune.platf.server.domain.I18nLanguage;
import org.ourproject.kune.platf.server.domain.I18nTranslation;
import org.ourproject.kune.platf.server.manager.I18nLanguageManager;
import org.ourproject.kune.platf.server.manager.I18nTranslationManager;

import com.google.gwt.user.client.rpc.SerializableException;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class I18nTranslationManagerDefault extends DefaultManager<I18nTranslation, Long> implements
        I18nTranslationManager {

    private final I18nTranslation finder;
    private final ConcurrentHashMap<String, HashMap<String, String>> langCache;
    private final I18nLanguageManager languageManager;

    @Inject
    public I18nTranslationManagerDefault(final Provider<EntityManager> provider, final I18nTranslation finder,
            final I18nLanguageManager languageManager) {
        super(provider, I18nTranslation.class);
        this.finder = finder;
        this.languageManager = languageManager;
        langCache = new ConcurrentHashMap<String, HashMap<String, String>>();
    }

    public HashMap<String, String> getLexicon(final String language) {
        HashMap<String, String> map = langCache.get(language);
        if (map == null) {
            map = getLexiconFromDb(language);
        }
        return map;
    }

    public List<I18nTranslation> getUntranslatedLexicon(final String languageCode) {
        I18nLanguage language = initUnstranlated(languageCode);
        return finder.getUnstranslatedLexicon(language);
    }

    public SearchResult getUntranslatedLexicon(final String languageCode, final Integer firstResult,
            final Integer maxResults) {
        I18nLanguage language = initUnstranlated(languageCode);
        Long count = finder.getUnstranslatedLexiconCount(language);
        List<I18nTranslation> list = finder.getUnstranslatedLexicon(language, firstResult, maxResults);
        return new SearchResult(count, list);
    }

    private I18nLanguage initUnstranlated(final String languageCode) {
        I18nLanguage defLanguage = languageManager.findByCode(I18nTranslation.DEFAULT_LANG);
        I18nLanguage language;
        if (languageCode.equals(I18nTranslation.DEFAULT_LANG)) {
            language = defLanguage;
        } else {
            language = languageManager.findByCode(languageCode);
            List<I18nTranslation> list = finder.getNonExistentFromDefault(defLanguage, language);
            for (Iterator<I18nTranslation> iterator = list.iterator(); iterator.hasNext();) {
                I18nTranslation defTrans = iterator.next();
                I18nTranslation newTrans = defTrans.cloneForNewLanguage();
                newTrans.setLanguage(language);
                persist(newTrans);
            }
        }
        return language;
    }

    public List<I18nTranslation> getTranslatedLexicon(final String languageCode) {
        I18nLanguage language = languageManager.findByCode(languageCode);
        return finder.getTranslatedLexicon(language);
    }

    public SearchResult getTranslatedLexicon(final String languageCode, final Integer firstResult,
            final Integer maxResults) {
        I18nLanguage language = languageManager.findByCode(languageCode);
        List<I18nTranslation> list = finder.getTranslatedLexicon(language, firstResult, maxResults);
        Long count = finder.getTranslatedLexiconCount(language);
        return new SearchResult(count, list);
    }

    public String getTranslation(final String language, final String text) {
        HashMap<String, String> lexicon = getLexicon(language);
        String escapedText = KuneStringUtils.escapeHtmlLight(text);
        if (lexicon.containsKey(escapedText)) {
            String translation = lexicon.get(escapedText);
            return translation;
        } else {
            // new key, add to language and default language and let
            // untranslated
            if (!getLexicon(I18nTranslation.DEFAULT_LANG).containsKey(text)) {
                I18nTranslation newTranslation = new I18nTranslation("", null, I18nTranslation.DEF_PLUR_INDEX, "",
                        I18nTranslation.UNTRANSLATED_VALUE, escapedText, I18nTranslation.DEF_NAMESPACE, languageManager
                                .findByCode(I18nTranslation.DEFAULT_LANG));
                persist(newTranslation);
                langCache.clear();
            }
            return I18nTranslation.UNTRANSLATED_VALUE;
        }
    }

    public String getTranslation(final String language, final String text, final String arg) {
        String translation = getTranslation(language, text);
        translation = translation.replaceFirst("\\[%s\\]", arg);
        return translation;
    }

    public String getTranslation(final String language, final String text, final Integer arg) {
        String translation = getTranslation(language, text);
        translation = translation.replaceFirst("\\[%d\\]", arg.toString());
        return translation;
    }

    public void setTranslation(final String languageId, final String text, final String translation) {
        I18nLanguage language = languageManager.findByCode(languageId);
        I18nTranslation newTranslation = new I18nTranslation(text, language, translation);
        persist(newTranslation);
        if (languageId != I18nTranslation.DEFAULT_LANG) {
            langCache.remove(languageId);
        } else {
            langCache.clear();
        }
    }

    public void setTranslation(final String id, final String translation) throws SerializableException {
        I18nTranslation trans = super.find(new Long(id));
        if (trans != null) {
            String escapedTranslation = KuneStringUtils.escapeHtmlLight(translation);
            trans.setText(escapedTranslation);
            persist(trans);
        } else {
            throw new SerializableException();
        }
    }

    private HashMap<String, String> getLexiconFromDb(final String language) {
        HashMap<String, String> map = new HashMap<String, String>();
        List<I18nTranslation> set = finder.findByLanguage(language);
        if (!language.equals(I18nTranslation.DEFAULT_LANG)) {
            map = (HashMap<String, String>) getLexicon(I18nTranslation.DEFAULT_LANG).clone();
        }
        for (Iterator<I18nTranslation> iterator = set.iterator(); iterator.hasNext();) {
            I18nTranslation trans = iterator.next();
            map.put(trans.getTrKey(), trans.getText());
        }
        langCache.put(language, map);
        return map;
    }

}
