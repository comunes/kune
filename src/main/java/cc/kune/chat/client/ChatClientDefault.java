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
import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.KeyStroke;
import cc.kune.common.client.actions.Shortcut;
import cc.kune.common.client.actions.ui.ParentWidget;
import cc.kune.common.client.actions.ui.descrip.IconLabelDescriptor;
import cc.kune.common.client.actions.ui.descrip.ToolbarSeparatorDescriptor;
import cc.kune.common.client.actions.ui.descrip.ToolbarSeparatorDescriptor.Type;
import cc.kune.common.client.noti.NotifyUser;
import cc.kune.common.client.shortcuts.GlobalShortcutRegister;
import cc.kune.common.client.ui.PopupTopPanel;
import cc.kune.common.client.utils.TextUtils;
import cc.kune.common.client.utils.WindowUtils;
import cc.kune.core.client.init.AppStartEvent;
import cc.kune.core.client.init.AppStopEvent;
import cc.kune.core.client.logs.Log;
import cc.kune.core.client.resources.icons.IconResources;
import cc.kune.core.client.sitebar.SitebarActionsPresenter;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.UserSignInEvent;
import cc.kune.core.client.state.UserSignInEvent.UserSignInHandler;
import cc.kune.core.client.state.UserSignOutEvent;
import cc.kune.core.client.state.UserSignOutEvent.UserSignOutHandler;
import cc.kune.core.shared.dto.UserInfoDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.calclab.emite.browser.client.PageAssist;
import com.calclab.emite.core.client.xmpp.session.XmppSession;
import com.calclab.emite.core.client.xmpp.stanzas.XmppURI;
import com.calclab.emite.im.client.chat.ChatManager;
import com.calclab.emite.im.client.roster.XmppRoster;
import com.calclab.emite.xep.avatar.client.AvatarManager;
import com.calclab.emite.xep.muc.client.Room;
import com.calclab.emite.xep.muc.client.RoomManager;
import com.calclab.emite.xep.muc.client.subject.RoomSubject;
import com.calclab.hablar.HablarComplete;
import com.calclab.hablar.HablarConfig;
import com.calclab.hablar.console.client.HablarConsole;
import com.calclab.hablar.core.client.Hablar;
import com.calclab.hablar.core.client.HablarWidget;
import com.calclab.hablar.html.client.HtmlConfig;
import com.calclab.hablar.icons.alt.client.AltIcons;
import com.calclab.hablar.icons.def.client.DefaultIcons;
import com.calclab.hablar.icons.ie6gif.client.IE6GifIcons;
import com.calclab.hablar.login.client.HablarLogin;
import com.calclab.hablar.login.client.LoginConfig;
import com.calclab.suco.client.Suco;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class ChatClientDefault implements ChatClient {

    public static class ChatClientAction extends AbstractExtendedAction {

        private final EventBus eventBus;
        private final IconResources res;

        @Inject
        public ChatClientAction(final EventBus eventBus, final IconResources res) {
            super();
            this.eventBus = eventBus;
            this.res = res;
            res.css().ensureInjected();
            putValue(Action.SMALL_ICON, res.chat());

        }

        @Override
        public void actionPerformed(final ActionEvent event) {
            eventBus.fireEvent(new ToggleShowChatDialogEvent());
        }

        public void setBlink(final boolean blink) {
            putValue(Action.SMALL_ICON, blink ? res.chatBlink() : res.chat());
        }

    }

    protected static final String CHAT_CLIENT_ICON_ID = "k-chat-icon-id";

    private final ChatClientAction action;
    protected IconLabelDescriptor chatIcon;
    private final ChatManager chatManager;
    private final ChatOptions chatOptions;
    private final I18nTranslationService i18n;
    private PopupTopPanel popup;
    private final RoomManager roomManager;
    private final XmppRoster roster;
    private final Session session;
    private final GlobalShortcutRegister shorcutRegister;
    private final SitebarActionsPresenter siteActions;
    private final XmppSession xmppSession;

    @Inject
    public ChatClientDefault(final EventBus eventBus, final I18nTranslationService i18n, final ChatClientAction action,
            final SitebarActionsPresenter siteActions, final Session session,
            final GlobalShortcutRegister shorcutRegister, final ChatOptions chatOptions) {

        // , final XmppSession xmppSession,
        // final XmppRoster roster, final ChatManager chatManager, final
        // RoomManager roomManager,
        // final SessionReconnect sessionReconnect, final
        // Provider<AvatarManager> avatarManager) {
        this.i18n = i18n;
        this.action = action;
        this.siteActions = siteActions;
        this.session = session;
        this.shorcutRegister = shorcutRegister;
        this.chatOptions = chatOptions;
        this.xmppSession = Suco.get(XmppSession.class);
        this.roster = Suco.get(XmppRoster.class);
        this.chatManager = Suco.get(ChatManager.class);
        this.roomManager = Suco.get(RoomManager.class);

        eventBus.addHandler(AppStartEvent.getType(), new AppStartEvent.AppStartHandler() {
            @Override
            public void onAppStart(final AppStartEvent event) {
                chatOptions.domain = event.getInitData().getChatDomain();
                chatOptions.httpBase = event.getInitData().getChatHttpBase();
                chatOptions.roomHost = event.getInitData().getChatRoomHost();
                checkChatDomain(chatOptions.domain);
                // if (session.isLogged()) {
                // session.check(new AsyncCallbackSimple<Void>() {
                // @Override
                // public void onSuccess(final Void result) {
                // doLogin();
                // }
                // });
                // }
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
                eventBus.addHandler(ShowChatDialogEvent.getType(), new ShowChatDialogHandler() {
                    @Override
                    public void onShowChatDialog(final ShowChatDialogEvent event) {
                        createActionIfNeeded();
                        showDialog(event.show);
                    }
                });
                eventBus.addHandler(ToggleShowChatDialogEvent.getType(), new ToggleShowChatDialogHandler() {
                    @Override
                    public void onToggleShowChatDialog(final ToggleShowChatDialogEvent event) {
                        toggleShowDialog();
                    }
                });
            }
        });
        eventBus.addHandler(AppStopEvent.getType(), new AppStopEvent.AppStopHandler() {
            @Override
            public void onAppStop(final AppStopEvent event) {
                logout();
            }
        });
    }

    @Override
    public void addNewBuddie(final String shortName) {
        roster.requestAddItem(uriFrom(shortName), shortName, "");
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
            chatIcon.setId(CHAT_CLIENT_ICON_ID);
            chatIcon.setStyles("k-no-backimage, k-btn-sitebar, k-chat-icon");
            chatIcon.putValue(Action.NAME, i18n.t("Chat ;)"));
            chatIcon.putValue(Action.SHORT_DESCRIPTION, i18n.t("Show/hide the chat window"));
            final KeyStroke shortcut = Shortcut.getShortcut(false, true, true, false, Character.valueOf('C'));
            shorcutRegister.put(shortcut, action);
            action.setShortcut(shortcut);
            chatIcon.setVisible(session.isLogged());
            siteActions.getLeftToolbar().addAction(
                    new ToolbarSeparatorDescriptor(Type.spacer, SitebarActionsPresenter.LEFT_TOOLBAR));
            siteActions.getLeftToolbar().addAction(
                    new ToolbarSeparatorDescriptor(Type.spacer, SitebarActionsPresenter.LEFT_TOOLBAR));
            siteActions.getLeftToolbar().addAction(
                    new ToolbarSeparatorDescriptor(Type.spacer, SitebarActionsPresenter.LEFT_TOOLBAR));
            siteActions.getLeftToolbar().addAction(chatIcon);
        }
    }

    private void createDialog(final HablarWidget widget, final HtmlConfig htmlConfig) {
        widget.addStyleName("k-chat-panel");
        setSize(widget, htmlConfig);
        popup.add(widget);
    }

    private void createDialogIfNeeded() {
        if (popup == null) {
            popup = new PopupTopPanel();
            popup.setStyleName("k-popup-top-centered");
            popup.addStyleName("k-bottom-10corners");
            popup.addStyleName("k-box-10shadow");
            popup.addStyleName("k-chat-window");
            initEmite();
        }
    }

    private boolean dialogVisible() {
        return popup != null && popup.isShowing();
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
        final String icons = PageAssist.getMeta("hablar.icons");
        if ("alt".equals(icons)) {
            AltIcons.load();
        } else if ("ie6".equals(icons)) {
            IE6GifIcons.load();
        } else {
            DefaultIcons.load();
        }

        final HablarConfig config = HablarConfig.getFromMeta();
        final HtmlConfig htmlConfig = HtmlConfig.getFromMeta();
        final HablarWidget widget = new HablarWidget(config.layout, config.tabHeaderSize);
        final Hablar hablar = widget.getHablar();
        HablarComplete.install(hablar, config);
        new KuneHablarSignals(xmppSession, hablar, action);
        if (htmlConfig.hasLogger) {
            new HablarConsole(hablar);
        }

        if (htmlConfig.hasLogin) {
            new HablarLogin(hablar, LoginConfig.getFromMeta());
        }
        createDialog(widget, htmlConfig);
    }

    @Override
    public boolean isBuddie(final String shortName) {
        return isBuddie(uriFrom(shortName));
    }

    @Override
    public boolean isBuddie(final XmppURI jid) {
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
    public void joinRoom(final String roomName, final String userAlias) {
        joinRoom(roomName, null, userAlias);
    }

    @Override
    public void joinRoom(final String roomName, final String subject, final String userAlias) {
        if (xmppSession.isReady()) {
            final XmppURI roomURI = XmppURI.uri(roomName + "@" + chatOptions.roomHost + "/" + chatOptions.username);
            final Room room = roomManager.open(roomURI, roomManager.getDefaultHistoryOptions());
            if (TextUtils.notEmpty(subject)) {
                RoomSubject.requestSubjectChange(room, subject);
            }
        } else {
            NotifyUser.error(i18n.t("Error"), i18n.t("To join a chatroom you need to be 'online'"), true);
        }
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
            popup.hide();
        }
        if (isLoggedIn()) {
            xmppSession.logout();
        }
    }

    @Override
    public void setAvatar(final String photoBinary) {
        Suco.get(AvatarManager.class).setVCardAvatar(photoBinary);
    }

    private void setSize(final Widget widget, final HtmlConfig htmlConfig) {
        if (htmlConfig.width != null) {
            widget.setWidth(htmlConfig.width);
        }
        if (htmlConfig.height != null) {
            widget.setHeight(htmlConfig.height);
        }
    }

    @Override
    public void show() {
        showDialog(true);
    }

    private void showDialog(final boolean show) {
        if (session.isLogged()) {
            createDialogIfNeeded();
            if (show) {
                popup.showNear((Widget) chatIcon.getValue(ParentWidget.PARENT_UI));
            } else {
                popup.hide();
            }
        }
    }

    private void toggleShowDialog() {
        showDialog(popup == null ? true : !popup.isShowing());
    }

    private XmppURI uriFrom(final String shortName) {
        return XmppURI.jid(shortName + "@" + chatOptions.domain);
    }
}
