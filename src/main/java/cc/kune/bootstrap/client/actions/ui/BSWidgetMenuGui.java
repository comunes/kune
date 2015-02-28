package cc.kune.bootstrap.client.actions.ui;

import org.gwtbootstrap3.client.ui.Anchor;
import org.gwtbootstrap3.client.ui.DropDown;
import org.gwtbootstrap3.client.ui.DropDownMenu;
import org.gwtbootstrap3.client.ui.constants.Pull;
import org.gwtbootstrap3.client.ui.constants.Toggle;

import cc.kune.common.client.actions.PropertyChangeEvent;
import cc.kune.common.client.actions.PropertyChangeListener;
import cc.kune.common.client.actions.ui.AbstractBasicGuiItem;
import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.ParentWidget;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.client.actions.ui.descrip.WidgetMenuDescriptor;
import cc.kune.common.client.errors.UIException;
import cc.kune.common.client.log.Log;

import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasEnabled;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

public class BSWidgetMenuGui extends AbstractBasicGuiItem implements AbstractBSMenuGui {

  private DropDownMenu menu;

  private PopupBSMenuGui popup;

  private Widget widget;

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
    final Object value = descriptor.getValue(ParentWidget.PARENT_UI);
    if (value != null) {
      // Already created ... so reset
      ((BSWidgetMenuGui) value).getMenu().clear();
      return this;
    }
    this.descriptor = descriptor;
    final DropDown dropDown = new DropDown();
    final Anchor anchor = new Anchor();
    anchor.setDataToggle(Toggle.DROPDOWN);
    dropDown.add(anchor);
    menu = new DropDownMenu();

    widget = (Widget) descriptor.getValue(WidgetMenuDescriptor.WIDGET);
    popup = new PopupBSMenuGui(menu, widget, descriptor);
    dropDown.add(menu);
    HasClickHandlers widgetHasClick;
    try {
      widgetHasClick = (HasClickHandlers) widget;
    } catch (final ClassCastException e) {
      throw new UIException("Cannot cast to HasClickHandlers descriptor" + descriptor);
    }
    widgetHasClick.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        event.stopPropagation();
        Log.warn("Clicked");
        clickHandlerDefault.onClick(event);
        show();
      }
    });

    descriptor.addPropertyChangeListener(new PropertyChangeListener() {
      @Override
      public void propertyChange(final PropertyChangeEvent event) {
        if (event.getPropertyName().equals(MenuDescriptor.MENU_HIDE)) {
          hide();
        } else if (event.getPropertyName().equals(MenuDescriptor.MENU_SHOW)) {
          show();
        }
        // } else if
        // (event.getPropertyName().equals(MenuDescriptor.MENU_SELECTION_DOWN))
        // {
        // menu.moveSelectionDown();
        // } else if
        // (event.getPropertyName().equals(MenuDescriptor.MENU_SELECTION_UP)) {
        // menu.moveSelectionUp();
        // } else if
        // (event.getPropertyName().equals(MenuDescriptor.MENU_SELECT_ITEM)) {
        // final HasMenuItem item = (HasMenuItem) ((MenuItemDescriptor)
        // descriptor.getValue(MenuDescriptor.MENU_SELECT_ITEM)).getValue(MenuItemDescriptor.UI);
        // menu.selectItem((MenuItem) item.getMenuItem());
        // }
      }
    });

    if ((Boolean) descriptor.getValue(MenuDescriptor.MENU_ATRIGHT)) {
      menu.setPull(Pull.RIGHT);
    }

    descriptor.putValue(ParentWidget.PARENT_UI, this);
    initWidget(dropDown);
    configureItemFromProperties();
    final String id = HTMLPanel.createUniqueId();
    menu.getElement().setId(id);
    return this;
  }

  public DropDownMenu getMenu() {
    return menu;
  }

  @Override
  public void hide() {
    popup.hide();
  }

  @Override
  public void insert(final int position, final UIObject uiObject) {
    menu.insert((Widget) uiObject, position);
  }

  @Override
  protected void setEnabled(final boolean enabled) {
    super.setEnabled(enabled);
    if (widget instanceof HasEnabled) {
      ((HasEnabled) widget).setEnabled(enabled);
    }
  }

  @Override
  public void setToolTipText(final String tooltipText) {
    setToolTipTextNextTo(widget, tooltipText);
    popup.setTooltip(tooltip);
  }

  @Override
  public void setVisible(final boolean visible) {
    super.setVisible(visible);
    if (widget != null) {
      widget.setVisible(visible);
    }
  }

  @Override
  public boolean shouldBeAdded() {
    // Because widget already exists
    return false;
  }

  @Override
  public void show() {
    menu.getElement().getStyle().setDisplay(Display.BLOCK);
    popup.show();
  }

}
