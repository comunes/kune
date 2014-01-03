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

import cc.kune.common.client.errors.UIException;
import cc.kune.common.client.log.Log;
import cc.kune.common.client.notify.NotifyLevel;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.core.client.auth.RegisterPresenter.RegisterView;
import cc.kune.core.client.cookies.CookiesManager;
import cc.kune.core.client.errors.EmailAddressInUseException;
import cc.kune.core.client.errors.GroupLongNameInUseException;
import cc.kune.core.client.errors.GroupShortNameInUseException;
import cc.kune.core.client.errors.UserRegistrationException;
import cc.kune.core.client.events.NewUserRegisteredEvent;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.resources.CoreMessages;
import cc.kune.core.client.rpcservices.UserServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.I18nCountryDTO;
import cc.kune.core.shared.dto.I18nLanguageDTO;
import cc.kune.core.shared.dto.ReservedWordsRegistryDTO;
import cc.kune.core.shared.dto.SubscriptionMode;
import cc.kune.core.shared.dto.TimeZoneDTO;
import cc.kune.core.shared.dto.UserDTO;

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

// TODO: Auto-generated Javadoc
/**
 * The Class RegisterPresenter.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class RegisterPresenter extends
    SignInAbstractPresenter<RegisterView, RegisterPresenter.RegisterProxy> implements Register {

  /**
   * The Interface RegisterProxy.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  @ProxyCodeSplit
  public interface RegisterProxy extends Proxy<RegisterPresenter> {
  }

  /**
   * The Interface RegisterView.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface RegisterView extends SignInAbstractView {

    /**
     * Gets the email.
     * 
     * @return the email
     */
    String getEmail();

    /**
     * Gets the long name.
     * 
     * @return the long name
     */
    String getLongName();

    /**
     * Gets the register password.
     * 
     * @return the register password
     */
    String getRegisterPassword();

    /**
     * Gets the short name.
     * 
     * @return the short name
     */
    String getShortName();

    /**
     * Checks if is register form valid.
     * 
     * @return true, if is register form valid
     */
    boolean isRegisterFormValid();

    /**
     * Checks if is valid.
     * 
     * @return true, if is valid
     */
    boolean isValid();

    /**
     * Sets the email failed.
     * 
     * @param msg
     *          the new email failed
     */
    void setEmailFailed(final String msg);

    /**
     * Sets the long name failed.
     * 
     * @param msg
     *          the new long name failed
     */
    void setLongNameFailed(final String msg);

    /**
     * Sets the short name failed.
     * 
     * @param msg
     *          the new short name failed
     */
    void setShortNameFailed(final String msg);

    /**
     * Validate.
     */
    void validate();

  }

  /** The sign in provider. */
  private final Provider<SignIn> signInProvider;

  /** The user service provider. */
  private final Provider<UserServiceAsync> userServiceProvider;

  /**
   * Instantiates a new register presenter.
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
   * @param userServiceProvider
   *          the user service provider
   * @param signInProvider
   *          the sign in provider
   * @param cookiesManager
   *          the cookies manager
   * @param loginRemember
   *          the login remember
   */
  @Inject
  public RegisterPresenter(final EventBus eventBus, final RegisterView view, final RegisterProxy proxy,
      final Session session, final StateManager stateManager, final I18nUITranslationService i18n,
      final Provider<UserServiceAsync> userServiceProvider, final Provider<SignIn> signInProvider,
      final CookiesManager cookiesManager, final LoginRememberManager loginRemember) {
    super(eventBus, view, proxy, session, stateManager, i18n, cookiesManager, loginRemember);
    this.userServiceProvider = userServiceProvider;
    this.signInProvider = signInProvider;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.auth.Register#doRegister()
   */
  @Override
  public void doRegister(final String onSuccessToken) {
    setGotoTokenOnSuccess(onSuccessToken);
    signInProvider.get().hide();
    if (!session.isLogged()) {
      NotifyUser.showProgress();
      getView().show();
      NotifyUser.hideProgress();
    } else {
      stateManager.restorePreviousToken(false);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.auth.SignInAbstractPresenter#getView()
   */
  @Override
  public RegisterView getView() {
    return (RegisterView) super.getView();
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

  /**
   * On form register.
   */
  private void onFormRegister() {
    getView().hideMessages();
    final ReservedWordsRegistryDTO reservedWords = session.getInitData().getReservedWords();
    if (reservedWords.contains(getView().getShortName())) {
      getView().setShortNameFailed(i18n.t(CoreMessages.NAME_RESTRICTED));
      getView().setErrorMessage(i18n.t(CoreMessages.NAME_RESTRICTED), NotifyLevel.error);
    } else if (reservedWords.contains(getView().getLongName())) {
      getView().setLongNameFailed(i18n.t(CoreMessages.NAME_RESTRICTED));
      getView().setErrorMessage(i18n.t(CoreMessages.NAME_RESTRICTED), NotifyLevel.error);
    } else if (getView().isRegisterFormValid()) {
      getView().maskProcessing();

      final I18nLanguageDTO language = new I18nLanguageDTO();
      language.setCode(i18n.getCurrentLanguage());

      final I18nCountryDTO country = new I18nCountryDTO();
      country.setCode("GB");

      final TimeZoneDTO timezone = new TimeZoneDTO();
      timezone.setId("GMT");

      final boolean wantHomepage = true;

      final UserDTO user = new UserDTO(getView().getShortName().toLowerCase(), getView().getLongName(),
          getView().getRegisterPassword(), getView().getEmail(), language, country, timezone, null,
          true, SubscriptionMode.manual, "blue");
      super.saveAutocompleteLoginData(getView().getShortName(), getView().getRegisterPassword());
      final AsyncCallback<Void> callback = new AsyncCallback<Void>() {
        final String siteCommonName = i18n.getSiteCommonName();

        @Override
        public void onFailure(final Throwable caught) {
          onRegistrationFailure(caught);
        }

        @Override
        public void onSuccess(final Void arg0) {
          signInProvider.get().doSignIn(getView().getShortName(), getView().getRegisterPassword(), true,
              new AsyncCallback<Void>() {

                @Override
                public void onFailure(final Throwable caught) {
                  onRegistrationFailure(caught);
                }

                @Override
                public void onSuccess(final Void result) {
                  NewUserRegisteredEvent.fire(getEventBus());
                  getView().hide();
                  getView().unMask();
                  if (wantHomepage) {
                    showWelcolmeDialog();
                  } else {
                    showWelcolmeDialogNoHomepage();
                  }
                }
              });
        }

        private void showWelcolmeDialog() {
          NotifyUser.info(i18n.t("Welcome"), i18n.t("Thanks for joining [%s]. "
              + "Now you can actively participate in [%s]. "
              + "You can also use your personal space to publish contents. ",
          // +
          // "Note: your email is not verified, please follow the instructions you will receive by email.",
              siteCommonName, siteCommonName), Register.WELCOME_ID, true);
        }

        private void showWelcolmeDialogNoHomepage() {
          NotifyUser.info(i18n.t("Welcome"), i18n.t("Thanks for joining [%s]. "
              + "Now you can actively participate in [%s]. ",
          // +
          // "Note: your email is not verified, please follow the instructions you will receive by email.",
              siteCommonName, siteCommonName), Register.WELCOME_ID, true);
        }
      };
      userServiceProvider.get().createUser(user, wantHomepage, callback);
    }
  }

  /**
   * On registration failure.
   * 
   * @param caught
   *          the caught
   */
  private void onRegistrationFailure(final Throwable caught) {
    getView().unMask();
    if (caught instanceof EmailAddressInUseException) {
      getView().setEmailFailed(i18n.t(CoreMessages.EMAIL_IN_USE));
      getView().setErrorMessage(i18n.t(CoreMessages.EMAIL_IN_USE), NotifyLevel.error);
    } else if (caught instanceof GroupShortNameInUseException) {
      getView().setShortNameFailed(i18n.t(CoreMessages.NAME_IN_USE));
      getView().setErrorMessage(i18n.t(CoreMessages.NAME_IN_USE), NotifyLevel.error);
    } else if (caught instanceof GroupLongNameInUseException) {
      getView().setLongNameFailed(i18n.t(CoreMessages.NAME_IN_USE));
      getView().setErrorMessage(i18n.t(CoreMessages.NAME_IN_USE), NotifyLevel.error);
    } else if (caught instanceof UserRegistrationException) {
      getView().setErrorMessage(i18n.t("Error during registration. " + caught.getMessage()),
          NotifyLevel.error);
    } else {
      getView().setErrorMessage(i18n.t("Error during registration."), NotifyLevel.error);
      throw new UIException("Other kind of exception in user registration", caught);
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
}
