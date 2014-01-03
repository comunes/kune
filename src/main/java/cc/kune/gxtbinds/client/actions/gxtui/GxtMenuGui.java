/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
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
package cc.kune.gxtbinds.client.actions.gxtui;

import cc.kune.common.client.actions.PropertyChangeEvent;
import cc.kune.common.client.actions.PropertyChangeListener;
import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.ParentWidget;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.client.errors.NotImplementedException;
import cc.kune.common.shared.res.KuneIcon;
import cc.kune.common.shared.utils.TextUtils;

import com.extjs.gxt.ui.client.Style.ButtonScale;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.button.SplitButton;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.extjs.gxt.ui.client.widget.menu.SeparatorMenuItem;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.UIObject;

// TODO: Auto-generated Javadoc
/**
 * The Class GxtMenuGui.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class GxtMenuGui extends AbstractGxtMenuGui implements ParentWidget {

  /** The button. */
  private SplitButton button;

  /** The not stand alone. */
  private boolean notStandAlone;

  /**
   * Instantiates a new gxt menu gui.
   */
  public GxtMenuGui() {
    super();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gxtbinds.client.actions.gxtui.AbstractGxtMenuGui#add(com.google
   * .gwt.user.client.ui.UIObject)
   */
  @Override
  public void add(final UIObject item) {
    menu.add((MenuItem) item);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gxtbinds.client.actions.gxtui.AbstractGxtMenuGui#addSeparator()
   */
  @Override
  public void addSeparator() {
    menu.add(new SeparatorMenuItem());
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
    button.addStyleName(style);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gxtbinds.client.actions.gxtui.AbstractGxtMenuGui#
   * configureItemFromProperties()
   */
  @Override
  public void configureItemFromProperties() {
    super.configureItemFromProperties();
    descriptor.addPropertyChangeListener(new PropertyChangeListener() {
      @Override
      public void propertyChange(final PropertyChangeEvent event) {
        if (event.getPropertyName().equals(MenuDescriptor.MENU_CLEAR)) {
          menu.removeAll();
        }
      }
    });
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gxtbinds.client.actions.gxtui.AbstractGxtMenuGui#create(cc.kune
   * .common.client.actions.ui.descrip.GuiActionDescrip)
   */
  @Override
  public AbstractGuiItem create(final GuiActionDescrip descriptor) {
    super.descriptor = descriptor;
    descriptor.putValue(ParentWidget.PARENT_UI, this);
    // Standalone menus are menus without and associated button in a
    // toolbar (sometimes, a menu showed in a grid, or other special
    // widgets)
    notStandAlone = !((MenuDescriptor) descriptor).isStandalone();
    if (notStandAlone) {
      button = new SplitButton("");
      button.setStylePrimaryName("oc-button");
      button.addSelectionListener(new SelectionListener<ButtonEvent>() {
        @Override
        public void componentSelected(final ButtonEvent ce) {
          show(button);
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
    }
    super.create(descriptor);
    configureItemFromProperties();
    return this;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gxtbinds.client.actions.gxtui.AbstractGxtMenuGui#insert(int,
   * com.google.gwt.user.client.ui.UIObject)
   */
  @Override
  public void insert(final int position, final UIObject item) {
    menu.insert((MenuItem) item, position);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.common.client.actions.ui.AbstractGuiItem#setEnabled(boolean)
   */
  @Override
  public void setEnabled(final boolean enabled) {
    if (button != null) {
      button.setEnabled(enabled);
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
    // TODO Auto-generated method stub
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.AbstractGuiItem#setIconResource(com.google
   * .gwt.resources.client.ImageResource)
   */
  @Override
  public void setIconResource(final ImageResource icon) {
    if (button != null) {
      button.setIcon(AbstractImagePrototype.create(icon));
      button.setScale(ButtonScale.SMALL);
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
    if (button != null) {
      button.setIconStyle(style);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gxtbinds.client.actions.gxtui.AbstractGxtMenuGui#setIconUrl(java
   * .lang.String)
   */
  @Override
  public void setIconUrl(final String url) {
    throw new NotImplementedException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.AbstractGuiItem#setText(java.lang.String)
   */
  @Override
  public void setText(final String text) {
    if (button != null) {
      button.setText(text);
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
  public void setToolTipText(final String tooltip) {
    if (button != null) {
      if (TextUtils.notEmpty(tooltip)) {
        button.setToolTip(new GxtDefTooltip(tooltip));
      }
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.gwt.user.client.ui.UIObject#setVisible(boolean)
   */
  @Override
  public void setVisible(final boolean visible) {
    if (button != null) {
      button.setVisible(visible);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gxtbinds.client.actions.gxtui.AbstractGxtMenuGui#shouldBeAdded()
   */
  @Override
  public boolean shouldBeAdded() {
    return notStandAlone;
  }
}
