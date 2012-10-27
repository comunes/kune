/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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
package cc.kune.gspace.client.themes;

import java.util.HashMap;

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.ActionExtensibleView;
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuRadioItemDescriptor;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.events.AppStartEvent;
import cc.kune.core.client.events.AppStartEvent.AppStartHandler;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.dto.GSpaceTheme;
import cc.kune.core.shared.dto.InitDataDTO;
import cc.kune.gspace.client.themes.GSpaceThemeChangeEvent.GSpaceThemeChangeHandler;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;

public class GSpaceThemeSelectorPresenter {

  class ThemeAction extends AbstractExtendedAction {

    private final GSpaceThemeManager manager;
    private final GSpaceTheme theme;

    public ThemeAction(final GSpaceTheme theme, final EventBus eventBus, final GSpaceThemeManager manager) {
      super();
      this.theme = theme;
      this.manager = manager;
      putValue(NAME, i18n.tWithNT(theme.getName(), "a theme name"));
      putValue(SMALL_ICON, theme.getBackColors()[0]);
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
      manager.changeTheme(session.getCurrentStateToken(), theme);
      menu.setText(i18n.t(theme.getName()));
    }
  }

  private static final String GROUP_THEME = "k-theme-group";

  private final EventBus eventBus;
  private final I18nTranslationService i18n;
  private final HashMap<String, MenuRadioItemDescriptor> itemMap;
  private final GSpaceThemeManager manager;
  private MenuDescriptor menu;
  private final IconicResources res;
  private final Session session;
  private ActionExtensibleView view;

  @Inject
  public GSpaceThemeSelectorPresenter(final Session session, final I18nTranslationService i18n,
      final IconicResources res, final EventBus eventBus, final GSpaceThemeManager manager,
      final GSpaceThemeSelectorPanel view) {
    this.session = session;
    this.i18n = i18n;
    this.res = res;
    this.eventBus = eventBus;
    this.manager = manager;
    this.view = view;
    itemMap = new HashMap<String, MenuRadioItemDescriptor>();
    this.view = view;
    createActions();
    eventBus.addHandler(GSpaceThemeChangeEvent.getType(), new GSpaceThemeChangeHandler() {
      @Override
      public void onGsThemeChange(final GSpaceThemeChangeEvent event) {
        final GSpaceTheme newTheme = event.getNewTheme();
        final GSpaceTheme oldTheme = event.getOldTheme();
        select(oldTheme, newTheme);
      }
    });
  }

  private void createActions() {
    createMenu();
    final InitDataDTO initData = session.getInitData();
    if (initData == null) {
      session.onAppStart(true, new AppStartHandler() {
        @Override
        public void onAppStart(final AppStartEvent event) {
          setThemes(event.getInitData());
        }
      });
    } else {
      setThemes(initData);
    }
  }

  private void createMenu() {
    menu = new MenuDescriptor("");
    menu.putValue(Action.STYLES, "k-button");
    menu.putValue(Action.SMALL_ICON, res.styleGrey());
  }

  private void createTheme(final GSpaceTheme theme) {
    final ThemeAction action = new ThemeAction(theme, eventBus, manager);
    final MenuRadioItemDescriptor item = new MenuRadioItemDescriptor(menu, action, GROUP_THEME);
    itemMap.put(theme.getName(), item);
    view.add(item);
  }

  public void select(final GSpaceTheme oldTheme, final GSpaceTheme newTheme) {
    final String oldThemeName = oldTheme.getName();
    final String newThemeName = newTheme.getName();
    itemMap.get(newThemeName).setChecked(true);
    itemMap.get(newThemeName).putValue(Action.SMALL_ICON, "#FFF");
    itemMap.get(newThemeName).putValue(Action.SMALL_ICON, newTheme.getBackColors()[0]);
    if (oldThemeName != null) {
      itemMap.get(oldThemeName).putValue(Action.SMALL_ICON, "#FFF");
      itemMap.get(oldThemeName).putValue(Action.SMALL_ICON, oldTheme.getBackColors()[0]);
    }
    menu.setText(i18n.t(newThemeName));
  }

  private void setThemes(final InitDataDTO initData) {
    view.add(menu);
    for (final GSpaceTheme theme : initData.getgSpaceThemes().values()) {
      createTheme(theme);
    }
  }

}
