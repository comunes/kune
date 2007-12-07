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
import org.ourproject.kune.platf.client.errors.EmailAddressInUseException;
import org.ourproject.kune.platf.client.errors.GroupNameInUseException;
import org.ourproject.kune.platf.client.errors.UserAuthException;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.sitebar.client.Site;
import org.ourproject.kune.sitebar.client.msg.MessagePresenter;
import org.ourproject.kune.sitebar.client.msg.SiteMessage;
import org.ourproject.kune.sitebar.client.rpc.UserService;
import org.ourproject.kune.sitebar.client.rpc.UserServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class LoginPresenter implements Login, MessagePresenter {

    LoginView view;

    final LoginListener listener;

    public LoginPresenter(final LoginListener listener) {
        this.listener = listener;
    }

    public void init(final LoginView loginview) {
        this.view = loginview;
        reset();
    }

    public void onCancel() {
        resetMessage();
        reset();
        listener.onLoginCancelled();
    }

    public void doLogin() {
        if (view.isSignInFormValid()) {
            Site.showProgressProcessing();
            final String nickOrEmail = view.getNickOrEmail();
            final String passwd = view.getLoginPassword();
            UserServiceAsync siteBarService = UserService.App.getInstance();
            siteBarService.login(nickOrEmail, passwd, new AsyncCallback() {
                public void onFailure(final Throwable caught) {
                    Site.hideProgress();
                    try {
                        throw caught;
                    } catch (final UserAuthException e) {
                        setMessage(Kune.I18N.t("Incorrect username/email or password"), SiteMessage.ERROR);
                    } catch (final Throwable e) {
                        setMessage("Error in login", SiteMessage.ERROR);
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
            final String language = view.getLanguage();
            final String country = view.getCountry();
            UserServiceAsync siteBarService = UserService.App.getInstance();
            // TODO: Form of register, license menu;
            LicenseDTO defaultLicense = new LicenseDTO("by-sa", "Creative Commons Attribution-ShareAlike", "",
                    "http://creativecommons.org/licenses/by-sa/3.0/", true, true, false, "", "");
            // FIXME: Timezone
            siteBarService.createUser(shortName, longName, email, passwd, defaultLicense, language, country, "GMT",
                    new AsyncCallback() {
                        public void onFailure(final Throwable caught) {
                            Site.hideProgress();
                            try {
                                throw caught;
                            } catch (final EmailAddressInUseException e) {
                                setMessage(Kune.I18N.t("This email in in use by other person, try with another."),
                                        SiteMessage.ERROR);
                            } catch (final GroupNameInUseException e) {
                                setMessage(Kune.I18N.t("This name in already in use, try with a different name."),
                                        SiteMessage.ERROR);
                            } catch (final Throwable e) {
                                setMessage(Kune.I18N.t("Error during registration."), SiteMessage.ERROR);
                                GWT.log("Other kind of exception in user registration", null);
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

    public void onMessageClose() {
        // From MessagePresenter: do nothing
    }

    public void onClose() {
        resetMessage();
        reset();
        listener.onLoginClose();
    }

    public void setMessage(final String message, final int type) {
        view.setMessage(message, type);
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
