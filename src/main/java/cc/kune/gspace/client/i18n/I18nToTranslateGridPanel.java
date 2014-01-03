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
package cc.kune.gspace.client.i18n;

import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.shared.dto.I18nLanguageSimpleDTO;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class I18nToTranslateGridPanel.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class I18nToTranslateGridPanel extends Composite {

  /** The list. */
  private final I18nCellList list;

  /** The tab title. */
  private final Label tabTitle;

  /**
   * Instantiates a new i18n to translate grid panel.
   * 
   * @param i18n
   *          the i18n
   * @param list
   *          the list
   */
  @Inject
  public I18nToTranslateGridPanel(final I18nTranslationService i18n, final I18nCellList list) {
    this.list = list;
    tabTitle = new Label(i18n.t("Not translated"));
    initWidget(list);
  }

  /**
   * Gets the tab title.
   * 
   * @return the tab title
   */
  public IsWidget getTabTitle() {
    return tabTitle;
  }

  /**
   * Sets the language.
   * 
   * @param fromLanguage
   *          the from language
   * @param toLanguage
   *          the to language
   */
  public void setLanguage(final I18nLanguageSimpleDTO fromLanguage,
      final I18nLanguageSimpleDTO toLanguage) {
    list.setLanguage(fromLanguage, toLanguage, true);
  }

}
