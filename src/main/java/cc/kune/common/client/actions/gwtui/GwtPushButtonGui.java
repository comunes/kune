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
package cc.kune.common.client.actions.gwtui;

import cc.kune.common.client.actions.PropertyChangeEvent;
import cc.kune.common.client.actions.PropertyChangeListener;
import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.PushButtonDescriptor;

// TODO: Auto-generated Javadoc
/**
 * The Class GwtPushButtonGui.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class GwtPushButtonGui extends AbstractGwtButtonGui {

  /**
   * Instantiates a new gwt push button gui.
   */
  public GwtPushButtonGui() {
    super(true);
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.gwtui.AbstractGwtButtonGui#create(cc.kune.common.client.actions.ui.descrip.GuiActionDescrip)
   */
  @Override
  public AbstractGuiItem create(final GuiActionDescrip descriptor) {
    super.create(descriptor);
    super.descriptor = descriptor;
    super.enableTongle = true;
    final PushButtonDescriptor btn = (PushButtonDescriptor) descriptor;
    setPressed(btn.isPushed());
    btn.addPropertyChangeListener(new PropertyChangeListener() {
      @Override
      public void propertyChange(final PropertyChangeEvent event) {
        if (event.getPropertyName().equals(PushButtonDescriptor.PUSHED)) {
          setPressed(btn.isPushed());
        }
      }
    });
    return this;
  }

}
