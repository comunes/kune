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
 \*/
package cc.kune.gspace.client.themes;

import java.util.HashMap;

import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.utils.CSSUtils;
import cc.kune.core.client.events.AppStartEvent;
import cc.kune.core.client.events.GroupChangedEvent;
import cc.kune.core.client.events.AppStartEvent.AppStartHandler;
import cc.kune.core.client.events.GroupChangedEvent.GroupChangedHandler;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.GroupServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.GSpaceTheme;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.InitDataDTO;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.gspace.client.armor.resources.GSpaceArmorResources;
import cc.kune.gspace.client.style.GSpaceBackgroundManager;

import com.google.gwt.dom.client.StyleElement;
import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class GSpaceThemeManager {

  private StyleElement cssAdded;
  private final EventBus eventBus;
  private final Provider<GroupServiceAsync> groupServiceProvider;
  private GSpaceTheme previousTheme;
  private final GSpaceArmorResources res;
  private final Session session;
  protected HashMap<String, GSpaceTheme> themes;
  private final GSpaceBackgroundManager wsBackManager;

  @Inject
  public GSpaceThemeManager(final Session session,
      final Provider<GroupServiceAsync> groupServiceProvider, final StateManager stateManager,
      final GSpaceBackgroundManager wsBackManager, final EventBus eventBus,
      final GSpaceArmorResources res) {
    this.session = session;
    this.groupServiceProvider = groupServiceProvider;
    this.wsBackManager = wsBackManager;
    this.eventBus = eventBus;
    this.res = res;
    session.onAppStart(true, new AppStartHandler() {
      @Override
      public void onAppStart(final AppStartEvent event) {
        final InitDataDTO initdata = session.getInitData();
        themes = initdata.getgSpaceThemes();
        stateManager.onGroupChanged(true, new GroupChangedHandler() {
          @Override
          public void onGroupChanged(final GroupChangedEvent event) {
            setState(session.getCurrentState());
          }
        });
      }
    });
  }

  private void changeCss(final GSpaceArmorResources res, final String themeName) {
    final GSpaceTheme theme = themes.get(themeName);
    CurrentEntityTheme.setColors(themes.get(themeName).getColors(), theme.getBackColors());
    if (cssAdded != null) {
      cssAdded.removeFromParent();
    }
    cssAdded = CSSUtils.addCss(res.style().getText());
  }

  public void changeTheme(final StateToken token, final GSpaceTheme newTheme) {
    NotifyUser.showProgress();
    groupServiceProvider.get().changeGroupWsTheme(session.getUserHash(), token, newTheme.getName(),
        new AsyncCallbackSimple<Void>() {
          @Override
          public void onSuccess(final Void result) {
            if (session.getCurrentState().getStateToken().getGroup().equals(token.getGroup())) {
              setTheme(newTheme);
            }
            NotifyUser.hideProgress();
          }
        });
  }

  protected void onChangeGroupWsTheme(final GSpaceTheme newTheme) {
    NotifyUser.showProgress();
    groupServiceProvider.get().changeGroupWsTheme(session.getUserHash(),
        session.getCurrentState().getStateToken(), newTheme.getName(), new AsyncCallbackSimple<Void>() {
          @Override
          public void onSuccess(final Void result) {
            setTheme(newTheme);
            NotifyUser.hideProgress();
          }
        });
  }

  private void setState(final StateAbstractDTO state) {
    setTheme(themes.get(state.getGroup().getWorkspaceTheme()));
    final GroupDTO group = state.getGroup();
    final String groupBackImage = group.getBackgroundImage();
    if (groupBackImage == null) {
      wsBackManager.clearBackgroundImage();
    } else {
      wsBackManager.setBackgroundImage();
    }
  }

  private void setTheme(final GSpaceTheme newTheme) {
    if (previousTheme == null || !previousTheme.equals(newTheme)) {
      GSpaceThemeChangeEvent.fire(eventBus, previousTheme, newTheme);
      changeCss(res, newTheme.getName());
    }
    previousTheme = newTheme;
  }

}
