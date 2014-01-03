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
package cc.kune.common.client.actions.ui;

import java.util.List;

import cc.kune.common.client.actions.ui.descrip.AbstractParentGuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.HasChilds;
import cc.kune.common.client.errors.UIException;
import cc.kune.common.shared.i18n.HasRTL;

import com.google.gwt.user.client.ui.Composite;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractComposedGuiItem.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public abstract class AbstractComposedGuiItem extends Composite implements IsActionExtensible {
  
  /** The bindings. */
  private final GuiProvider bindings;
  
  /** The gui items. */
  private GuiActionDescCollection guiItems;
  
  /** The i18n. */
  private final HasRTL i18n;

  /**
   * Instantiates a new abstract composed gui item.
   *
   * @param bindings the bindings
   * @param i18n the i18n
   */
  public AbstractComposedGuiItem(final GuiProvider bindings, final HasRTL i18n) {
    super();
    this.bindings = bindings;
    this.i18n = i18n;
  }

  /**
   * Adds the.
   *
   * @param descriptors the descriptors
   */
  public void add(final GuiActionDescCollection descriptors) {
    for (final GuiActionDescrip descriptor : descriptors) {
      add(descriptor);
    }
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.IsActionExtensible#add(cc.kune.common.client.actions.ui.descrip.GuiActionDescrip[])
   */
  @Override
  public void add(final GuiActionDescrip... descriptors) {
    for (final GuiActionDescrip descriptor : descriptors) {
      add(descriptor);
    }
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.IsActionExtensible#add(cc.kune.common.client.actions.ui.descrip.GuiActionDescrip)
   */
  @Override
  public void add(final GuiActionDescrip descriptor) {
    getGuiItems().add(descriptor);
    beforeAddWidget(descriptor);
  }

  /**
   * Adds the actions.
   *
   * @param descriptors the descriptors
   */
  public void addActions(final List<GuiActionDescrip> descriptors) {
    for (final GuiActionDescrip descriptor : descriptors) {
      add(descriptor);
    }
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.IsActionExtensible#addAll(cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection)
   */
  @Override
  public void addAll(final GuiActionDescCollection descriptors) {
    for (final GuiActionDescrip descriptor : descriptors) {
      add(descriptor);
    }
  }

  /**
   * Adds the widget.
   *
   * @param item the item
   */
  protected abstract void addWidget(AbstractGuiItem item);

  /**
   * Before add widget.
   *
   * @param descrip the descrip
   */
  protected void beforeAddWidget(final GuiActionDescrip descrip) {
    if (descrip.mustBeAdded()) {
      final GuiBinding binding = bindings.get(descrip.getType());
      if (binding == null) {
        throw new UIException("Unknown binding for: " + descrip);
      } else {
        // We set at that moment if the widget should be RTL or not
        descrip.setRTL(i18n.isRTL());
        final AbstractGuiItem item = binding.create(descrip);
        if (binding.shouldBeAdded()) {
          // TODO Change this ^ to shouldBeAttached
          if (descrip.getPosition() == GuiActionDescrip.NO_POSITION) {
            addWidget(item);
          } else {
            insertWidget(item, descrip.getPosition());
          }
        }
        if (descrip instanceof HasChilds) {
          for (final GuiActionDescrip child : ((AbstractParentGuiActionDescrip) descrip).getChilds()) {
            // Log.info("Child added: " + child.getValue(Action.NAME));
            add(child);
          }
        }
      }
    }
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.IsActionExtensible#clear()
   */
  @Override
  public void clear() {
    getGuiItems().clear();
  }

  /**
   * Gets the gui items.
   *
   * @return the gui items
   */
  public GuiActionDescCollection getGuiItems() {
    if (guiItems == null) {
      guiItems = new GuiActionDescCollection();
    }
    return guiItems;
  }

  /**
   * Insert widget.
   *
   * @param item the item
   * @param position the position
   */
  protected abstract void insertWidget(AbstractGuiItem item, int position);
}
