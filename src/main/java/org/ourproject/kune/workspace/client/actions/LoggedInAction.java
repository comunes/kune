/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.workspace.client.actions;

import java.util.Date;

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.dto.I18nLanguageDTO;
import org.ourproject.kune.platf.client.dto.UserInfoDTO;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
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
        Kune.I18N.changeCurrentLanguage(language.getCode());
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
