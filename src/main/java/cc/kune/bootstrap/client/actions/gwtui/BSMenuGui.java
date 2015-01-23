/*
 *
 * opyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package cc.kune.bootstrap.client.actions.gwtui;

import java.util.Arrays;
import java.util.List;

import org.gwtbootstrap3.client.shared.event.HideEvent;
import org.gwtbootstrap3.client.shared.event.HideHandler;
import org.gwtbootstrap3.client.ui.Label;
import org.gwtbootstrap3.client.ui.constants.Styles;

import cc.kune.bootstrap.client.ui.ComplexListDropDown;
import cc.kune.common.client.actions.PropertyChangeEvent;
import cc.kune.common.client.actions.PropertyChangeListener;
import cc.kune.common.client.actions.gwtui.HasMenuItem;
import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.ParentWidget;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.Position;
import cc.kune.common.shared.res.KuneIcon;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

/**
 * The Class BSMenuGui.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class BSMenuGui extends AbstractBSMenuGui {

  private ComplexListDropDown menu;

  /** The not stand alone. */
  private boolean notStandAlone;

  @Override
  public void add(final UIObject uiObject) {
    menu.add((Widget) uiObject);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.AbstractChildGuiItem#addStyle(java.lang
   * .String)
   */
  @Override
  protected void addStyle(final String style) {
    if (notStandAlone) {
      menu.addStyleName(style);
    }
  }

  private void clearCurrentActiveItem() {

    final int count = menu.getWidgetCount();
    for (int cur = 0; cur < count; cur++) {
      final Widget item = menu.getWidget(cur);
      final List<String> styles = Arrays.asList(item.getStyleName().split(" "));
      if (styles.contains(Styles.ACTIVE)) {
        item.removeStyleName(Styles.ACTIVE);
      }
    }
  }

  @Override
  public void configureItemFromProperties() {
    super.configureItemFromProperties();
    descriptor.addPropertyChangeListener(new PropertyChangeListener() {
      @Override
      public void propertyChange(final PropertyChangeEvent event) {
        if (event.getPropertyName().equals(MenuDescriptor.MENU_CLEAR)) {
          menu.clear();
        }
      }
    });
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.gwtui.AbstractGwtMenuGui#create(cc.kune.common
   * .client.actions.ui.descrip.GuiActionDescrip)
   */
  @Override
  public AbstractGuiItem create(final GuiActionDescrip descriptor) {
    super.descriptor = descriptor;
    descriptor.putValue(ParentWidget.PARENT_UI, this);
    // Standalone menus are menus without and associated button in a toolbar
    // (sometimes, a menu showed in a grid, or other special widgets)
    final Boolean inline = (Boolean) descriptor.getValue(MenuDescriptor.MENU_VERTICAL);

    notStandAlone = !((MenuDescriptor) descriptor).isStandalone();
    if (notStandAlone) {
      menu = new ComplexListDropDown();
      // TODO
      // menu.setInline(inline);
      // descriptor.putValue(MenuDescriptor.MENU_SHOW_NEAR_TO, button);
      final ImageResource rightIcon = ((MenuDescriptor) descriptor).getRightIcon();
      if (rightIcon != null) {
        menu.setIconRightResource(rightIcon);
      }
      menu.addClickHandler(new ClickHandler() {
        @Override
        public void onClick(final ClickEvent event) {
          if (menu.isEnabled()) {
            event.stopPropagation();
            show();
          }
        }
      });
      menu.addHideHandler(new HideHandler() {
        @Override
        public void onHide(final HideEvent hideEvent) {
          descriptor.putValue(MenuDescriptor.MENU_ONHIDE, menu);
        }
      });
      descriptor.addPropertyChangeListener(new PropertyChangeListener() {
        private void activeNextItem(final int increment) {
          final int count = menu.getWidgetCount();
          for (int cur = 0; cur < count && cur >= 0; cur = cur + increment) {
            final Widget item = menu.getWidget(cur);
            final List<String> styles = Arrays.asList(item.getStyleName().split(" "));
            if (styles.contains(Styles.ACTIVE)) {
              item.removeStyleName(Styles.ACTIVE);
              final int proposed = cur + increment;
              final int next = proposed >= count ? 0 : (proposed < 0) ? count - 1 : proposed;
              menu.getWidget(next).addStyleName(Styles.ACTIVE);
              break;
            }
          }
        }

        @Override
        public void propertyChange(final PropertyChangeEvent event) {
          if (event.getPropertyName().equals(MenuDescriptor.MENU_HIDE)) {
            if (menu != null && menu.isVisible()) {
              menu.hide();
            }
          } else if (event.getPropertyName().equals(MenuDescriptor.MENU_SHOW)) {
            show();
          } else if (event.getPropertyName().equals(MenuDescriptor.MENU_SELECTION_DOWN)) {
            activeNextItem(1);
          } else if (event.getPropertyName().equals(MenuDescriptor.MENU_SELECTION_UP)) {
            activeNextItem(-1);
          } else if (event.getPropertyName().equals(MenuDescriptor.MENU_SELECT_ITEM)) {
            final HasMenuItem item = (HasMenuItem) ((MenuItemDescriptor) descriptor.getValue(MenuDescriptor.MENU_SELECT_ITEM)).getValue(MenuItemDescriptor.UI);
            clearCurrentActiveItem();
            ((Widget) item).addStyleName(Styles.ACTIVE);
          }
        }
      });

      final String id = descriptor.getId();
      if (id != null && "undefined".equals(id)) {
        menu.ensureDebugId(id);
      }
      if (descriptor.isChild()) {
        child = menu;
      } else {
        initWidget(menu);
      }
    } else {
      initWidget(new Label());
    }
    super.create(descriptor);
    configureItemFromProperties();
    descriptor.addPropertyChangeListener(new PropertyChangeListener() {
      @Override
      public void propertyChange(final PropertyChangeEvent event) {
        final Object newValue = event.getNewValue();
        if (event.getPropertyName().equals(MenuDescriptor.MENU_RIGHTICON)) {
          setIconRightResource((ImageResource) newValue);
        }
      }
    });

    return this;
  }

  @Override
  public void insert(final int position, final UIObject widget) {
    menu.insert((Widget) widget, position);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.common.client.actions.ui.AbstractGuiItem#setEnabled(boolean)
   */
  @Override
  public void setEnabled(final boolean enabled) {
    if (notStandAlone) {
      menu.setEnabled(enabled);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.AbstractGuiItem#setIcon(cc.kune.common
   * .shared.res.KuneIcon)
   */
  @Override
  public void setIcon(final KuneIcon icon) {
    if (notStandAlone) {
      menu.setIcon(icon);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.AbstractGuiItem#setIconBackground(java
   * .lang.String)
   */
  @Override
  public void setIconBackColor(final String backgroundColor) {
    if (notStandAlone) {
      menu.setIconBackColor(backgroundColor);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.AbstractGuiItem#setIconResource(com.google
   * .gwt.resources.client.ImageResource)
   */
  @Override
  public void setIconResource(final ImageResource resource) {
    if (notStandAlone) {
      menu.setIconResource(resource);
    }
  }

  /**
   * Sets the icon right resource.
   *
   * @param resource
   *          the new icon right resource
   */
  public void setIconRightResource(final ImageResource resource) {
    if (notStandAlone) {
      menu.setIconRightResource(resource);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.AbstractGuiItem#setIconStyle(java.lang
   * .String)
   */
  @Override
  public void setIconStyle(final String style) {
    if (notStandAlone) {
      menu.setIconStyle(style);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.AbstractGuiItem#setIconUrl(java.lang.String
   * )
   */
  @Override
  public void setIconUrl(final String url) {
    if (notStandAlone) {
      menu.setIconUrl(url);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.AbstractGuiItem#setText(java.lang.String)
   */
  @Override
  public void setText(final String text) {
    if (notStandAlone) {
      // FIXME descriptor.getDirection()
      menu.setMenuText(text);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.AbstractGuiItem#setToolTipText(java.lang
   * .String)
   */
  @Override
  public void setToolTipText(final String tooltipText) {
    if (notStandAlone) {
      setToolTipTextNextTo(menu, tooltipText);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.gwt.user.client.ui.UIObject#setVisible(boolean)
   */
  @Override
  public void setVisible(final boolean visible) {
    if (notStandAlone) {
      menu.setVisible(visible);
    }
  }

  @Override
  public boolean shouldBeAdded() {
    return !descriptor.hasParent();
  }

  protected void show() {
    final Object relative = descriptor.getValue(MenuDescriptor.MENU_SHOW_NEAR_TO);
    if (relative instanceof String) {
      final RootPanel element = RootPanel.get((String) relative);
      menu.show(element.getOffsetWidth(), element.getOffsetHeight());
    } else if (relative instanceof UIObject) {
      final UIObject object = (UIObject) relative;
      menu.show(object.getOffsetWidth(), getOffsetHeight());
    } else if (relative instanceof Position) {
      final Position position = (Position) relative;
      menu.show(position.getX(), position.getY());
    } else {
      menu.show();
    }

  }
}
