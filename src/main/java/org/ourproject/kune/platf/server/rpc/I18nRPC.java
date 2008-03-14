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

package org.ourproject.kune.platf.server.rpc;

import java.util.HashMap;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;

import org.ourproject.kune.platf.client.dto.I18nLanguageDTO;
import org.ourproject.kune.platf.client.rpc.I18nService;
import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.auth.Authenticated;
import org.ourproject.kune.platf.server.domain.I18nLanguage;
import org.ourproject.kune.platf.server.domain.I18nTranslation;
import org.ourproject.kune.platf.server.manager.I18nLanguageManager;
import org.ourproject.kune.platf.server.manager.I18nTranslationManager;
import org.ourproject.kune.platf.server.mapper.Mapper;

import com.google.gwt.user.client.rpc.SerializableException;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.wideplay.warp.persist.TransactionType;
import com.wideplay.warp.persist.Transactional;

@Singleton
public class I18nRPC implements RPC, I18nService {
    private final I18nTranslationManager i18nTranslationManager;
    private final Provider<UserSession> userSessionProvider;
    private final Provider<HttpServletRequest> requestProvider;
    private final I18nLanguageManager languageManager;
    private final Mapper mapper;

    @Inject
    public I18nRPC(final Provider<HttpServletRequest> requestProvider, final Provider<UserSession> userSessionProvider,
            final I18nTranslationManager i18nTranslationManager, final I18nLanguageManager languageManager,
            final Mapper mapper) {
        this.requestProvider = requestProvider;
        this.userSessionProvider = userSessionProvider;
        this.i18nTranslationManager = i18nTranslationManager;
        this.languageManager = languageManager;
        this.mapper = mapper;
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public I18nLanguageDTO getInitialLanguage(final String localeParam) {
        String initLanguage;
        I18nLanguage lang;
        UserSession userSession = getUserSession();
        if (localeParam != null) {
            initLanguage = localeParam;
        } else {
            if (userSession.isUserLoggedIn()) {
                initLanguage = userSession.getUser().getLanguage().getCode();
            } else {
                String browserLang = requestProvider.get().getLocale().getLanguage();
                if (browserLang != null) {
                    // Not logged, use browser language if possible
                    String country = requestProvider.get().getLocale().getCountry();
                    if (browserLang.equals("pt") && country != null && country.equals("BR")) {
                        // FIXME: the only supported rfc 3066 lang supported
                        initLanguage = "pt-br";
                    } else {
                        initLanguage = browserLang;
                    }
                } else {
                    initLanguage = I18nTranslation.DEFAULT_LANG;
                }
            }
        }
        try {
            lang = languageManager.findByCode(initLanguage);
        } catch (NoResultException e) {
            lang = languageManager.findByCode(I18nTranslation.DEFAULT_LANG);
        }
        return mapper.map(lang, I18nLanguageDTO.class);
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public HashMap<String, String> getLexicon(final String language) {
        return i18nTranslationManager.getLexicon(language);
    }

    @Authenticated(mandatory = false)
    @Transactional(type = TransactionType.READ_WRITE)
    public String getTranslation(final String userHash, final String language, final String text)
            throws SerializableException {
        return i18nTranslationManager.getTranslation(language, text);
    }

    @Authenticated
    @Transactional(type = TransactionType.READ_WRITE)
    public void setTranslation(final String userHash, final String id, final String translation)
            throws SerializableException {
        i18nTranslationManager.setTranslation(id, translation);
    }

    private UserSession getUserSession() {
        return userSessionProvider.get();
    }

}
