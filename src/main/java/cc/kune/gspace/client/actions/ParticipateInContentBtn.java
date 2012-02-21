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
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.actions.RolAction;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.ContentServiceAsync;
import cc.kune.core.client.sn.actions.conditions.IsCurrentStateEditableCondition;
import cc.kune.core.client.sn.actions.conditions.IsLoggedCondition;
import cc.kune.core.client.sn.actions.conditions.IsNotParticipantOfCurrentStateCondition;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.AccessRolDTO;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class ParticipateInContentBtn extends ButtonDescriptor {

  public static class ParticipateInContentAction extends RolAction {

    private final Provider<ContentServiceAsync> contentService;
    private final Session session;
    private final StateManager stateManager;

    @Inject
    public ParticipateInContentAction(final Session session, final StateManager stateManager,
        final I18nTranslationService i18n, final Provider<ContentServiceAsync> contentService) {
      super(AccessRolDTO.Editor, true);
      this.session = session;
      this.stateManager = stateManager;
      this.contentService = contentService;
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
      NotifyUser.showProgress();
      contentService.get().addParticipant(session.getUserHash(), session.getCurrentStateToken(),
          session.getCurrentUser().getShortName(), new AsyncCallbackSimple<Void>() {
            @Override
            public void onSuccess(final Void arg) {
              NotifyUser.hideProgress();
              stateManager.refreshCurrentStateWithoutCache();
              // issue #73
              // stateManager.gotoStateToken(session.getCurrentStateToken());
            }
          });
    }
  }

  @Inject
  public ParticipateInContentBtn(final I18nTranslationService i18n,
      final ParticipateInContentAction action, final IsLoggedCondition isLogged,
      final IsCurrentStateEditableCondition isEditable,
      final IsNotParticipantOfCurrentStateCondition isNotParticipant, final CoreResources res) {
    super(action);
    this.withText(i18n.t("Participate")).withToolTip(i18n.t("Participate in the edition of this page")).withIcon(
        res.contentEdit()).withStyles("k-def-docbtn, k-fl");
    super.add(isLogged);
    super.add(isEditable);
    super.add(isNotParticipant);
  }
}
