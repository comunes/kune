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
package cc.kune.common.client.actions.gwtui;

import cc.kune.common.client.actions.PropertyChangeEvent;
import cc.kune.common.client.actions.PropertyChangeListener;
import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.ParentWidget;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.client.ui.IconLabel;
import cc.kune.common.shared.res.KuneIcon;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;

// TODO: Auto-generated Javadoc
/**
 * The Class GwtMenuGui.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class GwtMenuGui extends AbstractGwtMenuGui {

  /** The button. */
  private Button button;

  /** The icon label. */
  private IconLabel iconLabel;

  /** The not stand alone. */
  private boolean notStandAlone;

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
      // iconLabel.addStyleName(style);
      button.addStyleName(style);
      // layout();
    }
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
    notStandAlone = !((MenuDescriptor) descriptor).isStandalone();
    if (notStandAlone) {
      button = new Button();
      button.removeStyleName("gwt-Button");
      descriptor.putValue(MenuDescriptor.MENU_SHOW_NEAR_TO, button);
      iconLabel = new IconLabel("");
      final ImageResource rightIcon = ((MenuDescriptor) descriptor).getRightIcon();
      if (rightIcon != null) {
        iconLabel.setRightIconResource(rightIcon);
        // iconLabel.addRightIconStyle("k-fr");
      }
      button.addClickHandler(new ClickHandler() {
        @Override
        public void onClick(final ClickEvent event) {
          if (button.isEnabled()) {
            event.stopPropagation();
            showRelativeTo(button);
          }
        }
      });
      final String id = descriptor.getId();
      if (id != null) {
        button.ensureDebugId(id);
      }
      if (!descriptor.isChild()) {
        initWidget(button);
      } else {
        child = button;
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

  /**
   * Layout.
   */
  private void layout() {
    button.setHTML(iconLabel.getElement().getInnerHTML());
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.common.client.actions.ui.AbstractGuiItem#setEnabled(boolean)
   */
  @Override
  public void setEnabled(final boolean enabled) {
    if (notStandAlone) {
      button.setEnabled(enabled);
      // button.setVisible(enabled);
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
      iconLabel.setLeftIconFont(icon);
      layout();
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
  public void setIconBackground(final String backgroundColor) {
    if (notStandAlone) {
      iconLabel.setLeftIconBackground(backgroundColor);
      layout();
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
      iconLabel.setLeftIconResource(resource);
      layout();
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
      iconLabel.setRightIconResource(resource);
      layout();
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
      iconLabel.setRightIcon(style);
      layout();
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
      iconLabel.setLeftIconUrl(url);
      layout();
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
      iconLabel.setText(text, descriptor.getDirection());
      layout();
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
      setToolTipTextNextTo(button, tooltipText);
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
      button.setVisible(visible);
    } else {
      // button.setVisible(visible);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.common.client.actions.gwtui.AbstractGwtMenuGui#show()
   */
  @Override
  protected void show() {
    showRelativeTo(descriptor.getValue(MenuDescriptor.MENU_SHOW_NEAR_TO));
  }

}
