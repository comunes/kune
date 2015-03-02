package cc.kune.bootstrap.client.actions.ui;

import cc.kune.common.client.actions.PropertyChangeEvent;
import cc.kune.common.client.actions.PropertyChangeListener;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.client.actions.ui.descrip.Position;
import cc.kune.common.client.tooltip.Tooltip;

import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.PopupPanel.PositionCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.UIObject;

public class PopupBSMenuGui {

  private final GuiActionDescrip descriptor;
  private PopupPanel popup;
  protected IsWidget popupContent;
  private final IsWidget relativeTo;
  private Tooltip tooltip;

  public PopupBSMenuGui(final IsWidget content, final IsWidget relativeTo,
      final GuiActionDescrip descriptor) {
    this.popupContent = content;
    this.relativeTo = relativeTo;
    this.descriptor = descriptor;
    descriptor.addPropertyChangeListener(new PropertyChangeListener() {
      @Override
      public void propertyChange(final PropertyChangeEvent event) {
        if (event.getPropertyName().equals(MenuDescriptor.MENU_HIDE)) {
          if (popup != null && popup.isShowing()) {
            popup.hide();
          }
        } else if (event.getPropertyName().equals(MenuDescriptor.MENU_SHOW)) {
          show();
        }
      }
    });
  }

  /**
   * Creates the popup that includes the menu.
   *
   * @return the popup panel
   */
  private PopupPanel createPopup() {
    popup = new PopupPanel(true);
    popup.setStyleName("oc-menu");
    popup.add(popupContent);
    SmartMenuUtils.init(popup.getElement());
    popup.addCloseHandler(new CloseHandler<PopupPanel>() {
      @Override
      public void onClose(final CloseEvent<PopupPanel> event) {
        descriptor.putValue(MenuDescriptor.MENU_ONHIDE, popup);
      }
    });
    return popup;
  }

  private void createPopupIfNecessary() {
    if (popup == null) {
      createPopup();
    }
  }

  public void hide() {
    createPopupIfNecessary();
    popup.hide();
  }

  public boolean isShowing() {
    return isShowing();
  }

  public void setTooltip(final Tooltip tooltip) {
    this.tooltip = tooltip;
  }

  public void show() {
    createPopupIfNecessary();
    final Object pos = descriptor.getValue(MenuDescriptor.MENU_SHOW_NEAR_TO);
    if (pos != null) {
      showRelativeTo(pos);
    } else {
      showRelativeTo(relativeTo);
    }
  }

  public void showRelativeTo(final Object relative) {
    createPopupIfNecessary();
    if (relative instanceof String) {
      popup.showRelativeTo(RootPanel.get((String) relative));
    } else if (relative instanceof UIObject) {
      final Boolean atRight = ((Boolean) descriptor.getValue(MenuDescriptor.MENU_ATRIGHT));
      if (atRight) {
        popup.setPopupPositionAndShow(new PositionCallback() {
          @Override
          public void setPosition(final int offsetWidth, final int offsetHeight) {
            final UIObject obj = (UIObject) relative;
            popup.setPopupPosition(obj.getAbsoluteLeft() + obj.getOffsetWidth(),
                obj.getAbsoluteTop() + 30);
          }
        });
      } else {
        popup.showRelativeTo((UIObject) relative);
      }
    } else if (relative instanceof Position) {
      popup.setPopupPositionAndShow(new PositionCallback() {
        @Override
        public void setPosition(final int offsetWidth, final int offsetHeight) {
          final Position position = (Position) relative;
          popup.setPopupPosition(position.getX(), position.getY());
        }
      });
    }
    descriptor.putValue(MenuDescriptor.MENU_ONSHOW, popup);
    if (tooltip != null) {
      tooltip.hide();
    }
  }
}
