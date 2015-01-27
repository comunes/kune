package cc.kune.bootstrap.client.ui;

import org.gwtbootstrap3.client.ui.NavbarNav;
import org.gwtbootstrap3.client.ui.constants.Pull;
import org.gwtbootstrap3.client.ui.constants.Styles;

import cc.kune.common.client.actions.ui.ParentWidget;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

public class ComplexNavbarNav extends NavbarNav implements ParentWidget {

  private Pull currentPull = Pull.LEFT;

  @Override
  public void add(final UIObject uiObject) {
    final Widget widget = setPull(uiObject);
    super.add(widget);
  }

  /**
   * Adds the fill.
   *
   * @return the widget
   */
  public Widget addFill() {
    currentPull = Pull.RIGHT; // From now, all widgets to the right
    return new Label(""); // return an empty widget;
  }

  /**
   * Adds the separator.
   *
   * @return the widget
   */
  public Widget addSeparator() {
    final Label separator = new Label("");
    setPull(separator);
    this.add(separator);
    return separator;
  }

  /**
   * Adds the spacer.
   *
   * @return the widget
   */
  public Widget addSpacer() {
    final Label separator = new Label(" ");
    setPull(separator);
    this.add(separator);
    return separator;
  }

  @Override
  public void insert(final int position, final UIObject uiObject) {
    setPull(uiObject);
    super.insert((Widget) uiObject, position);
  }

  private Widget setPull(final UIObject uiObject) {
    final Widget widget = (Widget) uiObject;
    widget.addStyleName(currentPull.equals(Pull.LEFT) ? Styles.PULL_LEFT : Styles.PULL_RIGHT);
    return widget;
  }

}
