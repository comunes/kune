package cc.kune.sandbox.client;

import org.gwtbootstrap3.client.ui.AnchorButton;
import org.gwtbootstrap3.client.ui.AnchorListItem;
import org.gwtbootstrap3.client.ui.DropDownMenu;
import org.gwtbootstrap3.client.ui.ListDropDown;
import org.gwtbootstrap3.client.ui.constants.Toggle;

public class NavBarDropDown extends ListDropDown {
  private final AnchorButton anchorButton;
  private final DropDownMenu menu;

  public NavBarDropDown() {
    anchorButton = new AnchorButton();
    anchorButton.setDataToggle(Toggle.DROPDOWN);
    menu = new DropDownMenu();
    add(anchorButton);
    add(menu);
  }

  public void add(final AnchorListItem item) {
    menu.add(item);
  }

  public void setText(final String text) {
    anchorButton.setText(text);
  }
}
