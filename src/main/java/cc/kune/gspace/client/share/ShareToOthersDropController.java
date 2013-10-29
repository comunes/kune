/*******************************************************************************
 * Copyright (C) 2007, 2013 The kune development team (see CREDITS for details)
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
 *******************************************************************************/

package cc.kune.gspace.client.share;

import cc.kune.core.client.dnd.AbstractDropController;
import cc.kune.core.client.dnd.KuneDragController;
import cc.kune.core.client.rpcservices.ContentServiceAsync;
import cc.kune.core.client.ui.BasicDragableThumb;
import cc.kune.gspace.client.share.ShareDialogPresenter.OnAddGroupListener;

import com.allen_sauer.gwt.dnd.client.drop.SimpleDropController;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ShareToOthersDropController extends AbstractDropController {
  private OnAddGroupListener addListener;

  @Inject
  public ShareToOthersDropController(final KuneDragController dragController,
      final ContentServiceAsync contentService) {
    super(dragController);
    registerType(BasicDragableThumb.class);
  }

  public void onAddGroupListener(final OnAddGroupListener addListener) {
    this.addListener = addListener;
  }

  @Override
  public void onDropAllowed(final Widget widget, final SimpleDropController dropController) {
    if (widget instanceof BasicDragableThumb) {
      final String shortName = ((BasicDragableThumb) widget).getToken().getGroup();
      if (addListener != null) {
        addListener.onAdd(shortName);
      }
    }
  }

}
