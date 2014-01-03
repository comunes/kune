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

import cc.kune.common.shared.i18n.HasRTL;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class ActionSimplePanel.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ActionSimplePanel extends AbstractComposedGuiItem implements ActionExtensibleView {

  /** The bar. */
  private final HorizontalPanel bar;

  /**
   * Instantiates a new action simple panel.
   *
   * @param guiProvider the gui provider
   * @param i18n the i18n
   */
  @Inject
  public ActionSimplePanel(final GuiProvider guiProvider, final HasRTL i18n) {
    super(guiProvider, i18n);
    bar = new HorizontalPanel();
    initWidget(bar);
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.AbstractComposedGuiItem#addWidget(cc.kune.common.client.actions.ui.AbstractGuiItem)
   */
  @Override
  protected void addWidget(final AbstractGuiItem item) {
    bar.add(item);
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.AbstractComposedGuiItem#clear()
   */
  @Override
  public void clear() {
    super.clear();
    bar.clear();
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.AbstractComposedGuiItem#insertWidget(cc.kune.common.client.actions.ui.AbstractGuiItem, int)
   */
  @Override
  protected void insertWidget(final AbstractGuiItem item, final int position) {
    final int count = bar.getWidgetCount();
    bar.insert(item, count < position ? count : position);
  }
}
