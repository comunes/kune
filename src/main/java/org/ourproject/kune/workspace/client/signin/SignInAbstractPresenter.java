/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.ourproject.kune.workspace.client.signin;


import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.I18nLanguageDTO;
import cc.kune.core.shared.dto.UserInfoDTO;

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
