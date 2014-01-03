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
package cc.kune.core.client.dnd;

import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.armor.GSpaceArmor;

import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class NotImplementedDropManager.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class NotImplementedDropManager {

  /** The drag controller. */
  private final KuneDragController dragController;

  /** The i18n. */
  private final I18nTranslationService i18n;

  /**
   * Instantiates a new not implemented drop manager.
   * 
   * @param i18n
   *          the i18n
   * @param gSpaceArmor
   *          the g space armor
   * @param dragController
   *          the drag controller
   */
  @Inject
  public NotImplementedDropManager(final I18nTranslationService i18n, final GSpaceArmor gSpaceArmor,
      final KuneDragController dragController) {
    this.i18n = i18n;
    this.dragController = dragController;
    registerImpl((FlowPanel) gSpaceArmor.getEntityHeader());
  }

  /**
   * Register.
   * 
   * @param widget
   *          the widget
   */
  public void register(final Widget widget) {
    registerImpl(widget);
  }

  /**
   * Register impl.
   * 
   * @param widget
   *          the widget
   */
  private void registerImpl(final Widget widget) {
    final NotImplementedDropController dropController = new NotImplementedDropController(widget, i18n);
    dragController.registerDropController(dropController);
    widget.addAttachHandler(new Handler() {
      @Override
      public void onAttachOrDetach(final AttachEvent event) {
        if (!event.isAttached()) {
          dragController.unregisterDropController(dropController);
        }
      }
    });
  }
}
