/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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
package cc.kune.core.client.notify.confirm;

import cc.kune.common.client.notify.ConfirmAskEvent;
import cc.kune.common.client.ui.dialogs.BasicTopDialog;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.notify.confirm.UserConfirmPresenter.UserConfirmView;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class UserConfirmPanel extends ViewImpl implements UserConfirmView {
  public static final String CANCEL_ID = "k-conf-dial";
  public static final String DIALOG_ID = "k-conf-dial-nok";
  public static final String OK_ID = "k-conf-dial-ok";
  private HandlerRegistration acceptHandler;
  private final HTML askLabel;
  private HandlerRegistration cancelHandler;
  private final BasicTopDialog dialog;

  @Inject
  public UserConfirmPanel(final I18nTranslationService i18n) {
    dialog = new BasicTopDialog.Builder(DIALOG_ID, false, true, i18n.getDirection()).autoscroll(false).firstButtonId(
        OK_ID).sndButtonId(CANCEL_ID).tabIndexStart(1).build();
    askLabel = new HTML();
    askLabel.addStyleName("k-userconfirm-label");
    dialog.getInnerPanel().add(askLabel);
  }

  @Override
  public Widget asWidget() {
    return null;
  }

  @Override
  public void confirmAsk(final ConfirmAskEvent ask) {
    dialog.setFirstBtnText(ask.getAcceptBtnMsg());
    final String cancelBtnMsg = ask.getCancelBtnMsg();
    dialog.setFirstBtnTitle(ask.getAcceptBtnTooltip());
    dialog.setSecondBtnText(cancelBtnMsg);
    dialog.setSecondBtnTitle(ask.getCancelBtnTooltip());
    dialog.getTitleText().setText(ask.getTitle());
    dialog.setFirstBtnTabIndex(1);
    dialog.setSecondBtnTabIndex(2);
    askLabel.setHTML(ask.getMessage());
    dialog.showCentered();
    resetHandlers();
    acceptHandler = dialog.getFirstBtn().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        dialog.hide();
        ask.getCallback().onSuccess();
      }
    });
    cancelHandler = dialog.getSecondBtn().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        dialog.hide();
        ask.getCallback().onCancel();
      }
    });
    dialog.showCentered();
    dialog.setFirstBtnFocus();
    final ImageResource icon = ask.getIcon();
    if (icon != null) {
      dialog.setTitleIcon(icon);
    }
  }

  private void resetHandlers() {
    if (acceptHandler != null) {
      acceptHandler.removeHandler();
    }
    if (cancelHandler != null) {
      cancelHandler.removeHandler();
    }
  }

}
