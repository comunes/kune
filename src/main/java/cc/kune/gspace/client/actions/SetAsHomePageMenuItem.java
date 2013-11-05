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
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.AbstractContentSimpleDTO;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.core.shared.dto.ContentSimpleDTO;

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
      final StateToken token = ((AbstractContentSimpleDTO) event.getTarget()).getStateToken();
      NotifyUser.showProgress();
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
      final IconicResources res) {
    super(action);
    this.withIcon(res.home()).withText(i18n.t("Select as the homepage"));
  }

}
