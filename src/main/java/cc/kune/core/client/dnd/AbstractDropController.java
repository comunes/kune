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

import java.util.ArrayList;
import java.util.List;

import cc.kune.common.client.actions.ui.descrip.DropTarget;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.VetoDragException;
import com.allen_sauer.gwt.dnd.client.drop.SimpleDropController;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.user.client.ui.Widget;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractDropController.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public abstract class AbstractDropController implements DropTarget {

  /** The accepted types (which one we allow to drop here). */
  private final List<Class<?>> acceptedTypes;

  /** The drag controller. */
  private final KuneDragController dragController;

  /** The drop controller. */
  private SimpleDropController dropController;

  /** The target. */
  private Object target;

  /**
   * Instantiates a new abstract drop controller.
   * 
   * @param dragController
   *          the drag controller
   */
  public AbstractDropController(final KuneDragController dragController) {
    this.dragController = dragController;
    acceptedTypes = new ArrayList<Class<?>>();
  }

  /**
   * Gets the target.
   * 
   * @return the target
   */
  public Object getTarget() {
    return target;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.descrip.DropTarget#init(com.google.gwt
   * .user.client.ui.Widget)
   */
  @Override
  public void init(final Widget dropTarget) {
    dropController = new SimpleDropController(dropTarget) {

      @Override
      public void onDrop(final DragContext context) {
        boolean droppedSomething = false;
        super.onDrop(context);
        for (final Widget widget : context.selectedWidgets) {
          if (acceptedTypes.contains(widget.getClass())) {
            onDropAllowed(widget, this);
            droppedSomething = true;
          }
        }
        if (droppedSomething) {
          onGroupDropFinished(this);
        }
      }

      @Override
      public void onEnter(final DragContext context) {
        super.onEnter(context);
        for (final Widget widget : context.selectedWidgets) {
          if (acceptedTypes.contains(widget.getClass())) {
            onEnterAllowed(this);
          }
        }
      }

      @Override
      public void onLeave(final DragContext context) {
        super.onLeave(context);
        for (final Widget widget : context.selectedWidgets) {
          if (acceptedTypes.contains(widget.getClass())) {
            onLeaveAllowed(this);
          }
        }
      }

      @Override
      public void onPreviewDrop(final DragContext context) throws VetoDragException {
        for (final Widget widget : context.selectedWidgets) {
          if (acceptedTypes.contains(widget.getClass())) {
            onPreviewAllowed(widget, this);
          } else {
            throw new VetoDragException();
          }
        }
        super.onPreviewDrop(context);
      }
    };

    dropTarget.addStyleName("k-drop-allowed");

    if (dropTarget.isAttached()) {
      dragController.registerDropController(dropController);
    }

    dropTarget.addAttachHandler(new Handler() {
      @Override
      public void onAttachOrDetach(final AttachEvent event) {
        if (!event.isAttached()) {
          dragController.unregisterDropController(dropController);
        } else {
          dragController.registerDropController(dropController);
        }
      }
    });
  }

  /**
   * On drop allowed.
   * 
   * @param widget
   *          the widget
   * @param dropController
   *          the drop controller
   */
  public abstract void onDropAllowed(Widget widget, final SimpleDropController dropController);

  /**
   * On enter allowed.
   * 
   * @param dropController
   *          the drop controller
   */
  public void onEnterAllowed(final SimpleDropController dropController) {
    dropController.getDropTarget().addStyleName("k-drop-allowed-hover");
  }

  /**
   * On group drop finished, is fired when all the widgets are dropped (at the
   * end).
   * 
   * @param dropController
   *          the drop controller
   */
  public void onGroupDropFinished(final SimpleDropController dropController) {

  }

  /**
   * On leave allowed.
   * 
   * @param dropController
   *          the drop controller
   */
  public void onLeaveAllowed(final SimpleDropController dropController) {
    dropController.getDropTarget().removeStyleName("k-drop-allowed-hover");
  }

  /**
   * On preview allowed.
   * 
   * @param widget
   *          the widget
   * @param dropController
   *          the drop controller
   * @throws VetoDragException
   *           the veto drag exception
   */
  public void onPreviewAllowed(final Widget widget, final SimpleDropController dropController)
      throws VetoDragException {
    dropController.getDropTarget().removeStyleName("k-drop-allowed-hover");
  }

  /**
   * Register type to accept this kind of widgets to be dropped here.
   * 
   * @param classType
   *          the class type
   */
  public void registerType(final Class<?> classType) {
    acceptedTypes.add(classType);
  }

  /**
   * Sets the target (for instance a StateToken id, to perform operations),
   * because with the widget we don't have all the info.
   * 
   * @param target
   *          the new target
   */
  public void setTarget(final Object target) {
    this.target = target;
  }

}
