package cc.kune.bootstrap.client.ui;

import org.gwtbootstrap3.client.ui.constants.Styles;
import org.gwtbootstrap3.client.ui.html.UnorderedList;

import cc.kune.common.client.actions.ui.ParentWidget;

import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

public class DropDownSubmenu extends UnorderedList implements ParentWidget {

  public DropDownSubmenu() {
    addStyleName(Styles.DROPDOWN_MENU);
  }

  @Override
  public void add(final UIObject uiObject) {
    super.add((Widget) uiObject);
  }

  @Override
  public void insert(final int position, final UIObject uiObject) {
    super.insert((Widget) uiObject, position);
  }
}
