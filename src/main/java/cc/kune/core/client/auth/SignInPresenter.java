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
package cc.kune.core.client.auth;

import cc.kune.common.client.noti.NotifyLevel;
import cc.kune.core.client.cookies.CookiesManager;
import cc.kune.core.client.errors.UserAuthException;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.logs.Log;
import cc.kune.core.client.notify.spiner.ProgressHideEvent;
import cc.kune.core.client.notify.spiner.ProgressShowEvent;
import cc.kune.core.client.resources.CoreMessages;
import cc.kune.core.client.rpcservices.UserServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.SiteCommonTokens;
import cc.kune.core.client.state.StateManager;
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

public class SignInPresenter extends SignInAbstractPresenter<SignInView, SignInPresenter.SignInProxy> implements SignIn {

    @ProxyCodeSplit
    public interface SignInProxy extends Proxy<SignInPresenter> {
    }

    private final EventBus eventBus;
    private final Provider<Register> registerProvider;
    private final UserServiceAsync userService;

    @Inject
    public SignInPresenter(final EventBus eventBus, final SignInView view, final SignInProxy proxy,
            final Session session, final StateManager stateManager, final I18nUITranslationService i18n,
            final UserServiceAsync userService, final Provider<Register> registerProvider,
            final CookiesManager cookiesManager) {
        super(eventBus, view, proxy, session, stateManager, i18n, cookiesManager);
        this.eventBus = eventBus;
        this.userService = userService;
        this.registerProvider = registerProvider;
    }

    @Override
    public void doSignIn() {
        registerProvider.get().hide();
        if (session.isLogged()) {
            stateManager.restorePreviousToken();
        } else {
            eventBus.fireEvent(new ProgressShowEvent());
            getView().show();
            // getView().center();
            eventBus.fireEvent(new ProgressHideEvent());
            getView().focusOnNickname();
        }
    }

    @Override
    public SignInView getView() {
        return (SignInView) super.getView();
    }

    public void onAccountRegister() {
        getView().reset();
        getView().hideMessages();
        getView().hide();
        stateManager.gotoToken(SiteCommonTokens.REGISTER);
    }

    @Override
    protected void onBind() {
        super.onBind();
        getView().getFirstBtn().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(final ClickEvent event) {
                onFormSignIn();
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
                SignInPresenter.this.onClose();
            }
        });
        getView().getAccountRegister().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(final ClickEvent event) {
                onAccountRegister();
            }
        });
    }

    public void onFormSignIn() {
        getView().validate();
        if (getView().isSignInFormValid()) {
            getView().maskProcessing();

            final String nickOrEmail = getView().getNickOrEmail();
            final String passwd = getView().getLoginPassword();

            final UserDTO user = new UserDTO();
            user.setShortName(nickOrEmail);
            user.setPassword(passwd);

            final AsyncCallback<UserInfoDTO> callback = new AsyncCallback<UserInfoDTO>() {
                @Override
                public void onFailure(final Throwable caught) {
                    getView().unMask();
                    eventBus.fireEvent(new ProgressHideEvent());
                    if (caught instanceof UserAuthException) {
                        getView().setErrorMessage(i18n.t(CoreMessages.INCORRECT_NICKNAME_EMAIL_OR_PASSWORD),
                                NotifyLevel.error);
                    } else {
                        getView().setErrorMessage("Error in login", NotifyLevel.error);
                        Log.error("Other kind of exception in SignInPresenter/doLogin");
                    }
                }

                @Override
                public void onSuccess(final UserInfoDTO userInfoDTO) {
                    onSignIn(userInfoDTO);
                    stateManager.restorePreviousToken();
                    getView().hide();
                    getView().unMask();
                }
            };
            userService.login(user.getShortName(), user.getPassword(), callback);
        }
    }

    @Override
    protected void revealInParent() {
        RevealRootContentEvent.fire(this, this);
    }
}
