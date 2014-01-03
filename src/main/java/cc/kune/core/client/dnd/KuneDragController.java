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

import cc.kune.gspace.client.armor.GSpaceArmor;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class KuneDragController.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class KuneDragController extends PickupDragController {

  /** The main panel. */
  private final Widget mainPanel;

  /**
   * Instantiates a new kune drag controller.
   * 
   * @param armor
   *          the armor
   */
  @Inject
  public KuneDragController(final GSpaceArmor armor) {
    super(RootPanel.get(), false);
    mainPanel = (Widget) armor.getMainpanel();
    setBehaviorDragProxy(true);
    setBehaviorMultipleSelection(false);
    setBehaviorScrollIntoView(false);
    setBehaviorDragStartSensitivity(5);
    Window.addResizeHandler(new ResizeHandler() {

      @Override
      public void onResize(final ResizeEvent event) {
        setRootPanelSize();
      }

    });
    setRootPanelSize();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.allen_sauer.gwt.dnd.client.PickupDragController#dragEnd()
   */
  @Override
  public void dragEnd() {
    super.dragEnd();
    clearSelection();
  }

  /**
   * Sets the root panel size.
   */
  private void setRootPanelSize() {
    Scheduler.get().scheduleDeferred(new ScheduledCommand() {
      @Override
      public void execute() {
        // - 100 because of problems in chrommium (issue #76), not needed in ff
        RootPanel.get().setPixelSize(mainPanel.getOffsetWidth(), mainPanel.getOffsetHeight() - 100);
      }
    });
  }

}
