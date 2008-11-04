package org.ourproject.kune.workspace.client.signin;

import org.ourproject.kune.platf.client.dto.I18nLanguageDTO;
import org.ourproject.kune.platf.client.dto.UserInfoDTO;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;

public class SignInAbstractPresenter {

    protected final Session session;
    protected final StateManager stateManager;
    protected final I18nUITranslationService i18n;
    protected SignInAbstractView view;

    public SignInAbstractPresenter(Session session, StateManager stateManager, I18nUITranslationService i18n) {
        this.session = session;
        this.stateManager = stateManager;
        this.i18n = i18n;
    }

    public void hide() {
        view.hide();
    }

    public void onCancel() {
        view.reset();
        view.hideMessages();
        view.hide();
        stateManager.restorePreviousToken();
    }

    public void onClose() {
        view.reset();
        view.hideMessages();
        if (!session.isLogged()) {
            stateManager.restorePreviousToken();
        }
    }

    protected void onSignIn(final UserInfoDTO userInfoDTO) {
        final String userHash = userInfoDTO.getUserHash();
        view.setCookie(userHash);
        view.reset();
        session.setUserHash(userHash);
        session.setCurrentUserInfo(userInfoDTO);
        final I18nLanguageDTO language = userInfoDTO.getLanguage();
        i18n.changeCurrentLanguage(language.getCode());
        session.setCurrentLanguage(language);
        stateManager.restorePreviousToken();
    }

}
