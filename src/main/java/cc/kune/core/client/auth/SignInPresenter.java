/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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

import cc.kune.common.client.log.Log;
import cc.kune.common.client.notify.NotifyLevel;
import cc.kune.common.client.notify.ProgressHideEvent;
import cc.kune.common.client.notify.ProgressShowEvent;
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
import cc.kune.core.client.sitebar.auth.AskForPasswordResetPanel;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.SiteTokens;
import cc.kune.core.client.state.StateManager;
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

// TODO: Auto-generated Javadoc
/**
 * The Class SignInPresenter.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class SignInPresenter extends SignInAbstractPresenter<SignInView, SignInPresenter.SignInProxy>
    implements SignIn {

  /**
   * The Interface SignInProxy.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  @ProxyCodeSplit
  public interface SignInProxy extends Proxy<SignInPresenter> {
  }

  /**
   * The Interface SignInView.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface SignInView extends SignInAbstractView {

    /**
     * Focus on nickname.
     */
    void focusOnNickname();

    /**
     * Focus on password.
     */
    void focusOnPassword();

    /**
     * Gets the account register.
     * 
     * @return the account register
     */
    HasClickHandlers getAccountRegister();

    /**
     * Gets the forgot passwd.
     * 
     * @return the forgot passwd
     */
    HasClickHandlers getForgotPasswd();

    /**
     * Gets the login password.
     * 
     * @return the login password
     */
    String getLoginPassword();

    /**
     * Gets the nick or email.
     * 
     * @return the nick or email
     */
    String getNickOrEmail();

    /**
     * Checks if is sign in form valid.
     * 
     * @return true, if is sign in form valid
     */
    boolean isSignInFormValid();

    void setHeaderLogo(String url);

    /**
     * Sets the login password.
     * 
     * @param password
     *          the new login password
     */
    void setLoginPassword(String password);

    /**
     * Sets the nick or email.
     * 
     * @param nickOrEmail
     *          the new nick or email
     */
    void setNickOrEmail(String nickOrEmail);

    /**
     * Sets the on password return.
     * 
     * @param onAcceptCallback
     *          the new on password return
     */
    void setOnPasswordReturn(OnAcceptCallback onAcceptCallback);

    void setTitleIcon(String url);

    /**
     * Validate.
     */
    void validate();

  }

  /** The ask passwd reset. */
  private final Provider<AskForPasswordResetPanel> askPasswdReset;

  /** The event bus. */
  private final EventBus eventBus;

  /** The register provider. */
  private final Provider<Register> registerProvider;

  /** The timer. */
  private final TimerWrapper timer;

  /** The user service. */
  private final UserServiceAsync userService;

  /** The wave client authenticator. */
  private final WaveClientSimpleAuthenticator waveClientAuthenticator;

  /**
   * Instantiates a new sign in presenter.
   * 
   * @param eventBus
   *          the event bus
   * @param view
   *          the view
   * @param proxy
   *          the proxy
   * @param session
   *          the session
   * @param stateManager
   *          the state manager
   * @param i18n
   *          the i18n
   * @param userService
   *          the user service
   * @param registerProvider
   *          the register provider
   * @param cookiesManager
   *          the cookies manager
   * @param loginRemember
   *          the login remember
   * @param timeWrapper
   *          the time wrapper
   * @param waveClientAuthenticator
   *          the wave client authenticator
   * @param askPasswdReset
   *          the ask passwd reset
   */
  @Inject
  public SignInPresenter(final EventBus eventBus, final SignInView view, final SignInProxy proxy,
      final Session session, final StateManager stateManager, final I18nUITranslationService i18n,
      final UserServiceAsync userService, final Provider<Register> registerProvider,
      final CookiesManager cookiesManager, final LoginRememberManager loginRemember,
      final TimerWrapper timeWrapper, final WaveClientSimpleAuthenticator waveClientAuthenticator,
      final Provider<AskForPasswordResetPanel> askPasswdReset) {
    super(eventBus, view, proxy, session, stateManager, i18n, cookiesManager, loginRemember);
    this.eventBus = eventBus;
    this.userService = userService;
    this.registerProvider = registerProvider;
    this.timer = timeWrapper;
    this.waveClientAuthenticator = waveClientAuthenticator;
    this.askPasswdReset = askPasswdReset;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.auth.SignIn#doSignIn(java.lang.String,
   * java.lang.String, boolean, com.google.gwt.user.client.rpc.AsyncCallback)
   */
  private void continueLogin(final String shortName, final String passwd, final boolean gotoHomePage,
      final AsyncCallback<Void> extCallback) {
    saveAutocompleteLoginData(shortName, passwd);
    waveClientAuthenticator.doLogin(shortName, passwd, new AsyncCallback<Void>() {
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
            onSignIn(userInfoDTO, gotoHomePage, passwd);
            extCallback.onSuccess(null);
          }
        };
        userService.login(shortName, passwd, waveClientAuthenticator.getCookieTokenValue(), callback);
      }
    });
  }

  @Override
  public void doSignIn(final String nickOrEmail, final String passwd, final boolean gotoHomePage,
      final AsyncCallback<Void> extCallback) {
    final boolean isEmail = nickOrEmail.matches(TextUtils.EMAIL_REGEXP);
    if (isEmail) {
      userService.preLoginWithEmail(nickOrEmail, passwd, new AsyncCallback<String>() {
        @Override
        public void onFailure(final Throwable caught) {
          Log.error("SignInPresenter/doLogin fails pre auth with email");
          extCallback.onFailure(caught);
        }

        @Override
        public void onSuccess(final String shortName) {
          // We get the username of that email, so we continue the login
          continueLogin(shortName, passwd, gotoHomePage, extCallback);
        }
      });
    } else {
      // It's a nickname aka user shortname, so, do login with this
      continueLogin(nickOrEmail, passwd, gotoHomePage, extCallback);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.auth.SignInAbstractPresenter#getView()
   */
  @Override
  public SignInView getView() {
    return (SignInView) super.getView();
  }

  /**
   * On account register.
   */
  public void onAccountRegister() {
    getView().reset();
    getView().hideMessages();
    getView().hide();
    stateManager.gotoHistoryToken(SiteTokens.REGISTER);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.gwtplatform.mvp.client.HandlerContainerImpl#onBind()
   */
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
    getView().getForgotPasswd().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        askPasswdReset.get().show();
      }
    });
  }

  /**
   * On form sign in.
   */
  public void onFormSignIn() {
    getView().validate();
    if (getView().isSignInFormValid()) {
      getView().maskProcessing();

      final String nickOrEmail = getView().getNickOrEmail().toLowerCase();
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

  /**
   * On sing in failed.
   * 
   * @param caught
   *          the caught
   */
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

  /*
   * (non-Javadoc)
   * 
   * @see com.gwtplatform.mvp.client.Presenter#revealInParent()
   */
  @Override
  protected void revealInParent() {
    RevealRootContentEvent.fire(this, this);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.auth.SignIn#setErrorMessage(java.lang.String,
   * cc.kune.common.client.notify.NotifyLevel)
   */
  @Override
  public void setErrorMessage(final String message, final NotifyLevel level) {
    getView().setErrorMessage(message, level);
  }

  @Override
  public void setHeaderLogo(final String url) {
    getView().setHeaderLogo(url);
  }

  @Override
  public void setTitleIcon(final String url) {
    getView().setTitleIcon(url);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.auth.SignIn#showSignInDialog()
   */
  @Override
  public void showSignInDialog(final String token) {
    setGotoTokenOnSuccess(token);
    registerProvider.get().hide();
    registerProvider.get().setGotoTokenOnCancel(this.getGotoTokenOnCancel());
    if (session.isLogged()) {
      stateManager.restorePreviousToken(false);
    } else {
      eventBus.fireEvent(new ProgressShowEvent());
      getView().show();
      eventBus.fireEvent(new ProgressHideEvent());
      getView().focusOnNickname();
      timer.configure(new Executer() {
        @Override
        public void execute() {
          final String savedLogin = loginRemember.getNickOrEmail();
          final String savedPasswd = loginRemember.getPassword();
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
