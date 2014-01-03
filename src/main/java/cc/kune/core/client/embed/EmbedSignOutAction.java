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

package cc.kune.core.client.embed;

import cc.kune.common.client.actions.ActionEvent;
import cc.kune.core.client.sitebar.AbstractSignOutAction;
import cc.kune.core.client.state.SessionInstance;
import cc.kune.gspace.client.viewers.EmbedHelper;

import com.google.gwt.core.client.Callback;
import com.google.gwt.http.client.Response;

/**
 * The Class EmbedSignOutAction.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class EmbedSignOutAction extends AbstractSignOutAction {

  /**
   * Instantiates a new sitebar sign out action.
   */
  public EmbedSignOutAction() {
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ActionListener#actionPerformed(cc.kune.common
   * .client.actions.ActionEvent)
   */
  @Override
  public void actionPerformed(final ActionEvent event) {
    SessionInstance.get().signOut();
    final String signOutUrl = EmbedHelper.getServerWithPath() + "cors/SiteCORSService/logout";
    EmbedHelper.processRequest(signOutUrl, new Callback<Response, Void>() {

      @Override
      public void onFailure(final Void reason) {
      }

      @Override
      public void onSuccess(final Response response) {

      }
    });
  }

}