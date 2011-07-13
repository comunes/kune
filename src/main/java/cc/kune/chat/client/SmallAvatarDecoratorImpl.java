package cc.kune.chat.client;

import cc.kune.core.client.avatar.SmallAvatarDecorator;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Inject;

public class SmallAvatarDecoratorImpl extends AvatarDecoratorImpl implements SmallAvatarDecorator {

  @Inject
  public SmallAvatarDecoratorImpl(final I18nTranslationService i18n, final CoreResources res,
      final ChatInstances chatInstances, final ChatClient chatClient) {
    super(i18n, chatInstances, chatClient, res.chatDotBusySmall(), res.chatDotAwaySmall(),
        res.chatDotAwaySmall(), res.chatDotExtendedAwaySmall(), res.chatDotAvailableSmall());
    setImagePosition(23, -9, -8);
  }
}
