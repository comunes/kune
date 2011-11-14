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

import cc.kune.core.shared.dto.I18nLanguageSimpleDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;

public class I18nTranslatedGridPanel extends Composite {

  private final I18nCellList list;
  private final Label tabTitle;

  @Inject
  public I18nTranslatedGridPanel(final I18nTranslationService i18n, final I18nCellList list) {
    this.list = list;
    tabTitle = new Label(i18n.t("Translated"));
    initWidget(list);
  }

  public IsWidget getTabTitle() {
    return tabTitle;
  }

  public void setLanguage(final I18nLanguageSimpleDTO language) {
    list.setLanguage(language, false);
  }
}
