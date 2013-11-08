package cc.kune.gspace.client.share.items.actions;

import cc.kune.common.client.actions.InDevelopmentAction;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.services.ClientFileDownloadUtils;

import com.google.inject.Inject;

public class ContentEditorShareItemDescriptor extends GroupShareItemDescriptor {

  public static class ChangeToAdminAction extends InDevelopmentAction {
  }
  public static class ChangeToAdminMenuItem extends AbstractToggleShareMenuItem {
    @Inject
    public ChangeToAdminMenuItem(final ChangeToAdminAction action, final IconicResources icons) {
      super(action);
      withText(I18n.t("Change to administrator")).withIcon(icons.upArrow());
    }
  }

  public static class RemoveAction extends InDevelopmentAction {
  }

  public static class RemoveMenuItem extends MenuItemDescriptor {
    @Inject
    public RemoveMenuItem(final RemoveAction action, final IconicResources icons) {
      super(action);
      withText(I18n.t("Remove")).withIcon(icons.del());
    }
  }

  @Inject
  public ContentEditorShareItemDescriptor(final IconicResources icons,
      final ClientFileDownloadUtils downloadUtils, final ChangeToAdminMenuItem changeToAdmin,
      final RemoveMenuItem remove) {
    super(downloadUtils, I18n.tWithNT("is editor", "someone is editor"), changeToAdmin, remove);
  }

}