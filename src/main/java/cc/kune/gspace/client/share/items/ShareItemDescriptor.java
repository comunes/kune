package cc.kune.gspace.client.share.items;

import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;

import com.google.gwt.resources.client.ImageResource;

public class ShareItemDescriptor {
  public static final String ITEM = "share-item";
  private final ImageResource itemIcon;
  private final String itemText;
  private final MenuItemDescriptor[] menuItems;
  private final String menuText;

  public ShareItemDescriptor(final ImageResource itemIcon, final String itemText, final String menuText,
      final MenuItemDescriptor... menuItems) {
    this.itemIcon = itemIcon;
    this.itemText = itemText;
    this.menuText = menuText;
    this.menuItems = menuItems;
  }

  public ImageResource getItemIcon() {
    return itemIcon;
  }

  public String getItemText() {
    return itemText;
  }

  public MenuItemDescriptor[] getMenuItems() {
    return menuItems;
  }

  public String getMenuText() {
    return menuText;
  }

  public void setTarget(final Object target) {
    for (final MenuItemDescriptor item : menuItems) {
      item.getAction().putValue(ITEM, target);
    }
  }

}
