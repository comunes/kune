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
package cc.kune.chat.client;

import java.util.Date;

import cc.kune.chat.client.ShowChatDialogEvent.ShowChatDialogHandler;
import cc.kune.chat.client.ToggleShowChatDialogEvent.ToggleShowChatDialogHandler;
import cc.kune.chat.client.resources.ChatResources;
import cc.kune.chat.client.snd.KuneSoundManager;
import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ActionStyles;
import cc.kune.common.client.actions.KeyStroke;
import cc.kune.common.client.actions.Shortcut;
import cc.kune.common.client.actions.ui.ParentWidget;
import cc.kune.common.client.actions.ui.descrip.IconLabelDescriptor;
import cc.kune.common.client.actions.ui.descrip.ToolbarSeparatorDescriptor;
import cc.kune.common.client.actions.ui.descrip.ToolbarSeparatorDescriptor.Type;
import cc.kune.common.client.log.Log;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.shortcuts.GlobalShortcutRegister;
import cc.kune.common.client.utils.WindowUtils;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.common.shared.utils.SimpleResponseCallback;
import cc.kune.common.shared.utils.TextUtils;
import cc.kune.core.client.events.AppStartEvent;
import cc.kune.core.client.events.AppStopEvent;
import cc.kune.core.client.events.AvatarChangedEvent;
import cc.kune.core.client.events.NewUserRegisteredEvent;
import cc.kune.core.client.events.UserSignInEvent;
import cc.kune.core.client.events.UserSignInEvent.UserSignInHandler;
import cc.kune.core.client.events.UserSignOutEvent;
import cc.kune.core.client.events.UserSignOutEvent.UserSignOutHandler;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.services.ClientFileDownloadUtils;
import cc.kune.core.client.sitebar.SitebarActions;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.dto.UserInfoDTO;

import com.calclab.emite.core.client.xmpp.session.XmppSession;
import com.calclab.emite.core.client.xmpp.stanzas.XmppURI;
import com.calclab.emite.im.client.chat.ChatManager;
import com.calclab.emite.im.client.presence.PresenceManager;
import com.calclab.emite.im.client.roster.RosterItem;
import com.calclab.emite.im.client.roster.SubscriptionHandler;
import com.calclab.emite.im.client.roster.SubscriptionHandler.Behaviour;
import com.calclab.emite.im.client.roster.SubscriptionManager;
import com.calclab.emite.im.client.roster.SubscriptionState;
import com.calclab.emite.im.client.roster.XmppRoster;
import com.calclab.emite.im.client.roster.events.SubscriptionRequestReceivedEvent;
import com.calclab.emite.im.client.roster.events.SubscriptionRequestReceivedHandler;
import com.calclab.emite.xep.avatar.client.AvatarManager;
import com.calclab.emite.xep.chatstate.client.StateManager;
import com.calclab.emite.xep.muc.client.Room;
import com.calclab.emite.xep.muc.client.RoomManager;
import com.calclab.emite.xep.muc.client.subject.RoomSubject;
import com.calclab.emite.xep.mucchatstate.client.MUCChatStateManager;
import com.calclab.emite.xep.mucdisco.client.RoomDiscoveryManager;
import com.calclab.emite.xep.storage.client.PrivateStorageManager;
import com.calclab.hablar.chat.client.HablarChat;
import com.calclab.hablar.client.HablarConfig;
import com.calclab.hablar.core.client.Hablar;
import com.calclab.hablar.core.client.HablarCore;
import com.calclab.hablar.core.client.browser.BrowserFocusHandler;
import com.calclab.hablar.dock.client.HablarDock;
import com.calclab.hablar.editbuddy.client.HablarEditBuddy;
import com.calclab.hablar.group.client.HablarGroup;
import com.calclab.hablar.groupchat.client.HablarGroupChat;
import com.calclab.hablar.icons.client.AvatarProviderRegistry;
import com.calclab.hablar.openchat.client.HablarOpenChat;
import com.calclab.hablar.rooms.client.HablarRooms;
import com.calclab.hablar.roster.client.HablarRoster;
import com.calclab.hablar.roster.client.page.RosterPage;
import com.calclab.hablar.signals.client.sound.HablarSoundSignals;
import com.calclab.hablar.user.client.HablarUser;
import com.calclab.hablar.usergroups.client.HablarUserGroups;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class ChatClientDefault.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ChatClientDefault implements ChatClient {

  /**
   * The Class ChatClientAction.
   *
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public class ChatClientAction extends AbstractExtendedAction {

    /** The res. */
    private final ChatResources res;

    /**
     * Instantiates a new chat client action.
     *
     * @param res the res
     */
    public ChatClientAction(final ChatResources res) {
      super();
      this.res = res;
      kuneEventBus.addHandler(NewUserRegisteredEvent.getType(),
          new NewUserRegisteredEvent.NewUserRegisteredHandler() {
            @Override
            public void onNewUserRegistered(final NewUserRegisteredEvent event) {
              // Blink the chat some seconds
              setBlink(true);
              new Timer() {
                @Override
                public void run() {
                  setBlink(false);
                }
              }.schedule(20000);
            }
          });
    }

    /* (non-Javadoc)
     * @see cc.kune.common.client.actions.ActionListener#actionPerformed(cc.kune.common.client.actions.ActionEvent)
     */
    @Override
    public void actionPerformed(final ActionEvent event) {
      kuneEventBus.fireEvent(new ToggleShowChatDialogEvent());
    }

    /**
     * Sets the blink.
     *
     * @param blink the new blink
     */
    public void setBlink(final boolean blink) {
      final ImageResource icon = blink ? res.chatBlink() : res.chatNoBlink();
      putValue(Action.SMALL_ICON, icon);
      dialog.setIcon(AbstractImagePrototype.create(icon));
    }

  }

  /** The Constant CHAT_TITLE. */
  private static final String CHAT_TITLE = "Chat ;)";
  
  /** The action. */
  private final ChatClientAction action;
  
  /** The avatar config. */
  private final Provider<KuneChatAvatarConfig> avatarConfig;
  
  /** The avatar manager. */
  private final Provider<AvatarManager> avatarManager;
  
  /** The avatar provider registry. */
  private final Provider<AvatarProviderRegistry> avatarProviderRegistry;
  
  /** The chat icon. */
  protected IconLabelDescriptor chatIcon;
  
  /** The chat manager. */
  private final Provider<ChatManager> chatManager;
  
  /** The chat options. */
  private final ChatOptions chatOptions;
  
  /** The chat resources. */
  private final ChatResources chatResources;
  
  /** The dialog. */
  private Dialog dialog;
  
  /** The down utils. */
  private final ClientFileDownloadUtils downUtils;
  
  /** The i18n. */
  private final I18nTranslationService i18n;
  
  /** The kune event bus. */
  private final EventBus kuneEventBus;
  
  /** The muc chat state manager. */
  private final Provider<MUCChatStateManager> mucChatStateManager;
  
  /** The presence manager. */
  private final Provider<PresenceManager> presenceManager;
  
  /** The private storage manager. */
  private final Provider<PrivateStorageManager> privateStorageManager;
  
  /** The res. */
  private final CoreResources res;
  
  /** The room discovery manager. */
  private final Provider<RoomDiscoveryManager> roomDiscoveryManager;
  
  /** The room manager. */
  private final Provider<RoomManager> roomManager;
  
  /** The roster. */
  private final Provider<XmppRoster> roster;
  
  /** The session. */
  private final Session session;
  
  /** The shorcut register. */
  private final GlobalShortcutRegister shorcutRegister;
  
  /** The subscription handler. */
  private final Provider<SubscriptionHandler> subscriptionHandler;
  
  /** The subscription manager. */
  private final Provider<SubscriptionManager> subscriptionManager;
  
  /** The xmpp session. */
  private final Provider<XmppSession> xmppSession;
  
  /** The xmpp state manager. */
  private final Provider<StateManager> xmppStateManager;

  /**
   * Instantiates a new chat client default.
   *
   * @param kuneEventBus the kune event bus
   * @param i18n the i18n
   * @param siteActions the site actions
   * @param session the session
   * @param res the res
   * @param downUtils the down utils
   * @param shorcutRegister the shorcut register
   * @param chatOptions the chat options
   * @param chatResources the chat resources
   * @param xmppSession the xmpp session
   * @param roster the roster
   * @param chatManager the chat manager
   * @param roomManager the room manager
   * @param avatarManager the avatar manager
   * @param subscriptionManager the subscription manager
   * @param presenceManager the presence manager
   * @param xmppStateManager the xmpp state manager
   * @param roomDiscoveryManager the room discovery manager
   * @param mucChatStateManager the muc chat state manager
   * @param avatarProviderRegistry the avatar provider registry
   * @param privateStorageManager the private storage manager
   * @param subscriptionHandler the subscription handler
   * @param avatarConfig the avatar config
   */
  @Inject
  public ChatClientDefault(final EventBus kuneEventBus, final I18nTranslationService i18n,
      final SitebarActions siteActions, final Session session, final CoreResources res,
      final ClientFileDownloadUtils downUtils, final GlobalShortcutRegister shorcutRegister,
      final ChatOptions chatOptions, final ChatResources chatResources,
      final Provider<XmppSession> xmppSession, final Provider<XmppRoster> roster,
      final Provider<ChatManager> chatManager, final Provider<RoomManager> roomManager,
      final Provider<AvatarManager> avatarManager,
      final Provider<SubscriptionManager> subscriptionManager,
      final Provider<PresenceManager> presenceManager, final Provider<StateManager> xmppStateManager,
      final Provider<RoomDiscoveryManager> roomDiscoveryManager,
      final Provider<MUCChatStateManager> mucChatStateManager,
      final Provider<AvatarProviderRegistry> avatarProviderRegistry,
      final Provider<PrivateStorageManager> privateStorageManager,
      final Provider<SubscriptionHandler> subscriptionHandler,
      final Provider<KuneChatAvatarConfig> avatarConfig) {
    this.kuneEventBus = kuneEventBus;
    this.i18n = i18n;
    this.res = res;
    this.downUtils = downUtils;
    this.presenceManager = presenceManager;
    this.xmppStateManager = xmppStateManager;
    this.roomDiscoveryManager = roomDiscoveryManager;
    this.mucChatStateManager = mucChatStateManager;
    this.avatarProviderRegistry = avatarProviderRegistry;
    this.privateStorageManager = privateStorageManager;
    this.subscriptionHandler = subscriptionHandler;
    this.session = session;
    this.shorcutRegister = shorcutRegister;
    this.chatOptions = chatOptions;
    this.chatResources = chatResources;
    this.xmppSession = xmppSession;
    this.roster = roster;
    this.chatManager = chatManager;
    this.roomManager = roomManager;
    this.avatarManager = avatarManager;
    this.subscriptionManager = subscriptionManager;
    this.avatarConfig = avatarConfig;
    action = new ChatClientAction(chatResources);

    session.onAppStart(true, new AppStartEvent.AppStartHandler() {
      @Override
      public void onAppStart(final AppStartEvent event) {
        chatOptions.domain = event.getInitData().getChatDomain();
        chatOptions.httpBase = event.getInitData().getChatHttpBase();
        chatOptions.roomHost = event.getInitData().getChatRoomHost();
        checkChatDomain(chatOptions.domain);
        session.onUserSignIn(true, new UserSignInHandler() {
          @Override
          public void onUserSignIn(final UserSignInEvent event) {
            doLogin(event.getPassword());
          }
        });
        session.onUserSignOut(true, new UserSignOutHandler() {
          @Override
          public void onUserSignOut(final UserSignOutEvent event) {
            createActionIfNeeded();
            chatIcon.setVisible(false);
            logout();
          }
        });
        kuneEventBus.addHandler(ShowChatDialogEvent.getType(), new ShowChatDialogHandler() {
          @Override
          public void onShowChatDialog(final ShowChatDialogEvent event) {
            createActionIfNeeded();
            showDialog(event.show);
          }
        });
        kuneEventBus.addHandler(ToggleShowChatDialogEvent.getType(), new ToggleShowChatDialogHandler() {
          @Override
          public void onToggleShowChatDialog(final ToggleShowChatDialogEvent event) {
            toggleShowDialog();
          }
        });
        kuneEventBus.addHandler(AvatarChangedEvent.getType(),
            new AvatarChangedEvent.AvatarChangedHandler() {
              @Override
              public void onAvatarChanged(final AvatarChangedEvent event) {
                setAvatar(event.getPhotoBinary());
              }
            });
      }
    });
    kuneEventBus.addHandler(AppStopEvent.getType(), new AppStopEvent.AppStopHandler() {
      @Override
      public void onAppStop(final AppStopEvent event) {
        logout();
      }
    });
  }

  /* (non-Javadoc)
   * @see cc.kune.core.client.contacts.SimpleContactManager#addNewBuddy(java.lang.String)
   */
  @Override
  public void addNewBuddy(final String shortName) {
    roster.get().requestAddItem(uriFrom(shortName), shortName);
  }

  /* (non-Javadoc)
   * @see cc.kune.core.client.contacts.SimpleContactManager#chat(java.lang.String)
   */
  @Override
  public void chat(final String shortName) {
    chat(uriFrom(shortName));
  }

  /* (non-Javadoc)
   * @see cc.kune.chat.client.ChatClient#chat(com.calclab.emite.core.client.xmpp.stanzas.XmppURI)
   */
  @Override
  public void chat(final XmppURI jid) {
    chatManager.get().open(jid);
  }

  // Put this in Panel object
  /**
   * Check chat domain.
   *
   * @param chatDomain the chat domain
   */
  private void checkChatDomain(final String chatDomain) {
    final String httpDomain = WindowUtils.getHostName();
    if (!chatDomain.equals(httpDomain)) {
      Log.error("Your http domain (" + httpDomain + ") is different from the chat domain (" + chatDomain
          + "). This will cause problems with the chat functionality. "
          + "Please check kune.properties on the server.");
    }
  }

  /**
   * Creates the action if needed.
   */
  private void createActionIfNeeded() {
    if (chatIcon == null) {
      chatIcon = new IconLabelDescriptor(action);
      chatIcon.setParent(SitebarActions.LEFT_TOOLBAR);
      chatIcon.setId(CHAT_CLIENT_ICON_ID);
      chatIcon.setStyles(ActionStyles.SITEBAR_STYLE + ", k-chat-icon");
      chatIcon.putValue(Action.NAME, i18n.t(CHAT_TITLE));
      chatIcon.putValue(Action.SMALL_ICON, chatResources.chatNoBlink());
      chatIcon.putValue(Action.TOOLTIP, i18n.t("Show/hide the chat window"));
      final KeyStroke shortcut = Shortcut.getShortcut(false, true, false, false, Character.valueOf('C'));
      shorcutRegister.put(shortcut, action);
      action.setShortcut(shortcut);
      chatIcon.setVisible(session.isLogged());
      ToolbarSeparatorDescriptor.build(Type.spacer, SitebarActions.LEFT_TOOLBAR);
      ToolbarSeparatorDescriptor.build(Type.spacer, SitebarActions.LEFT_TOOLBAR);
      ToolbarSeparatorDescriptor.build(Type.spacer, SitebarActions.LEFT_TOOLBAR);
    }
  }

  /**
   * Creates the dialog.
   *
   * @param widget the widget
   * @param htmlConfig the html config
   */
  private void createDialog(final KuneHablarWidget widget, final CustomHtmlConfig htmlConfig) {
    widget.addStyleName("k-chat-panel");
    setSize(widget, htmlConfig);
    dialog.add(widget);
  }

  /**
   * Creates the dialog if needed.
   */
  private void createDialogIfNeeded() {
    if (dialog == null) {
      dialog = new Dialog();
      dialog.setHeadingText(i18n.t(CHAT_TITLE));
      dialog.setClosable(true);
      dialog.setResizable(true);
      dialog.setButtons("");
      dialog.setBodyStyleName("k-chat-window");
      dialog.setScrollMode(Scroll.NONE);
      dialog.setHideOnButtonClick(true);
      dialog.setCollapsible(true);
      dialog.setPosition(0, 0);
      dialog.setIcon(AbstractImagePrototype.create(chatResources.chatNoBlink()));
      // dialog.getItem(0).getFocusSupport().setIgnore(true);
      initEmite();
    }
  }

  /**
   * Dialog visible.
   *
   * @return true, if successful
   */
  private boolean dialogVisible() {
    return dialog != null && dialog.isVisible();
  }

  /* (non-Javadoc)
   * @see cc.kune.chat.client.ChatClient#doLogin()
   */
  @Override
  public void doLogin() {
    doLogin(null);
  }

  /**
   * Do login.
   *
   * @param password the password
   */
  private void doLogin(final String password) {
    assert session.getCurrentUserInfo() != null;
    doLogin(session.getCurrentUserInfo(), password == null ? session.getUserHash() : password);
  }

  /**
   * Do login.
   *
   * @param user the user
   * @param tokenOrPassword the token or password
   */
  private void doLogin(final UserInfoDTO user, final String tokenOrPassword) {
    createActionIfNeeded();
    createDialogIfNeeded();
    chatOptions.username = user.getChatName();
    chatOptions.passwd = tokenOrPassword;
    chatOptions.resource = "emite-" + new Date().getTime() + "-kune";
    chatOptions.useruri = XmppURI.uri(chatOptions.username, chatOptions.domain, chatOptions.resource);
    createActionIfNeeded();
    createDialogIfNeeded();
    chatIcon.setVisible(true);
    login(chatOptions.useruri, chatOptions.passwd);
  }

  /**
   * Inits the emite.
   */
  private void initEmite() {
    // Adapted from HablarHtml.java
    BrowserFocusHandler.getInstance();
    final HablarConfig config = HablarConfig.getFromMeta();
    final CustomHtmlConfig htmlConfig = CustomHtmlConfig.getFromMeta();

    config.dockConfig.headerSize = 0;
    config.dockConfig.rosterWidth = 150;
    config.dockConfig.rosterDock = "left";
    final KuneHablarWidget widget = new KuneHablarWidget(config.layout, config.tabHeaderSize);
    final Hablar hablar = widget.getHablar();
    avatarProviderRegistry.get().put("kune-avatars", avatarConfig.get());

    new HablarCore(hablar);
    new HablarChat(hablar, config.chatConfig, roster.get(), chatManager.get(), xmppStateManager.get(),
        avatarProviderRegistry.get());
    new HablarRooms(hablar, config.roomsConfig, xmppSession.get(), roster.get(), roomManager.get(),
        roomDiscoveryManager.get(), mucChatStateManager.get(), avatarProviderRegistry.get());
    new HablarGroupChat(hablar, config.roomsConfig, xmppSession.get(), roster.get(), chatManager.get(),
        roomManager.get(), avatarProviderRegistry.get());
    new HablarDock(hablar, config.dockConfig);
    new HablarUser(hablar, xmppSession.get(), presenceManager.get(), privateStorageManager.get());

    final HablarRoster hablarRoster = new HablarRoster(hablar, config.rosterConfig, xmppSession.get(),
        roster.get(), chatManager.get(), subscriptionHandler.get());
    final RosterPage rosterPage = hablarRoster.getRosterPage();

    new HablarOpenChat(hablar, xmppSession.get(), roster.get(), chatManager.get());
    new HablarEditBuddy(hablar, roster.get());
    new HablarUserGroups(rosterPage, hablar, roster.get());
    new HablarGroup(hablar, xmppSession.get(), roster.get(), avatarProviderRegistry.get());
    hablarRoster.addLowPriorityActions();

    new KuneHablarSignals(kuneEventBus, xmppSession.get(), hablar, action, privateStorageManager.get(),
        i18n, downUtils);
    new HablarSoundSignals(hablar);

    // if (htmlConfig.hasLogger) {
    // new HablarConsole(hablar, ginjector.getXmppConnection(), session);
    // }
    //
    // if (htmlConfig.hasLogin) {
    // new HablarLogin(hablar, LoginConfig.getFromMeta(), session);
    // }

    new KuneSoundManager(kuneEventBus);
    createDialog(widget, htmlConfig);
    subscriptionHandler.get().setBehaviour(Behaviour.none);
    subscriptionManager.get().addSubscriptionRequestReceivedHandler(
        new SubscriptionRequestReceivedHandler() {
          @Override
          public void onSubscriptionRequestReceived(final SubscriptionRequestReceivedEvent event) {
            final XmppURI uri = event.getFrom();
            final String nick = event.getNick();
            NotifyUser.askConfirmation(res.question32(), i18n.t("Confirm new buddy"), i18n.t(
                "[%s] had added you as a buddy. Do you want to add him/her also?",
                uri.getJID().toString()), new SimpleResponseCallback() {
              @Override
              public void onCancel() {
                subscriptionManager.get().refuseSubscriptionRequest(uri.getJID());
              }

              @Override
              public void onSuccess() {
                subscriptionManager.get().approveSubscriptionRequest(uri.getJID(), nick);
              }
            });
          }
        });
  }

  /* (non-Javadoc)
   * @see cc.kune.core.client.contacts.SimpleContactManager#isBuddy(java.lang.String)
   */
  @Override
  public boolean isBuddy(final String shortName) {
    return isBuddy(uriFrom(shortName));
  }

  /* (non-Javadoc)
   * @see cc.kune.chat.client.ChatClient#isBuddy(com.calclab.emite.core.client.xmpp.stanzas.XmppURI)
   */
  @Override
  public boolean isBuddy(final XmppURI jid) {
    if (roster.get().isRosterReady()) {
      final RosterItem rosterItem = roster.get().getItemByJID(jid);
      if (rosterItem != null && rosterItem.getSubscriptionState().equals(SubscriptionState.both)) {
        return true;
      }
    }
    return false;
  }

  /* (non-Javadoc)
   * @see cc.kune.chat.client.ChatClient#isXmppLoggedIn()
   */
  @Override
  public boolean isXmppLoggedIn() {
    return xmppSession.get().isReady();
  }

  /* (non-Javadoc)
   * @see cc.kune.chat.client.ChatClient#joinRoom(java.lang.String, java.lang.String)
   */
  @Override
  public Room joinRoom(final String roomName, final String userAlias) {
    return joinRoom(roomName, null, userAlias);
  }

  /* (non-Javadoc)
   * @see cc.kune.chat.client.ChatClient#joinRoom(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public Room joinRoom(final String roomName, final String subject, final String userAlias) {
    Room room = null;
    if (xmppSession.get().isReady()) {
      final XmppURI roomURI = XmppURI.uri(roomName + "@" + chatOptions.roomHost + "/"
          + chatOptions.username);
      room = roomManager.get().open(roomURI, roomManager.get().getDefaultHistoryOptions());
      if (TextUtils.notEmpty(subject)) {
        RoomSubject.requestSubjectChange(room, subject);
      }
    } else {
      NotifyUser.error(i18n.t("Error"), i18n.t("In order to join a chatroom you need to be 'online'"),
          true);
    }
    return room;
  }

  /* (non-Javadoc)
   * @see cc.kune.chat.client.ChatClient#login(com.calclab.emite.core.client.xmpp.stanzas.XmppURI, java.lang.String)
   */
  @Override
  public void login(final XmppURI uri, final String passwd) {
    xmppSession.get().login(uri, passwd);
  }

  /* (non-Javadoc)
   * @see cc.kune.chat.client.ChatClient#loginIfNecessary()
   */
  @Override
  public boolean loginIfNecessary() {
    if (!isXmppLoggedIn() && session.isLogged()) {
      doLogin();
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see cc.kune.chat.client.ChatClient#logout()
   */
  @Override
  public void logout() {
    if (dialogVisible()) {
      dialog.hide();
    }
    if (isXmppLoggedIn()) {
      xmppSession.get().logout();
    }
  }

  /* (non-Javadoc)
   * @see cc.kune.chat.client.ChatClient#roomUriFrom(java.lang.String)
   */
  @Override
  public XmppURI roomUriFrom(final String shortName) {
    return XmppURI.jid(shortName + "@" + chatOptions.roomHost);
  }

  /* (non-Javadoc)
   * @see cc.kune.chat.client.ChatClient#setAvatar(java.lang.String)
   */
  @Override
  public void setAvatar(final String photoBinary) {
    avatarManager.get().setVCardAvatar(photoBinary);
  }

  /**
   * Sets the size.
   *
   * @param widget the widget
   * @param htmlConfig the html config
   */
  private void setSize(final Widget widget, final CustomHtmlConfig htmlConfig) {
    if (htmlConfig.width != null) {
      widget.setWidth("98%");
      dialog.setWidth(htmlConfig.width);
    }
    if (htmlConfig.height != null) {
      widget.setHeight("98%");
      dialog.setHeight(htmlConfig.height);
    }
  }

  /* (non-Javadoc)
   * @see cc.kune.chat.client.ChatClient#show()
   */
  @Override
  public void show() {
    showDialog(true);
  }

  /**
   * Show dialog.
   *
   * @param show the show
   */
  private void showDialog(final boolean show) {
    Log.info("Show dialog: " + show);
    if (session.isLogged()) {
      createDialogIfNeeded();
      if (dialog.getAbsoluteTop() == 0 && dialog.getAbsoluteLeft() == 0) {
        dialog.setPosition(((Widget) chatIcon.getValue(ParentWidget.PARENT_UI)).getAbsoluteLeft() + 20,
            20);
      }
      if (show) {
        dialog.show();
        dialog.setZIndex(0);
        dialog.getHeader().setZIndex(0);
      } else {
        dialog.hide();
      }
    }
  }

  /**
   * Toggle show dialog.
   */
  private void toggleShowDialog() {
    Log.info("Toggle!");
    showDialog(dialog == null ? true : !dialogVisible());
  }

  /* (non-Javadoc)
   * @see cc.kune.chat.client.ChatClient#uriFrom(java.lang.String)
   */
  @Override
  public XmppURI uriFrom(final String shortName) {
    return chatOptions.uriFrom(shortName);
  }
}
