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

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasBlurHandlers;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
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
  Image copyIcon;
  @UiField
  Label noteForTranslators;
  @UiField
  Label toTranslate;
  @UiField
  TextArea translation;

  public I18nTranslatorForm() {
    initWidget(uiBinder.createAndBindUi(this));
    Tooltip.to(copyIcon, "FIXME");
  }

  public HasBlurHandlers getBlurTraslation() {
    return translation;
  }

  public HasKeyPressHandlers getKeysTraslation() {
    return translation;
  }

  @UiHandler("copyIcon")
  void handleClickOnCopyIcon(final ClickEvent e) {
    translation.setText(toTranslate.getText());
  }

  public void setInfo(final String toTranslate, final String noteForTranslators, final String translation) {
    this.toTranslate.setText(toTranslate);
    this.noteForTranslators.setText(noteForTranslators);
    this.translation.setText(translation);
  }
}
