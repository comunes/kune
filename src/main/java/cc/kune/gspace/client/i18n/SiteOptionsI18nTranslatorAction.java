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
package cc.kune.gspace.client.i18n;

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.sitebar.SitebarActionsPresenter;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class SiteOptionsI18nTranslatorAction extends AbstractExtendedAction {
  private I18nTranslator translator;
  private final Provider<I18nTranslator> translatorProv;

  @Inject
  public SiteOptionsI18nTranslatorAction(final I18nTranslationService i18n, final CoreResources img,
      final Provider<I18nTranslator> translatorProv, final SitebarActionsPresenter siteOptions) {
    super();
    this.translatorProv = translatorProv;
    putValue(Action.NAME, i18n.t("Help with the translation"));
    putValue(Action.SMALL_ICON, img.language());
    MenuItemDescriptor.build(siteOptions.getOptionsMenu(), this);
  }

  @Override
  public void actionPerformed(final ActionEvent event) {
    if (translator == null) {
      translator = translatorProv.get();
    }
    translator.show();
    // item.setPosition(1);
  }
}
