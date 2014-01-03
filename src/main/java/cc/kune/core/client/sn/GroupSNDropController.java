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
package cc.kune.core.client.sn;

import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.common.shared.utils.SimpleCallback;
import cc.kune.core.client.dnd.AbstractDropController;
import cc.kune.core.client.dnd.KuneDragController;
import cc.kune.core.client.rpcservices.ContentServiceHelper;
import cc.kune.core.client.rpcservices.SocialNetServiceHelper;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.ui.BasicDragableThumb;
import cc.kune.core.shared.dto.SocialNetworkSubGroup;
import cc.kune.gspace.client.viewers.items.FolderItemWidget;

import com.allen_sauer.gwt.dnd.client.VetoDragException;
import com.allen_sauer.gwt.dnd.client.drop.SimpleDropController;
import com.google.gwt.user.client.ui.Widget;

// TODO: Auto-generated Javadoc
/**
 * The Class GroupSNDropController.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class GroupSNDropController extends AbstractDropController {

  /** The content service. */
  private final ContentServiceHelper contentService;

  /** The session. */
  private final Session session;

  /** The s n client utils. */
  private final SocialNetServiceHelper sNClientUtils;

  /** The sub group. */
  private final SocialNetworkSubGroup subGroup;

  /**
   * Instantiates a new group sn drop controller.
   * 
   * @param dragController
   *          the drag controller
   * @param subGroup
   *          the sub group
   * @param contentService
   *          the content service
   * @param session
   *          the session
   * @param i18n
   *          the i18n
   * @param sNClientUtils
   *          the s n client utils
   */
  public GroupSNDropController(final KuneDragController dragController,
      final SocialNetworkSubGroup subGroup, final ContentServiceHelper contentService,
      final Session session, final I18nTranslationService i18n,
      final SocialNetServiceHelper sNClientUtils) {
    super(dragController);
    this.subGroup = subGroup;
    this.contentService = contentService;
    this.session = session;
    this.sNClientUtils = sNClientUtils;
    registerType(FolderItemWidget.class);
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
    if (widget instanceof FolderItemWidget) {
      contentService.addParticipants(((FolderItemWidget) widget).getToken(), subGroup,
          SimpleCallback.DO_NOTHING);
    }
    if (widget instanceof BasicDragableThumb) {
      final String shortName = ((BasicDragableThumb) widget).getToken().getGroup();
      if (subGroup.equals(SocialNetworkSubGroup.ADMINS)) {
        sNClientUtils.changeToAdmin(shortName);
      }
      if (subGroup.equals(SocialNetworkSubGroup.COLLABS)) {
        sNClientUtils.changeToCollab(shortName);
      }
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.dnd.AbstractDropController#onPreviewAllowed(com.google
   * .gwt.user.client.ui.Widget,
   * com.allen_sauer.gwt.dnd.client.drop.SimpleDropController)
   */
  @Override
  public void onPreviewAllowed(final Widget widget, final SimpleDropController dropController)
      throws VetoDragException {
    if (widget instanceof FolderItemWidget) {
      if (!((FolderItemWidget) widget).getToken().isComplete()) {
        throw new VetoDragException();
      }
    }
    if (widget instanceof BasicDragableThumb) {
      if (!session.getCurrentState().getGroupRights().isAdministrable()) {
        throw new VetoDragException();
      }
    }
    super.onPreviewAllowed(widget, dropController);
  }
}
