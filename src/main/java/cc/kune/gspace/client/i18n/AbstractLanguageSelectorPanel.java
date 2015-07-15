/*
 *
 * Copyright (C) 2007-2015 Licensed to the Comunes Association (CA) under
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

import java.util.List;

import cc.kune.common.client.errors.UIException;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.common.shared.utils.SimpleCallback;
import cc.kune.common.shared.utils.TextUtils;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.dto.I18nLanguageSimpleDTO;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.watopi.chosen.client.ChosenOptions;
import com.watopi.chosen.client.event.ChosenChangeEvent;
import com.watopi.chosen.client.event.ChosenChangeEvent.ChosenChangeHandler;
import com.watopi.chosen.client.gwt.ChosenListBox;

/**
 * The Class AbstractLanguageSelectorPanel.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public abstract class AbstractLanguageSelectorPanel extends HorizontalPanel {

  /** The Constant LANG_FIELD. */
  public static final String LANG_FIELD = "k-langsp-lf";

  private final InlineLabel fieldSep;

  private final InlineLabel fieldTitle;

  /** The i18n. */
  private final I18nTranslationService i18n;

  private ChosenListBox langChoose;
  /** The session. */
  private final Session session;

  private final FlowPanel titlePanel;

  /**
   * Instantiates a new abstract language selector panel.
   *
   * @param i18n
   *          the i18n
   * @param session
   *          the session
   * @param type
   *          the type
   */
  public AbstractLanguageSelectorPanel(final I18nTranslationService i18n, final Session session,
      final LanguageSelectorType type) {
    super();
    this.i18n = i18n;
    this.session = session;

    createLangChoose(type);
    titlePanel = new FlowPanel();
    fieldTitle = new InlineLabel();
    fieldSep = new InlineLabel();
    fieldTitle.addStyleName("k-fl");
    fieldTitle.addStyleName("x-form-item-label");
    fieldTitle.addStyleName("k-white-nowrap");
    fieldSep.addStyleName("k-fl");
    fieldSep.addStyleName("x-form-item-label");
    langChoose.addStyleName("k-fl");
    titlePanel.addStyleName("k-fl");
    titlePanel.addStyleName("kune-Margin-10-r");
    // fieldTitle.setWidth("140px");
    titlePanel.add(fieldTitle);
    titlePanel.add(fieldSep);
    super.add(titlePanel);
    super.add(langChoose);
    super.addStyleName("k-clean");
  }

  /**
   * Adds the change listener.
   *
   * @param onChange
   *          the on change
   */
  public void addChangeListener(final SimpleCallback onChange) {
    langChoose.addChosenChangeHandler(new ChosenChangeHandler() {
      @Override
      public void onChange(final ChosenChangeEvent event) {
        onChange.onCallback();
      }
    });
  }

  private void addItem(final I18nLanguageSimpleDTO lang) {
    langChoose.addItem(lang.getEnglishName(), lang.getCode());
  }

  @Override
  public void clear() {
    reset();
  }

  private void createLangChoose(final LanguageSelectorType type) {
    final ChosenOptions options = new ChosenOptions();
    options.setNoResultsText(i18n.t("Language not found"));
    options.setPlaceholderText(i18n.t("Enter language"));
    options.setSearchContains(true);
    langChoose = new ChosenListBox(false, options);

    final List<I18nLanguageSimpleDTO> langs = type == LanguageSelectorType.ONLY_FULL_TRANSLATED ? session.getFullTranslatedLanguages()
        : session.getLanguages();
    // Note: First empty, from the gwtchosen doc: on single selects, the first
    // element is assumed to be selected by the browser. To take advantage of
    // the default text support, you will need to include a blank option as the
    // first element of your select list.
    langChoose.addItem("");
    for (final I18nLanguageSimpleDTO lang : langs) {
      switch (type) {
      case ALL_EXCEPT_ENGLISH:
      case ONLY_FULL_TRANSLATED:
        if (!lang.getCode().equals("en")) {
          addItem(lang);
        }
        break;
      case ALL:
      default:
        addItem(lang);
        break;
      }
    }
  }

  /**
   * Gets the language.
   *
   * @return the language
   */
  public I18nLanguageSimpleDTO getLanguage() {
    final String langCode = langChoose.getSelectedValue();
    if (TextUtils.isEmpty(langCode)) {
      return null;
    }
    for (final I18nLanguageSimpleDTO lang : session.getLanguages()) {
      if (lang.getCode().equals(langCode)) {
        return lang;
      }
    }
    throw new UIException("Language not found");
  }

  /**
   * Gets the language code.
   *
   * @return the language code
   */
  public String getLanguageCode() {
    return langChoose.getSelectedValue();

  }

  /**
   * Gets the language english name.
   *
   * @return the language english name
   */
  public String getLanguageEnglishName() {
    final I18nLanguageSimpleDTO lang = getLanguage();
    return lang == null ? null : lang.getEnglishName();
  }

  private void reset() {
    // Read Note in createLangChoose()
    langChoose.setItemSelected(0, true);
  }

  public void setEnabled(final Boolean enabled) {
    langChoose.setEnabled(enabled);
  }

  /**
   * Sets the lang separator.
   *
   * @param separator
   *          the new lang separator
   */
  public void setLangSeparator(final String separator) {
    fieldSep.setText(separator);
  }

  /**
   * Sets the lang title.
   *
   * @param langFieldTitle
   *          the new lang title
   */
  public void setLangTitle(final String langFieldTitle) {
    if (langFieldTitle == null) {
      fieldTitle.setVisible(false);
      fieldSep.setVisible(false);
    } else {
      if (langFieldTitle != null) {
        fieldTitle.setText(langFieldTitle);
      }
    }
  }

  /**
   * Sets the language.
   *
   * @param language
   *          the new language
   */
  public void setLanguage(final I18nLanguageSimpleDTO language) {
    if (language == null) {
      reset();
    } else {
      for (int i = 0; i < langChoose.getItemCount(); i++) {
        final String val = langChoose.getValue(i);
        if (val.equals(language.getCode())) {
          langChoose.setSelectedIndex(i);
          return;
        }
      }
    }
  }

  public void setTitleWidth(final String width) {
    titlePanel.setWidth(width);
  }
}
