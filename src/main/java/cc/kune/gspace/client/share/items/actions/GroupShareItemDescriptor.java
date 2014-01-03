package cc.kune.gspace.client.share.items.actions;

import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.core.client.services.ClientFileDownloadUtils;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.gspace.client.share.items.ShareItemDescriptor;

public class GroupShareItemDescriptor extends ShareItemDescriptor {

  private final ClientFileDownloadUtils downloadUtils;

  public GroupShareItemDescriptor(final ClientFileDownloadUtils downloadUtils, final String menuText,
      final MenuItemDescriptor... menuItems) {
    super(menuText, menuItems);
    this.downloadUtils = downloadUtils;
  }

  public void setGroup(final GroupDTO group) {
    final boolean isAnUser = group.isPersonal();
    setItemText((isAnUser ? "" : I18n.t("Group") + ": ") + group.getLongName());
    setItemIcon(downloadUtils.getGroupLogo(group));
  }
}
