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
package cc.kune.chat.client.actions;

import org.waveprotocol.wave.client.events.ClientEvents;
import org.waveprotocol.wave.client.events.NetworkStatusEvent;
import org.waveprotocol.wave.client.events.NetworkStatusEventHandler;

import cc.kune.chat.client.ChatClient;
import cc.kune.chat.client.resources.ChatResources;
import cc.kune.common.client.actions.AbstractAction;
import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuRadioItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuSeparatorDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuTitleItemDescriptor;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.events.WindowFocusEvent;
import cc.kune.core.client.sitebar.SiteUserOptions;
import cc.kune.core.client.sitebar.SiteUserOptionsPresenter;
import cc.kune.core.shared.SessionConstants;

import com.calclab.emite.core.client.events.StateChangedEvent;
import com.calclab.emite.core.client.events.StateChangedHandler;
import com.calclab.emite.core.client.xmpp.session.XmppSession;
import com.calclab.emite.core.client.xmpp.stanzas.Presence;
import com.calclab.emite.core.client.xmpp.stanzas.Presence.Show;
import com.calclab.emite.im.client.presence.PresenceManager;
import com.calclab.emite.im.client.presence.events.OwnPresenceChangedEvent;
import com.calclab.emite.im.client.presence.events.OwnPresenceChangedHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Timer;
import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class ChatSitebarActions.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ChatSitebarActions {

  /**
   * The Class ChangeOfflineStatusAction.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public class ChangeOfflineStatusAction extends AbstractExtendedAction {

    /** The session. */
    private final XmppSession session;

    /**
     * Instantiates a new change offline status action.
     * 
     * @param session
     *          the session
     * @param icon
     *          the icon
     */
    public ChangeOfflineStatusAction(final XmppSession session, final ImageResource icon) {
      this.session = session;
      session.addSessionStateChangedHandler(true, new StateChangedHandler() {
        @Override
        public void onStateChanged(final StateChangedEvent event) {
          if (!session.isReady()) {
            SiteUserOptionsPresenter.LOGGED_USER_MENU.setRightIcon(icon);
          }
        }
      });
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cc.kune.common.client.actions.ActionListener#actionPerformed(cc.kune.
     * common.client.actions.ActionEvent)
     */
    @Override
    public void actionPerformed(final ActionEvent event) {
      session.logout();
    }
  }

  /**
   * The Class ChangeOnlineStatusAction.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public class ChangeOnlineStatusAction extends AbstractExtendedAction {

    /** The icon. */
    private final ImageResource icon;

    /** The manager. */
    private final PresenceManager manager;

    /** The this presence. */
    private final Presence thisPresence;

    /**
     * Instantiates a new change online status action.
     * 
     * @param presenceManager
     *          the presence manager
     * @param statusText
     *          the status text
     * @param show
     *          the show
     * @param icon
     *          the icon
     */
    public ChangeOnlineStatusAction(final PresenceManager presenceManager, final String statusText,
        final Show show, final ImageResource icon) {
      this.manager = presenceManager;
      this.icon = icon;
      thisPresence = Presence.build(statusText, show);
      updateStatusIcon(presenceManager.getOwnPresence());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cc.kune.common.client.actions.ActionListener#actionPerformed(cc.kune.
     * common.client.actions.ActionEvent)
     */
    @Override
    public void actionPerformed(final ActionEvent event) {
      if (!chatClient.loginIfNecessary()) {
        awayTimer.cancel();
        manager.changeOwnPresence(thisPresence);
      }
      nextPresence = thisPresence;
    }

    /**
     * Update status icon.
     * 
     * @param currentPresence
     *          the current presence
     */
    public void updateStatusIcon(final Presence currentPresence) {
      if (thisPresence.getShow().equals(currentPresence.getShow())
          && ((currentPresence.getStatus() == null) || currentPresence.getStatus().equals(
              thisPresence.getStatus()))) {
        SiteUserOptionsPresenter.LOGGED_USER_MENU.setRightIcon(icon);
      }
    }
  }

  /** The Constant AWAY_TIMER_MILLSECS. */
  private static final int AWAY_TIMER_MILLSECS = 60000;

  /** The Constant GROUP_CHAT_STATUS. */
  private static final String GROUP_CHAT_STATUS = "k-group-chat-status";

  /** The Constant NO_STATUS. */
  private static final String NO_STATUS = null;

  /** The away item. */
  private MenuRadioItemDescriptor awayItem;

  /** The away timer. */
  private Timer awayTimer;

  /** The busy item. */
  private MenuRadioItemDescriptor busyItem;

  /** The chat client. */
  private final ChatClient chatClient;

  /** The event bus. */
  private final EventBus eventBus;

  /** The i18n. */
  private final I18nTranslationService i18n;

  /** The next presence. */
  private Presence nextPresence = Presence.build(NO_STATUS, Show.notSpecified);

  /** The offline item. */
  private MenuRadioItemDescriptor offlineItem;

  /** The online item. */
  private MenuRadioItemDescriptor onlineItem;

  /** The presence manager. */
  private final PresenceManager presenceManager;

  /** The res. */
  private final ChatResources res;

  /** The user options. */
  private final SiteUserOptions userOptions;

  /** The xmpp session. */
  private final XmppSession xmppSession;

  /**
   * Instantiates a new chat sitebar actions.
   * 
   * @param session
   *          the session
   * @param chatClient
   *          the chat client
   * @param userOptions
   *          the user options
   * @param i18n
   *          the i18n
   * @param res
   *          the res
   * @param xmppSession
   *          the xmpp session
   * @param presenceManager
   *          the presence manager
   * @param eventBus
   *          the event bus
   */
  @Inject
  public ChatSitebarActions(final SessionConstants session, final ChatClient chatClient,
      final SiteUserOptions userOptions, final I18nTranslationService i18n, final ChatResources res,
      final XmppSession xmppSession, final PresenceManager presenceManager, final EventBus eventBus) {
    this.chatClient = chatClient;
    this.userOptions = userOptions;
    this.i18n = i18n;
    this.res = res;
    this.eventBus = eventBus;
    this.xmppSession = xmppSession;
    this.presenceManager = presenceManager;
    createActions();
    createListener();
  }

  /**
   * Creates the actions.
   */
  private void createActions() {
    final MenuTitleItemDescriptor chatActionsTitle = new MenuTitleItemDescriptor(
        SiteUserOptionsPresenter.LOGGED_USER_MENU, i18n.t("Set your chat status"));
    userOptions.addAction(new MenuSeparatorDescriptor(SiteUserOptionsPresenter.LOGGED_USER_MENU));
    userOptions.addAction(chatActionsTitle);
    onlineItem = createChatStatusAction(res.online(), i18n.t("Available"),
        onlineAction(NO_STATUS, Show.notSpecified, res.online()));
    awayItem = createChatStatusAction(res.away(), i18n.t("Away"),
        onlineAction(NO_STATUS, Show.away, res.away()));
    busyItem = createChatStatusAction(res.busy(), i18n.t("Busy"),
        onlineAction(NO_STATUS, Show.dnd, res.busy()));
    offlineItem = createChatStatusAction(res.offline(), i18n.t("Sign out of chat"),
        new ChangeOfflineStatusAction(xmppSession, res.offline()));
  }

  /**
   * Creates the chat status action.
   * 
   * @param icon
   *          the icon
   * @param text
   *          the text
   * @param action
   *          the action
   * @return the menu radio item descriptor
   */
  private MenuRadioItemDescriptor createChatStatusAction(final ImageResource icon, final String text,
      final AbstractAction action) {
    final MenuRadioItemDescriptor item = new MenuRadioItemDescriptor(
        SiteUserOptionsPresenter.LOGGED_USER_MENU, action, GROUP_CHAT_STATUS);
    item.putValue(AbstractAction.NAME, text);
    item.putValue(AbstractAction.SMALL_ICON, icon);
    return item;
  }

  /**
   * Creates the listener.
   */
  private void createListener() {
    awayTimer = new Timer() {
      @Override
      public void run() {
        final Presence awayPresence = Presence.build(NO_STATUS, Show.away);
        if (chatClient.isXmppLoggedIn()) {
          presenceManager.changeOwnPresence(awayPresence);
          updateMenuPresence(awayPresence);
        }
      }
    };
    xmppSession.addSessionStateChangedHandler(false, new StateChangedHandler() {
      @Override
      public void onStateChanged(final StateChangedEvent event) {
        if (xmppSession.isReady()) {
          awayTimer.cancel();
          presenceManager.changeOwnPresence(nextPresence);
          updateMenuPresence(nextPresence);
        } else {
          offlineItem.setChecked(true);
        }
      }
    });
    presenceManager.addOwnPresenceChangedHandler(new OwnPresenceChangedHandler() {
      @Override
      public void onOwnPresenceChanged(final OwnPresenceChangedEvent event) {
        updateMenuPresence(event.getCurrentPresence());
      }
    });
    eventBus.addHandler(WindowFocusEvent.getType(), new WindowFocusEvent.WindowFocusHandler() {
      @Override
      public void onWindowFocus(final WindowFocusEvent event) {
        if (event.isHasFocus()) {
          awayTimer.cancel();
          if (chatClient.isXmppLoggedIn()) {
            new Timer() {
              @Override
              public void run() {
                presenceManager.changeOwnPresence(nextPresence);
              }
            }.schedule(1000);
          }
        } else {
          awayTimer.schedule(AWAY_TIMER_MILLSECS);
        }
      }
    });
    ClientEvents.get().addNetworkStatusEventHandler(new NetworkStatusEventHandler() {
      @Override
      public void onNetworkStatus(final NetworkStatusEvent event) {
        switch (event.getStatus()) {
        case CONNECTED:
        case RECONNECTED:
          new Timer() {
            @Override
            public void run() {
              ChatSitebarActions.this.chatClient.loginIfNecessary();
            }
          }.schedule(2000);
          break;
        case DISCONNECTED:
        case RECONNECTING:
          break;
        }
      }
    });
  }

  /**
   * Online action.
   * 
   * @param statusText
   *          the status text
   * @param show
   *          the show
   * @param icon
   *          the icon
   * @return the abstract extended action
   */
  private AbstractExtendedAction onlineAction(final String statusText, final Show show,
      final ImageResource icon) {
    return new ChangeOnlineStatusAction(presenceManager, statusText, show, icon);
  }

  /**
   * Update menu presence.
   * 
   * @param presence
   *          the presence
   */
  private void updateMenuPresence(final Presence presence) {
    switch (presence.getShow()) {
    case notSpecified:
      onlineItem.setChecked(true);
      updateSitebarIconPresence(presence, onlineItem);
      break;
    case dnd:
      busyItem.setChecked(true);
      updateSitebarIconPresence(presence, busyItem);
      break;
    case chat:
      onlineItem.setChecked(true);
      updateSitebarIconPresence(presence, onlineItem);
      break;
    case away:
      awayItem.setChecked(true);
      updateSitebarIconPresence(presence, awayItem);
      break;
    }
  }

  /**
   * Update sitebar icon presence.
   * 
   * @param presence
   *          the presence
   * @param itemDescriptor
   *          the item descriptor
   */
  private void updateSitebarIconPresence(final Presence presence, final MenuItemDescriptor itemDescriptor) {
    ((ChatSitebarActions.ChangeOnlineStatusAction) itemDescriptor.getAction()).updateStatusIcon(presence);
  }
}
