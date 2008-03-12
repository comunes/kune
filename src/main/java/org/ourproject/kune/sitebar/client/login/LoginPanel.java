/*
 *
 * Copyright (C) FIELD_DEF_WIDTH7 The kune development team (see CREDITS for details)
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

package org.ourproject.kune.sitebar.client.login;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.ui.dialogs.BasicDialog;
import org.ourproject.kune.sitebar.client.Site;
import org.ourproject.kune.sitebar.client.msg.SiteMessage;
import org.ourproject.kune.sitebar.client.msg.SiteMessagePanel;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Position;
import com.gwtext.client.core.RegionPosition;
import com.gwtext.client.data.SimpleStore;
import com.gwtext.client.data.Store;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.TabPanel;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.event.PanelListenerAdapter;
import com.gwtext.client.widgets.event.WindowListenerAdapter;
import com.gwtext.client.widgets.form.ComboBox;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.form.VType;
import com.gwtext.client.widgets.form.ValidationException;
import com.gwtext.client.widgets.form.Validator;
import com.gwtext.client.widgets.layout.AnchorLayoutData;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.BorderLayoutData;

public class LoginPanel implements LoginView, View {
    private static final int FIELD_DEF_WIDTH = 200;

    private static final String MUST_BE_BETWEEN_3_AND_15 = "Must be between 3 and 15 lowercase characters. Can only contain characters, numbers, and dashes";

    private static final String NICKOREMAIL_FIELD = "nickOrEmail";
    private static final String PASSWORD_FIELD = "password";
    private static final String NICK_FIELD = "nick";
    private static final String EMAIL_FIELD = "email";
    private static final String LONGNAME_FIELD = "long_name";
    private static final String PASSWORD_FIELD_DUP = "passwordDup";
    private static final String LANG_FIELD = "lang";
    private static final String COUNTRY_FIELD = "country";
    private static final String TIMEZONE_FIELD = "timezone";

    private TextField loginPassField;

    private TextField loginNickOrEmailField;

    private BasicDialog dialog;

    private final LoginPresenter presenter;

    private TextField shortNameRegField;

    private TextField longNameRegField;

    private TextField emailRegField;

    private TextField passwdRegField;

    private FormPanel signInForm;

    private FormPanel registerForm;

    private TextField passwdRegFieldDup;

    private SiteMessagePanel messagesSignInPanel;

    private ComboBox countryCombo;

    private ComboBox languageCombo;

    private ComboBox timezoneCombo;

    private BasicDialog welcomeDialog;

    private TabPanel centerPanel;

    private SiteMessagePanel messagesRegisterPanel;

    public LoginPanel(final LoginPresenter initialPresenter) {
        Field.setMsgTarget("side");
        this.presenter = initialPresenter;
        createPanel();
    }

    private void mask(final String message) {
        dialog.getEl().mask(message, "x-mask-loading");
    }

    public void maskProcessing() {
        mask(Kune.I18N.t("Processing"));
    }

    public void unMask() {
        dialog.getEl().unmask();
    }

    public void showWelcolmeDialog() {
        welcomeDialog = new BasicDialog(Kune.I18N.t("Welcome"), true, true, 400, 270);
        Button okButton = new Button(Kune.I18N.t("Ok"));
        okButton.addListener(new ButtonListenerAdapter() {
            public void onClick(final Button button, final EventObject e) {
                welcomeDialog.hide();
            }
        });
        String message = Kune.I18N.t("Thanks for registering") + "\n\n"
                + Kune.I18N.t("Now you can participate more actively in this site with other people and groups.")
                + "\n" + Kune.I18N.t("You can also use your personal space to publish contents.") + "\n\n"
                + Kune.I18N.t("Your email is not verified, please follow the instructions you will receive by email.");

        HTML messageHtml = new HTML(DOM.getInnerHTML((new Label(message)).getElement()).replaceAll("\n", "<br/>\n"));
        messageHtml.addStyleName("kune-Margin-20-trbl");
        welcomeDialog.add(messageHtml);
        welcomeDialog.add(okButton);
        welcomeDialog.show();
    }

    public boolean isSignInFormValid() {
        return signInForm.getForm().isValid();
    }

    public boolean isRegisterFormValid() {
        return registerForm.getForm().isValid();
    }

    public void reset() {
        DeferredCommand.addCommand(new Command() {
            public void execute() {
                loginPassField.reset();
                if (registerForm != null) {
                    registerForm.getForm().reset();
                }
            }
        });
    }

    public String getNickOrEmail() {
        return loginNickOrEmailField.getValueAsString();
    }

    public String getLoginPassword() {
        return loginPassField.getValueAsString();
    }

    public String getShortName() {
        return shortNameRegField.getValueAsString();
    }

    public String getEmail() {
        return emailRegField.getValueAsString();
    }

    public String getLongName() {
        return longNameRegField.getValueAsString();
    }

    public String getRegisterPassword() {
        return passwdRegField.getValueAsString();
    }

    public String getRegisterPasswordDup() {
        return passwdRegFieldDup.getValueAsString();
    }

    public String getLanguage() {
        return languageCombo.getValueAsString();
    }

    public String getCountry() {
        return countryCombo.getValueAsString();
    }

    public String getTimezone() {
        return timezoneCombo.getValueAsString();
    }

    public void setSignInMessage(final String message, final int type) {
        messagesSignInPanel.setMessage(message, type, type);
        messagesSignInPanel.show();
    }

    public void setRegisterMessage(final String message, final int type) {
        messagesRegisterPanel.setMessage(message, type, type);
        messagesRegisterPanel.show();
    }

    public void hideMessages() {
        messagesSignInPanel.hide();
        if (messagesRegisterPanel != null) {
            messagesRegisterPanel.hide();
        }
    }

    public void show() {
        centerPanel.activate(0);
        dialog.setVisible(true);
        dialog.show();
        Site.hideProgress();
        dialog.focus();
        loginNickOrEmailField.focus(false);
    }

    public void hide() {
        dialog.hide();
    }

    public void center() {
        dialog.center();
    }

    private void createPanel() {
        dialog = new BasicDialog(Kune.I18N.t("Sign in"), true, true, 380, 400);
        dialog.setCollapsible(false);
        dialog.setLayout(new BorderLayout());

        centerPanel = new TabPanel();
        centerPanel.setActiveTab(0);
        centerPanel.setClosable(false);

        final Panel signInPanel = new Panel(Kune.I18N.t("Sign in"));
        confPanel(signInPanel);
        signInForm = createSignInForm();
        signInForm.addStyleName("kune-Default-Form");
        signInPanel.add(signInForm, new AnchorLayoutData("100% 100%"));
        signInPanel.add(createNoAccountRegister(), new AnchorLayoutData("100% -20"));

        final Panel registerPanel = new Panel(Kune.I18N.t("Register"));
        confPanel(registerPanel);

        centerPanel.add(signInPanel, new AnchorLayoutData("100% 100%"));
        centerPanel.add(registerPanel, new AnchorLayoutData("100% 100%"));
        dialog.add(centerPanel, new BorderLayoutData(RegionPosition.CENTER));

        messagesSignInPanel = createMessagePanel(signInPanel);

        final Button signInBtn = new Button(Kune.I18N.t("Sign in"));
        signInBtn.addListener(new ButtonListenerAdapter() {
            public void onClick(final Button button, final EventObject e) {
                presenter.doLogin();
            }
        });
        dialog.addButton(signInBtn);

        final Button registerBtn = new Button(Kune.I18N.t("Register"));
        registerBtn.addListener(new ButtonListenerAdapter() {
            public void onClick(final Button button, final EventObject e) {
                presenter.doRegister();
            }
        });
        dialog.addButton(registerBtn);
        registerBtn.hide();

        Button cancel = new Button();
        dialog.addButton(cancel);
        cancel.setText(Kune.I18N.tWithNT("Cancel", "used in button"));
        cancel.addListener(new ButtonListenerAdapter() {
            public void onClick(final Button button, final EventObject e) {
                presenter.onCancel();
            }
        });

        signInPanel.addListener(new PanelListenerAdapter() {
            public void onActivate(final Panel panel) {
                dialog.setTitle(Kune.I18N.t("Sign in"));
                registerBtn.hide();
                signInBtn.show();
            }
        });

        registerPanel.addListener(new PanelListenerAdapter() {
            public void onActivate(final Panel panel) {
                if (registerForm == null) {
                    maskProcessing();
                    registerForm = createRegistrationForm(presenter.getCurrentLanguage().getCode());
                    registerForm.addStyleName("kune-Default-Form");
                    registerPanel.add(registerForm);
                    messagesRegisterPanel = createMessagePanel(registerPanel);
                    registerPanel.doLayout();
                    unMask();
                }
                dialog.setTitle(Kune.I18N.t("Register"));
                signInBtn.hide();
                registerBtn.show();
                // tab.getTextEl().highlight();
            }
        });

        dialog.addListener(new WindowListenerAdapter() {
            public void onHide(final Component component) {
                presenter.onClose();
            }
        });
    }

    private void confPanel(final Panel panel) {
        panel.setAutoScroll(true);
        panel.setPaddings(20);
        panel.setHeight("100%");
    }

    private SiteMessagePanel createMessagePanel(final Panel panel) {
        SiteMessagePanel siteMessagesPanel = new SiteMessagePanel(null, false);
        siteMessagesPanel.setMessage("", SiteMessage.INFO, SiteMessage.ERROR);
        // HP before
        Panel messagesPanelWrapper = new Panel();
        messagesPanelWrapper.setBorder(false);
        messagesPanelWrapper.setMargins(20, 0, 0, 0);
        messagesPanelWrapper.add(siteMessagesPanel);
        panel.add(messagesPanelWrapper);
        return siteMessagesPanel;
    }

    private FormPanel createSignInForm() {
        FormPanel form = createStandardForm();

        loginNickOrEmailField = new TextField();
        loginNickOrEmailField.setFieldLabel(Kune.I18N.t("Nickname or email"));
        loginNickOrEmailField.setName(NICKOREMAIL_FIELD);
        loginNickOrEmailField.setWidth(FIELD_DEF_WIDTH);
        loginNickOrEmailField.setAllowBlank(false);
        loginNickOrEmailField.setValidateOnBlur(false);
        form.add(loginNickOrEmailField);

        loginPassField = new TextField();
        loginPassField.setFieldLabel(Kune.I18N.t("Password"));
        loginPassField.setName(PASSWORD_FIELD);
        loginPassField.setWidth(FIELD_DEF_WIDTH);
        loginPassField.setPassword(true);
        loginPassField.setAllowBlank(false);
        form.add(loginPassField);

        return form;
    }

    private FormPanel createRegistrationForm(final String langCode) {
        FormPanel form = createStandardForm();

        shortNameRegField = new TextField();
        shortNameRegField.setFieldLabel(Kune.I18N.t("Nickname"));
        shortNameRegField.setName(NICK_FIELD);
        shortNameRegField.setWidth(FIELD_DEF_WIDTH);
        shortNameRegField.setAllowBlank(false);
        shortNameRegField.setMinLength(3);
        shortNameRegField.setMaxLength(15);
        shortNameRegField.setRegex("^[a-z0-9_\\-]+$");
        shortNameRegField.setMinLengthText(Kune.I18N.t(MUST_BE_BETWEEN_3_AND_15));
        shortNameRegField.setMaxLengthText(Kune.I18N.t(MUST_BE_BETWEEN_3_AND_15));
        shortNameRegField.setRegexText(Kune.I18N.t(MUST_BE_BETWEEN_3_AND_15));
        shortNameRegField.setValidationDelay(1000);
        form.add(shortNameRegField);

        longNameRegField = new TextField();
        longNameRegField.setFieldLabel(Kune.I18N.t("Full Name"));
        longNameRegField.setName(LONGNAME_FIELD);
        longNameRegField.setWidth(FIELD_DEF_WIDTH);
        longNameRegField.setAllowBlank(false);
        longNameRegField.setMinLength(3);
        longNameRegField.setMaxLength(50);
        longNameRegField.setValidationDelay(1000);
        form.add(longNameRegField);

        passwdRegField = new TextField();
        passwdRegField.setFieldLabel(Kune.I18N.t("Password"));
        passwdRegField.setName(PASSWORD_FIELD);
        passwdRegField.setPassword(true);
        passwdRegField.setAllowBlank(false);
        passwdRegField.setMaxLength(40);
        passwdRegField.setWidth(FIELD_DEF_WIDTH);
        passwdRegField.setValidationDelay(1000);

        form.add(passwdRegField);

        passwdRegFieldDup = new TextField();
        passwdRegFieldDup.setFieldLabel(Kune.I18N.t("Retype password"));
        passwdRegFieldDup.setName(PASSWORD_FIELD_DUP);
        passwdRegFieldDup.setPassword(true);
        passwdRegFieldDup.setAllowBlank(false);
        passwdRegFieldDup.setMinLength(6);
        passwdRegFieldDup.setMaxLength(40);
        passwdRegFieldDup.setWidth(FIELD_DEF_WIDTH);
        passwdRegFieldDup.setInvalidText(Kune.I18N.t("Passwords do not match"));
        passwdRegFieldDup.setValidator(new Validator() {
            public boolean validate(final String value) throws ValidationException {
                return passwdRegField.getValueAsString().equals(passwdRegFieldDup.getValueAsString());
            }
        });
        passwdRegFieldDup.setValidationDelay(1000);
        form.add(passwdRegFieldDup);

        emailRegField = new TextField();
        emailRegField.setFieldLabel(Kune.I18N.t("Email"));
        emailRegField.setName(EMAIL_FIELD);
        emailRegField.setVtype(VType.EMAIL);
        emailRegField.setWidth(FIELD_DEF_WIDTH);
        emailRegField.setAllowBlank(false);
        emailRegField.setValidationDelay(1000);

        form.add(emailRegField);

        final Store langStore = new SimpleStore(new String[] { "abbr", "language" }, presenter.getLanguages());
        langStore.load();

        languageCombo = new ComboBox();
        languageCombo.setName(LANG_FIELD);
        languageCombo.setMinChars(1);
        languageCombo.setFieldLabel(Kune.I18N.t("Language"));
        languageCombo.setStore(langStore);
        languageCombo.setDisplayField("language");
        languageCombo.setMode(ComboBox.LOCAL);
        languageCombo.setTriggerAction(ComboBox.ALL);
        languageCombo.setEmptyText(Kune.I18N.t("Enter language"));
        languageCombo.setLoadingText(Kune.I18N.t("Searching..."));
        languageCombo.setTypeAhead(true);
        languageCombo.setTypeAheadDelay(1000);
        languageCombo.setSelectOnFocus(false);
        languageCombo.setWidth(186);
        languageCombo.setAllowBlank(false);
        languageCombo.setValueField("abbr");
        languageCombo.setValue(langCode);
        languageCombo.setPageSize(7);
        languageCombo.setForceSelection(true);

        form.add(languageCombo);

        final Store countryStore = new SimpleStore(new String[] { "abbr", "country" }, presenter.getCountries());
        countryStore.load();

        countryCombo = new ComboBox();
        countryCombo.setName(COUNTRY_FIELD);
        countryCombo.setMinChars(1);
        countryCombo.setFieldLabel(Kune.I18N.t("Country"));
        countryCombo.setStore(countryStore);
        countryCombo.setDisplayField("country");
        countryCombo.setMode(ComboBox.LOCAL);
        countryCombo.setTriggerAction(ComboBox.ALL);
        countryCombo.setEmptyText(Kune.I18N.t("Enter your country"));
        countryCombo.setLoadingText(Kune.I18N.t("Searching..."));
        countryCombo.setTypeAhead(true);
        countryCombo.setTypeAheadDelay(1000);
        countryCombo.setSelectOnFocus(false);
        countryCombo.setWidth(186);
        countryCombo.setAllowBlank(false);
        countryCombo.setValueField("abbr");
        countryCombo.setPageSize(7);
        countryCombo.setForceSelection(true);

        form.add(countryCombo);

        final Store timezoneStore = new SimpleStore(new String[] { "id" }, presenter.getTimezones());
        timezoneStore.load();

        timezoneCombo = new ComboBox();
        timezoneCombo.setName(TIMEZONE_FIELD);
        timezoneCombo.setMinChars(1);
        timezoneCombo.setFieldLabel(Kune.I18N.t("Timezone"));
        timezoneCombo.setStore(timezoneStore);
        timezoneCombo.setDisplayField("id");
        timezoneCombo.setMode(ComboBox.LOCAL);
        timezoneCombo.setTriggerAction(ComboBox.ALL);
        timezoneCombo.setEmptyText(Kune.I18N.t("Enter your timezone"));
        timezoneCombo.setLoadingText(Kune.I18N.t("Searching..."));
        timezoneCombo.setTypeAhead(true);
        timezoneCombo.setTypeAheadDelay(1000);
        timezoneCombo.setSelectOnFocus(false);
        timezoneCombo.setWidth(186);
        timezoneCombo.setAllowBlank(false);
        timezoneCombo.setValueField("id");
        timezoneCombo.setPageSize(7);
        timezoneCombo.setForceSelection(true);

        form.add(timezoneCombo);

        return form;
    }

    private FormPanel createStandardForm() {
        FormPanel form = new FormPanel();
        form.setWidth(300);
        form.setLabelWidth(75);
        form.setLabelAlign(Position.RIGHT);
        form.setBorder(false);
        return form;
    }

    private Panel createNoAccountRegister() {
        Panel noAccRegisterPanel = new Panel();
        noAccRegisterPanel.setBorder(false);
        noAccRegisterPanel.setMargins(0, 90, 0, 0);
        Label dontHaveAccountLabel = new Label(Kune.I18N.t("Don't have an account?"));
        Label registerLabel = new Label(Kune.I18N.t("Create one."));
        registerLabel.addClickListener(new ClickListener() {
            public void onClick(final Widget arg0) {
                centerPanel.activate(1);
            }
        });
        registerLabel.addStyleName("kune-Margin-Medium-l");
        registerLabel.addStyleName("kune-link");
        noAccRegisterPanel.add(dontHaveAccountLabel);
        noAccRegisterPanel.add(registerLabel);
        return noAccRegisterPanel;
    }
}
