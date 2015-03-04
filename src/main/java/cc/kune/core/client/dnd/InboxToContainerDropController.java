/*
 *
 * Copyright (C) 2007-2015 Licensed to the Comunes Association (CA) under
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
package cc.kune.core.client.dnd;

import org.waveprotocol.box.webclient.search.CustomDigestDomImpl;

import com.allen_sauer.gwt.dnd.client.drop.SimpleDropController;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * This class is responsible of the drop process of waves to folders. Should be
 * a singleton and change the target on every statechange
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class InboxToContainerDropController extends AbstractDropController {

  private final InboxToContainerHelper inboxToContainerHelper;

  @Inject
  public InboxToContainerDropController(final KuneDragController dragController,
      final InboxToContainerHelper inboxToContainerHelper) {
    super(dragController);
    this.inboxToContainerHelper = inboxToContainerHelper;
    registerType(CustomDigestDomImpl.class);
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
    final Widget dropTarget = dropController.getDropTarget();
    dropTarget.removeStyleName("k-drop-allowed-hover");
    if (widget instanceof CustomDigestDomImpl) {
      inboxToContainerHelper.publish(widget, getTarget());
    }
  }

  /*
   * @Override public void onPreviewAllowed(final Widget widget, final
   * SimpleDropController dropController) throws VetoDragException { if
   * (session.isCurrentStateAContent()) { throw new VetoDragException(); }
   * super.onPreviewAllowed(widget, dropController); }
   */

  /**
   * Move.
   *
   * @param widget
   *          the widget
   * @param destToken
   *          the dest token
   */

}
