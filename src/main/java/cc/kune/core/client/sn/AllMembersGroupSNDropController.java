package cc.kune.core.client.sn;

import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.dnd.KuneDragController;
import cc.kune.core.client.rpcservices.ContentServiceHelper;
import cc.kune.core.client.rpcservices.SocialNetServiceHelper;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.dto.SocialNetworkSubGroup;

import com.google.inject.Inject;

public class AllMembersGroupSNDropController extends GroupSNDropController {

  @Inject
  public AllMembersGroupSNDropController(final KuneDragController dragController,
      final ContentServiceHelper contentService, final Session session,
      final I18nTranslationService i18n, final SocialNetServiceHelper sNClientUtils) {
    super(dragController, SocialNetworkSubGroup.ALL_GROUP_MEMBERS, contentService, session, i18n, sNClientUtils);
  }

}
