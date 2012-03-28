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
 */
package cc.kune.gspace.client.viewers;

import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.common.client.ui.HasEditHandler;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.gspace.client.viewers.items.FolderItemDescriptor;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.gwtplatform.mvp.client.View;

public interface AbstractFolderViewerView extends View {
  void addItem(FolderItemDescriptor item, ClickHandler clickHandler,
      DoubleClickHandler doubleClickHandler);

  void attach();

  void clear();

  void detach();

  void editTitle();

  HasEditHandler getEditTitle();

  void highlightTitle();

  void setContainer(StateContainerDTO state);

  void setEditableTitle(String title);

  void setFooterActions(GuiActionDescCollection actions);

  void setSubheaderActions(GuiActionDescCollection actions);

  void showEmptyMsg(String message);

  void showTutorial(String tool);
}
