package org.ourproject.kune.workspace.client.actions;

import java.util.Date;

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.dto.I18nLanguageDTO;
import org.ourproject.kune.platf.client.dto.UserInfoDTO;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.sitebar.Site;

import com.google.gwt.user.client.Cookies;

public class LoggedInAction implements Action<UserInfoDTO> {
    private final Session session;
    private final StateManager stateManager;

    public LoggedInAction(final Session session, final StateManager stateManager) {
        this.session = session;
        this.stateManager = stateManager;
    }

    public void execute(final UserInfoDTO value) {
        onLoggedIn(value);
    }

    private void onLoggedIn(final UserInfoDTO userInfoDTO) {
        setCookie(userInfoDTO);
        session.setUserHash(userInfoDTO.getUserHash());
        Site.sitebar.showLoggedUser(userInfoDTO);
        I18nLanguageDTO language = userInfoDTO.getLanguage();
        stateManager.reload();
        I18nUITranslationService.getInstance().setCurrentLanguage(language.getCode());
        session.setCurrentLanguage(language);
    }

    private void setCookie(final UserInfoDTO userInfoDTO) {
        // http://code.google.com/p/google-web-toolkit-incubator/wiki/LoginSecurityFAQ
        String sessionId = userInfoDTO.getUserHash();
        final long duration = Session.SESSION_DURATION;
        Date expires = new Date(System.currentTimeMillis() + duration);
        Cookies.setCookie("userHash", sessionId, expires, null, "/", false);
    }
}
