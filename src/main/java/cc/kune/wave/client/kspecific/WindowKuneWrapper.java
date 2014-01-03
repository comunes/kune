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

package cc.kune.wave.client.kspecific;

import org.waveprotocol.wave.client.common.util.WindowConfirmCallback;
import org.waveprotocol.wave.client.common.util.WindowPromptCallback;
import org.waveprotocol.wave.client.common.util.WindowWrapper;

import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.common.shared.utils.SimpleResponseCallback;
import cc.kune.core.client.ui.dialogs.PromptTopDialog;
import cc.kune.core.client.ui.dialogs.PromptTopDialog.Builder;
import cc.kune.core.client.ui.dialogs.PromptTopDialog.OnEnter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

// TODO: Auto-generated Javadoc
/**
 * The Class WindowKuneWrapper.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class WindowKuneWrapper implements WindowWrapper {

  /** The Constant WINDOW_PROMPT_ID. */
  public static final String WINDOW_PROMPT_ID = "k-window-prompt-id";

  /** The callback. */
  private WindowPromptCallback callback;

  /** The diag. */
  private PromptTopDialog diag;

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.waveprotocol.box.webclient.client.WindowWrapper#alert(java.lang.String)
   */
  @Override
  public void alert(final String message) {
    NotifyUser.showAlertMessage(I18n.t("Warning"), message);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.waveprotocol.wave.client.common.util.WindowWrapper#confirm(java.lang
   * .String, org.waveprotocol.wave.client.common.util.WindowConfirmCallback)
   */
  @Override
  public void confirm(final String msg, final WindowConfirmCallback callback) {
    NotifyUser.askConfirmation(I18n.t("Confirm"), msg, new SimpleResponseCallback() {
      @Override
      public void onCancel() {
        callback.onCancel();
      }

      @Override
      public void onSuccess() {
        callback.onOk();
      }
    });
  }

  /**
   * Do action.
   *
   */
  protected void doPromptAction() {
    callback.onReturn(diag.getTextFieldValue());
    diag.hide();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.waveprotocol.wave.client.common.util.WindowWrapper#prompt(java.lang
   * .String, java.lang.String,
   * org.waveprotocol.wave.client.common.util.WindowPromptCallback)
   */
  @Override
  public void prompt(final String msg, final String value, final WindowPromptCallback callback) {
    this.callback = callback;
    if (diag == null) {
      final Builder builder = new PromptTopDialog.Builder(WINDOW_PROMPT_ID, msg, false, true,
          I18n.getDirection(), new OnEnter() {
            @Override
            public void onEnter() {
              doPromptAction();
            }
          });
      builder.firstButtonTitle(I18n.t("Ok"));
      builder.sndButtonTitle(I18n.t("Cancel"));
      builder.width("220px");
      builder.tabIndexStart(1);
      diag = builder.build();
      diag.getFirstBtn().addClickHandler(new ClickHandler() {
        @Override
        public void onClick(final ClickEvent event) {
          doPromptAction();
        }
      });
      diag.getSecondBtn().addClickHandler(new ClickHandler() {
        @Override
        public void onClick(final ClickEvent event) {
          diag.hide();
        }
      });
      diag.setFirstBtnTabIndex(1);
      diag.setSecondBtnTabIndex(2);
      diag.setTextFieldSelectOnFocus(true);
    }
    diag.setText(msg, I18n.getDirection());
    diag.clearTextFieldValue();
    diag.showCentered();
    diag.setTextFieldValue(value);
    diag.setTextFieldFocus();
  }

}
