/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package cc.kune.core.client.auth;

import cc.kune.common.client.errors.UIException;
import cc.kune.common.client.noti.NotifyLevel;
import cc.kune.common.client.noti.NotifyUser;
import cc.kune.core.client.cookies.CookiesManager;
import cc.kune.core.client.errors.EmailAddressInUseException;
import cc.kune.core.client.errors.GroupNameInUseException;
import cc.kune.core.client.errors.UserRegistrationException;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.logs.Log;
import cc.kune.core.client.notify.msgs.UserNotifyEvent;
import cc.kune.core.client.resources.CoreMessages;
import cc.kune.core.client.rpcservices.UserServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.I18nCountryDTO;
import cc.kune.core.shared.dto.I18nLanguageDTO;
import cc.kune.core.shared.dto.SubscriptionMode;
import cc.kune.core.shared.dto.TimeZoneDTO;
import cc.kune.core.shared.dto.UserDTO;
import cc.kune.core.shared.dto.UserInfoDTO;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

public class RegisterPresenter extends SignInAbstractPresenter<RegisterView, RegisterPresenter.RegisterProxy> implements
        Register {

    @ProxyCodeSplit
    public interface RegisterProxy extends Proxy<RegisterPresenter> {
    }
    private final Provider<SignIn> signInProvider;

    private final Provider<UserServiceAsync> userServiceProvider;

    @Inject
    public RegisterPresenter(final EventBus eventBus, final RegisterView view, final RegisterProxy proxy,
            final Session session, final StateManager stateManager, final I18nUITranslationService i18n,
            final Provider<UserServiceAsync> userServiceProvider, final Provider<SignIn> signInProvider,
            final CookiesManager cookiesManager, final UserPassAutocompleteManager autocomplete) {
        super(eventBus, view, proxy, session, stateManager, i18n, cookiesManager, autocomplete);
        this.userServiceProvider = userServiceProvider;
        this.signInProvider = signInProvider;
    }

    @Override
    public void doRegister() {
        signInProvider.get().hide();
        if (!session.isLogged()) {
            NotifyUser.showProgressProcessing();
            getView().show();
            // getView().center();
            NotifyUser.hideProgress();
        } else {
            stateManager.restorePreviousToken();
        }
    }

    @Override
    public RegisterView getView() {
        return (RegisterView) super.getView();
    }

    @Override
    protected void onBind() {
        super.onBind();
        getView().getFirstBtn().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(final ClickEvent event) {
                getView().validate();
                if (getView().isValid()) {
                    onFormRegister();
                }
            }
        });
        getView().getSecondBtn().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(final ClickEvent event) {
                onCancel();
            }
        });
        getView().getClose().addCloseHandler(new CloseHandler<PopupPanel>() {

            @Override
            public void onClose(final CloseEvent<PopupPanel> event) {
                Log.debug("Closing register presenter");
                RegisterPresenter.this.onClose();
            }
        });
    }

    public void onFormRegister() {
        if (getView().isRegisterFormValid()) {
            getView().maskProcessing();

            final I18nLanguageDTO language = new I18nLanguageDTO();
            language.setCode(i18n.getCurrentLanguage());

            final I18nCountryDTO country = new I18nCountryDTO();
            country.setCode("GB");

            final TimeZoneDTO timezone = new TimeZoneDTO();
            timezone.setId("GMT");

            final boolean wantHomepage = true;

            final UserDTO user = new UserDTO(getView().getLongName(), getView().getShortName(),
                    getView().getRegisterPassword(), getView().getEmail(), language, country, timezone, null, true,
                    SubscriptionMode.manual, "blue");
            super.saveAutocompleteLoginData(getView().getShortName(), getView().getRegisterPassword());
            final AsyncCallback<UserInfoDTO> callback = new AsyncCallback<UserInfoDTO>() {
                @Override
                public void onFailure(final Throwable caught) {
                    getView().unMask();
                    if (caught instanceof EmailAddressInUseException) {
                        getView().setErrorMessage(i18n.t(CoreMessages.EMAIL_IN_USE), NotifyLevel.error);
                    } else if (caught instanceof GroupNameInUseException) {
                        getView().setErrorMessage(i18n.t(CoreMessages.NAME_IN_USE), NotifyLevel.error);
                    } else if (caught instanceof UserRegistrationException) {
                        getView().setErrorMessage(i18n.t("Error during registration. " + caught.getMessage()),
                                NotifyLevel.error);
                    } else {
                        getView().setErrorMessage(i18n.t("Error during registration."), NotifyLevel.error);
                        throw new UIException("Other kind of exception in user registration", caught);
                    }
                }

                @Override
                public void onSuccess(final UserInfoDTO userInfoDTO) {
                    onSignIn(userInfoDTO);
                    stateManager.gotoToken(userInfoDTO.getHomePage());
                    getView().hide();
                    getView().unMask();
                    if (wantHomepage) {
                        showWelcolmeDialog();
                    } else {
                        showWelcolmeDialogNoHomepage();
                    }
                }

                private void showWelcolmeDialog() {
                    getEventBus().fireEvent(
                            new UserNotifyEvent(
                                    NotifyLevel.info,
                                    i18n.t("Welcome"),
                                    i18n.t("Thanks for joining this site. "
                                            + "Now you can actively participate in this site. "
                                            + "You can also use your personal space to publish contents. "
                                            + "Note: your email is not verified, please follow the instructions you will receive by email."),
                                    true));
                }

                private void showWelcolmeDialogNoHomepage() {
                    getEventBus().fireEvent(
                            new UserNotifyEvent(
                                    NotifyLevel.info,
                                    i18n.t("Welcome"),
                                    i18n.t("Thanks for joining this site"
                                            + "Now you can actively participate in this site. "
                                            + "Note: your email is not verified, please follow the instructions you will receive by email."),
                                    true));
                }
            };
            userServiceProvider.get().createUser(user, wantHomepage, callback);
        }
    }

    @Override
    protected void revealInParent() {
        RevealRootContentEvent.fire(this, this);
    }
}
