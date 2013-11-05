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
package cc.kune.gspace.client.i18n;

import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.actions.RolActionAutoUpdated;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.sitebar.SitebarActionsPresenter;
import cc.kune.core.client.state.AccessRightsClientManager;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.AccessRolDTO;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class SiteOptionsI18nTranslatorAction extends RolActionAutoUpdated {
  private final I18nTranslationService i18n;
  private I18nTranslator translator;
  private final Provider<I18nTranslator> translatorProv;

  @Inject
  public SiteOptionsI18nTranslatorAction(final StateManager stateManager, final Session session,
      final AccessRightsClientManager rightsManager, final I18nTranslationService i18n,
      final IconicResources img, final Provider<I18nTranslator> translatorProv,
      final SitebarActionsPresenter siteOptions) {
    super(stateManager, session, rightsManager, AccessRolDTO.Viewer, true, true, true);
    this.i18n = i18n;
    this.translatorProv = translatorProv;
    putValue(Action.NAME, i18n.t("Help with the translation"));
    putValue(Action.SMALL_ICON, img.world());
    MenuItemDescriptor.build(siteOptions.getOptionsMenu(), this);
  }

  @Override
  public void actionPerformed(final ActionEvent event) {
    NotifyUser.showProgressLoading();
    if (session.isLogged()) {
      if (translator == null) {
        translator = translatorProv.get();
      }
      translator.show();
    } else {
      NotifyUser.info(i18n.t("Sign in or register to help with the translation"));
    }
    NotifyUser.hideProgress();
  }
}
