package cc.kune.gspace.client.actions;

import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;

import com.google.inject.Provider;

/**
 * The Class NewMenusProvider ins a way to create a kind of singeltons for menu
 * entries. This can be register in a list of Providers<GuiDescriptor> but in
 * fact only references one item and can be used to select the parent of some
 * menu items
 */
public abstract class NewMenuProvider implements Provider<MenuDescriptor> {

  private final AbstractNewMenu menu;

  public NewMenuProvider(final AbstractNewMenu menu) {
    this.menu = menu;
  }

  @Override
  public MenuDescriptor get() {
    return menu;
  }

}
