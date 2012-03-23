package cc.kune.common.client.actions.ui.descrip;

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.ActionEvent;

import com.google.inject.Singleton;

@Singleton
public class MenuShowAction extends AbstractExtendedAction {

  private MenuDescriptor menu;

  @Override
  public void actionPerformed(final ActionEvent event) {
    menu.show();
    menu.moveSelectionDown();
  }

  public void setMenu(final MenuDescriptor menu) {
    this.menu = menu;
  }

}