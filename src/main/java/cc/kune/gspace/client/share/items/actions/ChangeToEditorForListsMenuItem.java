package cc.kune.gspace.client.share.items.actions;

import cc.kune.common.shared.i18n.I18n;
import cc.kune.core.client.resources.iconic.IconicResources;

import com.google.inject.Inject;

public class ChangeToEditorForListsMenuItem extends AbstractRoleShareMenuItem {
  @Inject
  public ChangeToEditorForListsMenuItem(final ChangeToEditorForContentsAction action, final IconicResources icons) {
    super(action);
    withText(I18n.t("Change to editor")).withIcon(icons.downArrow());
  }
}
