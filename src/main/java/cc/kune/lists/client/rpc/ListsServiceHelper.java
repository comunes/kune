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

package cc.kune.lists.client.rpc;

import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.common.shared.utils.SimpleCallback;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.core.shared.dto.StateContainerDTO;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class ListsServiceHelper {

  private final Provider<ListsServiceAsync> listsService;
  private final Session session;
  private final StateManager stateManager;

  @Inject
  public ListsServiceHelper(final Session session, final Provider<ListsServiceAsync> listsService,
      final StateManager stateManager) {
    this.session = session;
    this.listsService = listsService;
    this.stateManager = stateManager;
  }

  public void setPublic(final Boolean isPublic, final SimpleCallback onSuccess) {
    listsService.get().setPublic(session.getUserHash(), session.getCurrentStateToken(), isPublic,
        new AsyncCallbackSimple<StateContainerDTO>() {
          @Override
          public void onSuccess(final StateContainerDTO result) {
            onSuccess.onCallback();
            NotifyUser.info(isPublic ? I18n.t("This list is now public")
                : I18n.t("This list is now restricted to the public"));
            NotifyUser.hideProgress();
            stateManager.setRetrievedState(result);
          }
        });
  }

  public void setRol(final String group, final AccessRolDTO rol, final SimpleCallback onSucess) {
    NotifyUser.info("This should add '" + group + "' to list of rol: " + rol);
  }

  public void subscribeAnUserToList(final StateToken list, final String subscriber,
      final boolean subscribe, final SimpleCallback onSuccess) {
    listsService.get().subscribeAnUserToList(session.getUserHash(), list, subscriber, subscribe,
        new AsyncCallbackSimple<StateContainerDTO>() {
          @Override
          public void onSuccess(final StateContainerDTO result) {
            onSuccess.onCallback();
            if (subscribe) {
              NotifyUser.info(I18n.t("User '[%s]' added as member", subscriber));
            } else {
              NotifyUser.info(I18n.t("This user is already member"));
            }
            stateManager.setRetrievedState(result);
          }
        });
  }

  public void subscribeAnUserToList(final String subscriber, final boolean subscribe,
      final SimpleCallback onSuccess) {
    subscribeAnUserToList(session.getCurrentStateToken(), subscriber, subscribe, onSuccess);
  }
}
