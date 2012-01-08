/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
 * This file is part of kune.
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

public class FolderItemDescriptor {
  private final GuiActionDescCollection actionCollection;
  private final boolean allowDrag;
  private final boolean allowDrop;
  private final ContentStatus contentStatus;
  private final Object icon;
  private final String id;
  private final long modififiedOn;
  private final String parentId;
  private final String text;
  private final StateToken token;
  private final String tooltip;

  public FolderItemDescriptor(final String id, final String parentId, final Object icon,
      final String text, final String tooltip, final ContentStatus contentStatus,
      final StateToken token, final long modififiedOn, final boolean allowDrag, final boolean allowDrop,
      final GuiActionDescCollection actionCollection) {
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
  }

  public GuiActionDescCollection getActionCollection() {
    return actionCollection;
  }

  public ContentStatus getContentStatus() {
    return contentStatus;
  }

  public Object getIcon() {
    return icon;
  }

  public String getId() {
    return id;
  }

  public long getModififiedOn() {
    return modififiedOn;
  }

  public String getParentId() {
    return parentId;
  }

  public StateToken getStateToken() {
    return token;
  }

  public String getText() {
    return text;
  }

  public String getTooltip() {
    return tooltip;
  }

  public boolean isDraggable() {
    return allowDrag;
  }

  public boolean isDroppable() {
    return allowDrop;
  }

}
