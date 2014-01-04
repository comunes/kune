package cc.kune.gspace.client.share.items.actions;

import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.core.client.resources.iconic.IconicResources;

import com.google.inject.Inject;

public class RemoveShareItemMenuItem extends MenuItemDescriptor {
  @Inject
  public RemoveShareItemMenuItem(final RemoveShareItemAction action, final IconicResources icons) {
    super(action);
    withText(I18n.t("Remove")).withIcon(icons.del());
  }
}