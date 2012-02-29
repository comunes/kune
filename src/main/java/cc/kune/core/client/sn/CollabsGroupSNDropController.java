package cc.kune.core.client.sn;

import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.dnd.KuneDragController;
import cc.kune.core.client.rpcservices.ContentServiceAsync;
import cc.kune.core.client.sn.actions.SocialNetClientUtils;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.dto.SocialNetworkSubGroup;

import com.google.inject.Inject;

public class CollabsGroupSNDropController extends GroupSNDropController {

  @Inject
  public CollabsGroupSNDropController(final KuneDragController dragController,
      final ContentServiceAsync contentService, final Session session,
      final I18nTranslationService i18n, final SocialNetClientUtils sNClientUtils) {
    super(dragController, SocialNetworkSubGroup.collabs, contentService, session, i18n, sNClientUtils);
  }

}
