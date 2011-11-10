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
package cc.kune.core.client.actions.xml;

import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.core.client.actions.RolAction;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.ContentServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.dto.AccessRolDTO;

import com.google.inject.Provider;

public class AddGadgetAction extends RolAction {

  private final Provider<ContentServiceAsync> contentService;
  private final String gadgetName;
  private final Session session;

  public AddGadgetAction(final Provider<ContentServiceAsync> contentService, final Session session,
      final AccessRolDTO rol, final boolean authNeeded, final String gadgetName, final String iconUrl) {
    super(rol, authNeeded);
    this.contentService = contentService;
    this.gadgetName = gadgetName;
    this.session = session;
    putValue(Action.SMALL_ICON, iconUrl);
  }

  @Override
  public void actionPerformed(final ActionEvent event) {
    contentService.get().addGadgetToContent(session.getUserHash(), session.getCurrentStateToken(),
        gadgetName, new AsyncCallbackSimple<Void>() {
          @Override
          public void onSuccess(final Void result) {
          }
        });
  }

}