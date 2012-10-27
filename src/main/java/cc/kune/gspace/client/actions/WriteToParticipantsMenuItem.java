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
package cc.kune.gspace.client.actions;

import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.actions.RolAction;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.ContentServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.AbstractContentSimpleDTO;
import cc.kune.core.shared.dto.AccessRolDTO;

import com.google.inject.Inject;

public class WriteToParticipantsMenuItem extends MenuItemDescriptor {

  public static class WriteToParticipantsAction extends RolAction {

    private final ContentServiceAsync contentService;
    private final I18nTranslationService i18n;
    private final Session session;
    private final StateManager stateManager;

    @Inject
    public WriteToParticipantsAction(final Session session, final StateManager stateManager,
        final ContentServiceAsync contentService, final I18nTranslationService i18n) {
      super(AccessRolDTO.Editor, true);
      this.session = session;
      this.stateManager = stateManager;
      this.contentService = contentService;
      this.i18n = i18n;
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
      NotifyUser.showProgress(i18n.t("Writing"));
      final boolean inContent = session.getCurrentStateToken().hasAll();
      final StateToken token = inContent ? session.getCurrentStateToken()
          : ((AbstractContentSimpleDTO) event.getTarget()).getStateToken();
      contentService.writeToParticipants(session.getUserHash(), token,
          new AsyncCallbackSimple<String>() {
            @Override
            public void onSuccess(final String result) {
              NotifyUser.hideProgress();
              NotifyUser.info(i18n.t("Message created. Just edit it"));
              stateManager.gotoHistoryToken(result);
            }
          });
    }

  }

  @Inject
  public WriteToParticipantsMenuItem(final I18nTranslationService i18n,
      final WriteToParticipantsAction action, final IconicResources res,
      final ContentViewerOptionsMenu optionsMenu) {
    super(action);
    this.withText(i18n.t("New message with everyone here")).withIcon(res.write()).withParent(
        optionsMenu, false);
  }

}
