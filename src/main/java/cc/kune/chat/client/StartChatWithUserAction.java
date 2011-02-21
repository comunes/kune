package cc.kune.chat.client;

import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class StartChatWithUserAction extends StartChatWithMemberAction {
    @Inject
    public StartChatWithUserAction(final I18nTranslationService i18n, final CoreResources res,
            final Provider<ChatClient> chatClient) {
        super(i18n, res, chatClient);
        putValue(NAME, i18n.t("Start a chat with this person"));
    }
}
