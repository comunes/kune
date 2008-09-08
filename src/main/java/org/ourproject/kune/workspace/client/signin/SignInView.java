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
package org.ourproject.kune.workspace.client.signin;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.workspace.client.newgroup.SiteErrorType;

public interface SignInView extends View {

    public void center();

    public String getCountry();

    public String getEmail();

    public String getLanguage();

    public String getLoginPassword();

    public String getLongName();

    public String getNickOrEmail();

    public String getRegisterPassword();

    public String getRegisterPasswordDup();

    public String getShortName();

    public String getTimezone();

    public void hide();

    public void hideMessages();

    public boolean isRegisterFormValid();

    public boolean isSignInFormValid();

    public void maskProcessing();

    public void reset();

    public void show();

    public void showWelcolmeDialog();

    public void unMask();

    void setCookie(String userHash);

    void setRegisterMessage(String message, SiteErrorType type);

    void setSignInMessage(String message, SiteErrorType type);

}
