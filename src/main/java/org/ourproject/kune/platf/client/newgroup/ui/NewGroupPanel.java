/*
 *
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.platf.client.newgroup.ui;

import org.ourproject.kune.platf.client.dto.LicenseDTO;
import org.ourproject.kune.platf.client.license.LicenseChoose;
import org.ourproject.kune.platf.client.license.LicenseChoosePanel;
import org.ourproject.kune.platf.client.newgroup.NewGroupPresenter;
import org.ourproject.kune.platf.client.newgroup.NewGroupView;
import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.ui.KuneStringUtils;
import org.ourproject.kune.platf.client.ui.KuneUiUtils;
import org.ourproject.kune.platf.client.ui.dialogs.WizardDialog;
import org.ourproject.kune.sitebar.client.SiteBarFactory;
import org.ourproject.kune.sitebar.client.msg.SiteMessage;
import org.ourproject.kune.sitebar.client.msg.SiteMessagePanel;
import org.ourproject.kune.workspace.client.ui.form.WizardListener;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.core.Position;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.form.FieldSet;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.form.Radio;
import com.gwtext.client.widgets.form.TextArea;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.form.event.TextFieldListenerAdapter;
import com.gwtext.client.widgets.layout.FitLayout;

public class NewGroupPanel extends WizardDialog implements NewGroupView {
    private static final AbstractImagePrototype INFO_IMAGE = Images.App.getInstance().info();
    private static final String MUST_BE_BETWEEN_3_AND_15 = "Must be between 3 and 15 lowercase characters. Can only contain characters, numbers, and dashes";
    private static final String SHORTNAME_FIELD = "short_name";
    private static final String LONGNAME_FIELD = "long_name";
    private static final String PUBLICDESC_FIELD = "public_desc";
    private static final String TYPEOFGROUP_FIELD = "type_of_group";

    private final FormPanel newGroupInitialDataForm;
    private Radio projectRadio;
    private Radio orgRadio;
    private Radio communityRadio;
    private Radio orphanedProjectRadio;
    private TextField shortNameField;
    private TextField longNameField;
    private TextArea publicDescField;
    private final DeckPanel deck;
    private LicenseChoose licenseChoosePanel;
    private final SiteMessagePanel messagesPanel;

    public NewGroupPanel(final NewGroupPresenter presenter) {
        super(Kune.I18N.t("Register a new Group"), true, false, 460, 480, new WizardListener() {
            public void onBack() {
                presenter.onBack();
            }

            public void onCancel() {
                presenter.onCancel();
            }

            public void onFinish() {
                presenter.onFinish();
            }

            public void onNext() {
                presenter.onNext();
            }

            public void onClose() {
                presenter.onClose();
            }
        });
        Field.setMsgTarget("side");
        Panel centerPanel = new Panel();
        centerPanel.setLayout(new FitLayout());
        deck = new DeckPanel();
        newGroupInitialDataForm = createNewGroupInitialDataForm(presenter);
        createChooseLicensePanel();
        VerticalPanel newGroupInitialDataVP = new VerticalPanel();
        HorizontalPanel newGroupInitialDataHP = new HorizontalPanel();
        VerticalPanel chooseLicenseVP = new VerticalPanel();
        HorizontalPanel chooseLicenseHP = new HorizontalPanel();
        Images img = Images.App.getInstance();
        newGroupInitialDataHP.add(img.step1().createImage());
        Label step1Label = new Label(Kune.I18N
                .t("Please fill this form and follow the next steps to register a new group:"));
        newGroupInitialDataHP.add(step1Label);
        newGroupInitialDataVP.add(newGroupInitialDataHP);
        newGroupInitialDataVP.add(newGroupInitialDataForm);
        chooseLicenseHP.add(img.step2().createImage());
        HTML step2Label = new HTML(Kune.I18N.t("Select a license to share your group contents with other people. "
                + "We recomend [%s] licenses for practical works.", KuneStringUtils.generateHtmlLink(
                "http://en.wikipedia.org/wiki/Copyleft", "copyleft")));
        chooseLicenseHP.add(step2Label);
        Label licenseTypeLabel = new Label(Kune.I18N.t("Choose a license type:"));
        chooseLicenseVP.add(chooseLicenseHP);
        chooseLicenseVP.add(licenseTypeLabel);

        newGroupInitialDataHP.addStyleName("kune-Margin-Medium-b");
        step1Label.addStyleName("kune-Margin-Large-l");
        step2Label.addStyleName("kune-Margin-Large-l");
        step1Label.addStyleName("kune-Margin-Medium-b");
        step2Label.addStyleName("kune-Margin-Medium-b");

        messagesPanel = new SiteMessagePanel(null, false);
        messagesPanel.setWidth("425");
        messagesPanel.setMessage("", SiteMessage.INFO, SiteMessage.ERROR);
        newGroupInitialDataVP.add(messagesPanel);

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
        super.setFinishText(Kune.I18N.t("Register"));
    }

    public void mask(final String message) {
        super.mask(message);
    }

    public void maskProcessing() {
        super.maskProcessing();
    }

    public void unMask() {
        super.unMask();
    }

    public boolean isFormValid() {
        return newGroupInitialDataForm.getForm().isValid();
    }

    public void clearData() {
        deck.showWidget(0);
        newGroupInitialDataForm.getForm().reset();
        ((LicenseChoosePanel) licenseChoosePanel.getView()).reset();
        showNewGroupInitialDataForm();
        initBottomButtons();
        shortNameField.focus(false);
    }

    public String getShortName() {
        return shortNameField.getValueAsString();
    }

    public String getLongName() {
        return longNameField.getValueAsString();
    }

    public String getPublicDesc() {
        return publicDescField.getValueAsString();
    }

    public boolean isProject() {
        return projectRadio.getValue();
    }

    public boolean isOrphanedProject() {
        return orphanedProjectRadio.getValue();
    }

    public boolean isOrganization() {
        return orgRadio.getValue();
    }

    public boolean isCommunity() {
        return communityRadio.getValue();
    }

    public void showNewGroupInitialDataForm() {
        deck.showWidget(0);
    }

    public LicenseDTO getLicense() {
        return licenseChoosePanel.getLicense();
    }

    public void showLicenseForm() {
        deck.showWidget(1);
    }

    public void hideMessage() {
        messagesPanel.hide();
    }

    public void setMessage(final String message, final int type) {
        messagesPanel.setMessage(message, type, type);
        messagesPanel.show();
    }

    private void initBottomButtons() {
        super.setEnabledBackButton(false);
        super.setEnabledFinishButton(false);
        super.setEnabledNextButton(true);
    }

    private FormPanel createNewGroupInitialDataForm(final NewGroupPresenter presenter) {
        FormPanel form = new FormPanel();
        form.setBorder(false);
        form.setWidth(420);
        form.setLabelWidth(100);
        form.setLabelAlign(Position.RIGHT);
        form.setButtonAlign(Position.RIGHT);

        shortNameField = new TextField();
        shortNameField.setFieldLabel(Kune.I18N.t("Short name"));
        shortNameField.

        setName(SHORTNAME_FIELD);
        shortNameField.setWidth(175);
        shortNameField.setMinLength(3);
        shortNameField.setMaxLength(15);
        shortNameField.setAllowBlank(false);
        shortNameField.setRegex("^[a-z0-9_\\-]+$");
        shortNameField.setMinLengthText(Kune.I18N.t(MUST_BE_BETWEEN_3_AND_15));
        shortNameField.setMaxLengthText(Kune.I18N.t(MUST_BE_BETWEEN_3_AND_15));
        shortNameField.setRegexText(Kune.I18N.t(MUST_BE_BETWEEN_3_AND_15));
        shortNameField.setValidationDelay(1000);

        form.add(shortNameField);

        longNameField = new TextField();
        longNameField.setFieldLabel(Kune.I18N.t("Long name"));
        longNameField.setName(LONGNAME_FIELD);
        longNameField.setWidth(300);
        longNameField.setAllowBlank(false);

        longNameField.setMinLength(3);
        longNameField.setMaxLength(50);
        longNameField.setValidationDelay(1000);
        form.add(longNameField);

        publicDescField = new TextArea();
        publicDescField.setFieldLabel(Kune.I18N.t("Public description"));
        publicDescField.setName(PUBLICDESC_FIELD);
        publicDescField.setWidth(300);
        publicDescField.setAllowBlank(false);
        publicDescField.setMinLength(10);
        publicDescField.setMaxLength(255);
        publicDescField.setValidationDelay(1000);

        form.add(publicDescField);

        FieldSet groupTypeFieldSet = new FieldSet(Kune.I18N.t("Type of group"));
        groupTypeFieldSet.setStyle("margin-left: 105px");
        groupTypeFieldSet.setCollapsible(true);
        form.add(groupTypeFieldSet);

        projectRadio = new Radio();
        createRadio(groupTypeFieldSet, projectRadio, "Project",
                "A project is a kind of group in which new members inclusion "
                        + "is moderated by the project administrators. "
                        + "An administrator is the person who creates the project "
                        + "and other people she/he choose in the future as administrator too.");
        projectRadio.setChecked(true);

        orgRadio = new Radio();
        createRadio(groupTypeFieldSet, orgRadio, "Organization", "An organization is like a project, "
                + "but organizations must be a legal entity.");

        communityRadio = new Radio();
        createRadio(groupTypeFieldSet, communityRadio, "Community", "Communities are social group of persons "
                + "with shared interests and they are open to new members "
                + "(for instance the environmental community or the LGBT community). "
                + "Normally they aren't a legal entity.");

        orphanedProjectRadio = new Radio();
        createRadio(groupTypeFieldSet, orphanedProjectRadio, "Orphaned Project",
                "If you have an idea but you don't have " + "capacity/possibilities/resources to work on it, "
                        + "just register a orphaned project, and permit others to work and develop it.");

        groupTypeFieldSet.setCollapsible(false);

        shortNameField.addListener(new TextFieldListenerAdapter() {
            public void onChange(final Field field, final Object newVal, final Object oldVal) {
                presenter.onChange();
            }
        });

        longNameField.addListener(new TextFieldListenerAdapter() {
            public void onChange(final Field field, final Object newVal, final Object oldVal) {
                presenter.onChange();
            }
        });

        publicDescField.addListener(new TextFieldListenerAdapter() {
            public void onChange(final Field field, final Object newVal, final Object oldVal) {
                presenter.onChange();
            }
        });

        return form;
    }

    private void createRadio(final FieldSet fieldSet, final Radio radio, final String radioLabel, final String radioTip) {
        radio.setName(TYPEOFGROUP_FIELD);
        radio.setBoxLabel(KuneUiUtils
                .genQuickTipLabel(Kune.I18N.t(radioLabel), null, Kune.I18N.t(radioTip), INFO_IMAGE));
        radio.setAutoCreate(true);
        radio.setHideLabel(true);
        fieldSet.add(radio);
    }

    private void createChooseLicensePanel() {
        licenseChoosePanel = SiteBarFactory.createLicenseChoose();
    }
}
