package cc.kune.bootstrap.client.actions.ui;

import org.gwtbootstrap3.client.ui.Anchor;
import org.gwtbootstrap3.client.ui.DropDown;
import org.gwtbootstrap3.client.ui.DropDownMenu;
import org.gwtbootstrap3.client.ui.constants.Toggle;

import cc.kune.common.client.actions.PropertyChangeEvent;
import cc.kune.common.client.actions.PropertyChangeListener;
import cc.kune.common.client.actions.ui.AbstractBasicGuiItem;
import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.ParentWidget;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.WidgetMenuDescriptor;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

public class BSWidgetMenuGui extends AbstractBasicGuiItem implements ParentWidget {

  private Anchor anchor;
  private DropDownMenu menu;
  private IsWidget widget;

  @Override
  public void add(final UIObject uiObject) {
    final Widget childWidget = (Widget) uiObject;
    if (widget == null) {
      // the main widget is not configured, so we use the first added element,
      // as main widget
      anchor.add(childWidget);
      widget = childWidget;
    } else {
      menu.add(childWidget);
    }
  }

  @Override
  public void configureItemFromProperties() {
    super.configureItemFromProperties();
    descriptor.addPropertyChangeListener(new PropertyChangeListener() {
      @Override
      public void propertyChange(final PropertyChangeEvent event) {
        if (event.getPropertyName().equals(WidgetMenuDescriptor.MENU_CLEAR)) {
          menu.clear();
        }
      }
    });
  }

  @Override
  public AbstractGuiItem create(final GuiActionDescrip descriptor) {
    this.descriptor = descriptor;
    final DropDown dropDown = new DropDown();
    anchor = new Anchor();
    widget = (IsWidget) descriptor.getValue(WidgetMenuDescriptor.WIDGET);
    if (widget != null) {
      // We have a simple widget (thumbs, etc) with a menu, or if not, we add
      // the first child widget to the anchor
      anchor.add(widget);
    }
    anchor.setDataToggle(Toggle.DROPDOWN);
    dropDown.add(anchor);

    menu = new DropDownMenu();
    dropDown.add(menu);

    descriptor.putValue(ParentWidget.PARENT_UI, this);
    initWidget(dropDown);
    configureItemFromProperties();
    return this;
  }

  @Override
  public void insert(final int position, final UIObject uiObject) {
    menu.insert((Widget) uiObject, position);
  }

}
