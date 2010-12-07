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
package org.ourproject.kune.chat.client;

import java.util.Collection;
import java.util.Date;

import org.ourproject.kune.platf.client.actions.AbstractExtendedAction;
import org.ourproject.kune.platf.client.actions.ActionEvent;
import org.ourproject.kune.platf.client.actions.KeyStroke;
import org.ourproject.kune.platf.client.actions.Shortcut;
import org.ourproject.kune.platf.client.app.Application;
import org.ourproject.kune.platf.client.dto.InitDataDTO;
import org.ourproject.kune.platf.client.dto.UserInfoDTO;
import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.shortcuts.GlobalShortcutRegister;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.ui.WindowUtils;
import org.ourproject.kune.platf.client.ui.download.FileDownloadUtils;
import org.ourproject.kune.platf.client.ui.noti.NotifyUser;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.emite.core.client.xmpp.stanzas.XmppURI;
import com.calclab.emite.im.client.roster.RosterItem;
import com.calclab.emite.xep.muc.client.Room;
import com.calclab.emiteuimodule.client.EmiteUIDialog;
import com.calclab.emiteuimodule.client.SubscriptionMode;
import com.calclab.emiteuimodule.client.UserChatOptions;
import com.calclab.emiteuimodule.client.chat.ChatUI;
import com.calclab.emiteuimodule.client.params.AvatarProvider;
import com.calclab.emiteuimodule.client.room.RoomUI;
import com.calclab.emiteuimodule.client.status.OwnPresence.OwnStatus;
import com.calclab.suco.client.events.Event0;
import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener0;
import com.calclab.suco.client.ioc.Provider;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ClosingEvent;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;

class ChatEngineDefault implements ChatEngine {
    private ChatConnectionOptions chatOptions;
    private final I18nTranslationService i18n;
    private final WorkspaceSkeleton wskel;
    private ToolbarButton traybarButton;
    private final Provider<EmiteUIDialog> emiteUIProvider;
    private Collection<RosterItem> roster;
    private final Event0 onRosterChanged;
    private final KeyStroke shortcut;
    private final Provider<FileDownloadUtils> downloadUtils;

    public ChatEngineDefault(final I18nTranslationService i18n, final WorkspaceSkeleton wskel,
            final Application application, final Session session, final Provider<EmiteUIDialog> emiteUIProvider,
            final Provider<FileDownloadUtils> downloadUtils, final GlobalShortcutRegister shortcutRegister) {
        this.i18n = i18n;
        this.wskel = wskel;
        this.emiteUIProvider = emiteUIProvider;
        this.downloadUtils = downloadUtils;
        this.onRosterChanged = new Event0("onRosterChanged");
        shortcut = Shortcut.getShortcut(false, true, false, false, Character.valueOf('C'));
        shortcutRegister.put(shortcut, new AbstractExtendedAction() {
            public void actionPerformed(final ActionEvent event) {
                if (isDialogStarted()) {
                    toggleShow();
                }

            }
        });
        session.onInitDataReceived(new Listener<InitDataDTO>() {
            public void onEvent(final InitDataDTO initData) {
                checkChatDomain(initData.getChatDomain());
                chatOptions = new ChatConnectionOptions(initData.getChatHttpBase(), initData.getChatDomain(),
                        initData.getChatRoomHost());
            }

            private void checkChatDomain(final String chatDomain) {
                final String httpDomain = WindowUtils.getLocation().getHostName();
                if (!chatDomain.equals(httpDomain)) {
                    Log.error("Your http domain (" + httpDomain + ") is different from the chat domain (" + chatDomain
                            + "). This will cause problems with the chat functionality. "
                            + "Please check kune.properties on the server.");
                }
            }
        });
        session.onUserSignOut(new Listener0() {
            public void onEvent() {
                logout();
            }
        });
        session.onUserSignIn(new Listener<UserInfoDTO>() {
            public void onEvent(final UserInfoDTO user) {
                login(user.getChatName(), user.getChatPassword());
            }
        });
        application.onClosing(new Listener<ClosingEvent>() {
            public void onEvent(final ClosingEvent event) {
                stop();
            }
        });
    }

    public void addNewBuddie(final String shortName) {
        emiteUIProvider.get().addBuddie(getLocalUserJid(shortName), shortName, "");
    }

    public void addOnRosterChanged(final Listener0 slot) {
        onRosterChanged.add(slot);
    }

    public void chat(final XmppURI jid) {
        emiteUIProvider.get().chat(jid);
    }

    public ChatConnectionOptions getChatOptions() {
        return chatOptions;
    }

    public boolean isBuddie(final String shortName) {
        return isBuddie(getLocalUserJid(shortName));
    }

    public boolean isBuddie(final XmppURI jid) {
        if (roster != null) {
            for (final RosterItem item : roster) {
                if (item.getJID().equalsNoResource(jid)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isDialogStarted() {
        return !emiteUIProvider.get().isDialogNotStarted();
    }

    public boolean isLoggedIn() {
        return emiteUIProvider.get().isLoggedIn();
    }

    public void joinRoom(final String roomName, final String userAlias) {
        joinRoom(roomName, null, userAlias);
    }

    public void joinRoom(final String roomName, final String subject, final String userAlias) {
        if (emiteUIProvider.get().isLoggedIn()) {
            final XmppURI roomURI = XmppURI.uri(roomName + "@" + chatOptions.roomHost + "/"
                    + chatOptions.userOptions.getUserJid().getNode());
            final Room room = (Room) emiteUIProvider.get().joinRoom(roomURI);
            if (subject != null) {
                DeferredCommand.addCommand(new Command() {
                    public void execute() {
                        final RoomUI roomUI = (RoomUI) room.getData(ChatUI.class);
                        if (roomUI != null) {
                            roomUI.setSubject(subject);
                        }
                    }
                });
            }
        } else {
            NotifyUser.showAlertMessage(i18n.t("Error"), i18n.t("To join a chatroom you need to be 'online'"));
        }
    }

    public void login(final String jid, final String passwd) {
        final UserChatOptions userChatOptions = getUserChatOptions(jid, passwd);
        // FIXME: Avatar provider
        final AvatarProvider avatarProvider = new AvatarProvider() {
            public String getAvatarURL(final XmppURI userURI) {
                // if (userURI.getHost().equals(chatOptions.domain)) {
                // FIXME
                // return downloadUtils.get().getLogoImageUrl(new
                // StateToken(userURI.getNode()));
                // } else {
                // return "";
                // }
                return "images/person-def.gif";
            }
        };
        final String initialWindowTitle = Window.getTitle();
        chatOptions.userOptions = userChatOptions;
        if (emiteUIProvider.get().isDialogNotStarted()) {
            emiteUIProvider.get().onRosterChanged(new Listener<Collection<RosterItem>>() {
                public void onEvent(final Collection<RosterItem> rosterChanged) {
                    roster = rosterChanged;
                    onRosterChanged.fire();
                }
            });
            emiteUIProvider.get().start(userChatOptions, chatOptions.httpBase, chatOptions.domain,
                    chatOptions.roomHost, avatarProvider, i18n.t("Chat"));
        } else {
            emiteUIProvider.get().setEnableStatusUI(true);
            emiteUIProvider.get().refreshUserInfo(chatOptions.userOptions);
        }
        emiteUIProvider.get().show(OwnStatus.online);
        if (traybarButton == null) {
            traybarButton = new ToolbarButton();
            traybarButton.setTooltip(i18n.t("Show/hide the chat window" + shortcut.toString()));
            traybarButton.setIcon("images/e-icon.gif");
            traybarButton.addListener(new ButtonListenerAdapter() {
                @Override
                public void onClick(final Button button, final EventObject e) {
                    toggleShow();
                }
            });
            wskel.getSiteTraybar().addButton(traybarButton);
            emiteUIProvider.get().onChatAttended(new Listener<String>() {
                public void onEvent(final String parameter) {
                    traybarButton.setIcon("images/e-icon.gif");
                }
            });
            emiteUIProvider.get().onChatUnattendedWithActivity(new Listener<String>() {
                public void onEvent(final String parameter) {
                    traybarButton.setIcon("images/e-icon-a.gif");
                }
            });
        }
        emiteUIProvider.get().hide();
        emiteUIProvider.get().onChatAttended(new Listener<String>() {
            public void onEvent(final String parameter) {
                Window.setTitle(initialWindowTitle);
            }
        });
        emiteUIProvider.get().onChatUnattendedWithActivity(new Listener<String>() {
            public void onEvent(final String chatTitle) {
                Window.setTitle("(* " + chatTitle + ") " + initialWindowTitle);
            }
        });
    }

    public void logout() {
        if (!emiteUIProvider.get().isDialogNotStarted()) {
            emiteUIProvider.get().setOwnPresence(OwnStatus.offline);
            chatOptions.userOptions = getUserChatOptions("reset@example.com", "");
            emiteUIProvider.get().refreshUserInfo(chatOptions.userOptions);
            emiteUIProvider.get().setEnableStatusUI(false);
        }
    }

    public void setAvatar(final String photoBinary) {
        emiteUIProvider.get().setOwnVCardAvatar(photoBinary);
    }

    public void show() {
        emiteUIProvider.get().show();
    }

    public void stop() {
        if (!emiteUIProvider.get().isDialogNotStarted()) {
            emiteUIProvider.get().destroy();
        }
        if (emiteUIProvider.get().getSession().isLoggedIn()) {
            emiteUIProvider.get().getSession().logout();
        }
    }

    public void toggleShow() {
        if (emiteUIProvider.get().isVisible()) {
            emiteUIProvider.get().hide();
        } else {
            emiteUIProvider.get().show();
        }
    }

    private XmppURI getLocalUserJid(final String shortName) {
        return XmppURI.jid(shortName + "@" + chatOptions.domain);
    }

    private UserChatOptions getUserChatOptions(final String jid, final String passwd) {
        final String resource = "emiteui-" + new Date().getTime() + "-kune"; // +
        // getGwtMetaProperty(GWT_PROPERTY_RELEASE);
        // FIXME, get this from user profile
        return new UserChatOptions(jid + "@" + chatOptions.domain, passwd, resource, "blue", SubscriptionMode.manual,
                true);
    }
}
