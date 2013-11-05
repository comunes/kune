/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
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

import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.ContentServiceAsync;
import cc.kune.core.client.state.Session;
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

  private final ContentServiceAsync contentService;
  private final I18nTranslationService i18n;
  private final Session session;

  @Inject
  public FolderContentDropController(final KuneDragController dragController,
      final ContentServiceAsync contentService, final Session session, final I18nTranslationService i18n) {
    super(dragController);
    this.i18n = i18n;
    registerType(BasicDragableThumb.class);
    this.contentService = contentService;
    this.session = session;
  }

  @Override
  public void onDropAllowed(final Widget widget, final SimpleDropController dropController) {
    dropController.getDropTarget().removeStyleName("k-drop-allowed-hover");
    if (widget instanceof BasicDragableThumb) {
      final BasicDragableThumb thumb = (BasicDragableThumb) widget;
      final String userName = thumb.getToken().getGroup();
      contentService.addParticipant(session.getUserHash(), (StateToken) getTarget(), userName,
          new AsyncCallbackSimple<Boolean>() {
            @Override
            public void onSuccess(final Boolean result) {
              if (result) {
                NotifyUser.info(i18n.t("User '[%s]' added as participant", userName));
              } else {
                NotifyUser.info(i18n.t("This user is already partipanting"));
              }
            }
          });
    }
  }

}
