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

import cc.kune.common.client.events.ProgressHideEvent;
import cc.kune.common.client.events.ProgressShowEvent;
import cc.kune.common.client.log.Log;
import cc.kune.common.client.notify.NotifyLevel;
import cc.kune.common.client.utils.OnAcceptCallback;
import cc.kune.common.client.utils.TimerWrapper;
import cc.kune.common.client.utils.TimerWrapper.Executer;
import cc.kune.common.shared.utils.TextUtils;
import cc.kune.core.client.auth.SignInPresenter.SignInView;
import cc.kune.core.client.cookies.CookiesManager;
import cc.kune.core.client.errors.UserAuthException;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.resources.CoreMessages;
import cc.kune.core.client.rpcservices.UserServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.SiteTokens;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.UserDTO;
import cc.kune.core.shared.dto.UserInfoDTO;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
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

public class SignInPresenter extends SignInAbstractPresenter<SignInView, SignInPresenter.SignInProxy>
    implements SignIn {

  @ProxyCodeSplit
  public interface SignInProxy extends Proxy<SignInPresenter> {
  }

  public interface SignInView extends SignInAbstractView {

    void focusOnNickname();

    void focusOnPassword();

    HasClickHandlers getAccountRegister();

    String getLoginPassword();

    String getNickOrEmail();

    boolean isSignInFormValid();

    void setLoginPassword(String password);

    void setNickOrEmail(String nickOrEmail);

    void setOnPasswordReturn(OnAcceptCallback onAcceptCallback);

    void validate();

  }

  private final EventBus eventBus;
  private final Provider<Register> registerProvider;
  private final TimerWrapper timer;
  private final UserServiceAsync userService;
  private final WaveClientSimpleAuthenticator waveClientAuthenticator;

  @Inject
  public SignInPresenter(final EventBus eventBus, final SignInView view, final SignInProxy proxy,
      final Session session, final StateManager stateManager, final I18nUITranslationService i18n,
      final UserServiceAsync userService, final Provider<Register> registerProvider,
      final CookiesManager cookiesManager, final UserPassAutocompleteManager autocomplete,
      final TimerWrapper timeWrapper, final WaveClientSimpleAuthenticator waveClientAuthenticator) {
    super(eventBus, view, proxy, session, stateManager, i18n, cookiesManager, autocomplete);
    this.eventBus = eventBus;
    this.userService = userService;
    this.registerProvider = registerProvider;
    this.timer = timeWrapper;
    this.waveClientAuthenticator = waveClientAuthenticator;
  }

  @Override
  public void doSignIn(final String nickOrEmail, final String passwd, final boolean gotoHomePage,
      final AsyncCallback<Void> extCallback) {
    final UserDTO user = new UserDTO();
    user.setShortName(nickOrEmail);
    user.setPassword(passwd);
    saveAutocompleteLoginData(nickOrEmail, passwd);
    waveClientAuthenticator.doLogin(nickOrEmail, passwd, new AsyncCallback<Void>() {
      @Override
      public void onFailure(final Throwable caught) {
        Log.error("SignInPresenter/doLogin fails in Wave auth");
        extCallback.onFailure(caught);
      }

      @Override
      public void onSuccess(final Void arg) {

        final AsyncCallback<UserInfoDTO> callback = new AsyncCallback<UserInfoDTO>() {
          @Override
          public void onFailure(final Throwable caught) {
            extCallback.onFailure(caught);
          }

          @Override
          public void onSuccess(final UserInfoDTO userInfoDTO) {
            onSignIn(userInfoDTO, gotoHomePage);
            extCallback.onSuccess(null);
          }
        };
        userService.login(user.getShortName(), user.getPassword(),
            waveClientAuthenticator.getCookieTokenValue(), callback);
      }
    });
  }

  @Override
  public SignInView getView() {
    return (SignInView) super.getView();
  }

  public void onAccountRegister() {
    getView().reset();
    getView().hideMessages();
    getView().hide();
    stateManager.gotoHistoryToken(SiteTokens.REGISTER);
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
    getView().setOnPasswordReturn(new OnAcceptCallback() {
      @Override
      public void onSuccess() {
        onFormSignIn();
      }
    });
    getView().getSecondBtn().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        Log.debug("On cancel signin presenter");
        onCancel();
      }
    });
    getView().getClose().addCloseHandler(new CloseHandler<PopupPanel>() {
      @Override
      public void onClose(final CloseEvent<PopupPanel> event) {
        Log.debug("Closing signin presenter");
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
      doSignIn(nickOrEmail, passwd, false, new AsyncCallback<Void>() {

        @Override
        public void onFailure(final Throwable caught) {
          onSingInFailed(caught);
        }

        @Override
        public void onSuccess(final Void result) {
          getView().hide();
          getView().unMask();
        }
      });
    }
  }

  private void onSingInFailed(final Throwable caught) {
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
  protected void revealInParent() {
    RevealRootContentEvent.fire(this, this);
  }

  @Override
  public void setErrorMessage(final String message, final NotifyLevel level) {
    getView().setErrorMessage(message, level);
  }

  @Override
  public void showSignInDialog() {
    registerProvider.get().hide();
    registerProvider.get().setGotoTokenOnCancel(this.getGotoTokenOnCancel());
    if (session.isLogged()) {
      stateManager.restorePreviousToken();
    } else {
      eventBus.fireEvent(new ProgressShowEvent());
      getView().show();
      // getView().center();
      eventBus.fireEvent(new ProgressHideEvent());
      getView().focusOnNickname();
      timer.configure(new Executer() {
        @Override
        public void execute() {
          final String savedLogin = autocomplete.getNickOrEmail();
          final String savedPasswd = autocomplete.getPassword();
          if (TextUtils.notEmpty(savedLogin)) {
            getView().setNickOrEmail(savedLogin);
            getView().setLoginPassword(savedPasswd);
            getView().focusOnPassword();
          }
        }
      });
      timer.schedule(500);
    }
  }

}
