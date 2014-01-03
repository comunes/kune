package cc.kune.gspace.client.share.items.actions;

import cc.kune.common.client.actions.InDevelopmentAction;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.services.ClientFileDownloadUtils;

import com.google.inject.Inject;

public class ContentAdminShareItemDescriptor extends GroupShareItemDescriptor {

  public static class ChangeToEditorAction extends InDevelopmentAction {
  }
  public static class ChangeToEditorMenuItem extends AbstractToggleShareMenuItem {
    @Inject
    public ChangeToEditorMenuItem(final ChangeToEditorAction action, final IconicResources icons) {
      super(action);
      withText(I18n.t("Change to editor")).withIcon(icons.downArrow());
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
  public ContentAdminShareItemDescriptor(final IconicResources icons,
      final ClientFileDownloadUtils downloadUtils, final ChangeToEditorMenuItem changeToEditor,
      final RemoveMenuItem remove) {
    super(downloadUtils, I18n.tWithNT("is admin", "someone is administrator"), changeToEditor, remove);
  }

}