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
import org.ourproject.kune.platf.client.ui.TextUtils;
import org.ourproject.kune.platf.client.ui.dialogs.MessageToolbar;
import org.ourproject.kune.platf.client.ui.dialogs.WizardDialog;
import org.ourproject.kune.platf.client.ui.dialogs.WizardListener;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.licensechoose.LicenseChoose;
import org.ourproject.kune.workspace.client.licensechoose.LicenseChoosePanel;

import com.calclab.suco.client.ioc.Provider;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.core.Position;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.ToolTip;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.form.FieldSet;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.form.Radio;
import com.gwtext.client.widgets.form.TextArea;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.layout.FitLayout;

public class NewGroupPanel extends WizardDialog implements NewGroupView {
    public static final String REGISTER_A_NEW_GROUP_TITLE = "Register a new Group";
    public static final String MUST_BE_BETWEEN_3_AND_15 = "Must be between 3 and 15 lowercase characters. Can only contain characters, numbers, and dashes";
    public static final String SHORTNAME_FIELD = "k-ngp-short_name";
    public static final String LONGNAME_FIELD = "k-ngp-long_name";
    public static final String PUBLICDESC_FIELD = "k-ngp-public_desc";
    public static final String TYPEOFGROUP_FIELD = "k-ngp-type_of_group";
    public static final String PROJ_GROUP_TYPE_ID = "k-ngp-type_of_group_proj";
    public static final String ORG_GROUP_TYPE_ID = "k-ngp-type_of_group_org";
    public static final String COMM_GROUP_TYPE_ID = "k-ngp-type_of_group_comm";
    public static final String TAGS_FIELD = "tags";
    public static final String NEWGROUP_WIZARD = "k-ngp-wiz";
    public static final String CANCEL_BUTTON = "k-ngp-cancel-bt";
    public static final String CLOSE_BUTTON = "k-ngp-close-bt";
    public static final String FINISH_BUTTON = "k-ngp-finish-bt";
    public static final String NEXT_BUTTON = "k-ngp-next-bt";
    public static final String BACK_BUTTON = "k-ngp-back-bt";
    public static final String ERROR_MSG_BAR = "k-ngp-error-mb";

    private final FormPanel newGroupInitialDataForm;
    private Radio projectRadio;
    private Radio orgRadio;
    private Radio communityRadio;

    private TextField shortNameField;
    private TextField longNameField;
    private TextArea publicDescField;
    private final DeckPanel deck;
    private final LicenseChoose licenseChoosePanel;
    private final I18nUITranslationService i18n;
    private TextField tags;
    private final MessageToolbar messageErrorBar;

    public NewGroupPanel(final NewGroupPresenter presenter, final I18nUITranslationService i18n,
            final Provider<LicenseChoose> licenseChooseProvider, Images img) {
        super(i18n.t(REGISTER_A_NEW_GROUP_TITLE), true, false, 460, 480, new WizardListener() {
            public void onBack() {
                presenter.onBack();
            }

            public void onCancel() {
                presenter.onCancel();
            }

            public void onClose() {
                presenter.onClose();
            }

            public void onFinish() {
                presenter.onFinish();
            }

            public void onNext() {
                presenter.onNext();
            }
        }, i18n, NEWGROUP_WIZARD, BACK_BUTTON, NEXT_BUTTON, FINISH_BUTTON, CANCEL_BUTTON, CLOSE_BUTTON);
        this.i18n = i18n;
        Field.setMsgTarget("side");
        final Panel centerPanel = new Panel();
        centerPanel.setPaddings(10);
        centerPanel.setLayout(new FitLayout());
        deck = new DeckPanel();
        newGroupInitialDataForm = createNewGroupInitialDataForm(presenter);
        licenseChoosePanel = licenseChooseProvider.get();
        final VerticalPanel newGroupInitialDataVP = new VerticalPanel();
        final HorizontalPanel newGroupInitialDataHP = new HorizontalPanel();
        final VerticalPanel chooseLicenseVP = new VerticalPanel();
        final HorizontalPanel chooseLicenseHP = new HorizontalPanel();
        newGroupInitialDataHP.add(img.step1().createImage());
        final Label step1Label = new Label(
                i18n.t("Please fill this form and follow the next steps to register a new group:"));
        newGroupInitialDataHP.add(step1Label);
        newGroupInitialDataVP.add(newGroupInitialDataHP);
        newGroupInitialDataVP.add(newGroupInitialDataForm);
        chooseLicenseHP.add(img.step2().createImage());
        final HTML step2Label = new HTML(i18n.t("Select a license to share your group contents with other people. "
                + "We recomend [%s] licenses for practical works.", TextUtils.generateHtmlLink(
                "http://en.wikipedia.org/wiki/Copyleft", "copyleft")));
        chooseLicenseHP.add(step2Label);
        final Label licenseTypeLabel = new Label(i18n.t("Choose a license type:"));
        chooseLicenseVP.add(chooseLicenseHP);
        chooseLicenseVP.add(licenseTypeLabel);

        newGroupInitialDataHP.addStyleName("kune-Margin-Medium-b");
        step1Label.addStyleName("kune-Margin-Large-l");
        step2Label.addStyleName("kune-Margin-Large-l");
        step1Label.addStyleName("kune-Margin-Medium-b");
        step2Label.addStyleName("kune-Margin-Medium-b");

        messageErrorBar = new MessageToolbar(img, ERROR_MSG_BAR);
        super.setBottomToolbar(messageErrorBar.getToolbar());

        chooseLicenseVP.add((Widget) licenseChoosePanel.getView());
        deck.add(newGroupInitialDataVP);
        deck.add(chooseLicenseVP);
        centerPanel.add(deck);
        super.add(centerPanel);
        deck.showWidget(0);
        initBottomButtons();
        // newGroupInitialDataVP.addStyleName("kune-Default-Form");
        deck.addStyleName("kune-Default-Form");
        licenseTypeLabel.addStyleName("kune-License-CC-Header");
        newGroupInitialDataVP.setHeight("10"); // Ext set this to 100% ...
        chooseLicenseVP.setHeight("10"); // (same here)
        super.setFinishText(i18n.t("Register"));
    }

    public void clearData() {
        deck.showWidget(0);
        newGroupInitialDataForm.getForm().reset();
        ((LicenseChoosePanel) licenseChoosePanel.getView()).reset();
        showNewGroupInitialDataForm();
        initBottomButtons();
        shortNameField.focus(false);
    }

    public LicenseDTO getLicense() {
        return licenseChoosePanel.getLicense();
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
        return newGroupInitialDataForm.getForm().isValid();
    }

    public boolean isOrganization() {
        return orgRadio.getValue();
    }

    public boolean isProject() {
        return projectRadio.getValue();
    }

    public void setMessage(final String message, final SiteErrorType type) {
        messageErrorBar.setErrorMessage(message, type);
    }

    public void showLicenseForm() {
        deck.showWidget(1);
    }

    public void showNewGroupInitialDataForm() {
        deck.showWidget(0);
    }

    private FormPanel createNewGroupInitialDataForm(final NewGroupPresenter presenter) {
        final FormPanel form = new FormPanel();
        form.setBorder(false);
        form.setWidth(420);
        form.setLabelWidth(100);
        form.setLabelAlign(Position.RIGHT);
        form.setButtonAlign(Position.RIGHT);

        shortNameField = new TextField();
        shortNameField.setFieldLabel(i18n.t("Short name"));
        shortNameField.setName(SHORTNAME_FIELD);
        shortNameField.setWidth(175);
        shortNameField.setMinLength(3);
        shortNameField.setMaxLength(15);
        shortNameField.setAllowBlank(false);
        shortNameField.setRegex("^[a-z0-9_\\-]+$");
        shortNameField.setMinLengthText(i18n.t(MUST_BE_BETWEEN_3_AND_15));
        shortNameField.setMaxLengthText(i18n.t(MUST_BE_BETWEEN_3_AND_15));
        shortNameField.setRegexText(i18n.t(MUST_BE_BETWEEN_3_AND_15));
        shortNameField.setValidationDelay(1000);

        form.add(shortNameField);

        longNameField = new TextField();
        longNameField.setFieldLabel(i18n.t("Long name"));
        longNameField.setName(LONGNAME_FIELD);
        longNameField.setWidth(300);
        longNameField.setAllowBlank(false);

        longNameField.setMinLength(3);
        longNameField.setMaxLength(50);
        longNameField.setValidationDelay(1000);
        form.add(longNameField);

        publicDescField = new TextArea();
        publicDescField.setFieldLabel(i18n.t("Public description"));
        publicDescField.setName(PUBLICDESC_FIELD);
        publicDescField.setWidth(300);
        publicDescField.setAllowBlank(false);
        publicDescField.setMinLength(10);
        publicDescField.setMaxLength(255);
        publicDescField.setValidationDelay(1000);
        form.add(publicDescField);

        tags = new TextField();
        tags.setFieldLabel(i18n.t("Group tags"));
        tags.setName(TAGS_FIELD);
        tags.setWidth(300);
        tags.setAllowBlank(false);
        final ToolTip fieldToolTip = new ToolTip(i18n.t("Some words related to this group (separated with spaces)."));
        fieldToolTip.applyTo(tags);
        tags.setValidationDelay(1000);
        form.add(tags);

        final FieldSet groupTypeFieldSet = new FieldSet(i18n.t("Type of group"));
        groupTypeFieldSet.setStyle("margin-left: 105px");
        groupTypeFieldSet.setWidth(300);

        form.add(groupTypeFieldSet);

        projectRadio = new Radio();
        createRadio(groupTypeFieldSet, projectRadio, "Project",
                "A project is a kind of group in which new members inclusion "
                        + "is moderated by the project administrators. "
                        + "An administrator is the person who creates the project "
                        + "and other people she/he choose in the future as administrator too.", PROJ_GROUP_TYPE_ID);
        projectRadio.setChecked(true);

        orgRadio = new Radio();
        createRadio(groupTypeFieldSet, orgRadio, "Organization", "An organization is like a project, "
                + "but organizations must be a legal entity.", ORG_GROUP_TYPE_ID);

        communityRadio = new Radio();
        createRadio(groupTypeFieldSet, communityRadio, "Community", "Communities are social group of persons "
                + "with shared interests and they are open to new members "
                + "(for instance the environmental community or the LGBT community). "
                + "Normally they aren't a legal entity.", COMM_GROUP_TYPE_ID);

        groupTypeFieldSet.setCollapsible(false);

        return form;
    }

    private void createRadio(final FieldSet fieldSet, final Radio radio, final String radioLabel,
            final String radioTip, final String id) {
        radio.setName(TYPEOFGROUP_FIELD);
        radio.setBoxLabel(KuneUiUtils.genQuickTipLabel(i18n.t(radioLabel), null, i18n.t(radioTip)));
        radio.setAutoCreate(true);
        radio.setHideLabel(true);
        radio.setId(id);
        fieldSet.add(radio);

        ToolTip tooltip = new ToolTip();
        tooltip.setHtml(radioTip);
        tooltip.setWidth(250);
        tooltip.applyTo(radio);
    }

    private void initBottomButtons() {
        super.setEnabledBackButton(false);
        super.setEnabledFinishButton(false);
        super.setEnabledNextButton(true);
    }
}
