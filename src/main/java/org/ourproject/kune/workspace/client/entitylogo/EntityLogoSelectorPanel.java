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
import org.ourproject.kune.platf.client.ui.dialogs.BasicDialogExtended;
import org.ourproject.kune.platf.client.ui.download.FileParams;
import org.ourproject.kune.workspace.client.site.Site;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.calclab.suco.client.listener.Listener0;
import com.gwtext.client.core.Connection;
import com.gwtext.client.core.EventCallback;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.form.Form;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.form.Hidden;
import com.gwtext.client.widgets.form.Label;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.form.event.FieldListenerAdapter;
import com.gwtext.client.widgets.form.event.FormListener;

public class EntityLogoSelectorPanel implements EntityLogoSelectorView {

    public static final String NORMAL_TITLE = "Select a logo for your group";
    public static final String PERSON_TITLE = "Select your avatar";
    public static final String ICON_UPLOAD_SERVLET = "/kune/servlets/EntityLogoUploadManager";
    public static final String SUBID = "k-elogoselp-subb";
    public static final String CANCELID = "k-elogoselp-canb";
    public static final String DIALOG_ID = "k-elogoselp";
    private final BasicDialogExtended dialog;
    private final FormPanel formPanel;
    private final Hidden userhashField;
    private final Hidden tokenField;
    private final TextField file;
    private final Label dialogInfoLabel;
    private final I18nTranslationService i18n;

    public EntityLogoSelectorPanel(final EntityLogoSelectorPresenter presenter, final WorkspaceSkeleton ws,
            I18nTranslationService i18n) {
        this.i18n = i18n;
        dialog = new BasicDialogExtended(i18n.t(NORMAL_TITLE), true, true, 400, 200, "", i18n.t("Select"), SUBID,
                i18n.tWithNT("Cancel", "used in button"), CANCELID, new Listener0() {
                    public void onEvent() {
                        String filename = file.getValueAsString();
                        if (filename != null && filename.length() > 0) {
                            formPanel.getForm().submit();
                        }
                    }
                }, new Listener0() {
                    public void onEvent() {
                        presenter.onCancel();
                    }
                }, 2);
        dialog.setId(DIALOG_ID);
        dialog.setCollapsible(false);
        dialog.setBorder(false);
        // dialog.getFirstButton().disable();

        formPanel = new FormPanel();
        formPanel.setFrame(true);
        formPanel.setAutoScroll(false);
        formPanel.setBorder(false);
        formPanel.setFileUpload(true);
        formPanel.setWidth(400);
        formPanel.setMethod(Connection.POST);
        formPanel.setUrl(ICON_UPLOAD_SERVLET);
        formPanel.setWaitMsgTarget(true);
        formPanel.setHideLabels(true);
        formPanel.setPaddings(10);
        formPanel.addFormListener(new FormListener() {
            public boolean doBeforeAction(Form form) {
                return true;
            }

            public void onActionComplete(Form form, int httpStatus, String responseText) {
                presenter.onSubmitComplete(httpStatus, responseText);
            }

            public void onActionFailed(Form form, int httpStatus, String responseText) {
                Site.important(responseText);
                presenter.onSubmitFailed(httpStatus, responseText);
            }
        });
        dialogInfoLabel = new Label();
        formPanel.add(dialogInfoLabel);
        file = new TextField("File", EntityLogoView.LOGO_FORM_FIELD);
        EventCallback keyListener = new EventCallback() {
            public void execute(EventObject e) {
                // setEnableFileField();
            }
        };
        file.addKeyPressListener(keyListener);
        file.setId(EntityLogoView.LOGO_FORM_FIELD);
        file.setInputType("file");
        userhashField = new Hidden(FileParams.HASH, FileParams.HASH);
        tokenField = new Hidden(FileParams.TOKEN, FileParams.TOKEN);
        formPanel.add(userhashField);
        formPanel.add(tokenField);
        formPanel.add(file);
        FieldListenerAdapter changeListener = new FieldListenerAdapter() {
            @Override
            public void onChange(Field field, Object newVal, Object oldVal) {
                Site.info("change");
                // setEnableFileField();
            }
        };
        // Don't works:
        file.addListener(changeListener);
        dialog.add(formPanel);
    }

    // BrowseButton browseButton = new BrowseButton("SelectIcon");
    // browseButton.addListener(new BrowseButtonListenerAdapter() {
    // @Override
    // public void onInputFileChange(BrowseButton browseButton, String filename)
    // {
    // // TODO Auto-generated method stub
    // }
    // });

    public void hide() {
        dialog.hide();
        formPanel.getForm().reset();
    }

    public void setNormalGroupsLabels() {
        dialogInfoLabel.setHtml(i18n.t("Select an image in your computer as the logo for this group. "
                + "For best results use a [%d]x[%d] pixel image. We will automatically resize bigger images.",
                EntityLogoView.LOGO_ICON_DEFAULT_HEIGHT, EntityLogoView.LOGO_ICON_DEFAULT_HEIGHT)
                + "<br/><br/>");
        dialog.setTitle(NORMAL_TITLE);
    }

    public void setPersonalGroupsLabels() {
        dialogInfoLabel.setHtml(i18n.t("Select an image in your computer as your avatar. "
                + "For best results use a [%d]x[%d] pixel image. We will automatically resize bigger images.",
                EntityLogoView.LOGO_ICON_DEFAULT_HEIGHT, EntityLogoView.LOGO_ICON_DEFAULT_HEIGHT)
                + "<br/><br/>");
        dialog.setTitle(PERSON_TITLE);
    }

    public void setUploadParams(String userHash, String token) {
        userhashField.setValue(userHash);
        tokenField.setValue(token);
    }

    public void show() {
        dialog.show();
    }

    @SuppressWarnings("unused")
    private void setEnableFileField() {
        if (file.getValueAsString().length() > 0) {
            dialog.getFirstButton().enable();
        } else {
            dialog.getFirstButton().disable();
        }
    }
}
