package cc.kune.gspace.client.share.items;

import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;

public class ShareItemDescriptor {
  public static final String ITEM = "share-item";
  private Object itemIcon;
  private String itemText;
  private final MenuItemDescriptor[] menuItems;
  private final String menuText;

  public ShareItemDescriptor(final Object itemIcon, final String itemText, final String menuText,
      final MenuItemDescriptor... menuItems) {
    this.setItemIcon(itemIcon);
    this.setItemText(itemText);
    this.menuText = menuText;
    this.menuItems = menuItems;
  }

  public ShareItemDescriptor(final String menuText, final MenuItemDescriptor... menuItems) {
    this.menuText = menuText;
    this.menuItems = menuItems;
  }

  public Object getItemIcon() {
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

  public void setItemIcon(final Object itemIcon) {
    this.itemIcon = itemIcon;
  }

  public void setItemText(final String itemText) {
    this.itemText = itemText;
  }

  public void setTarget(final Object target) {
    for (final MenuItemDescriptor item : menuItems) {
      item.getAction().putValue(ITEM, target);
    }
  }

}
