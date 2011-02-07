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
 \*/
package org.ourproject.kune.workspace.client.options.logo;

import org.ourproject.kune.platf.client.ui.dialogs.DefaultFormUtils;
import org.ourproject.kune.platf.client.ui.download.FileConstants;
import org.ourproject.kune.platf.client.ui.noti.NotifyUser;
import org.ourproject.kune.workspace.client.options.EntityOptionsView;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import cc.kune.core.client.resources.CoreMessages;
import cc.kune.core.shared.i18n.I18nTranslationService;

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

public abstract class EntityOptionsLogoPanel extends FormPanel implements EntityOptionsLogoView {

    public static final String ICON_UPLD_SERVLET = "/ws/servlets/EntityLogoUploadManager";
    private final Hidden userhashField;
    private final Hidden tokenField;
    private final TextField file;
    private final Label dialogInfoLabel;
    private final I18nTranslationService i18n;

    public EntityOptionsLogoPanel(final EntityOptionsLogoPresenter presenter, final WorkspaceSkeleton wskel,
            final I18nTranslationService i18n, final String panelId, final String buttonId, final String inputId) {
        super();
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
        super.setUrl(ICON_UPLD_SERVLET);
        super.setWaitMsgTarget(true);
        super.setHideLabels(true);
        super.setPaddings(10);
        super.addFormListener(new FormListener() {
            public boolean doBeforeAction(final Form form) {
                return true;
            }

            public void onActionComplete(final Form form, final int httpStatus, final String responseText) {
                presenter.onSubmitComplete(httpStatus, responseText);
            }

            public void onActionFailed(final Form form, final int httpStatus, final String responseText) {
                presenter.onSubmitFailed(httpStatus, responseText);
            }
        });
        dialogInfoLabel = new Label();
        super.add(dialogInfoLabel);
        file = new TextField("File", inputId);
        final EventCallback keyListener = new EventCallback() {
            public void execute(final EventObject e) {
                // setEnableFileField();
            }
        };
        file.addKeyPressListener(keyListener);
        file.setId(inputId);
        file.setInputType("file");
        userhashField = new Hidden(FileConstants.HASH, FileConstants.HASH);
        tokenField = new Hidden(FileConstants.TOKEN, FileConstants.TOKEN);
        super.add(userhashField);
        super.add(tokenField);
        super.add(file);
        final FieldListenerAdapter changeListener = new FieldListenerAdapter() {
            @Override
            public void onChange(final Field field, final Object newVal, final Object oldVal) {
                NotifyUser.info("change");
                // setEnableFileField();
            }
        };
        // Don't works:
        file.addListener(changeListener);
        setId(panelId);

        final Button sendButton = new Button(i18n.t("Send"), new ButtonListenerAdapter() {
            @Override
            public void onClick(final Button button, final EventObject e) {
                final String filename = file.getValueAsString();
                if (filename != null && filename.length() > 0) {
                    getForm().submit();
                }
            }
        });
        sendButton.setId(buttonId);
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
                FileConstants.LOGO_DEF_HEIGHT, FileConstants.LOGO_DEF_HEIGHT)
                + DefaultFormUtils.brbr());
        super.setTitle(CoreMessages.ENT_LOGO_SELECTOR_NORMAL_TITLE);
        doLayoutIfNeeded();
    }

    public void setPersonalGroupsLabels() {
        dialogInfoLabel.setHtml(i18n.t("Select an image in your computer as your avatar. "
                + "For best results use a [%d]x[%d] pixel image. We will automatically resize bigger images.",
                FileConstants.LOGO_DEF_HEIGHT, FileConstants.LOGO_DEF_HEIGHT)
                + "<br/><br/>");
        super.setTitle(CoreMessages.ENT_LOGO_SELECTOR_PERSON_TITLE);
        doLayoutIfNeeded();
    }

    public void setUploadParams(final String userHash, final String token) {
        userhashField.setValue(userHash);
        tokenField.setValue(token);
    }

    private void doLayoutIfNeeded() {
        if (super.isRendered()) {
            doLayout(false);
        }
    }

}
