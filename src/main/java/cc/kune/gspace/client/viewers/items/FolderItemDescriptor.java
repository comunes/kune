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
 \*/
package cc.kune.gspace.client.viewers.items;

import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.core.shared.domain.ContentStatus;
import cc.kune.core.shared.domain.utils.StateToken;

// TODO: Auto-generated Javadoc
/**
 * The Class FolderItemDescriptor.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class FolderItemDescriptor {

  /** The action collection. */
  private final GuiActionDescCollection actionCollection;

  /** The allow drag. */
  private final boolean allowDrag;

  /** The allow drop. */
  private final boolean allowDrop;

  /** The content status. */
  private final ContentStatus contentStatus;

  /** The icon. */
  private final Object icon;

  /** The id. */
  private final String id;

  /** The is container. */
  private final boolean isContainer;

  /** The modifified on. */
  private final long modififiedOn;

  /** The parent id. */
  private final String parentId;

  /** The text. */
  private final String text;

  /** The token. */
  private final StateToken token;

  /** The tooltip. */
  private final String tooltip;

  /**
   * Instantiates a new folder item descriptor.
   * 
   * @param id
   *          the id
   * @param parentId
   *          the parent id
   * @param icon
   *          the icon
   * @param text
   *          the text
   * @param tooltip
   *          the tooltip
   * @param contentStatus
   *          the content status
   * @param token
   *          the token
   * @param modififiedOn
   *          the modifified on
   * @param allowDrag
   *          the allow drag
   * @param allowDrop
   *          the allow drop
   * @param actionCollection
   *          the action collection
   * @param isContainer
   *          the is container
   */
  public FolderItemDescriptor(final String id, final String parentId, final Object icon,
      final String text, final String tooltip, final ContentStatus contentStatus,
      final StateToken token, final long modififiedOn, final boolean allowDrag, final boolean allowDrop,
      final GuiActionDescCollection actionCollection, final boolean isContainer) {
    this.id = id;
    this.parentId = parentId;
    this.icon = icon;
    this.text = text;
    this.tooltip = tooltip;
    this.contentStatus = contentStatus;
    this.token = token;
    this.modififiedOn = modififiedOn;
    this.allowDrag = allowDrag;
    this.allowDrop = allowDrop;
    this.actionCollection = actionCollection;
    this.isContainer = isContainer;
  }

  /**
   * Gets the action collection.
   * 
   * @return the action collection
   */
  public GuiActionDescCollection getActionCollection() {
    return actionCollection;
  }

  /**
   * Gets the content status.
   * 
   * @return the content status
   */
  public ContentStatus getContentStatus() {
    return contentStatus;
  }

  /**
   * Gets the icon.
   * 
   * @return the icon
   */
  public Object getIcon() {
    return icon;
  }

  /**
   * Gets the id.
   * 
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * Gets the modifified on.
   * 
   * @return the modifified on
   */
  public long getModififiedOn() {
    return modififiedOn;
  }

  /**
   * Gets the parent id.
   * 
   * @return the parent id
   */
  public String getParentId() {
    return parentId;
  }

  /**
   * Gets the state token.
   * 
   * @return the state token
   */
  public StateToken getStateToken() {
    return token;
  }

  /**
   * Gets the text.
   * 
   * @return the text
   */
  public String getText() {
    return text;
  }

  /**
   * Gets the tooltip.
   * 
   * @return the tooltip
   */
  public String getTooltip() {
    return tooltip;
  }

  /**
   * Checks if is container.
   * 
   * @return true, if is container
   */
  public boolean isContainer() {
    return isContainer;
  }

  /**
   * Checks if is draggable.
   * 
   * @return true, if is draggable
   */
  public boolean isDraggable() {
    return allowDrag;
  }

  /**
   * Checks if is droppable.
   * 
   * @return true, if is droppable
   */
  public boolean isDroppable() {
    return allowDrop;
  }

}
