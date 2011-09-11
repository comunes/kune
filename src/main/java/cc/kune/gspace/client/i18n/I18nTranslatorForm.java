/*
 * Copyright 2010 Google Inc.
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
import cc.kune.common.client.utils.TextUtils;
import cc.kune.core.shared.dto.I18nLanguageSimpleDTO;
import cc.kune.core.shared.dto.I18nTranslationDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;

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

/**
 * A form used for editing contacts.
 */
public class I18nTranslatorForm extends Composite {

  interface Binder extends UiBinder<Widget, I18nTranslatorForm> {
  }

  private static Binder uiBinder = GWT.create(Binder.class);
  @UiField
  PushButton copyIcon;
  private I18nTranslationDataProvider dataProvider;
  private I18nTranslationService i18n;
  private I18nTranslationDTO item;
  @UiField
  Label keyboardRecomendation;
  @UiField
  Label keyboardRecomendationTitle;
  private final Timer keyboardTimer;
  @UiField
  Label noteForTranslators;
  @UiField
  Label noteForTranslatorsTittle;
  private I18nTraslatorSaver saver;
  @UiField
  Label toLanguageTitle;
  @UiField
  Label toTranslate;
  @UiField
  Label toTranslateTitle;
  @UiField
  TextArea translation;

  public I18nTranslatorForm() {
    initWidget(uiBinder.createAndBindUi(this));
    keyboardTimer = new Timer() {
      @Override
      public void run() {
        saveIfNeeded();
      }
    };
  }

  private void copyTranslation() {
    translation.setText(toTranslate.getText());
  }

  public void focusToTranslate() {
    translation.setFocus(true);
  }

  @UiHandler("translation")
  void handleBlur(final BlurEvent event) {
    saveIfNeeded();
  }

  @UiHandler("copyIcon")
  void handleClickOnCopyIcon(final ClickEvent e) {
    copyTranslation();
    translation.setFocus(true);
    updateWithTimer();
  }

  @UiHandler("translation")
  void handleKeyPress(final ChangeEvent event) {
    updateWithTimer();
  }

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

  public void init(final I18nTranslationDataProvider dataProvider, final I18nTranslationService i18n,
      final I18nTraslatorSaver saver) {
    this.dataProvider = dataProvider;
    this.i18n = i18n;
    this.saver = saver;
    Tooltip.to(copyIcon, i18n.t("Copy the text to translate"));
    toTranslateTitle.setText(i18n.t("translate this:"));
    noteForTranslatorsTittle.setText(i18n.t("Notes:"));
    keyboardRecomendationTitle.setText(i18n.t("Tip:"));
    keyboardRecomendation.setText(i18n.t("Pulse Alt+PageUp or Alt+PageDown to move up/down in the list while translating, and Alt-V to copy the original text. The translations are autosaved"));
  }

  private void saveIfNeeded() {
    keyboardTimer.cancel();
    final String newTranslation = translation.getText();
    if (item != null && TextUtils.notEmpty(newTranslation) && !newTranslation.equals(item.getText())) {
      item.setText(translation.getText());
      item.setDirty(true);
      dataProvider.refreshDisplays();
      saver.save(new I18nTranslationDTO(item.getId(), item.getTrKey(), newTranslation));
    }
  }

  public void setInfo(final I18nTranslationDTO item) {
    saveIfNeeded();
    this.item = item;
    final String[] splitted = splitNT(item.getTrKey());
    toTranslate.setText(splitted[0]);
    final boolean hasNT = splitted.length > 1;
    noteForTranslators.setVisible(hasNT);
    noteForTranslatorsTittle.setVisible(hasNT);
    if (hasNT) {
      noteForTranslators.setText(splitted[1]);
    }
    translation.setText(item.getText());
  }

  public void setToLanguage(final I18nLanguageSimpleDTO language) {
    toLanguageTitle.setText(i18n.tWithNT("to [%s]:", "For example, 'to Spanish':",
        language.getEnglishName()));
  }

  private String[] splitNT(final String textWithNT) {
    String[] nt;
    final String[] splitted = textWithNT.split(" \\[%NT ");
    if (splitted.length > 1) {
      nt = splitted[1].split("\\]$");
      splitted[1] = nt[0];
    }
    return splitted;
  }

  private void updateWithTimer() {
    keyboardTimer.cancel();
    keyboardTimer.schedule(3000);
  }
}
