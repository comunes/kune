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
import cc.kune.common.client.errors.UIException;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

public class BSWidgetMenuGui extends AbstractBasicGuiItem implements AbstractBSMenuGui {

  private Anchor anchor;
  private DropDownMenu menu;
  private Style menuStyle;
  private Widget widget;
  private HasClickHandlers widgetHasClick;

  @Override
  public void add(final UIObject uiObject) {
    final Widget childWidget = (Widget) uiObject;
    menu.add(childWidget);
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
    anchor.setDataToggle(Toggle.DROPDOWN);
    dropDown.add(anchor);
    menu = new DropDownMenu();
    dropDown.add(menu);

    // Inspired in:
    // https://stackoverflow.com/questions/18666601/use-bootstrap-3-dropdown-menu-as-context-menu
    menuStyle = menu.getElement().getStyle();
    widget = (Widget) descriptor.getValue(WidgetMenuDescriptor.WIDGET);
    try {
      widgetHasClick = (HasClickHandlers) widget;
    } catch (final ClassCastException e) {
      throw new UIException("Cannot cast to HasClickHandlers descriptor" + descriptor);
    }
    widgetHasClick.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        show();
        clickHandlerDefault.onClick(event);
      }
    });
    hide();

    descriptor.putValue(ParentWidget.PARENT_UI, this);
    initWidget(dropDown);
    configureItemFromProperties();
    return this;
  }

  @Override
  public void hide() {
    menuStyle.setDisplay(Display.NONE);
    menuStyle.setPosition(Position.ABSOLUTE);
  }

  @Override
  public void insert(final int position, final UIObject uiObject) {
    menu.insert((Widget) uiObject, position);
  }

  @Override
  public void show() {
    menuStyle.setDisplay(Display.BLOCK);
    menuStyle.setPosition(Position.STATIC);
    menuStyle.setLeft(widget.getOffsetWidth() + 1, Unit.PX);
    menuStyle.setTop(widget.getOffsetHeight() + 1, Unit.PX);
  }

}
