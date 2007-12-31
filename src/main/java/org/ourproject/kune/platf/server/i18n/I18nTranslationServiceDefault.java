package org.ourproject.kune.platf.server.i18n;

import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.ui.KuneStringUtils;
import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.domain.I18nTranslation;
import org.ourproject.kune.platf.server.manager.I18nTranslationManager;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class I18nTranslationServiceDefault extends I18nTranslationService {

    private final I18nTranslationManager translationManager;
    private final Provider<UserSession> userSessionProvider;

    @Inject
    public I18nTranslationServiceDefault(final I18nTranslationManager translationManager,
            final Provider<UserSession> userSessionProvider) {
        this.translationManager = translationManager;
        this.userSessionProvider = userSessionProvider;
    }

    /**
     * If the text is not in the db, it stores the text pending for translation.
     * 
     * Warning: text is escaped as html before insert in the db. Don't use html
     * here (o user this method with params).
     * 
     * @param text
     * @return text translated in the current language
     */
    @Override
    public String t(final String text) {
        String language;

        UserSession userSession = userSessionProvider.get();
        if (userSession.isUserLoggedIn()) {
            language = userSession.getUser().getLanguage().getCode();
        } else {
            language = I18nTranslation.DEFAULT_LANG;
        }
        String encodeText = KuneStringUtils.escapeHtmlLight(text);
        String translation = translationManager.getTranslation(language, text);
        if (translation == UNTRANSLATED_VALUE) {
            // Not translated but in db, return text
            translation = removeNT(encodeText);
        }
        return decodeHtml(translation);
    }
}
