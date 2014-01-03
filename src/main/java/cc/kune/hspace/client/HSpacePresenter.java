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
package cc.kune.hspace.client;

import java.util.List;

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.IsActionExtensible;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.events.InboxUnreadUpdatedEvent;
import cc.kune.core.client.events.UserSignInOrSignOutEvent;
import cc.kune.core.client.events.UserSignInOrSignOutEvent.UserSignInOrSignOutHandler;
import cc.kune.core.client.resources.CoreMessages;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.sitebar.spaces.Space;
import cc.kune.core.client.sitebar.spaces.SpaceSelectEvent;
import cc.kune.core.client.sitebar.spaces.SpaceSelectEvent.SpaceSelectHandler;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.SiteTokens;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.ContentSimpleDTO;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.HomeStatsDTO;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.HasText;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class HSpacePresenter.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class HSpacePresenter extends Presenter<HSpacePresenter.HSpaceView, HSpacePresenter.HSpaceProxy>
    implements HSpace {

  /**
   * The Interface HSpaceProxy.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  @ProxyCodeSplit
  public interface HSpaceProxy extends Proxy<HSpacePresenter> {
  }

  /**
   * The Interface HSpaceView.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface HSpaceView extends View {

    /**
     * Blink current tab.
     */
    void blinkCurrentTab();

    /**
     * Gets the global stats total groups count.
     * 
     * @return the global stats total groups count
     */
    HasText getGlobalStatsTotalGroupsCount();

    /**
     * Gets the global stats total users count.
     * 
     * @return the global stats total users count
     */
    HasText getGlobalStatsTotalUsersCount();

    /**
     * Gets the toolbar.
     * 
     * @return the toolbar
     */
    IsActionExtensible getToolbar();

    /**
     * Gets the unread in your inbox.
     * 
     * @return the unread in your inbox
     */
    HasText getUnreadInYourInbox();

    /**
     * Sets the inbox unread visible.
     * 
     * @param visible
     *          the new inbox unread visible
     */
    void setInboxUnreadVisible(boolean visible);

    /**
     * Sets the last contents of my group.
     * 
     * @param lastContentsOfMyGroupsList
     *          the new last contents of my group
     */
    void setLastContentsOfMyGroup(List<ContentSimpleDTO> lastContentsOfMyGroupsList);

    /**
     * Sets the last groups.
     * 
     * @param lastGroups
     *          the new last groups
     */
    void setLastGroups(List<GroupDTO> lastGroups);

    /**
     * Sets the last published contents.
     * 
     * @param lastPublishedContentsList
     *          the new last published contents
     */
    void setLastPublishedContents(List<ContentSimpleDTO> lastPublishedContentsList);

    /**
     * Sets the stats visible.
     * 
     * @param visible
     *          the new stats visible
     */
    void setStatsVisible(boolean visible);

    /**
     * Sets the user groups activity visible.
     * 
     * @param visible
     *          the new user groups activity visible
     */
    void setUserGroupsActivityVisible(boolean visible);
  }

  /** The session. */
  private final Session session;

  /** The state manager. */
  private final StateManager stateManager;

  /** The stats service. */
  private final Provider<ClientStatsServiceAsync> statsService;

  /**
   * Instantiates a new h space presenter.
   * 
   * @param session
   *          the session
   * @param eventBus
   *          the event bus
   * @param view
   *          the view
   * @param proxy
   *          the proxy
   * @param statsService
   *          the stats service
   * @param stateManager
   *          the state manager
   * @param i18n
   *          the i18n
   */
  @Inject
  public HSpacePresenter(final Session session, final EventBus eventBus, final HSpaceView view,
      final HSpaceProxy proxy, final Provider<ClientStatsServiceAsync> statsService,
      final StateManager stateManager, final I18nTranslationService i18n) {
    super(eventBus, view, proxy);
    this.session = session;
    this.statsService = statsService;
    this.stateManager = stateManager;
    eventBus.addHandler(InboxUnreadUpdatedEvent.getType(),
        new InboxUnreadUpdatedEvent.InboxUnreadUpdatedHandler() {
          @Override
          public void onInboxUnreadUpdated(final InboxUnreadUpdatedEvent event) {
            final int total = event.getCount();
            if (total > 0 && session.isLogged()) {
              getView().getUnreadInYourInbox().setText(
                  total == 1 ? i18n.t("One recent conversation unread") : i18n.t(
                      "[%d] recent conversations unread", total));
              getView().setInboxUnreadVisible(true);
            } else {
              getView().setInboxUnreadVisible(false);
            }
          }
        });
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.hspace.client.HSpace#getToolbar()
   */
  @Override
  public IsActionExtensible getToolbar() {
    return getView().getToolbar();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.gwtplatform.mvp.client.HandlerContainerImpl#onBind()
   */
  @Override
  protected void onBind() {
    super.onBind();

    final ButtonDescriptor signInHomeBtn = new ButtonDescriptor(new AbstractExtendedAction() {
      @Override
      public void actionPerformed(final ActionEvent event) {
        stateManager.gotoHistoryTokenButRedirectToCurrent(SiteTokens.SIGN_IN);
      }
    });
    signInHomeBtn.withText(I18n.t(CoreMessages.SIGN_IN_TITLE)).withStyles("k-home-toolbar-btn, k-fl");
    final ButtonDescriptor newGroupHomeBtn = new ButtonDescriptor(new AbstractExtendedAction() {
      @Override
      public void actionPerformed(final ActionEvent event) {
        stateManager.gotoHistoryToken(SiteTokens.NEW_GROUP);
      }
    });
    newGroupHomeBtn.withText(I18n.t(CoreMessages.NEW_GROUP_TITLE)).withStyles("k-home-toolbar-btn, k-fr");
    getView().getToolbar().add(signInHomeBtn);
    getView().getToolbar().add(newGroupHomeBtn);
    final AsyncCallbackSimple<HomeStatsDTO> callback = new AsyncCallbackSimple<HomeStatsDTO>() {
      @Override
      public void onSuccess(final HomeStatsDTO result) {
        getView().getGlobalStatsTotalGroupsCount().setText(result.getTotalGroups().toString());
        getView().getGlobalStatsTotalUsersCount().setText(result.getTotalUsers().toString());
        getView().setLastGroups(result.getLastGroups());
        getView().setLastPublishedContents(result.getLastPublishedContents());
        getView().setStatsVisible(true);
        final List<ContentSimpleDTO> lastContentsOfMyGroups = result.getLastContentsOfMyGroups();
        final boolean logged = session.isLogged();
        final boolean myGroupsHasActivity = logged && lastContentsOfMyGroups != null
            && lastContentsOfMyGroups.size() > 0;
        if (myGroupsHasActivity) {
          getView().setLastContentsOfMyGroup(lastContentsOfMyGroups);
        }
        getView().setUserGroupsActivityVisible(myGroupsHasActivity);
        getView().setInboxUnreadVisible(logged);
        getEventBus().addHandler(SpaceSelectEvent.getType(), new SpaceSelectHandler() {
          @Override
          public void onSpaceSelect(final SpaceSelectEvent event) {
            if (event.getSpace().equals(Space.homeSpace)) {
              getView().blinkCurrentTab();
            }
          }
        });
        getView().blinkCurrentTab();
      }
    };
    session.onUserSignInOrSignOut(true, new UserSignInOrSignOutHandler() {
      @Override
      public void onUserSignInOrSignOut(final UserSignInOrSignOutEvent event) {
        final boolean logged = event.isLogged();
        signInHomeBtn.setVisible(!logged);
        if (logged) {
          statsService.get().getHomeStats(session.getUserHash(), callback);
        } else {
          statsService.get().getHomeStats(callback);
        }
      }
    });
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.gwtplatform.mvp.client.PresenterWidget#onReveal()
   */
  @Override
  protected void onReveal() {
    super.onReveal();
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
