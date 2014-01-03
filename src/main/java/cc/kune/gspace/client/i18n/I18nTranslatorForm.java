/*
 * Copyright 2010, 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package cc.kune.gspace.client.i18n;

import cc.kune.common.client.tooltip.Tooltip;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.common.shared.utils.TextUtils;
import cc.kune.core.shared.dto.I18nLanguageSimpleDTO;
import cc.kune.core.shared.dto.I18nTranslationDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

// TODO: Auto-generated Javadoc
/**
 * A form used for editing translations.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class I18nTranslatorForm extends Composite {

  /**
   * The Interface Binder.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  interface Binder extends UiBinder<Widget, I18nTranslatorForm> {
  }

  /** The ui binder. */
  private static Binder uiBinder = GWT.create(Binder.class);

  /** The copy icon. */
  @UiField
  PushButton copyIcon;

  /** The data provider. */
  private I18nTranslationDataProvider dataProvider;

  /** The i18n. */
  private I18nTranslationService i18n;

  /** The item. */
  private I18nTranslationDTO item;

  /** The keyboard recomendation. */
  @UiField
  Label keyboardRecomendation;

  /** The keyboard recomendation title. */
  @UiField
  Label keyboardRecomendationTitle;

  /** The keyboard timer. */
  private final Timer keyboardTimer;

  /** The note for translators. */
  @UiField
  Label noteForTranslators;

  /** The note for translators tittle. */
  @UiField
  Label noteForTranslatorsTittle;

  /** The saver. */
  private I18nTranslatorSaver saver;

  /** The to language title. */
  @UiField
  Label toLanguageTitle;

  /** The to translate. */
  @UiField
  Label toTranslate;

  /** The to translate title. */
  @UiField
  Label toTranslateTitle;

  /** The translation. */
  @UiField
  TextArea translation;

  /**
   * Instantiates a new i18n translator form.
   */
  public I18nTranslatorForm() {
    initWidget(uiBinder.createAndBindUi(this));
    keyboardTimer = new Timer() {
      @Override
      public void run() {
        saveIfNeeded();
      }
    };
  }

  /**
   * Copy translation.
   */
  private void copyTranslation() {
    translation.setText(toTranslate.getText());
  }

  /**
   * Focus to translate.
   */
  public void focusToTranslate() {
    translation.setFocus(true);
  }

  /**
   * Handle blur.
   * 
   * @param event
   *          the event
   */
  @UiHandler("translation")
  void handleBlur(final BlurEvent event) {
    saveIfNeeded();
  }

  /**
   * Handle click on copy icon.
   * 
   * @param e
   *          the e
   */
  @UiHandler("copyIcon")
  void handleClickOnCopyIcon(final ClickEvent e) {
    copyTranslation();
    translation.setFocus(true);
    updateWithTimer();
  }

  /**
   * Handle key press.
   * 
   * @param event
   *          the event
   */
  @UiHandler("translation")
  void handleKeyPress(final ChangeEvent event) {
    updateWithTimer();
  }

  /**
   * Handle key press.
   * 
   * @param event
   *          the event
   */
  @UiHandler("translation")
  void handleKeyPress(final KeyPressEvent event) {
    if (event.isAltKeyDown()
        && event.getNativeEvent().getKeyCode() == com.google.gwt.event.dom.client.KeyCodes.KEY_PAGEUP) {
      saveIfNeeded();
      dataProvider.selectPrevious();
    } else if (event.isAltKeyDown()
        && event.getNativeEvent().getKeyCode() == com.google.gwt.event.dom.client.KeyCodes.KEY_PAGEDOWN) {
      saveIfNeeded();
      dataProvider.selectNext();
    } else if (event.isAltKeyDown() && event.getCharCode() == 'v') {
      copyTranslation();
      event.stopPropagation();
      updateWithTimer();
    }
  }

  /**
   * Inits the.
   * 
   * @param dataProvider
   *          the data provider
   * @param i18n
   *          the i18n
   * @param saver
   *          the saver
   */
  public void init(final I18nTranslationDataProvider dataProvider, final I18nTranslationService i18n,
      final I18nTranslatorSaver saver) {
    this.dataProvider = dataProvider;
    this.i18n = i18n;
    this.saver = saver;
    Tooltip.to(copyIcon, i18n.t("Copy the text to translate"));
    toTranslateTitle.setText(i18n.t("translate this:"));
    noteForTranslatorsTittle.setText(i18n.t("Notes:"));
    keyboardRecomendationTitle.setText(i18n.t("Tip:"));
    keyboardRecomendation.setText(i18n.t("You can resize the above textarea.")
        + " "
        + i18n.t("Click Alt+PageUp or Alt+PageDown to move up/down in the list while translating, and Alt-V to copy the original text. The translations are autosaved"));
  }

  /**
   * Save if needed.
   */
  private void saveIfNeeded() {
    keyboardTimer.cancel();
    final String newTranslation = translation.getText();
    if (item != null && TextUtils.notEmpty(newTranslation) && !newTranslation.equals(item.getText())) {
      item.setText(translation.getText());
      item.setDirty(true);
      dataProvider.refreshDisplays();
      saver.save(new I18nTranslationDTO(item.getId(), item.getTrKey(), null, newTranslation,
          item.getParentId(), item.getParentTrKey(), item.getNoteForTranslators()));
    }
  }

  /**
   * Sets the info.
   * 
   * @param item
   *          the new info
   */
  public void setInfo(final I18nTranslationDTO item) {
    saveIfNeeded();
    this.item = item;
    final String trKey = item.getTrKey();
    toTranslate.setText(trKey == null ? item.getParentTrKey() : trKey);
    translation.setText(item.getText());
    final boolean hasNT = TextUtils.notEmpty(item.getNoteForTranslators());
    noteForTranslators.setVisible(hasNT);
    noteForTranslatorsTittle.setVisible(hasNT);
    if (hasNT) {
      noteForTranslators.setText(item.getNoteForTranslators());
    }
  }

  /**
   * Sets the to language.
   * 
   * @param language
   *          the new to language
   */
  public void setToLanguage(final I18nLanguageSimpleDTO language) {
    toLanguageTitle.setText(i18n.tWithNT("to [%s]:", "For example, 'to Spanish':",
        language.getEnglishName()));
    toTranslate.setText("");
    translation.setText("");
    noteForTranslators.setVisible(false);
  }

  /**
   * Update with timer.
   */
  private void updateWithTimer() {
    keyboardTimer.cancel();
    keyboardTimer.schedule(3000);
  }
}
