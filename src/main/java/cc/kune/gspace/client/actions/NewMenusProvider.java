package cc.kune.gspace.client.actions;

import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;

import com.google.inject.Provider;

public abstract class NewMenusProvider implements Provider<MenuDescriptor> {

  private final AbstractNewMenu menu;

  public NewMenusProvider(final AbstractNewMenu menu) {
    this.menu = menu;
  }

  @Override
  public MenuDescriptor get() {
    return menu;
  }

}
