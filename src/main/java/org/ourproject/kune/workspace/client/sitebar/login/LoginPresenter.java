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
package org.ourproject.kune.workspace.client.sitebar.login;

import org.ourproject.kune.platf.client.PlatformEvents;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dispatch.DefaultDispatcher;
import org.ourproject.kune.platf.client.dto.I18nCountryDTO;
import org.ourproject.kune.platf.client.dto.I18nLanguageDTO;
import org.ourproject.kune.platf.client.dto.TimeZoneDTO;
import org.ourproject.kune.platf.client.dto.UserDTO;
import org.ourproject.kune.platf.client.dto.UserInfoDTO;
import org.ourproject.kune.platf.client.errors.EmailAddressInUseException;
import org.ourproject.kune.platf.client.errors.GroupNameInUseException;
import org.ourproject.kune.platf.client.errors.UserAuthException;
import org.ourproject.kune.platf.client.rpc.ParamCallback;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.workspace.client.WorkspaceEvents;
import org.ourproject.kune.workspace.client.newgroup.ui.SiteErrorType;
import org.ourproject.kune.workspace.client.sitebar.Site;

import com.calclab.emite.client.im.roster.RosterManager.SubscriptionMode;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class LoginPresenter implements Login {

    LoginView view;

    final LoginListener listener;

    private final Session session;

    private final I18nTranslationService i18n;

    public LoginPresenter(final Session session, final LoginListener listener, final I18nTranslationService i18n) {
        this.session = session;
        this.listener = listener;
        this.i18n = i18n;
    }

    public void doLogin() {
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
                    Site.hideProgress();
                    try {
                        throw caught;
                    } catch (final UserAuthException e) {
                        view.setSignInMessage(i18n.t("Incorrect nickname/email or password"), SiteErrorType.error);
                    } catch (final Throwable e) {
                        view.setSignInMessage("Error in login", SiteErrorType.error);
                        GWT.log("Other kind of exception in LoginFormPresenter/doLogin", null);
                        throw new RuntimeException();
                    }
                }

                public void onSuccess(final UserInfoDTO response) {
                    listener.userLoggedIn(response);
                    view.unMask();
                }
            };

            DefaultDispatcher.getInstance().fire(WorkspaceEvents.USER_LOGIN,
                    new ParamCallback<UserDTO, UserInfoDTO>(user, callback));
        }
    }

    public void doRegister() {
        if (view.isRegisterFormValid()) {
            view.maskProcessing();

            final I18nLanguageDTO language = new I18nLanguageDTO();
            language.setCode(view.getLanguage());

            final I18nCountryDTO country = new I18nCountryDTO();
            country.setCode(view.getCountry());

            final TimeZoneDTO timezone = new TimeZoneDTO();
            timezone.setId(view.getTimezone());

            final UserDTO user = new UserDTO(view.getLongName(), view.getShortName(), view.getRegisterPassword(), view
                    .getEmail(), language, country, timezone, null, true, SubscriptionMode.manual, "blue");
            final AsyncCallback<UserInfoDTO> callback = new AsyncCallback<UserInfoDTO>() {
                public void onFailure(final Throwable caught) {
                    view.unMask();
                    try {
                        throw caught;
                    } catch (final EmailAddressInUseException e) {
                        view.setRegisterMessage(i18n.t("This email in in use by other person, try with another."),
                                SiteErrorType.error);
                    } catch (final GroupNameInUseException e) {
                        view.setRegisterMessage(i18n.t("This name in already in use, try with a different name."),
                                SiteErrorType.error);
                    } catch (final Throwable e) {
                        view.setRegisterMessage(i18n.t("Error during registration."), SiteErrorType.error);
                        GWT.log("Other kind of exception in user registration", null);
                        throw new RuntimeException();
                    }
                }

                public void onSuccess(final UserInfoDTO userInfoDTO) {
                    listener.userLoggedIn(userInfoDTO);
                    view.unMask();
                    view.showWelcolmeDialog();
                    DefaultDispatcher.getInstance().fire(PlatformEvents.GOTO, userInfoDTO.getShortName());
                }
            };
            DefaultDispatcher.getInstance().fire(WorkspaceEvents.USER_REGISTER,
                    new ParamCallback<UserDTO, UserInfoDTO>(user, callback));
        }
    }

    public Object[][] getCountries() {
        return session.getCountriesArray();
    }

    public I18nLanguageDTO getCurrentLanguage() {
        return session.getCurrentLanguage();
    }

    public Object[][] getLanguages() {
        return session.getLanguagesArray();
    }

    public Object[][] getTimezones() {
        return session.getTimezones();
    }

    public View getView() {
        return view;
    }

    public void init(final LoginView loginview) {
        this.view = loginview;
    }

    public void onCancel() {
        resetMessages();
        reset();
        listener.onLoginCancelled();
    }

    public void onClose() {
        reset();
        view.hideMessages();
        listener.onLoginClose();
    }

    private void reset() {
        view.reset();
    }

    private void resetMessages() {
        view.hideMessages();
    }

}
