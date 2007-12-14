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

import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.dto.LicenseDTO;
import org.ourproject.kune.platf.client.license.LicenseChoose;
import org.ourproject.kune.platf.client.license.LicenseChoosePanel;
import org.ourproject.kune.platf.client.newgroup.NewGroupPresenter;
import org.ourproject.kune.platf.client.newgroup.NewGroupView;
import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.platf.client.ui.KuneStringUtils;
import org.ourproject.kune.platf.client.ui.dialogs.WizardDialog;
import org.ourproject.kune.sitebar.client.SiteBarFactory;
import org.ourproject.kune.sitebar.client.bar.SiteBarTrans;
import org.ourproject.kune.sitebar.client.msg.SiteMessage;
import org.ourproject.kune.sitebar.client.msg.SiteMessagePanel;
import org.ourproject.kune.sitebar.client.services.Translate;
import org.ourproject.kune.workspace.client.ui.form.WizardListener;

import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.core.EventCallback;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Position;
import com.gwtext.client.widgets.form.CheckboxConfig;
import com.gwtext.client.widgets.form.FieldSetConfig;
import com.gwtext.client.widgets.form.Form;
import com.gwtext.client.widgets.form.FormConfig;
import com.gwtext.client.widgets.form.Radio;
import com.gwtext.client.widgets.form.TextArea;
import com.gwtext.client.widgets.form.TextAreaConfig;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.form.TextFieldConfig;

public class NewGroupPanel extends WizardDialog implements NewGroupView {
    private static final String MUST_BE_BETWEEN_3_AND_15 = "Must be between 3 and 15 lowercase characters. Can only contain characters, numbers, and dashes";

    private static final Translate t = SiteBarTrans.getInstance().t;

    private static final String SHORTNAME_FIELD = "short_name";
    private static final String LONGNAME_FIELD = "long_name";
    private static final String PUBLICDESC_FIELD = "public_desc";
    private static final String TYPEOFGROUP_FIELD = "type_of_group";
    private final Form newGroupInitialDataForm;
    private Radio projectRadio;
    private Radio orgRadio;
    private Radio communityRadio;

    private TextField shortNameField;
    private TextField longNameField;
    private TextArea publicDescField;
    private final DeckPanel deck;
    private LicenseChoose licenseChoosePanel;

    private final SiteMessagePanel messagesPanel;

    public NewGroupPanel(final NewGroupPresenter presenter) {
        super(Kune.I18N.t("Register a new Group"), true, false, 470, 440, new WizardListener() {
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
        HTML step2Label = new HTML(
                Kune.I18N
                        .t(
                                "Select a license to share your group contents with other people. We recomend [%s] licenses for practical works.",
                                KuneStringUtils.generateHtmlLink("http://en.wikipedia.org/wiki/Copyleft", "copyleft")));
        chooseLicenseHP.add(step2Label);
        Label licenseTypeLabel = new Label("Choose a license type:");
        chooseLicenseVP.add(chooseLicenseHP);
        chooseLicenseVP.add(licenseTypeLabel);

        newGroupInitialDataHP.addStyleName("kune-Margin-Medium-b");
        newGroupInitialDataHP.addStyleName("kune-Margin-Medium-b");
        step1Label.addStyleName("kune-Margin-Large-l");
        step2Label.addStyleName("kune-Margin-Large-l");

        messagesPanel = new SiteMessagePanel(presenter, false);
        messagesPanel.setWidth("425");
        messagesPanel.setMessage("", SiteMessage.INFO, SiteMessage.ERROR);
        newGroupInitialDataVP.add(messagesPanel);

        chooseLicenseVP.add((Widget) licenseChoosePanel.getView());
        deck.add(newGroupInitialDataVP);
        deck.add(chooseLicenseVP);
        super.add(deck);
        deck.showWidget(0);
        initBottomButtons();
        // newGroupInitialDataVP.addStyleName("kune-Default-Form");
        deck.addStyleName("kune-Default-Form");
        licenseTypeLabel.addStyleName("kune-License-CC-Header");
        newGroupInitialDataVP.setHeight("10"); // Ext set this to 100% ...
        chooseLicenseVP.setHeight("10"); // (same here)
        super.setFinishText(t.Register());
    }

    public boolean isFormValid() {
        return newGroupInitialDataForm.isValid();
    }

    public void clearData() {
        deck.showWidget(0);
        newGroupInitialDataForm.reset();
        ((LicenseChoosePanel) licenseChoosePanel.getView()).reset();
        showNewGroupInitialDataForm();
        initBottomButtons();
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

    private Form createNewGroupInitialDataForm(final NewGroupPresenter presenter) {
        Form form = new Form(new FormConfig() {
            {
                setWidth(400);
                setLabelWidth(100);
                setLabelAlign(Position.RIGHT);
                setButtonAlign(Position.RIGHT);
            }
        });

        shortNameField = new TextField(new TextFieldConfig() {
            {
                setFieldLabel(t.ShortName());
                setName(SHORTNAME_FIELD);
                setWidth(175);
                setMinLength(3);
                setMaxLength(15);
                setAllowBlank(false);
                setMsgTarget("side");
                setRegex("^[a-z0-9_\\-]+$");
                setMinLengthText(Kune.I18N.t(MUST_BE_BETWEEN_3_AND_15));
                setMaxLengthText(Kune.I18N.t(MUST_BE_BETWEEN_3_AND_15));
                setRegexText(Kune.I18N.t(MUST_BE_BETWEEN_3_AND_15));
            }
        });
        form.add(shortNameField);

        longNameField = new TextField(new TextFieldConfig() {
            {
                setFieldLabel(t.LongName());
                setName(LONGNAME_FIELD);
                setWidth(300);
                setAllowBlank(false);
                setMsgTarget("side");
                setMinLength(3);
                setMaxLength(50);
            }
        });
        form.add(longNameField);

        publicDescField = new TextArea(new TextAreaConfig() {
            {
                setFieldLabel(t.PublicDescription());
                setName(PUBLICDESC_FIELD);
                setWidth(300);
                setAllowBlank(false);
                setMsgTarget("side");
                setMinLength(10);
                setMaxLength(255);
            }
        });
        form.add(publicDescField);

        form.fieldset(new FieldSetConfig() {
            {
                setLegend(t.TypeOfGroup());
                setHideLabels(true);
                setStyle("margin-left: 105px");
            }
        });

        projectRadio = new Radio(new CheckboxConfig() {
            {
                setName(TYPEOFGROUP_FIELD);
                setBoxLabel(t.Project());
                setAutoCreate(true);
                setChecked(true);
            }
        });
        form.add(projectRadio);

        orgRadio = new Radio(new CheckboxConfig() {
            {
                setName(TYPEOFGROUP_FIELD);
                setBoxLabel(t.Organization());
                setAutoCreate(true);
            }
        });
        form.add(orgRadio);

        communityRadio = new Radio(new CheckboxConfig() {
            {
                setName(TYPEOFGROUP_FIELD);
                setBoxLabel(t.Community());
                setAutoCreate(true);
            }
        });
        form.add(communityRadio);
        form.end();

        form.end();
        form.render();

        shortNameField.getEl().addListener("keypress", new EventCallback() {
            public void execute(final EventObject e) {
                presenter.onChange();
            }
        });

        longNameField.getEl().addListener("keypress", new EventCallback() {
            public void execute(final EventObject e) {
                presenter.onChange();
            }
        });

        publicDescField.getEl().addListener("keypress", new EventCallback() {
            public void execute(final EventObject e) {
                presenter.onChange();
            }
        });

        return form;
    }

    private void createChooseLicensePanel() {
        licenseChoosePanel = SiteBarFactory.createLicenseChoose();

    }
}
