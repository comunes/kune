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
import org.ourproject.kune.platf.client.dto.I18nCountryDTO;
import org.ourproject.kune.platf.client.dto.I18nLanguageDTO;
import org.ourproject.kune.platf.client.dto.TimeZoneDTO;
import org.ourproject.kune.platf.client.dto.UserDTO;
import org.ourproject.kune.platf.client.dto.UserInfoDTO;
import org.ourproject.kune.platf.client.errors.EmailAddressInUseException;
import org.ourproject.kune.platf.client.errors.GroupNameInUseException;
import org.ourproject.kune.platf.client.rpc.UserServiceAsync;
import org.ourproject.kune.platf.client.services.I18nUITranslationService;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.newgroup.SiteErrorType;
import org.ourproject.kune.workspace.client.site.Site;

import com.calclab.emiteuimodule.client.SubscriptionMode;
import com.calclab.suco.client.ioc.Provider;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class RegisterPresenter extends SignInAbstractPresenter implements Register {

    private RegisterView view;
    private final Provider<UserServiceAsync> userServiceProvider;
    private final Provider<SignIn> signInProvider;

    public RegisterPresenter(Session session, StateManager stateManager, I18nUITranslationService i18n,
            Provider<UserServiceAsync> userServiceProvider, Provider<SignIn> signInProvider) {
        super(session, stateManager, i18n);
        this.userServiceProvider = userServiceProvider;
        this.signInProvider = signInProvider;
    }

    public void doRegister() {
        signInProvider.get().hide();
        if (!session.isLogged()) {
            Site.showProgressProcessing();
            view.show();
            view.center();
            Site.hideProgress();
        } else {
            stateManager.restorePreviousToken();
        }
    }

    public View getView() {
        return view;
    }

    public void init(RegisterView view) {
        this.view = view;
        super.view = view;
    }

    public void onFormRegister() {
        if (view.isRegisterFormValid()) {
            view.maskProcessing();

            final I18nLanguageDTO language = new I18nLanguageDTO();
            language.setCode(view.getLanguage());

            final I18nCountryDTO country = new I18nCountryDTO();
            country.setCode(view.getCountry());

            final TimeZoneDTO timezone = new TimeZoneDTO();
            timezone.setId(view.getTimezone());

            final boolean wantPersonalHomepage = view.wantPersonalHomepage();

            final UserDTO user = new UserDTO(view.getLongName(), view.getShortName(), view.getRegisterPassword(),
                    view.getEmail(), language, country, timezone, null, true, SubscriptionMode.manual, "blue");
            final AsyncCallback<UserInfoDTO> callback = new AsyncCallback<UserInfoDTO>() {
                public void onFailure(final Throwable caught) {
                    view.unMask();
                    try {
                        throw caught;
                    } catch (final EmailAddressInUseException e) {
                        view.setErrorMessage(i18n.t(PlatfMessages.EMAIL_IN_USE), SiteErrorType.error);
                    } catch (final GroupNameInUseException e) {
                        view.setErrorMessage(i18n.t(PlatfMessages.NAME_IN_USE), SiteErrorType.error);
                    } catch (final Throwable e) {
                        view.setErrorMessage(i18n.t("Error during registration."), SiteErrorType.error);
                        GWT.log("Other kind of exception in user registration" + e.getMessage() + ", "
                                + e.getLocalizedMessage(), null);
                        e.printStackTrace();
                        throw new RuntimeException();
                    }
                }

                public void onSuccess(final UserInfoDTO userInfoDTO) {
                    onSignIn(userInfoDTO);
                    stateManager.gotoToken(userInfoDTO.getHomePage());
                    view.hide();
                    view.unMask();
                    if (wantPersonalHomepage) {
                        view.showWelcolmeDialog();
                    } else {
                        view.showWelcolmeDialogNoHomepage();
                    }
                }
            };
            userServiceProvider.get().createUser(user, wantPersonalHomepage, callback);
        }
    }
}
