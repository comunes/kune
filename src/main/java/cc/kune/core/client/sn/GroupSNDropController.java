package cc.kune.core.client.sn;

import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.dnd.AbstractDropController;
import cc.kune.core.client.dnd.KuneDragController;
import cc.kune.core.client.rpcservices.ContentServiceHelper;
import cc.kune.core.client.rpcservices.SocialNetServiceHelper;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.ui.BasicDragableThumb;
import cc.kune.core.shared.dto.SocialNetworkSubGroup;
import cc.kune.gspace.client.viewers.items.FolderItemWidget;

import com.allen_sauer.gwt.dnd.client.VetoDragException;
import com.allen_sauer.gwt.dnd.client.drop.SimpleDropController;
import com.google.gwt.user.client.ui.Widget;

public class GroupSNDropController extends AbstractDropController {

  private final ContentServiceHelper contentService;
  private final Session session;
  private final SocialNetServiceHelper sNClientUtils;
  private final SocialNetworkSubGroup subGroup;

  public GroupSNDropController(final KuneDragController dragController,
      final SocialNetworkSubGroup subGroup, final ContentServiceHelper contentService,
      final Session session, final I18nTranslationService i18n,
      final SocialNetServiceHelper sNClientUtils) {
    super(dragController);
    this.subGroup = subGroup;
    this.contentService = contentService;
    this.session = session;
    this.sNClientUtils = sNClientUtils;
    registerType(FolderItemWidget.class);
    registerType(BasicDragableThumb.class);
  }

  @Override
  public void onDropAllowed(final Widget widget, final SimpleDropController dropController) {
    if (widget instanceof FolderItemWidget) {
      contentService.addParticipants(((FolderItemWidget) widget).getToken(), subGroup);
    }
    if (widget instanceof BasicDragableThumb) {
      final String shortName = ((BasicDragableThumb) widget).getToken().getGroup();
      if (subGroup.equals(SocialNetworkSubGroup.admins)) {
        sNClientUtils.changeToAdmin(shortName);
      }
      if (subGroup.equals(SocialNetworkSubGroup.collabs)) {
        sNClientUtils.changeToCollab(shortName);
      }
    }
  }

  @Override
  public void onPreviewAllowed(final Widget widget, final SimpleDropController dropController)
      throws VetoDragException {
    if (widget instanceof FolderItemWidget) {
      if (!((FolderItemWidget) widget).getToken().isComplete()) {
        throw new VetoDragException();
      }
    }
    if (widget instanceof BasicDragableThumb) {
      if (!session.getCurrentState().getGroupRights().isAdministrable()) {
        throw new VetoDragException();
      }
    }
    super.onPreviewAllowed(widget, dropController);
  }
}
