package cc.kune.core.client.rpcservices;

import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.SocialNetworkSubGroup;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class ContentServiceHelper {

  private final Provider<ContentServiceAsync> contentService;
  private final I18nTranslationService i18n;
  private final Session session;

  @Inject
  public ContentServiceHelper(final Session session, final I18nTranslationService i18n,
      final Provider<ContentServiceAsync> contentService) {
    this.session = session;
    this.i18n = i18n;
    this.contentService = contentService;
  }

  public void addParticipants(final StateToken token, final SocialNetworkSubGroup subGroup) {
    contentService.get().addParticipants(session.getUserHash(), token,
        session.getCurrentGroupShortName(), subGroup, new AsyncCallbackSimple<Boolean>() {
          @Override
          public void onSuccess(final Boolean result) {
            NotifyUser.info(result ? i18n.t("Shared with members")
                : i18n.t("All these members are already partipating"));
          }
        });
  }
}
