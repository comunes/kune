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
package cc.kune.gspace.client.viewers;

import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.common.client.ui.HasEditHandler;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.gspace.client.viewers.items.FolderItemDescriptor;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.gwtplatform.mvp.client.View;

// TODO: Auto-generated Javadoc
/**
 * The Interface AbstractFolderViewerView.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface AbstractFolderViewerView extends View {

  /**
   * Adds the item.
   * 
   * @param item
   *          the item
   * @param clickHandler
   *          the click handler
   * @param doubleClickHandler
   *          the double click handler
   */
  void addItem(FolderItemDescriptor item, ClickHandler clickHandler,
      DoubleClickHandler doubleClickHandler);

  /**
   * Attach.
   */
  void attach();

  /**
   * Clear.
   */
  void clear();

  /**
   * Detach.
   */
  void detach();

  /**
   * Edits the title.
   */
  void editTitle();

  /**
   * Gets the edits the title.
   * 
   * @return the edits the title
   */
  HasEditHandler getEditTitle();

  /**
   * Highlight title.
   */
  void highlightTitle();

  /**
   * Sets the container.
   * 
   * @param state
   *          the new container
   */
  void setContainer(StateContainerDTO state);

  /**
   * Sets the editable title.
   * 
   * @param title
   *          the new editable title
   */
  void setEditableTitle(String title);

  /**
   * Sets the footer actions.
   * 
   * @param actions
   *          the new footer actions
   */
  void setFooterActions(GuiActionDescCollection actions);

  /**
   * Sets the subheader actions.
   * 
   * @param actions
   *          the new subheader actions
   */
  void setSubheaderActions(GuiActionDescCollection actions);

  /**
   * Show empty msg.
   * 
   * @param message
   *          the message
   */
  void showEmptyMsg(String message);

  /**
   * Show folder.
   */
  void showFolder();
}
