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
package cc.kune.core.client.notify.confirm;

import cc.kune.common.client.notify.ConfirmAskEvent;
import cc.kune.common.client.utils.TextUtils;
import cc.kune.core.client.notify.confirm.UserConfirmPresenter.UserConfirmView;
import cc.kune.core.client.ui.dialogs.BasicTopDialog;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class UserConfirmPanel extends ViewImpl implements UserConfirmView {
    private static final String CANCEL_ID = "k-conf-dial";
    private static final String DIALOG_ID = "k-conf-dial-nok";
    private static final String OK_ID = "k-conf-dial-ok";
    private HandlerRegistration acceptHandler;
    private final Label askLabel;
    private HandlerRegistration cancelHandler;
    private final BasicTopDialog dialog;

    @Inject
    public UserConfirmPanel(final I18nTranslationService i18n) {
        dialog = new BasicTopDialog(DIALOG_ID, "", false, true, false, 100, 100, "", "", OK_ID, "", CANCEL_ID, 1);
        askLabel = new Label();
        askLabel.addStyleName("k-userconfirm-label");
        dialog.getInnerPanel().add(askLabel);
    }

    @Override
    public Widget asWidget() {
        return null;
    }

    @Override
    public void confirmAsk(final ConfirmAskEvent ask) {
        dialog.getFirstBtnText().setText(ask.getAcceptBtnMsg());
        final String cancelBtnMsg = ask.getCancelBtnMsg();
        dialog.setFirstBtnTitle(ask.getAcceptBtnTooltip());
        if (TextUtils.notEmpty(cancelBtnMsg)) {
            dialog.getSecondBtnText().setText(cancelBtnMsg);
            dialog.setSecondBtnTitle(ask.getCancelBtnTooltip());
            dialog.setSecondBtnVisible(true);
        } else {
            dialog.setSecondBtnVisible(false);
        }
        dialog.getTitleText().setText(ask.getTitle());
        askLabel.setText(ask.getMessage());
        dialog.showCentered();
        resetHandlers();
        acceptHandler = dialog.getFirstBtn().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(final ClickEvent event) {
                ask.getCallback().onSuccess();
                dialog.hide();
            }
        });
        cancelHandler = dialog.getSecondBtn().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(final ClickEvent event) {
                ask.getCallback().onCancel();
                dialog.hide();
            }
        });
        dialog.showCentered();
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
