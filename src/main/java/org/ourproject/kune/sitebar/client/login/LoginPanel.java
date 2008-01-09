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

package org.ourproject.kune.sitebar.client.login;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.I18nLanguageDTO;
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
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Ext;
import com.gwtext.client.core.Position;
import com.gwtext.client.data.SimpleStore;
import com.gwtext.client.data.Store;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.ButtonConfig;
import com.gwtext.client.widgets.LayoutDialog;
import com.gwtext.client.widgets.LayoutDialogConfig;
import com.gwtext.client.widgets.TabPanel;
import com.gwtext.client.widgets.TabPanelItem;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.event.DialogListener;
import com.gwtext.client.widgets.event.TabPanelItemListenerAdapter;
import com.gwtext.client.widgets.form.ComboBox;
import com.gwtext.client.widgets.form.ComboBoxConfig;
import com.gwtext.client.widgets.form.Form;
import com.gwtext.client.widgets.form.FormConfig;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.form.TextFieldConfig;
import com.gwtext.client.widgets.form.VType;
import com.gwtext.client.widgets.form.ValidationException;
import com.gwtext.client.widgets.form.Validator;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.ContentPanel;
import com.gwtext.client.widgets.layout.ContentPanelConfig;
import com.gwtext.client.widgets.layout.LayoutRegionConfig;

public class LoginPanel implements LoginView, View {
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

    private LayoutDialog dialog;

    private final LoginPresenter presenter;

    private TextField shortNameRegField;

    private TextField longNameRegField;

    private TextField emailRegField;

    private TextField passwdRegField;

    private Form signInForm;

    private Form registerForm;

    private TextField passwdRegFieldDup;

    private SiteMessagePanel messagesPanel;

    private TabPanel tabPanel;

    private final Object[][] countries;

    private final Object[][] languages;

    private final Object[][] timezones;

    private ComboBox countryCombo;

    private ComboBox languageCombo;

    private ComboBox timezoneCombo;

    private BasicDialog welcomeDialog;

    public LoginPanel(final LoginPresenter initialPresenter, final Object[][] languages, final Object[][] countries,
            final String[] timezones) {
        this.presenter = initialPresenter;
        this.languages = languages;
        this.countries = countries;
        this.timezones = new Object[timezones.length][1];
        for (int i = 0; i < timezones.length; i++) {
            Object[] obj = new Object[] { timezones[i] };
            this.timezones[i] = obj;
        }
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
        welcomeDialog = new BasicDialog(Kune.I18N.t("Welcome"), Kune.I18N.t("Ok"), true, 400, 250, new ClickListener() {
            public void onClick(final Widget sender) {
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
        welcomeDialog.show();
    }

    public boolean isSignInFormValid() {
        return signInForm.isValid();
    }

    public boolean isRegisterFormValid() {
        return registerForm.isValid();
    }

    public void reset() {
        DeferredCommand.addCommand(new Command() {
            public void execute() {
                // signInForm.reset();
                loginPassField.reset();
                registerForm.reset();
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

    public void showErrorMessage(final String message) {
        messagesPanel.setMessage(message, SiteMessage.ERROR, SiteMessage.ERROR);
        messagesPanel.show();
    }

    public void setMessage(final String message, final int type) {
        messagesPanel.setMessage(message, type, type);
        messagesPanel.show();
    }

    public void hideMessage() {
        messagesPanel.hide();
    }

    public void show(final I18nLanguageDTO currentLanguage) {
        tabPanel.getTab(0).activate();
        dialog.setVisible(true);
        dialog.show();
        Site.hideProgress();
        dialog.focus();
        loginNickOrEmailField.focus(false);
        languageCombo.setValue(currentLanguage.getEnglishName());
    }

    public void hide() {
        dialog.hide();
    }

    public void center() {
        dialog.center();
    }

    private Object[][] getLanguages() {
        return languages;
    }

    private Object[][] getCountries() {
        return countries;
    }

    private Object[][] getTimezones() {
        return timezones;
    }

    private void createPanel() {

        LayoutRegionConfig south = new LayoutRegionConfig() {
            {
                setSplit(false);
                setInitialSize(49);
                setHideWhenEmpty(true);
                setFloatable(true);
                setAutoHide(true);
            }
        };

        LayoutRegionConfig center = new LayoutRegionConfig() {
            {
                setAutoScroll(true);
                setTabPosition("top");
                setCloseOnTab(true);
                setAlwaysShowTabs(true);
            }
        };

        dialog = new LayoutDialog(new LayoutDialogConfig() {
            {
                setModal(true);
                setWidth(400);
                setHeight(440);
                setShadow(true);
                setResizable(true);
                setClosable(true);
                setProxyDrag(true);
                setCollapsible(false);
                setTitle(Kune.I18N.t("Sign in"));
            }
        }, null, south, null, null, center);

        final BorderLayout layout = dialog.getLayout();
        layout.beginUpdate();

        ContentPanel signInPanel = new ContentPanel(Ext.generateId(), Kune.I18N.t("Sign in"));

        signInForm = createSignInForm();

        signInForm.addStyleName("kune-Default-Form");

        VerticalPanel signInWrapper = new VerticalPanel() {
            {
                setSpacing(30);
                setWidth("100%");
                setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
            }
        };
        signInWrapper.add(signInForm);

        signInPanel.add(signInWrapper);
        signInPanel.add(createNoAccountRegister());
        layout.add(LayoutRegionConfig.CENTER, signInPanel);

        ContentPanel registerPanel = new ContentPanel(Ext.generateId(), new ContentPanelConfig() {
            {
                setTitle(Kune.I18N.t("Register"));
                setBackground(true);
            }
        });

        registerForm = createRegistrationForm();

        registerForm.addStyleName("kune-Default-Form");

        VerticalPanel registerWrapper = new VerticalPanel() {
            {
                setSpacing(30);
                setWidth("100%");
                setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
            }
        };
        registerWrapper.add(registerForm);

        registerPanel.add(registerWrapper);
        layout.add(LayoutRegionConfig.CENTER, registerPanel);

        messagesPanel = new SiteMessagePanel(presenter, false);
        ContentPanel southPanel = new ContentPanel(messagesPanel, "", new ContentPanelConfig() {
            {
                setBackground(false);
                setFitToFrame(true);
            }
        });
        messagesPanel.setWidth("100%");
        messagesPanel.setHeight("100%");
        messagesPanel.setMessage("", SiteMessage.INFO, SiteMessage.ERROR);
        layout.add(LayoutRegionConfig.SOUTH, southPanel);

        layout.endUpdate();

        final Button signInBtn = dialog.addButton(Kune.I18N.t("Sign in"));
        signInBtn.addButtonListener(new ButtonListenerAdapter() {
            public void onClick(final Button button, final EventObject e) {
                presenter.doLogin();
            }
        });

        final Button registerBtn = dialog.addButton(Kune.I18N.t("Register"));
        registerBtn.addButtonListener(new ButtonListenerAdapter() {
            public void onClick(final Button button, final EventObject e) {
                presenter.doRegister();
            }
        });
        registerBtn.hide();

        dialog.addButton(new Button(new ButtonConfig() {
            {
                setText(Kune.I18N.tWithNT("Cancel", "used in button"));
                setButtonListener(new ButtonListenerAdapter() {
                    public void onClick(final Button button, final EventObject e) {
                        presenter.onCancel();
                    }
                });
            }
        }));

        tabPanel = layout.getRegion(LayoutRegionConfig.CENTER).getTabs();
        tabPanel.getTab(0).addTabPanelItemListener(new TabPanelItemListenerAdapter() {
            public void onActivate(final TabPanelItem tab) {
                dialog.setTitle(Kune.I18N.t("Sign in"));
                registerBtn.hide();
                signInBtn.show();
                tab.getTextEl().highlight();
            }
        });

        tabPanel.getTab(1).addTabPanelItemListener(new TabPanelItemListenerAdapter() {
            public void onActivate(final TabPanelItem tab) {
                dialog.setTitle(Kune.I18N.t("Register"));
                signInBtn.hide();
                registerBtn.show();
                tab.getTextEl().highlight();
            }
        });

        dialog.addDialogListener(new DialogListener() {
            public boolean doBeforeHide(final LayoutDialog dialog) {
                // presenter.onClose();
                return true;
            }

            public boolean doBeforeShow(final LayoutDialog dialog) {
                return true;
            }

            public void onHide(final LayoutDialog dialog) {
                presenter.onClose();
            }

            public void onKeyDown(final LayoutDialog dialog, final EventObject e) {
            }

            public void onMove(final LayoutDialog dialog, final int x, final int y) {
            }

            public void onResize(final LayoutDialog dialog, final int width, final int height) {
            }

            public void onShow(final LayoutDialog dialog) {
            }
        });
    }

    private Form createSignInForm() {

        Form form = new Form(new FormConfig() {
            {
                setWidth(300);
                setLabelWidth(75);
                setLabelAlign(Position.RIGHT);
            }
        });
        form.fieldset(Kune.I18N.t("Sign in"));
        loginNickOrEmailField = new TextField(new TextFieldConfig() {
            {
                setFieldLabel(Kune.I18N.t("Nickname or email"));
                setName(NICKOREMAIL_FIELD);
                setWidth(175);
                setAllowBlank(false);
                setMsgTarget("side");
            }
        });
        form.add(loginNickOrEmailField);

        loginPassField = new TextField(new TextFieldConfig() {
            {
                setFieldLabel(Kune.I18N.t("Password"));
                setName(PASSWORD_FIELD);
                setWidth(175);
                setPassword(true);
                setAllowBlank(false);
                setMsgTarget("side");
            }
        });
        form.add(loginPassField);
        form.end();
        form.render();

        return form;
    }

    private Form createRegistrationForm() {
        Form form = new Form(new FormConfig() {
            {
                setWidth(300);
                setLabelWidth(75);
                setLabelAlign(Position.RIGHT);
            }
        });

        form.fieldset(Kune.I18N.t("Register"));

        shortNameRegField = new TextField(new TextFieldConfig() {
            {
                setFieldLabel(Kune.I18N.t("Nickname"));
                setName(NICK_FIELD);
                setWidth(200);
                setAllowBlank(false);
                setMsgTarget("side");
                setMinLength(3);
                setMaxLength(15);
                setRegex("^[a-z0-9_\\-]+$");
                setMinLengthText(Kune.I18N.t(MUST_BE_BETWEEN_3_AND_15));
                setMaxLengthText(Kune.I18N.t(MUST_BE_BETWEEN_3_AND_15));
                setRegexText(Kune.I18N.t(MUST_BE_BETWEEN_3_AND_15));
            }
        });
        form.add(shortNameRegField);

        longNameRegField = new TextField(new TextFieldConfig() {
            {
                setFieldLabel(Kune.I18N.t("Full Name"));
                setName(LONGNAME_FIELD);
                setWidth(200);
                setAllowBlank(false);
                setMsgTarget("side");
                setMinLength(3);
                setMaxLength(50);
            }
        });
        form.add(longNameRegField);

        passwdRegField = new TextField(new TextFieldConfig() {
            {
                setFieldLabel(Kune.I18N.t("Password"));
                setName(PASSWORD_FIELD);
                setPassword(true);
                setAllowBlank(false);
                setMinLength(6);
                setMaxLength(40);
                setWidth(200);
                setMsgTarget("side");
            }
        });
        form.add(passwdRegField);

        passwdRegFieldDup = new TextField(new TextFieldConfig() {
            {
                setFieldLabel(Kune.I18N.t("Retype password"));
                setName(PASSWORD_FIELD_DUP);
                setPassword(true);
                setAllowBlank(false);
                setMinLength(6);
                setMaxLength(40);
                setWidth(200);
                setMsgTarget("side");
                setInvalidText(Kune.I18N.t("Passwords do not match"));
                setValidator(new Validator() {
                    public boolean validate(final String value) throws ValidationException {
                        return passwdRegField.getValueAsString().equals(passwdRegFieldDup.getValueAsString());
                    }
                });
            }
        });
        form.add(passwdRegFieldDup);

        emailRegField = new TextField(new TextFieldConfig() {
            {
                setFieldLabel(Kune.I18N.t("Email"));
                setName(EMAIL_FIELD);
                setVtype(VType.EMAIL);
                setWidth(200);
                setMsgTarget("side");
                setAllowBlank(false);
            }
        });
        form.add(emailRegField);

        final Store langStore = new SimpleStore(new String[] { "abbr", "language" }, getLanguages());
        langStore.load();

        languageCombo = new ComboBox(new ComboBoxConfig() {
            {
                setName(LANG_FIELD);
                setMinChars(1);
                setFieldLabel(Kune.I18N.t("Language"));
                setStore(langStore);
                setDisplayField("language");
                setMode(ComboBox.LOCAL);
                setTriggerAction(ComboBox.ALL);
                setEmptyText(Kune.I18N.t("Enter language"));
                setLoadingText(Kune.I18N.t("Searching..."));
                setTypeAhead(true);
                setTypeAheadDelay(1000);
                setSelectOnFocus(false);
                setWidth(186);
                setMsgTarget("side");
                setAllowBlank(false);
                setValueField("abbr");
                setPageSize(7);
            }
        });

        form.add(languageCombo);

        final Store countryStore = new SimpleStore(new String[] { "abbr", "country" }, getCountries());
        countryStore.load();

        countryCombo = new ComboBox(new ComboBoxConfig() {
            {
                setName(COUNTRY_FIELD);
                setMinChars(1);
                setFieldLabel(Kune.I18N.t("Country"));
                setStore(countryStore);
                setDisplayField("country");
                setMode(ComboBox.LOCAL);
                setTriggerAction(ComboBox.ALL);
                setEmptyText(Kune.I18N.t("Enter your country"));
                setLoadingText(Kune.I18N.t("Searching..."));
                setTypeAhead(true);
                setTypeAheadDelay(1000);
                setSelectOnFocus(false);
                setWidth(186);
                setMsgTarget("side");
                setAllowBlank(false);
                setValueField("abbr");
                setPageSize(7);
            }
        });

        form.add(countryCombo);

        final Store timezoneStore = new SimpleStore(new String[] { "id" }, getTimezones());
        timezoneStore.load();

        timezoneCombo = new ComboBox(new ComboBoxConfig() {
            {
                setName(TIMEZONE_FIELD);
                setMinChars(1);
                setFieldLabel(Kune.I18N.t("Timezone"));
                setStore(timezoneStore);
                setDisplayField("id");
                setMode(ComboBox.LOCAL);
                setTriggerAction(ComboBox.ALL);
                setEmptyText(Kune.I18N.t("Enter your timezone"));
                setLoadingText(Kune.I18N.t("Searching..."));
                setTypeAhead(true);
                setTypeAheadDelay(1000);
                setSelectOnFocus(false);
                setWidth(186);
                setMsgTarget("side");
                setAllowBlank(false);
                setValueField("id");
                setPageSize(7);
            }
        });

        form.add(timezoneCombo);

        form.end();
        form.render();
        return form;
    }

    private HorizontalPanel createNoAccountRegister() {
        HorizontalPanel registerHP = new HorizontalPanel();
        Label dontHaveAccountLabel = new Label(Kune.I18N.t("Don't have an account?"));
        Label registerLabel = new Label(Kune.I18N.t("Create one."));
        registerLabel.addClickListener(new ClickListener() {
            public void onClick(final Widget arg0) {
                tabPanel.getTab(1).activate();
            }
        });
        registerLabel.addStyleName("kune-Margin-Medium-l");
        registerLabel.addStyleName("kune-link");
        registerHP.addStyleName("kune-Margin-40-l");
        registerHP.add(dontHaveAccountLabel);
        registerHP.add(registerLabel);
        return registerHP;
    }
}
