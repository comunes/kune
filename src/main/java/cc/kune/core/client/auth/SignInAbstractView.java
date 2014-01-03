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
package cc.kune.core.client.auth;

import cc.kune.common.client.notify.NotifyLevel;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.HasCloseHandlers;
import com.google.gwt.user.client.ui.PopupPanel;
import com.gwtplatform.mvp.client.View;

// TODO: Auto-generated Javadoc
/**
 * The Interface SignInAbstractView.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface SignInAbstractView extends View {

  /**
   * Gets the close.
   * 
   * @return the close
   */
  HasCloseHandlers<PopupPanel> getClose();

  /**
   * Gets the first btn.
   * 
   * @return the first btn
   */
  HasClickHandlers getFirstBtn();

  /**
   * Gets the second btn.
   * 
   * @return the second btn
   */
  HasClickHandlers getSecondBtn();

  /**
   * Hide.
   */
  void hide();

  /**
   * Hide messages.
   */
  void hideMessages();

  /**
   * Mask.
   * 
   * @param message
   *          the message
   */
  void mask(final String message);

  /**
   * Mask processing.
   */
  void maskProcessing();

  /**
   * Reset.
   */
  void reset();

  /**
   * Sets the error message.
   * 
   * @param message
   *          the message
   * @param level
   *          the level
   */
  void setErrorMessage(final String message, final NotifyLevel level);

  /**
   * Show.
   */
  void show();

  /**
   * Un mask.
   */
  void unMask();

}
