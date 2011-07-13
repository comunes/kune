package cc.kune.chat.client;

import cc.kune.core.client.avatar.MediumAvatarDecorator;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Inject;

public class MediumAvatarDecoratorImpl extends AvatarDecoratorImpl implements MediumAvatarDecorator {

  @Inject
  public MediumAvatarDecoratorImpl(final I18nTranslationService i18n, final CoreResources res,
      final ChatInstances chatInstances, final ChatClient chatClient) {
    super(i18n, chatInstances, chatClient, res.chatDotBusyMedium(), res.chatDotAwayMedium(),
        res.chatDotAwayMedium(), res.chatDotExtendedAwayMedium(), res.chatDotAvailableMedium());
    setImagePosition(53, -6, -10);
  }
}
