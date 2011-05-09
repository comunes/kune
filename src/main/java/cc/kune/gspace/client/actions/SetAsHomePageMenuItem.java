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
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.core.client.actions.RolAction;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.ContentServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.core.shared.dto.ContentSimpleDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class SetAsHomePageMenuItem extends MenuItemDescriptor {

  public static class SetAsHomePageAction extends RolAction {

    private final Provider<ContentServiceAsync> contentService;
    private final I18nTranslationService i18n;
    private final Session session;

    @Inject
    public SetAsHomePageAction(final Session session,
        final Provider<ContentServiceAsync> contentService, final I18nTranslationService i18n) {
      super(AccessRolDTO.Administrator, true);
      this.session = session;
      this.contentService = contentService;
      this.i18n = i18n;
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
      final StateToken token = (StateToken) event.getTarget();
      NotifyUser.showProgressProcessing();
      contentService.get().setAsDefaultContent(session.getUserHash(), token,
          new AsyncCallbackSimple<ContentSimpleDTO>() {
            @Override
            public void onSuccess(final ContentSimpleDTO defContent) {
              session.getCurrentState().getGroup().setDefaultContent(defContent);
              NotifyUser.hideProgress();
              NotifyUser.info(i18n.t("Selected as the homepage"));
            }
          });
    }

  }

  @Inject
  public SetAsHomePageMenuItem(final I18nTranslationService i18n, final SetAsHomePageAction action,
      final CoreResources res) {
    super(action);
    this.withText(i18n.t("Delete")).withIcon(res.groupHome()).withText(i18n.t("Select as the homepage"));
  }

}
