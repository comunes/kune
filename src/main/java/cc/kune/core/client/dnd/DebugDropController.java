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

import cc.kune.common.client.notify.NotifyUser;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.VetoDragException;
import com.allen_sauer.gwt.dnd.client.drop.SimpleDropController;
import com.google.gwt.user.client.ui.Widget;

// TODO: Auto-generated Javadoc
/**
 * The Class DebugDropController.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class DebugDropController extends SimpleDropController {

  /**
   * Instantiates a new debug drop controller.
   * 
   * @param dropTarget
   *          the drop target
   */
  public DebugDropController(final Widget dropTarget) {
    super(dropTarget);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.allen_sauer.gwt.dnd.client.drop.AbstractDropController#onEnter(com.
   * allen_sauer.gwt.dnd.client.DragContext)
   */
  @Override
  public void onEnter(final DragContext context) {
    NotifyUser.info("On enter in drop area");
    super.onEnter(context);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.allen_sauer.gwt.dnd.client.drop.AbstractDropController#onLeave(com.
   * allen_sauer.gwt.dnd.client.DragContext)
   */
  @Override
  public void onLeave(final DragContext context) {
    NotifyUser.info("On leave drop area");
    super.onLeave(context);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.allen_sauer.gwt.dnd.client.drop.AbstractDropController#onMove(com.
   * allen_sauer.gwt.dnd.client.DragContext)
   */
  @Override
  public void onMove(final DragContext context) {
    // NotifyUser.info("On move over drop area");
    super.onMove(context);
  };

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.allen_sauer.gwt.dnd.client.drop.AbstractDropController#onPreviewDrop
   * (com.allen_sauer.gwt.dnd.client.DragContext)
   */
  @Override
  public void onPreviewDrop(final DragContext context) throws VetoDragException {
    NotifyUser.info("On preview drop");
    throw new VetoDragException();
  }
}
