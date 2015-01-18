package cc.kune.bootstrap.client.actions.gwtui;

import org.gwtbootstrap3.client.ui.AnchorButton;
import org.gwtbootstrap3.client.ui.DropDownMenu;
import org.gwtbootstrap3.client.ui.ListDropDown;
import org.gwtbootstrap3.client.ui.constants.Toggle;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public class CustomListDropDown extends ListDropDown {

  private final AnchorButton anchor;
  private final DropDownMenu menu;

  public CustomListDropDown() {
    menu = new DropDownMenu();
    anchor = new AnchorButton();
    anchor.setDataToggle(Toggle.DROPDOWN);
    anchor.setText("Dropdown");
    super.add(anchor);
    super.add(menu);
  }

  @Override
  public void add(final IsWidget child) {
    menu.add(child);
  }

  @Override
  public void add(final Widget child) {
    menu.add(child);
  }

  @Override
  public boolean remove(final int index) {
    return menu.remove(index);
  }

  @Override
  public boolean remove(final Widget w) {
    return menu.remove(w);
  }
}
