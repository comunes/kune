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

import cc.kune.common.client.notify.NotifyLevelImages;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.common.shared.utils.SimpleCallback;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.ui.dialogs.tabbed.AbstractTabbedDialogPanel;
import cc.kune.core.shared.dto.I18nLanguageSimpleDTO;
import cc.kune.gspace.client.i18n.I18nTranslatorPresenter.I18nTranslatorView;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class I18nTranslatorPanel.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class I18nTranslatorPanel extends AbstractTabbedDialogPanel implements I18nTranslatorView {

  /** The Constant HEIGHT. */
  private static final int HEIGHT = 280;

  /** The Constant TRANSLATOR_ERROR_ID. */
  private static final String TRANSLATOR_ERROR_ID = "i18n-trans-panel-error";

  /** The Constant TRANSLATOR_PANEL_ID. */
  private static final String TRANSLATOR_PANEL_ID = "i18n-trans-panel";

  /** The Constant WIDTH. */
  private static final int WIDTH = 570;

  /** The checkbox. */
  private final CheckBox checkbox;

  /** The lan selector full translated panel. */
  private final LanguageSelectorOnlyFullTranslatedPanel lanSelectorFullTranslatedPanel;

  /** The lan selector panel. */
  private final AbstractLanguageSelectorPanel lanSelectorPanel;

  /** The to translate grid. */
  private final I18nToTranslateGridPanel toTranslateGrid;

  /** The translated grid. */
  private final I18nTranslatedGridPanel translatedGrid;

  /** The trans recommend. */
  private final I18nTranslateRecomendPanel transRecommend;

  /**
   * Instantiates a new i18n translator panel.
   * 
   * @param i18n
   *          the i18n
   * @param images
   *          the images
   * @param transGroup
   *          the trans group
   * @param lanSelectorFullTranslatedPanel
   *          the lan selector full translated panel
   * @param lanSelectorPanel
   *          the lan selector panel
   * @param toTranslateGrid
   *          the to translate grid
   * @param translatedGrid
   *          the translated grid
   * @param transRecommend
   *          the trans recommend
   * @param res
   *          the res
   */
  @Inject
  public I18nTranslatorPanel(final I18nTranslationService i18n, final NotifyLevelImages images,
      final I18nTranslatorTabsCollection transGroup,
      final LanguageSelectorOnlyFullTranslatedPanel lanSelectorFullTranslatedPanel,
      final LanguageSelectorWithoutEnglishPanel lanSelectorPanel,
      final I18nToTranslateGridPanel toTranslateGrid, final I18nTranslatedGridPanel translatedGrid,
      final I18nTranslateRecomendPanel transRecommend, final IconicResources res) {
    // Warning: Modal = true == you cannot select languages with mouse
    super(TRANSLATOR_PANEL_ID, "", false, false, images, TRANSLATOR_ERROR_ID, i18n.t("Close"), null,
        null, null, transGroup, i18n.getDirection());
    setIcon(res.world());
    this.lanSelectorPanel = lanSelectorPanel;
    this.lanSelectorFullTranslatedPanel = lanSelectorFullTranslatedPanel;
    this.toTranslateGrid = toTranslateGrid;
    this.translatedGrid = translatedGrid;
    this.transRecommend = transRecommend;
    transRecommend.setSize(WIDTH, HEIGHT);
    super.setIconCls("k-options-icon");
    super.setTitle(i18n.t("Help to translate kune"));

    checkbox = new CheckBox();
    checkbox.setBoxLabel(i18n.t("Translate from other non-English language"));
    checkbox.setValue(false);
    lanSelectorFullTranslatedPanel.setVisible(false);
    checkbox.addListener(Events.Change, new Listener<BaseEvent>() {
      @Override
      public void handleEvent(final BaseEvent be) {
        final Boolean value = checkbox.getValue();
        lanSelectorFullTranslatedPanel.setVisible(value);
        lanSelectorFullTranslatedPanel.setLanguage(null);
        refreshLangs(lanSelectorFullTranslatedPanel, lanSelectorPanel);
      }
    });
    super.getInnerPanel().insert(checkbox, 0);

    final SimpleCallback onLangChange = new SimpleCallback() {
      @Override
      public void onCallback() {
        refreshLangs(lanSelectorFullTranslatedPanel, lanSelectorPanel);
      }
    };

    super.getInnerPanel().insert(lanSelectorFullTranslatedPanel, 1);
    lanSelectorFullTranslatedPanel.setLangTitle(i18n.t("from"));
    lanSelectorFullTranslatedPanel.setLabelAlign(LabelAlign.RIGHT);
    lanSelectorFullTranslatedPanel.setLangSeparator(":");
    lanSelectorFullTranslatedPanel.addChangeListener(onLangChange);
    super.getInnerPanel().insert(lanSelectorPanel, 2);
    lanSelectorPanel.setLangTitle(i18n.t("to"));
    lanSelectorPanel.setLabelAlign(LabelAlign.RIGHT);
    lanSelectorPanel.setLangSeparator(":");
    lanSelectorPanel.addChangeListener(onLangChange);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.i18n.I18nTranslatorPresenter.I18nTranslatorView#init
   * ()
   */
  @Override
  public void init() {
    addTab(toTranslateGrid, toTranslateGrid.getTabTitle());
    addTab(translatedGrid, translatedGrid.getTabTitle());
    addTab(transRecommend, transRecommend.getTabTitle());
  }

  /**
   * Refresh langs.
   * 
   * @param lanSelectorFullTranslatedPanel
   *          the lan selector full translated panel
   * @param lanSelectorPanel
   *          the lan selector panel
   */
  private void refreshLangs(
      final LanguageSelectorOnlyFullTranslatedPanel lanSelectorFullTranslatedPanel,
      final LanguageSelectorWithoutEnglishPanel lanSelectorPanel) {
    setLanguage(lanSelectorFullTranslatedPanel.getLanguage(), lanSelectorPanel.getLanguage());
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.i18n.I18nTranslatorPresenter.I18nTranslatorView#
   * setLanguage(cc.kune.core.shared.dto.I18nLanguageSimpleDTO)
   */
  @Override
  public void setLanguage(final I18nLanguageSimpleDTO currentLanguage) {
    setLanguage(null, currentLanguage);
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
    if (checkbox.getValue() && fromLanguage != null) {
      lanSelectorFullTranslatedPanel.setLanguage(fromLanguage);
    } else {
      lanSelectorFullTranslatedPanel.clear();
    }
    if (toLanguage != null) {
      lanSelectorPanel.setLanguage(toLanguage);
      toTranslateGrid.setLanguage(fromLanguage, toLanguage);
      translatedGrid.setLanguage(fromLanguage, toLanguage);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.ui.dialogs.tabbed.AbstractTabbedDialogPanel#show()
   */
  @Override
  public void show() {
    super.show();
  }

}
