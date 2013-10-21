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

import cc.kune.common.client.actions.ui.ActionSimplePanel;
import cc.kune.common.client.resources.CommonResources;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.core.client.rpcservices.ContentServiceAsync;
import cc.kune.core.client.services.ClientFileDownloadUtils;

import com.google.inject.Inject;

public class ShareItemNotVisibleByOthers extends AbstractShareItemWithMenu {

  @Inject
  public ShareItemNotVisibleByOthers(final ActionSimplePanel actionsPanel,
      final ClientFileDownloadUtils downloadUtils, final ContentServiceAsync contentServiceAsync,
      final CommonResources res) {
    super(res.worldDeny16(), I18n.t("Nobody"), I18n.t("can't view"), I18n.t("Do this public to anyone"),
        actionsPanel, downloadUtils, contentServiceAsync, res);
  }

}
