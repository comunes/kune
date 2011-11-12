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
package cc.kune.core.client.actions.xml;

import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.core.client.actions.RolAction;
import cc.kune.core.client.resources.CoreMessages;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.ContentServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Provider;

public class AddGadgetAction extends RolAction {

  private final Provider<ContentServiceAsync> contentService;
  private final String gadgetExtName;
  private final String gadgetName;
  private final I18nTranslationService i18n;
  private final Session session;

  public AddGadgetAction(final Provider<ContentServiceAsync> contentService, final Session session,
      final I18nTranslationService i18n, final AccessRolDTO rol, final boolean authNeeded,
      final String gadgetExtName, final String gadgetName, final String iconUrl) {
    super(rol, authNeeded);
    this.contentService = contentService;
    this.i18n = i18n;
    this.session = session;
    this.gadgetExtName = gadgetExtName;
    this.gadgetName = gadgetName;
    putValue(Action.SMALL_ICON, iconUrl);
  }

  @Override
  public void actionPerformed(final ActionEvent event) {
    contentService.get().addGadgetToContent(session.getUserHash(), session.getCurrentStateToken(),
        gadgetExtName, new AsyncCallbackSimple<Void>() {
          @Override
          public void onSuccess(final Void result) {
            NotifyUser.info(i18n.t("'[%s]' created succesfully", i18n.t(gadgetName)),
                i18n.t(CoreMessages.GADGETS_EXPERIMENTAL));
          }
        });
  }

}