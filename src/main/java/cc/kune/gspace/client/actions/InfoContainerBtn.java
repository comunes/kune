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
package cc.kune.gspace.client.actions;

import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.actions.RolAction;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.dto.AccessRolDTO;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;

public class InfoContainerBtn extends ButtonDescriptor {

  public static class GoParentContainerAction extends RolAction {

    private final EventBus bus;
    private final Session session;

    @Inject
    public GoParentContainerAction(final EventBus eventBus, final Session session) {
      super(AccessRolDTO.Editor, true);
      this.bus = eventBus;
      this.session = session;
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
      ShowHelpContainerEvent.fire(bus, session.getCurrentStateToken().getTool());
    }

  }

  public static final String INFO_CONTAINER_ID = "k-container-info-id";

  public InfoContainerBtn(final I18nTranslationService i18n, final GoParentContainerAction action,
      final CoreResources res) {
    super(action);
    this.withToolTip(i18n.t("New to this tool? Here there is some help")).withIcon(res.infoLight()).withStyles(
        "k-btn-min, k-fr");
    this.withId(INFO_CONTAINER_ID);
  }

}
