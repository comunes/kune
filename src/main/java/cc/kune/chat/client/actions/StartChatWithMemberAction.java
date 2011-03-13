package cc.kune.chat.client.actions;

import cc.kune.chat.client.ChatClient;
import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.noti.NotifyUser;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.calclab.emite.core.client.xmpp.stanzas.XmppURI;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class StartChatWithMemberAction extends AbstractExtendedAction {
    private final Provider<ChatClient> chatClient;
    private final I18nTranslationService i18n;

    @Inject
    public StartChatWithMemberAction(final I18nTranslationService i18n, final CoreResources res,
            final Provider<ChatClient> chatClient) {
        this.i18n = i18n;
        this.chatClient = chatClient;
        putValue(NAME, i18n.t("Chat with this member"));
        putValue(Action.SMALL_ICON, res.newChat());
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
        chatClient.get().show();
        if (chatClient.get().isLoggedIn()) {
            chatClient.get().chat(XmppURI.jid(((GroupDTO) event.getTarget()).getShortName()));
        } else {
            NotifyUser.important(i18n.t("To start a chat you need to be 'online'"));
        }
    }

}
