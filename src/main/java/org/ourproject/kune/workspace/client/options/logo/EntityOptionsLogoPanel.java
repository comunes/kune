/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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
 \*/
package org.ourproject.kune.workspace.client.options.logo;

import org.ourproject.kune.platf.client.PlatfMessages;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.ui.dialogs.DefaultFormUtils;
import org.ourproject.kune.platf.client.ui.download.FileParams;
import org.ourproject.kune.platf.client.ui.noti.NotifyUser;
import org.ourproject.kune.workspace.client.entityheader.EntityHeaderView;
import org.ourproject.kune.workspace.client.options.EntityOptionsView;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.gwtext.client.core.Connection;
import com.gwtext.client.core.EventCallback;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Position;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.form.Form;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.form.Hidden;
import com.gwtext.client.widgets.form.Label;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.form.event.FieldListenerAdapter;
import com.gwtext.client.widgets.form.event.FormListener;

public class EntityOptionsLogoPanel extends FormPanel implements EntityOptionsLogoView {

    public static final String ICON_UPLOAD_SERVLET = "/kune/servlets/EntityLogoUploadManager";
    public static final String SET_LOGO_ID = "k-eolp-sendb";
    public static final String PANEL_ID = "k-eolp-pan";
    private final Hidden userhashField;
    private final Hidden tokenField;
    private final TextField file;
    private final Label dialogInfoLabel;
    private final I18nTranslationService i18n;

    public EntityOptionsLogoPanel(final EntityOptionsLogoPresenter presenter, final WorkspaceSkeleton ws,
            I18nTranslationService i18n) {
        this.i18n = i18n;
        super.setHeight(EntityOptionsView.HEIGHT);
        super.setButtonAlign(Position.LEFT);
        super.setFrame(true);
        super.setAutoScroll(false);
        super.setBorder(false);
        super.setFileUpload(true);
        super.setWidth(400);
        super.setIconCls("k-picture-icon");
        super.setMethod(Connection.POST);
        super.setUrl(ICON_UPLOAD_SERVLET);
        super.setWaitMsgTarget(true);
        super.setHideLabels(true);
        super.setPaddings(10);
        super.addFormListener(new FormListener() {
            public boolean doBeforeAction(Form form) {
                return true;
            }

            public void onActionComplete(Form form, int httpStatus, String responseText) {
                presenter.onSubmitComplete(httpStatus, responseText);
            }

            public void onActionFailed(Form form, int httpStatus, String responseText) {
                presenter.onSubmitFailed(httpStatus, responseText);
            }
        });
        dialogInfoLabel = new Label();
        super.add(dialogInfoLabel);
        file = new TextField("File", EntityHeaderView.LOGO_FORM_FIELD);
        EventCallback keyListener = new EventCallback() {
            public void execute(EventObject e) {
                // setEnableFileField();
            }
        };
        file.addKeyPressListener(keyListener);
        file.setId(EntityHeaderView.LOGO_FORM_FIELD);
        file.setInputType("file");
        userhashField = new Hidden(FileParams.HASH, FileParams.HASH);
        tokenField = new Hidden(FileParams.TOKEN, FileParams.TOKEN);
        super.add(userhashField);
        super.add(tokenField);
        super.add(file);
        FieldListenerAdapter changeListener = new FieldListenerAdapter() {
            @Override
            public void onChange(Field field, Object newVal, Object oldVal) {
                NotifyUser.info("change");
                // setEnableFileField();
            }
        };
        // Don't works:
        file.addListener(changeListener);
        setId(PANEL_ID);

        Button sendButton = new Button(i18n.t("Send"), new ButtonListenerAdapter() {
            @Override
            public void onClick(Button button, EventObject e) {
                String filename = file.getValueAsString();
                if (filename != null && filename.length() > 0) {
                    getForm().submit();
                }
            }
        });
        sendButton.setId(SET_LOGO_ID);
        super.addButton(sendButton);
    }

    // BrowseButton browseButton = new BrowseButton("SelectIcon");
    // browseButton.addListener(new BrowseButtonListenerAdapter() {
    // @Override
    // public void onInputFileChange(BrowseButton browseButton, String filename)
    // {
    // //
    // }
    // });

    public void reset() {
        super.getForm().reset();
    }

    public void setNormalGroupsLabels() {
        dialogInfoLabel.setHtml(i18n.t("Select an image in your computer as the logo for this group. "
                + "For best results use a [%d]x[%d] pixel image. We will automatically resize bigger images.",
                EntityHeaderView.LOGO_ICON_DEFAULT_HEIGHT, EntityHeaderView.LOGO_ICON_DEFAULT_HEIGHT)
                + DefaultFormUtils.brbr());
        super.setTitle(PlatfMessages.ENT_LOGO_SELECTOR_NORMAL_TITLE);
        doLayoutIfNeeded();
    }

    public void setPersonalGroupsLabels() {
        dialogInfoLabel.setHtml(i18n.t("Select an image in your computer as your avatar. "
                + "For best results use a [%d]x[%d] pixel image. We will automatically resize bigger images.",
                EntityHeaderView.LOGO_ICON_DEFAULT_HEIGHT, EntityHeaderView.LOGO_ICON_DEFAULT_HEIGHT)
                + "<br/><br/>");
        super.setTitle(PlatfMessages.ENT_LOGO_SELECTOR_PERSON_TITLE);
        doLayoutIfNeeded();
    }

    public void setUploadParams(String userHash, String token) {
        userhashField.setValue(userHash);
        tokenField.setValue(token);
    }

    private void doLayoutIfNeeded() {
        if (super.isRendered()) {
            doLayout(false);
        }
    }

}
