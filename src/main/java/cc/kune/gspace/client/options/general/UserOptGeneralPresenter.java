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
package cc.kune.gspace.client.options.general;

import cc.kune.common.client.notify.NotifyUser;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.UserServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.state.UserSignInEvent;
import cc.kune.core.client.state.UserSignInEvent.UserSignInHandler;
import cc.kune.core.shared.dto.I18nLanguageSimpleDTO;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.core.shared.dto.UserDTO;
import cc.kune.core.shared.dto.UserSimpleDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.options.UserOptions;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class UserOptGeneralPresenter extends EntityOptGeneralPresenter implements UserOptGeneral {

  private final Provider<UserServiceAsync> userService;
  private final UserOptGeneralView userView;

  @Inject
  public UserOptGeneralPresenter(final Session session, final StateManager stateManager,
      final EventBus eventBus, final I18nTranslationService i18n, final UserOptions entityOptions,
      final Provider<UserServiceAsync> userService, final UserOptGeneralView view) {
    super(session, stateManager, eventBus, i18n, entityOptions);
    this.userService = userService;
    userView = view;
    init(view);
    session.onUserSignIn(true, new UserSignInHandler() {
      @Override
      public void onUserSignIn(final UserSignInEvent event) {
        setState();
      }
    });
  }

  @Override
  protected boolean applicable() {
    return session.isLogged();
  }

  @Override
  public void init(final EntityOptGeneralView view) {
    super.init(view);
  }

  @Override
  protected void setState() {
    final UserSimpleDTO currentUser = session.getCurrentUser();
    userView.setLongName(currentUser.getName());
    userView.setLanguage(I18nLanguageSimpleDTO.create(currentUser.getLanguage()));
  }

  @Override
  protected void updateInServer() {
    if (view.isValid()) {
      NotifyUser.showProgress();
      final UserSimpleDTO currentUser = session.getCurrentUser();
      final UserDTO user = new UserDTO();
      user.setId(currentUser.getId());
      user.setName(userView.getLongName());
      userService.get().updateUser(session.getUserHash(), user, userView.getLanguage(),
          new AsyncCallbackSimple<StateAbstractDTO>() {
            @Override
            public void onSuccess(final StateAbstractDTO result) {
              NotifyUser.hideProgress();
              stateManager.setRetrievedStateAndGo(result);
              sendChangeEntityEvent();
            }
          });
    }
  }
}
