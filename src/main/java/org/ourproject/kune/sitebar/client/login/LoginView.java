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

package org.ourproject.kune.sitebar.client.login;

import org.ourproject.kune.platf.client.View;

public interface LoginView extends View {

    public void reset();

    public String getNickOrEmail();

    public String getLoginPassword();

    public String getShortName();

    public String getLongName();

    public String getEmail();

    public String getRegisterPassword();

    public String getRegisterPasswordDup();

    public boolean isSignInFormValid();

    public boolean isRegisterFormValid();

    public String getLanguage();

    public String getCountry();

    public String getTimezone();

    public void showWelcolmeDialog();

    public void unMask();

    public void maskProcessing();

    public void setSignInMessage(String message, int type);

    public void setRegisterMessage(String t, int error);

    public void hideMessages();

}
