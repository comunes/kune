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
package cc.kune.gspace.client.options.logo;

import cc.kune.common.client.log.Log;
import cc.kune.common.client.ui.IconLabel;
import cc.kune.common.client.utils.OnAcceptCallback;
import cc.kune.common.client.utils.TextUtils;
import cc.kune.core.client.resources.CoreMessages;
import cc.kune.core.client.resources.nav.NavResources;
import cc.kune.core.client.services.FileConstants;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.options.EntityOptionsView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class EntityOptionsLogoPanel extends Composite implements EntityOptionsLogoView {

    public static final String ICON_UPLD_SERVLET = "servlets/EntityLogoUploadManager";
    private final Label dialogInfoLabel;
    private final FileUpload fileUpload = new FileUpload();
    private final FormPanel form = new FormPanel();
    private final I18nTranslationService i18n;
    private OnAcceptCallback onAccept;
    private final IconLabel tabTitle;
    private final Hidden tokenField;
    private final Hidden userhashField;

    // private final TextField file;

    public EntityOptionsLogoPanel(final EventBus eventBus, final I18nTranslationService i18n, final String panelId,
            final String buttonId, final String inputId, final NavResources res) {
        super();
        this.i18n = i18n;
        tabTitle = new IconLabel(res.picture(), "");
        // super.setButtonAlign(HorizontalAlignment.LEFT);
        // super.setFrame(true);
        // super.setAutoScroll(false);
        // super.setBorder(false);
        // super.setFileUpload(true);
        // super.setWidth(400);
        // super.setIconCls("k-picture-icon");
        // super.setMethod(Method.POST);
        // super.setUrl(ICON_UPLD_SERVLET);
        // super.setWaitMsgTarget(true);
        // super.setHideLabels(true);
        // super.setPadding(10);
        // super.addFormListener(new FormListener() {
        // @Override
        // public boolean doBeforeAction(final Form form) {
        // return true;
        // }
        //
        // @Override
        // public void onActionComplete(final Form form, final int httpStatus,
        // final String responseText) {
        // presenter.onSubmitComplete(httpStatus, responseText);
        // }
        //
        // @Override
        // public void onActionFailed(final Form form, final int httpStatus,
        // final String responseText) {
        // presenter.onSubmitFailed(httpStatus, responseText);
        // }
        // });
        // super.add(dialogInfoLabel);
        // file = new TextField("File", inputId);
        // final EventCallback keyListener = new EventCallback() {
        // @Override
        // public void execute(final EventObject e) {
        // // setEnableFileField();
        // }
        // };
        // file.addKeyPressListener(keyListener);
        // file.setId(inputId);
        // file.setInputType("file");
        // super.add(file);
        // final FieldListenerAdapter changeListener = new
        // FieldListenerAdapter() {
        // @Override
        // public void onChange(final Field field, final Object newVal, final
        // Object oldVal) {
        // NotifyUser.info("change");
        // // setEnableFileField();
        // }
        // };
        // // Don't works:
        // file.addListener(changeListener);
        // setId(panelId);
        //
        // sendButton.setId(buttonId);
        // super.addButton(sendButton);

        dialogInfoLabel = new Label();
        dialogInfoLabel.setWordWrap(true);

        form.setEncoding(FormPanel.ENCODING_MULTIPART);
        form.setMethod(FormPanel.METHOD_POST);
        form.setAction(GWT.getModuleBaseURL() + ICON_UPLD_SERVLET);

        userhashField = new Hidden(FileConstants.HASH, FileConstants.HASH);
        tokenField = new Hidden(FileConstants.TOKEN, FileConstants.TOKEN);

        final VerticalPanel holder = new VerticalPanel();

        fileUpload.setName("upload");
        holder.add(dialogInfoLabel);
        holder.add(fileUpload);
        holder.add(userhashField);
        holder.add(tokenField);
        holder.add(new Button(i18n.t("Send"), new ClickHandler() {
            @Override
            public void onClick(final ClickEvent event) {
                Log.info("You selected: " + fileUpload.getFilename(), null);
                form.submit();
            }
        }));

        form.addSubmitHandler(new FormPanel.SubmitHandler() {
            @Override
            public void onSubmit(final SubmitEvent event) {
                // final String filename = file.getValueAsString();
                // if (filename != null && filename.length() > 0) {
                // getForm().submit();
                // }

                if (!"".equalsIgnoreCase(fileUpload.getFilename())) {
                    GWT.log("UPLOADING FILE????", null);
                    // NOW WHAT????
                } else {
                    event.cancel(); // cancel the event
                }

            }
        });

        form.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
            @Override
            public void onSubmitComplete(final SubmitCompleteEvent event) {
                CurrentLogoChangedEvent.fire(eventBus);
            }
        });
        form.add(holder);
        initWidget(form);
        setHeight(String.valueOf(EntityOptionsView.HEIGHT));
        setWidth(String.valueOf(EntityOptionsView.WIDTH));
    }

    @Override
    public OnAcceptCallback getOnSubmit() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IsWidget getTabTitle() {
        return tabTitle;
    }

    public void reset() {
        form.reset();
    }

    @Override
    public void setNormalGroupsLabels() {
        dialogInfoLabel.setText(i18n.t("Select an image in your computer as the logo for this group. "
                + "For best results use a [%d]x[%d] pixel image. We will automatically resize bigger images.",
                FileConstants.LOGO_DEF_HEIGHT, FileConstants.LOGO_DEF_HEIGHT)
                + TextUtils.brbr());
        tabTitle.setText(CoreMessages.ENT_LOGO_SELECTOR_NORMAL_TITLE);
    }

    @Override
    public void setPersonalGroupsLabels() {
        dialogInfoLabel.setText(i18n.t("Select an image in your computer as your avatar. "
                + "For best results use a [%d]x[%d] pixel image. We will automatically resize bigger images.",
                FileConstants.LOGO_DEF_HEIGHT, FileConstants.LOGO_DEF_HEIGHT)
                + "<br/><br/>");
        tabTitle.setText(CoreMessages.ENT_LOGO_SELECTOR_PERSON_TITLE);
    }

    @Override
    public void setUploadParams(final String userHash, final String token) {
        userhashField.setValue(userHash);
        tokenField.setValue(token);
    }

}
