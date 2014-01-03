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

package cc.kune.gspace.client.actions.share;

import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.ui.KuneWindowUtils;
import cc.kune.common.client.utils.ClientFormattedString;
import cc.kune.core.client.actions.RolAction;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.dto.AccessRolDTO;

import com.google.inject.Inject;

public class AbstractShareInSocialNetAction extends RolAction {

  private ClientFormattedString url;

  @Inject
  public AbstractShareInSocialNetAction(final Session session) {
    // FIXME remove after #550 is closed and removed from "In development"
    super(AccessRolDTO.Viewer, session.isGuiInDevelopment() ? AccessRolDTO.Editor
        : AccessRolDTO.Administrator, false);
  }

  @Override
  public void actionPerformed(final ActionEvent event) {
    KuneWindowUtils.open(url.getString());
  }

  public void setUrl(final ClientFormattedString url) {
    this.url = url;
  }
}