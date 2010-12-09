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

import org.ourproject.kune.platf.client.PlatfMessages;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.ui.noti.NotifyUser;
import org.ourproject.kune.platf.client.ui.noti.NotifyUser.Level;
import org.ourproject.kune.workspace.client.site.SiteToken;

import cc.kune.core.client.errors.UserAuthException;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.rpcservices.UserServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.UserDTO;
import cc.kune.core.shared.dto.UserInfoDTO;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.suco.client.ioc.Provider;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class SignInPresenter extends SignInAbstractPresenter implements SignIn {

    private SignInView view;
    private final Provider<UserServiceAsync> userService;
    private final Provider<Register> registerProvider;

    public SignInPresenter(final Session session, final StateManager stateManager, final I18nUITranslationService i18n,
            final Provider<UserServiceAsync> userService, final Provider<Register> registerProvider) {
        super(session, stateManager, i18n);
        this.userService = userService;
        this.registerProvider = registerProvider;
    }

    public void doSignIn() {
        registerProvider.get().hide();
        if (session.isLogged()) {
            stateManager.restorePreviousToken();
        } else {
            NotifyUser.showProgressProcessing();
            view.show();
            view.center();
            NotifyUser.hideProgress();
            view.focusOnNickname();
        }
    }

    public View getView() {
        return view;
    }

    public void init(final SignInView view) {
        this.view = view;
        super.view = view;
    }

    public void onAccountRegister() {
        view.reset();
        view.hideMessages();
        view.hide();
        stateManager.gotoToken(SiteToken.register.toString());
    }

    public void onFormSignIn() {
        view.validate();
        if (view.isSignInFormValid()) {
            view.maskProcessing();

            final String nickOrEmail = view.getNickOrEmail();
            final String passwd = view.getLoginPassword();

            final UserDTO user = new UserDTO();
            user.setShortName(nickOrEmail);
            user.setPassword(passwd);

            final AsyncCallback<UserInfoDTO> callback = new AsyncCallback<UserInfoDTO>() {
                public void onFailure(final Throwable caught) {
                    view.unMask();
                    NotifyUser.hideProgress();
                    if (caught instanceof UserAuthException) {
                        view.setErrorMessage(i18n.t(PlatfMessages.INCORRECT_NICKNAME_EMAIL_OR_PASSWORD), Level.error);
                    } else {
                        view.setErrorMessage("Error in login", Level.error);
                        Log.error("Other kind of exception in SignInPresenter/doLogin");
                    }
                }

                public void onSuccess(final UserInfoDTO userInfoDTO) {
                    onSignIn(userInfoDTO);
                    stateManager.restorePreviousToken();
                    view.hide();
                    view.unMask();
                }
            };
            userService.get().login(user.getShortName(), user.getPassword(), callback);
        }
    }
}
