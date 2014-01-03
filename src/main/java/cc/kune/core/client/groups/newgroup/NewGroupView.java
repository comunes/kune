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
package cc.kune.core.client.groups.newgroup;

import cc.kune.common.client.notify.NotifyLevel;

import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.HasCloseHandlers;
import com.google.gwt.user.client.ui.PopupPanel;
import com.gwtplatform.mvp.client.View;

// TODO: Auto-generated Javadoc
/**
 * The Interface NewGroupView.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface NewGroupView extends View {

  /**
   * Clear data.
   */
  void clearData();

  /**
   * Focus on long name.
   */
  void focusOnLongName();

  /**
   * Focus on short name.
   */
  void focusOnShortName();

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
   * Gets the long name.
   * 
   * @return the long name
   */
  String getLongName();

  /**
   * Gets the long name field.
   * 
   * @return the long name field
   */
  TextField<String> getLongNameField();

  /**
   * Gets the public desc.
   * 
   * @return the public desc
   */
  String getPublicDesc();

  /**
   * Gets the second btn.
   * 
   * @return the second btn
   */
  HasClickHandlers getSecondBtn();

  /**
   * Gets the short name.
   * 
   * @return the short name
   */
  String getShortName();

  /**
   * Gets the tags.
   * 
   * @return the tags
   */
  String getTags();

  /**
   * Hide.
   */
  void hide();

  /**
   * Hide message.
   */
  void hideMessage();

  /**
   * Checks if is closed.
   * 
   * @return true, if is closed
   */
  boolean isClosed();

  /**
   * Checks if is community.
   * 
   * @return true, if is community
   */
  boolean isCommunity();

  /**
   * Checks if is form valid.
   * 
   * @return true, if is form valid
   */
  boolean isFormValid();

  /**
   * Checks if is organization.
   * 
   * @return true, if is organization
   */
  boolean isOrganization();

  /**
   * Checks if is project.
   * 
   * @return true, if is project
   */
  boolean isProject();

  /**
   * Mask processing.
   */
  void maskProcessing();

  /**
   * Sets the long name.
   * 
   * @param longName
   *          the new long name
   */
  void setLongName(final String longName);

  /**
   * Sets the long name failed.
   * 
   * @param msg
   *          the new long name failed
   */
  void setLongNameFailed(final String msg);

  /**
   * Sets the message.
   * 
   * @param message
   *          the message
   * @param level
   *          the level
   */
  void setMessage(String message, NotifyLevel level);

  /**
   * Sets the short name.
   * 
   * @param shortName
   *          the new short name
   */
  void setShortName(final String shortName);

  /**
   * Sets the short name failed.
   * 
   * @param msg
   *          the new short name failed
   */
  void setShortNameFailed(final String msg);

  /**
   * Show.
   */
  void show();

  /**
   * Un mask.
   */
  void unMask();

}
