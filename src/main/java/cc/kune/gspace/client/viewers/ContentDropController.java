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
package cc.kune.gspace.client.viewers;

import cc.kune.core.client.dnd.AbstractDropController;
import cc.kune.core.client.dnd.KuneDragController;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.ContentServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.ui.BasicDragableThumb;
import cc.kune.core.shared.domain.utils.StateToken;

import com.allen_sauer.gwt.dnd.client.drop.SimpleDropController;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The Class ContentDropController is used to allow the drop of users as
 * participants to waves
 */
@Singleton
public class ContentDropController extends AbstractDropController {

  private final ContentServiceAsync contentService;
  private final Session session;

  /**
   * Instantiates a new content drop controller.
   * 
   * @param dragController
   *          the drag controller
   */
  @Inject
  public ContentDropController(final KuneDragController dragController,
      final ContentServiceAsync contentService, final Session session) {
    super(dragController);
    this.contentService = contentService;
    this.session = session;
    registerType(BasicDragableThumb.class);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.dnd.AbstractDropController#onDropAllowed(com.google
   * .gwt.user.client.ui.Widget,
   * com.allen_sauer.gwt.dnd.client.drop.SimpleDropController)
   */
  @Override
  public void onDropAllowed(final Widget widget, final SimpleDropController dropController) {
    final BasicDragableThumb thumb = (BasicDragableThumb) widget;
    contentService.addParticipant(session.getUserHash(), (StateToken) getTarget(),
        thumb.getToken().getGroup(), new AsyncCallbackSimple<Boolean>() {
          @Override
          public void onSuccess(final Boolean result) {
          }
        });
  }

  @Override
  public void onGroupDropFinished(final SimpleDropController dropController) {
  }

}
