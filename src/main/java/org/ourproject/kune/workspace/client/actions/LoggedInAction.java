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

package org.ourproject.kune.workspace.client.actions;

import java.util.Date;

import org.ourproject.kune.platf.client.Services;
import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.dto.UserInfoDTO;
import org.ourproject.kune.platf.client.services.I18nUITranslation;
import org.ourproject.kune.sitebar.client.Site;

import com.google.gwt.user.client.Cookies;

public class LoggedInAction implements Action {
    public void execute(final Object value, final Object extra, final Services services) {
        onLoggedIn(services, (UserInfoDTO) value);
    }

    private void onLoggedIn(final Services services, final UserInfoDTO userInfoDTO) {
        setCookie(userInfoDTO);
        services.session.userHash = userInfoDTO.getUserHash();
        Site.sitebar.showLoggedUser(userInfoDTO);
        services.stateManager.reload();
        services.stateManager.reloadSocialNetwork();
        I18nUITranslation.getInstance().setLexicon(userInfoDTO.getLanguage().getCode(), userInfoDTO.getLexicon());
    }

    private void setCookie(final UserInfoDTO userInfoDTO) {
        // http://code.google.com/p/google-web-toolkit-incubator/wiki/LoginSecurityFAQ
        String sessionId = userInfoDTO.getUserHash();
        // duration remembering login. 2 weeks
        final long duration = 1000 * 60 * 60 * 24 * 14;
        Date expires = new Date(System.currentTimeMillis() + duration);
        Cookies.setCookie("userHash", sessionId, expires, null, "/", false);
    }
}
