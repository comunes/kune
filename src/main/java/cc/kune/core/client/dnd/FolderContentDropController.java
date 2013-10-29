/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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
package cc.kune.core.client.dnd;

import cc.kune.common.shared.utils.SimpleCallback;
import cc.kune.core.client.rpcservices.ContentServiceHelper;
import cc.kune.core.client.ui.BasicDragableThumb;
import cc.kune.core.shared.domain.utils.StateToken;

import com.allen_sauer.gwt.dnd.client.drop.SimpleDropController;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

/**
 * The Class FolderContentDropController is responsible of the drop process to
 * contents (for instance of user, to add then as participants). Must not be a
 * 
 * @singleton, and should exist one drop controller per item
 */
public class FolderContentDropController extends AbstractDropController {

  private final ContentServiceHelper contentService;

  @Inject
  public FolderContentDropController(final KuneDragController dragController,
      final ContentServiceHelper contentService) {
    super(dragController);
    registerType(BasicDragableThumb.class);
    this.contentService = contentService;
  }

  @Override
  public void onDropAllowed(final Widget widget, final SimpleDropController dropController) {
    dropController.getDropTarget().removeStyleName("k-drop-allowed-hover");
    if (widget instanceof BasicDragableThumb) {
      final BasicDragableThumb thumb = (BasicDragableThumb) widget;
      final String userName = thumb.getToken().getGroup();
      contentService.addParticipant((StateToken) getTarget(), userName, new SimpleCallback() {
        @Override
        public void onCallback() {
          // Do nothing
        }
      });
    }
  }

}