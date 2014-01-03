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
package cc.kune.common.client.ui.dialogs;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasDirectionalText;
import com.google.gwt.user.client.ui.InsertPanel.ForIsWidget;

// TODO: Auto-generated Javadoc
/**
 * The Interface BasicDialogView.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface BasicDialogView {

  /**
   * Gets the bottom panel.
   * 
   * @return the bottom panel
   */
  ForIsWidget getBottomPanel();

  /**
   * Gets the close btn.
   * 
   * @return the close btn
   */
  HasClickHandlers getCloseBtn();

  /**
   * Gets the first btn.
   * 
   * @return the first btn
   */
  HasClickHandlers getFirstBtn();

  /**
   * Gets the inner panel.
   * 
   * @return the inner panel
   */
  ForIsWidget getInnerPanel();

  /**
   * Gets the main panel.
   * 
   * @return the main panel
   */
  ForIsWidget getMainPanel();

  /**
   * Gets the second btn.
   * 
   * @return the second btn
   */
  HasClickHandlers getSecondBtn();

  /**
   * Gets the title text.
   * 
   * @return the title text
   */
  HasDirectionalText getTitleText();

  /**
   * Sets the close btn tooltip.
   * 
   * @param tooltip
   *          the new close btn tooltip
   */
  void setCloseBtnTooltip(String tooltip);

  /**
   * Sets the close btn visible.
   * 
   * @param visible
   *          the new close btn visible
   */
  void setCloseBtnVisible(boolean visible);

  /**
   * Sets the first btn text.
   * 
   * @param text
   *          the new first btn text
   */
  void setFirstBtnText(String text);

  /**
   * Sets the first btn title.
   * 
   * @param title
   *          the new first btn title
   */
  void setFirstBtnTitle(String title);

  /**
   * Sets the first btn visible.
   * 
   * @param visible
   *          the new first btn visible
   */
  void setFirstBtnVisible(boolean visible);

  /**
   * Sets the second btn text.
   * 
   * @param text
   *          the new second btn text
   */
  void setSecondBtnText(String text);

  /**
   * Sets the second btn title.
   * 
   * @param title
   *          the new second btn title
   */
  void setSecondBtnTitle(String title);

  /**
   * Sets the second btn visible.
   * 
   * @param visible
   *          the new second btn visible
   */
  void setSecondBtnVisible(boolean visible);

}
