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
package cc.kune.common.client.actions.ui.descrip;

import cc.kune.common.client.actions.AbstractAction;
import cc.kune.common.client.actions.Action;

// TODO: Auto-generated Javadoc
/**
 * The Class PushButtonDescriptor.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class PushButtonDescriptor extends ButtonDescriptor {

  /** The Constant PUSHED. */
  public static final String PUSHED = "pushed";

  /**
   * Instantiates a new push button descriptor.
   *
   * @param action the action
   */
  public PushButtonDescriptor(final AbstractAction action) {
    super(action);
    setPushedImpl(false);
  }

  /**
   * Instantiates a new push button descriptor.
   *
   * @param button the button
   */
  public PushButtonDescriptor(final PushButtonDescriptor button) {
    this(button.getAction());
  }

  /**
   * Instantiates a new push button descriptor.
   *
   * @param text the text
   * @param action the action
   */
  public PushButtonDescriptor(final String text, final AbstractAction action) {
    this(action);
    putValue(Action.NAME, text);
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.descrip.ButtonDescriptor#getType()
   */
  @Override
  public Class<?> getType() {
    return PushButtonDescriptor.class;
  }

  /**
   * Checks if is pushed.
   *
   * @return true, if is pushed
   */
  public boolean isPushed() {
    return (Boolean) getValue(PUSHED);
  }

  /**
   * Sets the pushed.
   *
   * @param pushed the new pushed
   */
  public void setPushed(final boolean pushed) {
    setPushedImpl(pushed);
  }

  /**
   * Sets the pushed impl.
   *
   * @param pushed the new pushed impl
   */
  private void setPushedImpl(final boolean pushed) {
    putValue(PUSHED, pushed);
  }
}
