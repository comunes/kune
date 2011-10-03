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
import cc.kune.common.client.notify.ConfirmAskEvent;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.utils.OnAcceptCallback;
import cc.kune.core.client.actions.RolAction;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.rpcservices.ContentServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.AbstractContentSimpleDTO;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.viewers.FolderViewerPresenter;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class DelContainerMenuItem extends MenuItemDescriptor {

  public static class DelContainerAction extends RolAction {

    private final Provider<ContentServiceAsync> contentService;
    private final EventBus eventBus;
    private final I18nTranslationService i18n;
    private final Provider<FolderViewerPresenter> presenter;
    private final Session session;
    private final StateManager stateManager;

    @Inject
    public DelContainerAction(final EventBus eventBus, final StateManager stateManager,
        final Session session, final Provider<ContentServiceAsync> contentService,
        final I18nTranslationService i18n, final Provider<FolderViewerPresenter> presenter) {
      super(AccessRolDTO.Administrator, true);
      this.eventBus = eventBus;
      this.stateManager = stateManager;
      this.session = session;
      this.contentService = contentService;
      this.i18n = i18n;
      this.presenter = presenter;
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
      final StateToken token = ((AbstractContentSimpleDTO) event.getTarget()).getStateToken();
      ConfirmAskEvent.fire(eventBus, i18n.t("Please confirm"),
          i18n.t("Are you sure you want this? It will be deleted with all its contents."),
          i18n.t("Yes"), i18n.t("No"), null, null, new OnAcceptCallback() {
            @Override
            public void onSuccess() {
              NotifyUser.showProgress();
              NotifyUser.info("Sorry, in development");
              NotifyUser.hideProgress();
            }
          });
    }

  }

  public DelContainerMenuItem(final I18nTranslationService i18n, final DelContainerAction action,
      final CoreResources res) {
    super(action);
    this.withText(i18n.t("Delete")).withIcon(res.cancel());
  }

}
