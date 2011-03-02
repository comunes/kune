package cc.kune.chat.client;

import cc.kune.chat.client.resources.ChatResources;
import cc.kune.common.client.actions.AbstractAction;
import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.MenuRadioItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuSeparatorDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuTitleItemDescriptor;
import cc.kune.core.client.sitebar.SiteUserOptions;
import cc.kune.core.client.sitebar.SiteUserOptionsPresenter;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.calclab.emite.core.client.events.StateChangedEvent;
import com.calclab.emite.core.client.events.StateChangedHandler;
import com.calclab.emite.core.client.xmpp.session.XmppSession;
import com.calclab.emite.core.client.xmpp.stanzas.Presence;
import com.calclab.emite.core.client.xmpp.stanzas.Presence.Show;
import com.calclab.emite.im.client.presence.PresenceManager;
import com.calclab.emite.im.client.presence.events.OwnPresenceChangedEvent;
import com.calclab.emite.im.client.presence.events.OwnPresenceChangedHandler;
import com.calclab.suco.client.Suco;
import com.google.gwt.resources.client.ImageResource;
import com.google.inject.Inject;

public class ChatSitebarActions {

    public class ChangeOfflineStatusAction extends AbstractExtendedAction {
        public ChangeOfflineStatusAction(final ImageResource icon) {
            xmppSession.addSessionStateChangedHandler(true, new StateChangedHandler() {
                @Override
                public void onStateChanged(final StateChangedEvent event) {
                    if (!xmppSession.isReady()) {
                        SiteUserOptionsPresenter.LOGGED_USER_MENU.putValue(AbstractAction.SMALL_ICON, icon);
                    }
                }
            });
        }

        @Override
        public void actionPerformed(final ActionEvent event) {
            // presenceManager.changeOwnPresence(Presence.build(NO_STATUS,
            // Show.xa));
            xmppSession.logout();
        }
    }
    public class ChangeOnlineStatusAction extends AbstractExtendedAction {
        private final Presence thisPresence;

        public ChangeOnlineStatusAction(final String statusText, final Show show, final ImageResource icon) {
            thisPresence = Presence.build(statusText, show);
            presenceManager.addOwnPresenceChangedHandler(new OwnPresenceChangedHandler() {

                @Override
                public void onOwnPresenceChanged(final OwnPresenceChangedEvent event) {
                    updateStatusIcon(icon, event.getCurrentPresence());
                }

            });
            updateStatusIcon(icon, presenceManager.getOwnPresence());
        }

        @Override
        public void actionPerformed(final ActionEvent event) {
            if (!chatClient.loginIfNecessary()) {
                presenceManager.changeOwnPresence(thisPresence);
            }
            nextPresence = thisPresence;
        }

        private void updateStatusIcon(final ImageResource icon, final Presence currentPresence) {
            if (thisPresence.getShow().equals(currentPresence.getShow())
                    && ((currentPresence.getStatus() == null) || currentPresence.getStatus().equals(
                            thisPresence.getStatus()))) {
                SiteUserOptionsPresenter.LOGGED_USER_MENU.putValue(AbstractAction.SMALL_ICON, icon);
            }
        }
    }

    private static final String CHAT_STATUS = "k-chat-status-group";
    private static final String GROUP_CHAT_STATUS = "k-group-chat-status";
    private static final String NO_STATUS = null;
    private final ChatClient chatClient;
    private final I18nTranslationService i18n;
    private Presence nextPresence = Presence.build(NO_STATUS, Show.notSpecified);
    private final PresenceManager presenceManager = Suco.get(PresenceManager.class);
    private final ChatResources res;
    private final Session session;
    private final SiteUserOptions userOptions;
    private final XmppSession xmppSession = Suco.get(XmppSession.class);

    @Inject
    public ChatSitebarActions(final Session session, final ChatClient chatClient, final SiteUserOptions userOptions,
            final I18nTranslationService i18n, final ChatResources res) {
        this.session = session;
        this.chatClient = chatClient;
        this.userOptions = userOptions;
        this.i18n = i18n;
        this.res = res;
        createActions();
    }

    private void createActions() {
        final MenuTitleItemDescriptor chatActionsTitle = new MenuTitleItemDescriptor(
                SiteUserOptionsPresenter.LOGGED_USER_MENU, i18n.t("Set your chat status"));
        userOptions.addAction(new MenuSeparatorDescriptor(SiteUserOptionsPresenter.LOGGED_USER_MENU));
        userOptions.addAction(chatActionsTitle);
        final MenuRadioItemDescriptor onlineItem = createChatStatusAction(res.online(), i18n.t("Available"),
                onlineAction(NO_STATUS, Show.notSpecified, res.online()));
        final MenuRadioItemDescriptor availableItem = createChatStatusAction(res.xa(), i18n.t("Available for chat"),
                onlineAction(NO_STATUS, Show.chat, res.xa()));
        final MenuRadioItemDescriptor awayItem = createChatStatusAction(res.away(), i18n.t("Away"),
                onlineAction(NO_STATUS, Show.away, res.away()));
        final MenuRadioItemDescriptor busyItem = createChatStatusAction(res.busy(), i18n.t("Busy"),
                onlineAction(NO_STATUS, Show.dnd, res.busy()));
        final MenuRadioItemDescriptor offlineItem = createChatStatusAction(res.offline(), i18n.t("Sign out of chat"),
                new ChangeOfflineStatusAction(res.offline()));
        userOptions.addAction(onlineItem);
        userOptions.addAction(availableItem);
        userOptions.addAction(awayItem);
        userOptions.addAction(busyItem);
        userOptions.addAction(offlineItem);
        xmppSession.addSessionStateChangedHandler(false, new StateChangedHandler() {

            @Override
            public void onStateChanged(final StateChangedEvent event) {
                if (xmppSession.isReady()) {
                    presenceManager.changeOwnPresence(nextPresence);
                    switch (nextPresence.getShow()) {
                    case notSpecified:
                        onlineItem.setChecked(true);
                        break;
                    case dnd:
                        busyItem.setChecked(true);
                        break;
                    case chat:
                        availableItem.setChecked(true);
                        break;
                    case away:
                        awayItem.setChecked(true);
                        break;
                    }
                } else {
                    offlineItem.setChecked(true);
                }
            }
        });
    }

    private MenuRadioItemDescriptor createChatStatusAction(final ImageResource icon, final String text,
            final AbstractAction action) {
        final MenuRadioItemDescriptor item = new MenuRadioItemDescriptor(SiteUserOptionsPresenter.LOGGED_USER_MENU,
                action, GROUP_CHAT_STATUS);
        item.putValue(AbstractAction.NAME, text);
        item.putValue(AbstractAction.SMALL_ICON, icon);
        return item;
    }

    private AbstractExtendedAction onlineAction(final String statusText, final Show show, final ImageResource icon) {
        return new ChangeOnlineStatusAction(statusText, show, icon);
    }
}
