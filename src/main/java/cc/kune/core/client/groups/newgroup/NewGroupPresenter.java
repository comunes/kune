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
package cc.kune.core.client.groups.newgroup;

import cc.kune.common.client.errors.UIException;
import cc.kune.common.client.notify.NotifyLevel;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.common.shared.utils.TextUtils;
import cc.kune.core.client.auth.SignIn;
import cc.kune.core.client.errors.GroupLongNameInUseException;
import cc.kune.core.client.errors.GroupShortNameInUseException;
import cc.kune.core.client.events.MyGroupsChangedEvent;
import cc.kune.core.client.resources.CoreMessages;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.GroupServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.SiteTokens;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.state.TokenUtils;
import cc.kune.core.client.state.impl.SessionChecker;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.GroupType;
import cc.kune.core.shared.dto.LicenseDTO;
import cc.kune.core.shared.dto.ReservedWordsRegistryDTO;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.gspace.client.actions.ShowHelpContainerEvent;
import cc.kune.gspace.client.options.GroupOptions;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class NewGroupPresenter.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class NewGroupPresenter extends Presenter<NewGroupView, NewGroupPresenter.NewGroupProxy>
    implements NewGroup {

  /**
   * The Interface NewGroupProxy.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  @ProxyCodeSplit
  public interface NewGroupProxy extends Proxy<NewGroupPresenter> {
  }

  /** The group options. */
  private final GroupOptions groupOptions;

  /** The group service. */
  private final Provider<GroupServiceAsync> groupService;

  /** The i18n. */
  private final I18nTranslationService i18n;

  /** The just created a group. */
  private boolean justCreatedAGroup;

  /** The session. */
  private final Session session;

  private final SessionChecker sessionChecker;

  /** The sign in. */
  private final Provider<SignIn> signIn;

  /** The state manager. */
  private final StateManager stateManager;

  /**
   * Instantiates a new new group presenter.
   * 
   * @param eventBus
   *          the event bus
   * @param view
   *          the view
   * @param proxy
   *          the proxy
   * @param i18n
   *          the i18n
   * @param session
   *          the session
   * @param stateManager
   *          the state manager
   * @param groupService
   *          the group service
   * @param signIn
   *          the sign in
   * @param groupOptions
   *          the group options
   */
  @Inject
  public NewGroupPresenter(final EventBus eventBus, final NewGroupView view, final NewGroupProxy proxy,
      final I18nTranslationService i18n, final Session session, final StateManager stateManager,
      final Provider<GroupServiceAsync> groupService, final Provider<SignIn> signIn,
      final GroupOptions groupOptions, final SessionChecker sessionChecker) {
    super(eventBus, view, proxy);
    this.i18n = i18n;
    this.session = session;
    this.stateManager = stateManager;
    this.groupService = groupService;
    this.signIn = signIn;
    this.groupOptions = groupOptions;
    this.sessionChecker = sessionChecker;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.groups.newgroup.NewGroup#doNewGroup()
   */
  @Override
  public void doNewGroup() {
    justCreatedAGroup = false;
    sessionChecker.check(new AsyncCallbackSimple<Void>() {
      @Override
      public void onSuccess(final Void result) {
        if (session.isLogged()) {
          NotifyUser.showProgress();
          getView().show();
          getView().focusOnLongName();
          NotifyUser.hideProgress();
        } else {
          // signIn.get().showSignInDialog();
          signIn.get().setErrorMessage(i18n.t(CoreMessages.REGISTER_TO_CREATE_A_GROUP), NotifyLevel.info);
          stateManager.gotoHistoryToken(TokenUtils.addRedirect(SiteTokens.SIGN_IN,
              session.getCurrentStateToken().toString()));
        }
      }
    });
  }

  /**
   * Automatically generate a "short name" for a group, after typing the
   * "long name":.
   * 
   * @return the string
   */
  protected String generateShortName() {
    String autoGenShort = getView().getLongName();
    // If there is a short-name, no automatic generation (for not stepping on
    // manually inserted modifications, and doing it just once)
    // If under 3 chars, incorrect LongName => no automatic generation
    if (getView().getShortName() == null && autoGenShort != null && autoGenShort.length() > 2) {
      // to lower-case, transformed accented letters into English characters:
      autoGenShort = TextUtils.deAccent(autoGenShort.toLowerCase());
      // remove not alphanumeric characters
      autoGenShort = autoGenShort.replaceAll("[^a-zA-Z0-9]", "");
      // guarantee <30 chars:
      if (autoGenShort.length() > 30) {
        autoGenShort = autoGenShort.substring(0, 29);
      }
      if (!autoGenShort.isEmpty() && autoGenShort.length() > 2) {
        getView().setShortName(autoGenShort);
        return autoGenShort;
      }
    }
    return null;
  }

  /**
   * Gets the type of group.
   * 
   * @return the type of group
   */
  private GroupType getTypeOfGroup() {
    // Duplicate in GroupOptGeneralPanel
    if (getView().isProject()) {
      return GroupType.PROJECT;
    } else if (getView().isOrganization()) {
      return GroupType.ORGANIZATION;
    } else if (getView().isClosed()) {
      return GroupType.CLOSED;
    } else {
      return GroupType.COMMUNITY;
    }
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
        onRegister();
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
        NewGroupPresenter.this.onClose();
      }
    });
    // When you leave the field "long name", auto-generate the "short name":
    getView().getLongNameField().addHandler(new BlurHandler() {
      @Override
      public void onBlur(final BlurEvent event) {
        generateShortName();
      }
    }, BlurEvent.getType());
  }

  /**
   * On cancel.
   */
  public void onCancel() {
    getView().hide();
    getView().unMask();
  }

  /**
   * On close.
   */
  public void onClose() {
    reset();
    if (!justCreatedAGroup) {
      stateManager.redirectOrRestorePreviousToken(false);
    }
  }

  /**
   * On register.
   */
  private void onRegister() {
    getView().hideMessage();
    final ReservedWordsRegistryDTO reservedWords = session.getInitData().getReservedWords();
    if (reservedWords.contains(getView().getShortName())) {
      getView().setShortNameFailed(i18n.t(CoreMessages.NAME_RESTRICTED));
      getView().setMessage(i18n.t(CoreMessages.NAME_RESTRICTED), NotifyLevel.error);
    } else if (reservedWords.contains(getView().getLongName())) {
      getView().setLongNameFailed(i18n.t(CoreMessages.NAME_RESTRICTED));
      getView().setMessage(i18n.t(CoreMessages.NAME_RESTRICTED), NotifyLevel.error);
    } else if (getView().isFormValid()) {
      getView().maskProcessing();
      final String shortName = getView().getShortName().toLowerCase();
      final String longName = getView().getLongName();
      final String publicDesc = getView().getPublicDesc();
      final LicenseDTO license = session.getDefLicense();
      final GroupDTO group = new GroupDTO(shortName, longName, getTypeOfGroup());
      group.setDefaultLicense(license);

      final AsyncCallback<StateAbstractDTO> callback = new AsyncCallback<StateAbstractDTO>() {
        @Override
        public void onFailure(final Throwable caught) {
          if (caught instanceof GroupShortNameInUseException) {
            getView().unMask();
            final String msg = i18n.t(CoreMessages.NAME_IN_ALREADY_IN_USE);
            getView().setShortNameFailed(msg);
            setMessage(msg, NotifyLevel.error);
          } else if (caught instanceof GroupLongNameInUseException) {
            getView().unMask();
            final String msg = i18n.t(CoreMessages.NAME_IN_ALREADY_IN_USE);
            getView().setLongNameFailed(msg);
            setMessage(msg, NotifyLevel.error);
          } else {
            getView().unMask();
            setMessage(i18n.t("Error creating group"), NotifyLevel.error);
            throw new UIException("Other kind of exception in group registration", caught);
          }
        }

        @Override
        public void onSuccess(final StateAbstractDTO state) {
          justCreatedAGroup = true;
          stateManager.setRetrievedStateAndGo(state);
          getView().hide();
          getView().unMask();
          MyGroupsChangedEvent.fire(getEventBus());
          Scheduler.get().scheduleDeferred(new ScheduledCommand() {
            @Override
            public void execute() {
              groupOptions.showTooltip();
              new Timer() {
                @Override
                public void run() {
                  ShowHelpContainerEvent.fire(getEventBus());
                }
              }.schedule(2000);
            }
          });
        }
      };
      groupService.get().createNewGroup(session.getUserHash(), group, publicDesc, getView().getTags(),
          null, callback);
    }
  }

  /**
   * Reset.
   */
  private void reset() {
    getView().clearData();
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

  /**
   * Sets the message.
   * 
   * @param message
   *          the message
   * @param level
   *          the level
   */
  public void setMessage(final String message, final NotifyLevel level) {
    getView().setMessage(message, level);
  }
}
