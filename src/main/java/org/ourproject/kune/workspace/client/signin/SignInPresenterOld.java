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

import org.ourproject.kune.platf.client.dto.I18nCountryDTO;
import org.ourproject.kune.platf.client.dto.I18nLanguageDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.dto.TimeZoneDTO;
import org.ourproject.kune.platf.client.dto.UserDTO;
import org.ourproject.kune.platf.client.dto.UserInfoDTO;
import org.ourproject.kune.platf.client.errors.EmailAddressInUseException;
import org.ourproject.kune.platf.client.errors.GroupNameInUseException;
import org.ourproject.kune.platf.client.errors.UserAuthException;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.newgroup.SiteErrorType;
import org.ourproject.kune.workspace.client.site.Site;
import org.ourproject.kune.workspace.client.site.rpc.UserServiceAsync;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.emiteuimodule.client.SubscriptionMode;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class SignInPresenterOld implements SignInOld {

    SignInViewOld view;
    private final Session session;
    private final I18nUITranslationService i18n;
    private final UserServiceAsync userService;
    private final StateManager stateManager;
    private StateToken previousStateToken;

    public SignInPresenterOld(final Session session, final StateManager stateManager, final I18nUITranslationService i18n,
            final UserServiceAsync userService) {
        this.session = session;
        this.stateManager = stateManager;
        this.userService = userService;
        this.i18n = i18n;
    }

    public void doSignIn(final StateToken previousStateToken) {
        this.previousStateToken = previousStateToken;
        if (!session.isLogged()) {
            Site.showProgressProcessing();
            view.show();
            view.center();
            Site.hideProgress();
        } else {
            stateManager.gotoToken(previousStateToken);
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

    public void init(final SignInViewOld loginview) {
        this.view = loginview;
    }

    public void onCancel() {
        resetMessages();
        reset();
        view.hide();
        stateManager.gotoToken(previousStateToken);
    }

    public void onClose() {
        reset();
        view.hideMessages();
        if (!session.isLogged()) {
            stateManager.gotoToken(previousStateToken);
        }
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

            boolean wantPersonalHomepage = view.wantPersonalHomepage();

            final UserDTO user = new UserDTO(view.getLongName(), view.getShortName(), view.getRegisterPassword(),
                    view.getEmail(), language, country, timezone, null, true, SubscriptionMode.manual, "blue");
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
                        GWT.log("Other kind of exception in user registration" + e.getMessage() + ", "
                                + e.getLocalizedMessage(), null);
                        e.printStackTrace();
                        throw new RuntimeException();
                    }
                }

                public void onSuccess(final UserInfoDTO userInfoDTO) {
                    stateManager.gotoToken(userInfoDTO.getHomePage());
                    onSignIn(userInfoDTO);
                    view.hide();
                    view.unMask();
                    view.showWelcolmeDialog();
                }
            };
            userService.createUser(user, wantPersonalHomepage, callback);
        }
    }

    protected void onFormSignIn() {
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
                        Log.error("Other kind of exception in LoginFormPresenter/doLogin");
                    }
                }

                public void onSuccess(final UserInfoDTO userInfoDTO) {
                    onSignIn(userInfoDTO);
                    stateManager.gotoToken(previousStateToken);
                    view.hide();
                    view.unMask();
                }
            };
            userService.login(user.getShortName(), user.getPassword(), callback);
        }
    }

    private void onSignIn(final UserInfoDTO userInfoDTO) {
        final String userHash = userInfoDTO.getUserHash();
        view.setCookie(userHash);
        session.setUserHash(userHash);
        session.setCurrentUserInfo(userInfoDTO);
        final I18nLanguageDTO language = userInfoDTO.getLanguage();
        i18n.changeCurrentLanguage(language.getCode());
        session.setCurrentLanguage(language);
    }

    private void reset() {
        view.reset();
    }

    private void resetMessages() {
        view.hideMessages();
    }

}
