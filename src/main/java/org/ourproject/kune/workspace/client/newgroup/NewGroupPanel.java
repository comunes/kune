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
 */
package org.ourproject.kune.workspace.client.newgroup;

import org.ourproject.kune.platf.client.dto.LicenseDTO;
import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.platf.client.ui.KuneUiUtils;
import org.ourproject.kune.platf.client.ui.WindowUtils;
import org.ourproject.kune.platf.client.ui.dialogs.BasicDialogExtended;
import org.ourproject.kune.platf.client.ui.dialogs.DefaultFormUtils;
import org.ourproject.kune.platf.client.ui.dialogs.MessageToolbar;
import org.ourproject.kune.workspace.client.WorkspaceMessages;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.licensewizard.LicenseWizard;

import com.calclab.suco.client.ioc.Provider;
import com.calclab.suco.client.listener.Listener;
import com.calclab.suco.client.listener.Listener0;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Position;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.PaddedPanel;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.ToolTip;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.form.FieldSet;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.form.Label;
import com.gwtext.client.widgets.form.Radio;
import com.gwtext.client.widgets.form.TextArea;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.layout.HorizontalLayout;

public class NewGroupPanel extends BasicDialogExtended implements NewGroupView {
    private static final String MARGIN_LEFT_105PX = "margin-left: 105px";
    private static final int LABEL_WIDTH = 100;
    public static final String SHORTNAME_FIELD = "k-ngp-short_name";
    public static final String LONGNAME_FIELD = "k-ngp-long_name";
    public static final String PUBLICDESC_FIELD = "k-ngp-public_desc";
    public static final String TYPEOFGROUP_FIELD = "k-ngp-type_of_group";
    public static final String PROJ_GROUP_TYPE_ID = "k-ngp-type_of_group_proj";
    public static final String ORG_GROUP_TYPE_ID = "k-ngp-type_of_group_org";
    public static final String COMM_GROUP_TYPE_ID = "k-ngp-type_of_group_comm";
    public static final String TAGS_FIELD = "k-ngp-tags";
    public static final String NEWGROUP_WIZARD = "k-ngp-wiz";
    public static final String REGISTER_BUTTON = "k-ngp-finish-bt";
    public static final String CANCEL_BUTTON = "k-ngp-cancel-bt";
    public static final String ERROR_MSG_BAR = "k-ngp-error-mb";
    private static final int BIG_FIELD_WIDTH = 280;

    private final FormPanel form;
    private Radio projectRadio;
    private Radio orgRadio;
    private Radio communityRadio;

    private TextField shortNameField;
    private TextField longNameField;
    private TextArea publicDescField;
    private TextField tags;
    private final MessageToolbar messageErrorBar;
    private final Provider<LicenseWizard> licenseWizard;
    private final I18nUITranslationService i18n;
    private Image licenseImage;
    private LicenseDTO license;

    public NewGroupPanel(final NewGroupPresenter presenter, final I18nUITranslationService i18n,
            final Provider<LicenseWizard> licenseWizard, Images img) {
        super(NEWGROUP_WIZARD, WorkspaceMessages.REGISTER_A_NEW_GROUP_TITLE, true, true, 450, 430, "k-newgroup-icon",
                i18n.t("Cancel"), CANCEL_BUTTON, i18n.t("Register"), REGISTER_BUTTON, new Listener0() {
                    public void onEvent() {
                        presenter.onCancel();
                    }
                }, new Listener0() {
                    public void onEvent() {
                        presenter.onRegister();
                    }
                }, 0);
        this.i18n = i18n;
        this.licenseWizard = licenseWizard;
        Field.setMsgTarget("side");
        form = createNewGroupInitialDataForm(presenter);

        messageErrorBar = new MessageToolbar(img, ERROR_MSG_BAR);
        super.setBottomToolbar(messageErrorBar.getToolbar());

        super.add(form);
    }

    public void clearData() {
        form.getForm().reset();
        shortNameField.focus(false);
    }

    public LicenseDTO getLicense() {
        return license;
    }

    public String getLongName() {
        return longNameField.getValueAsString();
    }

    public String getPublicDesc() {
        return publicDescField.getValueAsString();
    }

    public String getShortName() {
        return shortNameField.getValueAsString();
    }

    public String getTags() {
        return tags.getRawValue();
    }

    public void hideMessage() {
        messageErrorBar.hideErrorMessage();
    }

    public boolean isCommunity() {
        return communityRadio.getValue();
    }

    public boolean isFormValid() {
        return form.getForm().isValid();
    }

    public boolean isOrganization() {
        return orgRadio.getValue();
    }

    public boolean isProject() {
        return projectRadio.getValue();
    }

    public void maskProcessing() {
        mask(i18n.t("Processing"));
    }

    public void setLicense(LicenseDTO license) {
        this.license = license;
        licenseImage.setUrl(license.getImageUrl());
        KuneUiUtils.setQuickTip(licenseImage, license.getLongName());
    }

    public void setMessage(final String message, final SiteErrorType type) {
        messageErrorBar.setErrorMessage(message, type);
    }

    private FormPanel createNewGroupInitialDataForm(final NewGroupPresenter presenter) {
        final FormPanel form = new FormPanel();
        form.setFrame(true);
        form.setPaddings(10);
        form.setBorder(false);
        form.setWidth(420);
        form.setLabelWidth(LABEL_WIDTH);
        form.setLabelAlign(Position.RIGHT);
        form.setButtonAlign(Position.RIGHT);

        final Label intro = new Label();
        intro.setHtml(i18n.t("Please fill this form to register a new group:") + DefaultFormUtils.brbr());
        form.add(intro);

        shortNameField = new TextField();
        shortNameField.setTabIndex(1);
        shortNameField.setFieldLabel(i18n.t("Short name"));
        shortNameField.setName(SHORTNAME_FIELD);
        shortNameField.setWidth(175);
        shortNameField.setMinLength(3);
        shortNameField.setMaxLength(15);
        shortNameField.setAllowBlank(false);
        shortNameField.setRegex("^[a-z0-9_\\-]+$");
        shortNameField.setMinLengthText(i18n.t(WorkspaceMessages.FIELD_MUST_BE_BETWEEN_3_AND_15));
        shortNameField.setMaxLengthText(i18n.t(WorkspaceMessages.FIELD_MUST_BE_BETWEEN_3_AND_15));
        shortNameField.setRegexText(i18n.t(WorkspaceMessages.FIELD_MUST_BE_BETWEEN_3_AND_15));
        shortNameField.setValidationDelay(1000);

        form.add(shortNameField);

        longNameField = new TextField();
        longNameField.setTabIndex(2);
        longNameField.setFieldLabel(i18n.t("Long name"));
        longNameField.setName(LONGNAME_FIELD);
        longNameField.setWidth(BIG_FIELD_WIDTH);
        longNameField.setAllowBlank(false);
        longNameField.setMinLength(3);
        longNameField.setMaxLength(50);
        longNameField.setValidationDelay(1000);
        form.add(longNameField);

        publicDescField = new TextArea();
        publicDescField.setTabIndex(3);
        publicDescField.setFieldLabel(i18n.t("Public description"));
        publicDescField.setName(PUBLICDESC_FIELD);
        publicDescField.setWidth(BIG_FIELD_WIDTH);
        publicDescField.setAllowBlank(false);
        publicDescField.setMinLength(10);
        publicDescField.setMaxLength(255);
        publicDescField.setValidationDelay(1000);
        form.add(publicDescField);

        tags = new TextField();
        tags.setTabIndex(4);
        tags.setFieldLabel(i18n.t("Group tags"));
        tags.setName(TAGS_FIELD);
        tags.setWidth(BIG_FIELD_WIDTH);
        tags.setAllowBlank(false);
        final ToolTip fieldToolTip = new ToolTip(
                i18n.t("Please, write some keywords for this group (separated with spaces)."));
        fieldToolTip.applyTo(tags);
        tags.setValidationDelay(1000);
        form.add(tags);

        licenseImage = new Image("images/lic/bysa80x15.png");
        licenseImage.addClickListener(new ClickListener() {
            public void onClick(Widget arg0) {
                WindowUtils.open(license.getUrl());
            }
        });
        licenseImage.addStyleName("kune-pointer");
        Panel licenseImagePanel = new Panel();
        // licenseImagePanel.setLayout(new FitLayout());
        // licenseImagePanel.setWidth(80);
        licenseImagePanel.add(licenseImage);

        Button changeLicenseButton = new Button(i18n.t("Change"));
        changeLicenseButton.addListener(new ButtonListenerAdapter() {
            @Override
            public void onClick(Button button, EventObject e) {
                licenseWizard.get().start(new Listener<LicenseDTO>() {
                    public void onEvent(LicenseDTO license) {
                        setLicense(license);
                    }
                });
            }
        });
        changeLicenseButton.addClass("kune-Margin-Medium-trbl");

        Label licenseLabel = new Label();
        licenseLabel.setHtml(i18n.t("Default license for this group:") + DefaultFormUtils.br());
        form.add(licenseLabel);
        licenseLabel.setStyle(MARGIN_LEFT_105PX);

        Panel licPanel = new Panel();
        licPanel.setBorder(false);
        licPanel.setLayout(new HorizontalLayout(5));
        licPanel.add(licenseImagePanel);
        licPanel.add(changeLicenseButton);
        form.add(new PaddedPanel(licPanel, 0, 105, 0, 0));

        final FieldSet groupTypeFieldSet = new FieldSet(i18n.t("Group type"));
        groupTypeFieldSet.setStyle(MARGIN_LEFT_105PX);
        groupTypeFieldSet.setWidth(BIG_FIELD_WIDTH);
        groupTypeFieldSet.setFrame(false);
        groupTypeFieldSet.setCollapsible(false);
        groupTypeFieldSet.setAutoHeight(true);

        form.add(groupTypeFieldSet);

        projectRadio = DefaultFormUtils.createRadio(groupTypeFieldSet, i18n.t("Project"), TYPEOFGROUP_FIELD,
                i18n.t("A project is a kind of group in which new members joining "
                        + "is moderated by the project administrators. "
                        + "An administrator is the person who creates the project "
                        + "and other people she/he chooses to be administrator as well."), PROJ_GROUP_TYPE_ID);
        projectRadio.setTabIndex(5);
        projectRadio.setChecked(true);

        orgRadio = DefaultFormUtils.createRadio(groupTypeFieldSet, i18n.t("Organization"), TYPEOFGROUP_FIELD,
                i18n.t("An organization works as a project, " + "but organizations must be a legal entity."),
                ORG_GROUP_TYPE_ID);
        orgRadio.setTabIndex(6);

        communityRadio = DefaultFormUtils.createRadio(groupTypeFieldSet, i18n.t("Community"), TYPEOFGROUP_FIELD,
                i18n.t("Communities are social groups of persons "
                        + "with shared interests, which are open to new members "
                        + "(for instance the environmental community or the LGBT community). "
                        + "They rarely are a legal entity."), COMM_GROUP_TYPE_ID);
        communityRadio.setTabIndex(7);

        return form;
    }
}
