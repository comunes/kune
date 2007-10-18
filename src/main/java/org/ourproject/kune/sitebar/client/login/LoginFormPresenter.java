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
import org.ourproject.kune.platf.client.dto.LicenseDTO;
import org.ourproject.kune.platf.client.dto.UserInfoDTO;
import org.ourproject.kune.platf.client.errors.UserAuthException;
import org.ourproject.kune.sitebar.client.Site;
import org.ourproject.kune.sitebar.client.msg.MessagePresenter;
import org.ourproject.kune.sitebar.client.rpc.SiteBarService;
import org.ourproject.kune.sitebar.client.rpc.SiteBarServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class LoginFormPresenter implements LoginForm, MessagePresenter {

    LoginFormView view;

    final LoginListener listener;

    // private boolean loginButtonEnabled;

    public LoginFormPresenter(final LoginListener listener) {
	this.listener = listener;
	// //this.loginButtonEnabled = false;
    }

    public void init(final LoginFormView loginview) {
	this.view = loginview;
	reset();
    }

    public void doCancel() {
	reset();
	resetMessage();
	listener.onLoginCancelled();
    }

    public void doLogin() {
	if (view.isSignInFormValid()) {
	    Site.showProgressProcessing();
	    final String nickOrEmail = view.getNickOrEmail();
	    final String passwd = view.getLoginPassword();
	    SiteBarServiceAsync siteBarService = SiteBarService.App.getInstance();
	    siteBarService.login(nickOrEmail, passwd, new AsyncCallback() {
		public void onFailure(final Throwable caught) {
		    Site.hideProgress();
		    try {
			throw caught;
		    } catch (final UserAuthException e) {
			// i18n

			view.showErrorMessage("Error in authentication");
		    } catch (final Throwable e) {
			view.showErrorMessage("Error in login");
			GWT.log("Other kind of exception in LoginFormPresenter/doLogin", null);
			throw new RuntimeException();
		    }

		}

		public void onSuccess(final Object response) {
		    listener.userLoggedIn((UserInfoDTO) response);
		    Site.hideProgress();
		}
	    });
	}
    }

    public void doRegister() {
	if (view.isRegisterFormValid()) {
	    Site.showProgressProcessing();
	    final String shortName = view.getShortName();
	    final String passwd = view.getRegisterPassword();
	    final String longName = view.getLongName();
	    final String email = view.getEmail();
	    SiteBarServiceAsync siteBarService = SiteBarService.App.getInstance();
	    // TODO: Form of register, license menu;
	    LicenseDTO defaultLicense = new LicenseDTO("by-sa", "Creative Commons Attribution-ShareAlike", "",
		    "http://creativecommons.org/licenses/by-sa/3.0/", true, true, false, "", "");
	    siteBarService.createUser(shortName, longName, email, passwd, defaultLicense, new AsyncCallback() {
		public void onFailure(final Throwable arg0) {
		    // i18n: Error creating user
		    Site.hideProgress();
		    Site.important("Error creating user");
		}

		public void onSuccess(final Object response) {
		    listener.userLoggedIn((UserInfoDTO) response);
		    Site.hideProgress();
		}
	    });
	}
    }

    public void onClose() {
	// Do nothing
    }

    public void setMessage(final String message, final int type) {
	// TODO Auto-generated method stub

    }

    public View getView() {
	return view;
    }

    public void resetMessage() {
	view.hideMessage();
    }

    private void reset() {
	view.reset();
    }

}
