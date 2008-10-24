/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
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
 */package org.ourproject.kune.workspace.client.entitylogo;

import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.ui.dialogs.BasicDialog;
import org.ourproject.kune.platf.client.ui.download.FileParams;
import org.ourproject.kune.workspace.client.site.Site;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.gwtext.client.core.Connection;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.form.Form;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.form.Hidden;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.form.event.FormListener;

public class EntityLogoSelectorPanel implements EntityLogoSelectorView {

    private static final String ICON_UPLOAD_SERVLET = "/kune/servlets/EntityLogoUploadManager";
    private final BasicDialog dialog;
    private final FormPanel form;
    private final Hidden userhashField;
    private final Hidden tokenField;

    public EntityLogoSelectorPanel(final EntityLogoSelectorPresenter presenter, final WorkspaceSkeleton ws,
            I18nTranslationService i18n) {
        dialog = new BasicDialog(i18n.t("Select an logo for your group"), true, true, 300, 120);
        dialog.setCollapsible(false);

        form = new FormPanel();
        form.setBorder(false);
        form.setFrame(false);
        form.setFileUpload(true);
        form.setMethod(Connection.POST);
        form.setUrl(ICON_UPLOAD_SERVLET);
        form.setWaitMsgTarget(true);
        form.setHideLabels(true);
        dialog.setMargins(10);
        form.addFormListener(new FormListener() {
            public boolean doBeforeAction(Form form) {
                return true;
            }

            public void onActionComplete(Form form, int httpStatus, String responseText) {
                Site.important(responseText);
                presenter.onSubmitComplete(httpStatus, responseText);
            }

            public void onActionFailed(Form form, int httpStatus, String responseText) {
                Site.important(responseText);
                presenter.onSubmitFailed(httpStatus, responseText);
            }
        });

        final TextField file = new TextField("File", EntityLogoView.LOGO_FORM_FIELD);
        file.setInputType("file");
        userhashField = new Hidden(FileParams.HASH, FileParams.HASH);
        tokenField = new Hidden(FileParams.TOKEN, FileParams.TOKEN);
        form.add(userhashField);
        form.add(tokenField);
        form.add(file);

        Button submit = new Button(i18n.t("Select"));
        submit.addListener(new ButtonListenerAdapter() {
            @Override
            public void onClick(Button button, EventObject e) {
                form.getForm().submit();
            }
        });
        Button cancel = new Button(i18n.t("Cancel"));
        cancel.addListener(new ButtonListenerAdapter() {
            @Override
            public void onClick(Button button, EventObject e) {
                presenter.onCancel();
            }
        });
        dialog.addButton(submit);
        dialog.addButton(cancel);
        dialog.add(form);
    }

    public void hide() {
        dialog.hide();
        form.getForm().reset();
    }

    public void setUploadParams(String userHash, String token) {
        userhashField.setValue(userHash);
        tokenField.setValue(token);
    }

    public void show() {
        dialog.show();
    }
}
