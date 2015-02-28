/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
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
package cc.kune.core.client.sitebar;

import org.gwtbootstrap3.client.ui.constants.Styles;

import br.com.rpa.client._coreelements.CoreIconButton;
import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.WidgetMenuDescriptor;
import cc.kune.common.client.tooltip.Tooltip;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.core.client.events.AppStartEvent;
import cc.kune.core.client.events.AppStartEvent.AppStartHandler;
import cc.kune.core.client.i18n.I18nUrlUtils;
import cc.kune.core.client.state.Session;
import cc.kune.polymer.client.PolymerId;

import com.google.gwt.i18n.client.LocaleInfo;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;

/**
 * The Class SiteLanguateSelector.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class SiteLanguageSelector extends WidgetMenuDescriptor {
  public static class LocaleAction extends AbstractExtendedAction {

    private final String locale;

    public LocaleAction(final String locale) {
      this.locale = locale;
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
      I18nUrlUtils.changeLanguageInUrl(locale);
    }

  }

  public static final String MENU_ID = PolymerId.SITEBAR_LANGUAGE_BTN.getId();

  @Inject
  public SiteLanguageSelector(final Session session, final EventBus eventBus) {
    super();
    final CoreIconButton btn = CoreIconButton.wrap(MENU_ID);
    setWidget(btn);
    setParent(SitebarActions.RIGHT_TOOLBAR);
    Tooltip.to(btn, I18n.t("Choose your language"));

    session.onAppStart(true, new AppStartHandler() {
      @Override
      public void onAppStart(final AppStartEvent event) {
        final String currentLocale = LocaleInfo.getCurrentLocale().getLocaleName();
        btn.setText(LocaleInfo.getLocaleNativeDisplayName(currentLocale));
        for (final String locale : LocaleInfo.getAvailableLocaleNames()) {
          final MenuItemDescriptor menuItem = new MenuItemDescriptor(SiteLanguageSelector.this,
              new LocaleAction(locale));
          menuItem.withText(LocaleInfo.getLocaleNativeDisplayName(locale));
          if (locale.equals(currentLocale)) {
            menuItem.setStyles(Styles.ACTIVE);
          }
        }
      }
    });
  }
}
