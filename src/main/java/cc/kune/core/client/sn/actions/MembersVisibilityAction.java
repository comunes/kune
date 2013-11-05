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
package cc.kune.core.client.sn.actions;

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.GroupServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.domain.SocialNetworkVisibility;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class MembersVisibilityAction extends AbstractExtendedAction {

  private final Provider<GroupServiceAsync> groupServiceProvider;
  private final I18nTranslationService i18n;
  private final Session session;
  private SocialNetworkVisibility visibility;

  @Inject
  public MembersVisibilityAction(final Session session, final I18nTranslationService i18n,
      final Provider<GroupServiceAsync> groupServiceProvider) {
    this.session = session;
    this.i18n = i18n;
    this.groupServiceProvider = groupServiceProvider;
  }

  @Override
  public void actionPerformed(final ActionEvent event) {
    groupServiceProvider.get().setSocialNetworkVisibility(session.getUserHash(),
        session.getCurrentState().getGroup().getStateToken(), visibility,
        new AsyncCallbackSimple<Void>() {
          @Override
          public void onSuccess(final Void result) {
            NotifyUser.info(i18n.t("Members visibility changed"));
          }
        });
  }

  public void setVisibility(final SocialNetworkVisibility visibility) {
    this.visibility = visibility;

  }

}
