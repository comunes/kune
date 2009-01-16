/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
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

import org.ourproject.kune.platf.client.app.Application;
import org.ourproject.kune.platf.client.dto.InitDataDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.dto.UserInfoDTO;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.ui.WindowUtils;
import org.ourproject.kune.platf.client.ui.download.FileDownloadUtils;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.emite.core.client.xmpp.stanzas.XmppURI;
import com.calclab.emite.im.client.roster.RosterItem;
import com.calclab.emiteuimodule.client.EmiteUIDialog;
import com.calclab.emiteuimodule.client.SubscriptionMode;
import com.calclab.emiteuimodule.client.UserChatOptions;
import com.calclab.emiteuimodule.client.params.AvatarProvider;
import com.calclab.emiteuimodule.client.status.OwnPresence.OwnStatus;
import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener0;
import com.calclab.suco.client.ioc.Provider;
import com.google.gwt.user.client.Window;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;

class ChatEngineDefault implements ChatEngine {
    private ChatConnectionOptions chatOptions;
    private final I18nTranslationService i18n;
    private final WorkspaceSkeleton ws;
    private ToolbarButton traybarButton;
    private final Provider<EmiteUIDialog> emiteUIProvider;
    private final Provider<FileDownloadUtils> downloadUtils;
    private Collection<RosterItem> roster;

    public ChatEngineDefault(final I18nTranslationService i18n, final WorkspaceSkeleton ws, Application application,
            Session session, final Provider<EmiteUIDialog> emiteUIProvider,
            final Provider<FileDownloadUtils> downloadUtils) {
        this.i18n = i18n;
        this.ws = ws;
        this.emiteUIProvider = emiteUIProvider;
        this.downloadUtils = downloadUtils;
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
        application.onApplicationStop(new Listener0() {
            public void onEvent() {
                stop();
            }
        });
    }

    public void addNewBuddie(String shortName) {
        emiteUIProvider.get().addBuddie(getLocalUserJid(shortName), shortName, "");
    }

    public void chat(XmppURI jid) {
        emiteUIProvider.get().chat(jid);
    }

    public ChatConnectionOptions getChatOptions() {
        return chatOptions;
    }

    public boolean isBuddie(String shortName) {
        return isBuddie(getLocalUserJid(shortName));
    }

    public boolean isBuddie(XmppURI jid) {
        if (roster != null) {
            for (RosterItem item : roster) {
                if (item.getJID().equalsNoResource(jid)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isLoggedIn() {
        return emiteUIProvider.get().isLoggedIn();
    }

    public void joinRoom(final String roomName, final String userAlias) {
        if (emiteUIProvider.get().isLoggedIn()) {
            final XmppURI roomURI = XmppURI.uri(roomName + "@" + chatOptions.roomHost + "/"
                    + chatOptions.userOptions.getUserJid().getNode());
            emiteUIProvider.get().joinRoom(roomURI);
        } else {
            ws.showAlertMessage(i18n.t("Error"), i18n.t("To join a chatroom you need to be 'online'"));
        }
    }

    public void login(final String jid, final String passwd) {
        final UserChatOptions userChatOptions = getUserChatOptions(jid, passwd);
        // FIXME: Avatar provider
        final AvatarProvider avatarProvider = new AvatarProvider() {
            public String getAvatarURL(XmppURI userURI) {
                if (userURI.getHost().equals(chatOptions.domain)) {
                    return downloadUtils.get().getLogoImageUrl(new StateToken(userURI.getNode()));
                } else {
                    return "";
                    // return "images/person-def.gif";
                }
            }
        };
        final String initialWindowTitle = Window.getTitle();
        chatOptions.userOptions = userChatOptions;
        if (emiteUIProvider.get().isDialogNotStarted()) {
            emiteUIProvider.get().onRosterChanged(new Listener<Collection<RosterItem>>() {
                public void onEvent(Collection<RosterItem> rosterChanged) {
                    roster = rosterChanged;
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
            traybarButton.setTooltip(i18n.t("Show/hide the chat window"));
            traybarButton.setIcon("images/e-icon.gif");
            traybarButton.addListener(new ButtonListenerAdapter() {
                @Override
                public void onClick(final Button button, final EventObject e) {
                    if (emiteUIProvider.get().isVisible()) {
                        emiteUIProvider.get().hide();
                    } else {
                        emiteUIProvider.get().show();
                    }
                }
            });
            ws.getSiteTraybar().addButton(traybarButton);
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

    public void setAvatar(String photoBinary) {
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

    private XmppURI getLocalUserJid(String shortName) {
        return XmppURI.jid(shortName + "@" + chatOptions.domain);
    }

    private UserChatOptions getUserChatOptions(final String jid, final String passwd) {
        final String resource = "emiteui-" + new Date().getTime() + "-kune"; // +
        // getGwtMetaProperty(GWT_PROPERTY_RELEASE);
        // FIXME, get this from user profile
        return new UserChatOptions(jid + "@" + chatOptions.domain, passwd, resource, "blue",
                SubscriptionMode.autoAcceptAll, true);
    }
}
