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
package cc.kune.common.client.ui.dialogs.wizard;

import com.google.gwt.user.client.ui.IsWidget;

// TODO: Auto-generated Javadoc
/**
 * The Interface WizardDialogView.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface WizardDialogView {

  /**
   * Adds the.
   *
   * @param view the view
   */
  void add(IsWidget view);

  /**
   * Clear.
   */
  void clear();

  /**
   * Hide.
   */
  void hide();

  /**
   * Checks if is current page.
   *
   * @param view the view
   * @return true, if is current page
   */
  boolean isCurrentPage(IsWidget view);

  /**
   * Mask.
   *
   * @param message the message
   */
  void mask(final String message);

  /**
   * Mask processing.
   */
  void maskProcessing();

  /**
   * Removes the.
   *
   * @param view the view
   */
  void remove(IsWidget view);

  /**
   * Sets the enabled.
   *
   * @param back the back
   * @param next the next
   * @param cancel the cancel
   * @param finish the finish
   */
  void setEnabled(boolean back, boolean next, boolean cancel, boolean finish);

  /**
   * Sets the enabled back button.
   *
   * @param enabled the new enabled back button
   */
  void setEnabledBackButton(final boolean enabled);

  /**
   * Sets the enabled cancel button.
   *
   * @param enabled the new enabled cancel button
   */
  void setEnabledCancelButton(final boolean enabled);

  /**
   * Sets the enabled finish button.
   *
   * @param enabled the new enabled finish button
   */
  void setEnabledFinishButton(final boolean enabled);

  /**
   * Sets the enabled next button.
   *
   * @param enabled the new enabled next button
   */
  void setEnabledNextButton(final boolean enabled);

  /**
   * Sets the finish text.
   *
   * @param text the new finish text
   */
  void setFinishText(final String text);

  /**
   * Sets the listener.
   *
   * @param listener the new listener
   */
  void setListener(WizardListener listener);

  /**
   * Sets the visible.
   *
   * @param back the back
   * @param next the next
   * @param cancel the cancel
   * @param finish the finish
   */
  void setVisible(boolean back, boolean next, boolean cancel, boolean finish);

  /**
   * Sets the visible back button.
   *
   * @param visible the new visible back button
   */
  void setVisibleBackButton(final boolean visible);

  /**
   * Sets the visible cancel button.
   *
   * @param visible the new visible cancel button
   */
  void setVisibleCancelButton(final boolean visible);

  /**
   * Sets the visible finish button.
   *
   * @param visible the new visible finish button
   */
  void setVisibleFinishButton(final boolean visible);

  /**
   * Sets the visible next button.
   *
   * @param visible the new visible next button
   */
  void setVisibleNextButton(final boolean visible);

  /**
   * Show.
   */
  void show();

  /**
   * Show.
   *
   * @param view the view
   */
  void show(IsWidget view);

  /**
   * Un mask.
   */
  void unMask();

}
