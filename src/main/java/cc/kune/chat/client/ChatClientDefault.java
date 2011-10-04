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
package cc.kune.chat.client;

import java.util.Date;

import cc.kune.chat.client.ShowChatDialogEvent.ShowChatDialogHandler;
import cc.kune.chat.client.ToggleShowChatDialogEvent.ToggleShowChatDialogHandler;
import cc.kune.chat.client.resources.ChatResources;
import cc.kune.chat.client.snd.KuneSoundManager;
import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.KeyStroke;
import cc.kune.common.client.actions.Shortcut;
import cc.kune.common.client.actions.ui.descrip.IconLabelDescriptor;
import cc.kune.common.client.actions.ui.descrip.ToolbarSeparatorDescriptor;
import cc.kune.common.client.actions.ui.descrip.ToolbarSeparatorDescriptor.Type;
import cc.kune.common.client.log.Log;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.shortcuts.GlobalShortcutRegister;
import cc.kune.common.client.utils.SimpleResponseCallback;
import cc.kune.common.client.utils.TextUtils;
import cc.kune.common.client.utils.WindowUtils;
import cc.kune.core.client.events.AvatarChangedEvent;
import cc.kune.core.client.events.NewUserRegisteredEvent;
import cc.kune.core.client.init.AppStartEvent;
import cc.kune.core.client.init.AppStopEvent;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.sitebar.SitebarActions;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.UserSignInEvent;
import cc.kune.core.client.state.UserSignInEvent.UserSignInHandler;
import cc.kune.core.client.state.UserSignOutEvent;
import cc.kune.core.client.state.UserSignOutEvent.UserSignOutHandler;
import cc.kune.core.shared.dto.UserInfoDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.calclab.emite.core.client.xmpp.session.XmppSession;
import com.calclab.emite.core.client.xmpp.stanzas.XmppURI;
import com.calclab.emite.im.client.chat.ChatManager;
import com.calclab.emite.im.client.roster.SubscriptionHandler.Behaviour;
import com.calclab.emite.im.client.roster.SubscriptionManager;
import com.calclab.emite.im.client.roster.XmppRoster;
import com.calclab.emite.im.client.roster.events.SubscriptionRequestReceivedEvent;
import com.calclab.emite.im.client.roster.events.SubscriptionRequestReceivedHandler;
import com.calclab.emite.xep.avatar.client.AvatarManager;
import com.calclab.emite.xep.muc.client.Room;
import com.calclab.emite.xep.muc.client.RoomManager;
import com.calclab.emite.xep.muc.client.subject.RoomSubject;
import com.calclab.hablar.HablarComplete;
import com.calclab.hablar.HablarConfig;
import com.calclab.hablar.console.client.HablarConsole;
import com.calclab.hablar.core.client.Hablar;
import com.calclab.hablar.core.client.ui.icon.Icons;
import com.calclab.hablar.html.client.HtmlConfig;
import com.calclab.hablar.icons.alt.client.AltIconsBundle;
import com.calclab.hablar.login.client.HablarLogin;
import com.calclab.hablar.login.client.LoginConfig;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class ChatClientDefault implements ChatClient {

  public class ChatClientAction extends AbstractExtendedAction {

    private final ChatResources res;

    public ChatClientAction(final ChatResources res) {
      super();
      this.res = res;
      putValue(Action.SMALL_ICON, res.chat());
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
              }.schedule(10000);
            }
          });
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
      kuneEventBus.fireEvent(new ToggleShowChatDialogEvent());
    }

    public void setBlink(final boolean blink) {
      final ImageResource icon = blink ? res.chatBlink() : res.chat();
      putValue(Action.SMALL_ICON, icon);
      dialog.setIcon(AbstractImagePrototype.create(icon));
    }

  }

  private static final String CHAT_TITLE = "Chat ;)";

  private final ChatClientAction action;
  private final AvatarManager avatarManager;
  protected IconLabelDescriptor chatIcon;
  private final ChatInstances chatInstances;
  private final ChatManager chatManager;
  private final ChatOptions chatOptions;
  private final ChatResources chatResources;
  private Dialog dialog;
  private final I18nTranslationService i18n;
  private final EventBus kuneEventBus;
  private final CoreResources res;
  private final RoomManager roomManager;
  private final XmppRoster roster;
  private final Session session;
  private final GlobalShortcutRegister shorcutRegister;
  private final SitebarActions siteActions;
  private final SubscriptionManager subscriptionManager;
  private final XmppSession xmppSession;

  @Inject
  public ChatClientDefault(final EventBus kuneEventBus, final I18nTranslationService i18n,
      final SitebarActions siteActions, final Session session, final CoreResources res,
      final GlobalShortcutRegister shorcutRegister, final ChatOptions chatOptions,
      final ChatResources chatResources, final ChatInstances chatInstances) {
    this.kuneEventBus = kuneEventBus;
    this.i18n = i18n;
    this.res = res;
    this.chatInstances = chatInstances;
    action = new ChatClientAction(chatResources);
    this.siteActions = siteActions;
    this.session = session;
    this.shorcutRegister = shorcutRegister;
    this.chatOptions = chatOptions;
    this.chatResources = chatResources;
    chatResources.css().ensureInjected();
    this.xmppSession = chatInstances.xmppSession;
    this.roster = chatInstances.roster;
    this.chatManager = chatInstances.chatManager;
    this.roomManager = chatInstances.roomManager;
    this.avatarManager = chatInstances.avatarManager;
    this.subscriptionManager = chatInstances.subscriptionManager;

    // Not necessary, in ChatInstance
    // Suco.get(SessionReconnect.class);

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
            doLogin();
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

  @Override
  public void addNewBuddy(final String shortName) {
    roster.requestAddItem(uriFrom(shortName), shortName);
  }

  @Override
  public void chat(final String shortName) {
    chat(uriFrom(shortName));
  }

  @Override
  public void chat(final XmppURI jid) {
    chatManager.open(jid);
  }

  // Put this in Panel object
  private void checkChatDomain(final String chatDomain) {
    final String httpDomain = WindowUtils.getLocation().getHostName();
    if (!chatDomain.equals(httpDomain)) {
      Log.error("Your http domain (" + httpDomain + ") is different from the chat domain (" + chatDomain
          + "). This will cause problems with the chat functionality. "
          + "Please check kune.properties on the server.");
    }
  }

  private void createActionIfNeeded() {
    if (chatIcon == null) {
      chatIcon = new IconLabelDescriptor(action);
      chatIcon.setParent(siteActions.getLeftToolbar());
      chatIcon.setId(CHAT_CLIENT_ICON_ID);
      chatIcon.setStyles("k-no-backimage, k-btn-sitebar, k-chat-icon");
      chatIcon.putValue(Action.NAME, i18n.t(CHAT_TITLE));
      chatIcon.putValue(Action.TOOLTIP, i18n.t("Show/hide the chat window"));
      final KeyStroke shortcut = Shortcut.getShortcut(false, true, false, false, Character.valueOf('C'));
      shorcutRegister.put(shortcut, action);
      action.setShortcut(shortcut);
      chatIcon.setVisible(session.isLogged());
      ToolbarSeparatorDescriptor.build(Type.spacer, siteActions.getLeftToolbar());
      ToolbarSeparatorDescriptor.build(Type.spacer, siteActions.getLeftToolbar());
      ToolbarSeparatorDescriptor.build(Type.spacer, siteActions.getLeftToolbar());
    }
  }

  private void createDialog(final KuneHablarWidget widget, final HtmlConfig htmlConfig) {
    widget.addStyleName("k-chat-panel");
    setSize(widget, htmlConfig);
    dialog.add(widget);
  }

  private void createDialogIfNeeded() {
    if (dialog == null) {
      dialog = new Dialog();
      dialog.setHeading(i18n.t(CHAT_TITLE));
      dialog.setClosable(true);
      dialog.setResizable(true);
      dialog.setButtons("");
      dialog.setBodyStyleName("k-chat-window");
      dialog.setScrollMode(Scroll.NONE);
      dialog.setHideOnButtonClick(true);
      dialog.setCollapsible(true);
      // final Widget btn = (Widget)
      // chatIcon.getValue(ParentWidget.PARENT_UI);
      dialog.setPosition(118, 1);
      dialog.setIcon(AbstractImagePrototype.create(chatResources.chat()));
      // dialog.getItem(0).getFocusSupport().setIgnore(true);
      initEmite();
    }
  }

  private boolean dialogVisible() {
    return dialog != null && dialog.isVisible();
  }

  @Override
  public void doLogin() {
    assert session.getCurrentUserInfo() != null;
    doLogin(session.getCurrentUserInfo());
  }

  private void doLogin(final UserInfoDTO user) {
    createActionIfNeeded();
    createDialogIfNeeded();
    chatOptions.username = user.getChatName();
    chatOptions.passwd = user.getChatPassword();
    chatOptions.resource = "emite-" + new Date().getTime() + "-kune";
    chatOptions.useruri = XmppURI.uri(chatOptions.username, chatOptions.domain, chatOptions.resource);
    createActionIfNeeded();
    createDialogIfNeeded();
    chatIcon.setVisible(true);
    login(chatOptions.useruri, chatOptions.passwd);
  }

  private void initEmite() {
    loadIcons(chatResources);

    final HablarConfig config = HablarConfig.getFromMeta();
    final HtmlConfig htmlConfig = HtmlConfig.getFromMeta();
    config.dockConfig.headerSize = 0;
    config.dockConfig.rosterWidth = 150;
    config.dockConfig.rosterDock = "left";
    final KuneHablarWidget widget = new KuneHablarWidget(config.layout, config.tabHeaderSize);
    final Hablar hablar = widget.getHablar();
    HablarComplete.install(hablar, config);
    new KuneHablarSignals(kuneEventBus, xmppSession, hablar, action, chatInstances);
    if (htmlConfig.hasLogger) {
      new HablarConsole(hablar);
    }

    if (htmlConfig.hasLogin) {
      new HablarLogin(hablar, LoginConfig.getFromMeta());
    }
    new KuneSoundManager(kuneEventBus, config.soundConfig);
    createDialog(widget, htmlConfig);
    chatInstances.subscriptionHandler.setBehaviour(Behaviour.none);
    subscriptionManager.addSubscriptionRequestReceivedHandler(new SubscriptionRequestReceivedHandler() {
      @Override
      public void onSubscriptionRequestReceived(final SubscriptionRequestReceivedEvent event) {
        final XmppURI uri = event.getFrom();
        final String nick = event.getNick();
        NotifyUser.askConfirmation(res.question32(), i18n.t("Confirm new buddy"), i18n.t(
            "[%s] had added you as a buddy. Do you want to add him/her also?", uri.getJID().toString()),
            new SimpleResponseCallback() {
              @Override
              public void onCancel() {
                subscriptionManager.refuseSubscriptionRequest(uri.getJID());
              }

              @Override
              public void onSuccess() {
                subscriptionManager.approveSubscriptionRequest(uri.getJID(), nick);
              }
            });
      }
    });
  }

  @Override
  public boolean isBuddy(final String shortName) {
    return isBuddy(uriFrom(shortName));
  }

  @Override
  public boolean isBuddy(final XmppURI jid) {
    if (roster.isRosterReady()) {
      if (roster.getItemByJID(jid) != null) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean isLoggedIn() {
    return xmppSession.isReady();
  }

  @Override
  public Room joinRoom(final String roomName, final String userAlias) {
    return joinRoom(roomName, null, userAlias);
  }

  @Override
  public Room joinRoom(final String roomName, final String subject, final String userAlias) {
    Room room = null;
    if (xmppSession.isReady()) {
      final XmppURI roomURI = XmppURI.uri(roomName + "@" + chatOptions.roomHost + "/"
          + chatOptions.username);
      room = roomManager.open(roomURI, roomManager.getDefaultHistoryOptions());
      if (TextUtils.notEmpty(subject)) {
        RoomSubject.requestSubjectChange(room, subject);
      }
    } else {
      NotifyUser.error(i18n.t("Error"), i18n.t("To join a chatroom you need to be 'online'"), true);
    }
    return room;
  }

  public void loadIcons(final ChatResources others) {
    final AltIconsBundle bundle = GWT.create(AltIconsBundle.class);
    Icons.register(Icons.BUDDY_ADD, bundle.buddyAddIcon());
    Icons.register(Icons.BUDDY, bundle.buddyIcon());
    Icons.register(Icons.BUDDY_DND, others.busy());
    Icons.register(Icons.BUDDY_OFF, others.offline());
    Icons.register(Icons.BUDDY_ON, others.online());
    Icons.register(Icons.BUDDY_WAIT, others.away());
    Icons.register(Icons.ADD_CHAT, bundle.chatAddIcon());
    Icons.register(Icons.CHAT, others.xa());
    Icons.register(Icons.CLIPBOARD, bundle.clipboardIcon());
    Icons.register(Icons.CLOSE, bundle.closeIcon());
    Icons.register(Icons.CONSOLE, bundle.consoleIcon());
    Icons.register(Icons.ADD_GROUP, bundle.groupAddIcon());
    Icons.register(Icons.GROUP_CHAT, bundle.groupChatIcon());
    Icons.register(Icons.GROUP_CHAT_ADD, bundle.groupChatAddIcon());
    Icons.register(Icons.LOADING, bundle.loadingIcon());
    Icons.register(Icons.MENU, bundle.menuIcon());
    Icons.register(Icons.MISSING_ICON, bundle.missingIcon());
    Icons.register(Icons.NOT_CONNECTED, others.offline());
    Icons.register(Icons.CONNECTED, others.online());
    Icons.register(Icons.ROSTER, bundle.rosterIcon());
    Icons.register(Icons.SEARCH, bundle.searchIcon());
  }

  @Override
  public void login(final XmppURI uri, final String passwd) {
    xmppSession.login(uri, passwd);
  }

  @Override
  public boolean loginIfNecessary() {
    if (!isLoggedIn()) {
      doLogin();
      return true;
    }
    return false;
  }

  @Override
  public void logout() {
    if (dialogVisible()) {
      dialog.hide();
    }
    if (isLoggedIn()) {
      xmppSession.logout();
    }
  }

  @Override
  public XmppURI roomUriFrom(final String shortName) {
    return XmppURI.jid(shortName + "@" + chatOptions.roomHost);
  }

  @Override
  public void setAvatar(final String photoBinary) {
    avatarManager.setVCardAvatar(photoBinary);
  }

  private void setSize(final Widget widget, final HtmlConfig htmlConfig) {

    if (htmlConfig.width != null) {
      widget.setWidth("98%");
      dialog.setWidth(htmlConfig.width);
    }
    if (htmlConfig.height != null) {
      widget.setHeight("98%");
      dialog.setHeight(htmlConfig.height);
    }
  }

  @Override
  public void show() {
    showDialog(true);
  }

  private void showDialog(final boolean show) {
    Log.info("Show dialog: " + show);
    if (session.isLogged()) {
      createDialogIfNeeded();
      if (show) {
        dialog.show();
        dialog.setZIndex(0);
        dialog.getHeader().setZIndex(0);
      } else {
        dialog.hide();
      }
    }
  }

  private void toggleShowDialog() {
    Log.info("Toggle!");
    showDialog(dialog == null ? true : !dialogVisible());
  }

  @Override
  public XmppURI uriFrom(final String shortName) {
    return chatOptions.uriFrom(shortName);
  }
}
