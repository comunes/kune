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

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.ActionSimplePanel;
import cc.kune.common.client.actions.ui.descrip.LabelDescriptor;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.resources.CommonResources;
import cc.kune.common.shared.i18n.I18n;

import com.google.inject.Inject;

public class ShareToNobody extends AbstractShareItem {

  @Inject
  public ShareToNobody(final ActionSimplePanel actionsPanel, final CommonResources res) {
    super(actionsPanel);
    withIcon(res.worldDeny16()).withText(I18n.t("Nobody"));
  }

  public void init() {
    final LabelDescriptor descr = new LabelDescriptor(I18n.t(I18n.t("Share"),
        I18n.t("Share with everyone")));
    descr.setAction(new AbstractExtendedAction() {
      @Override
      public void actionPerformed(final ActionEvent event) {
        // TODO
        NotifyUser.info("In development");
      }
    });
    super.add(descr);
  }

}
