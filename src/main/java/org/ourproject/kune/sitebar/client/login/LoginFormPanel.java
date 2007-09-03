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
import org.ourproject.kune.sitebar.client.bar.SiteBarTrans;
import org.ourproject.kune.sitebar.client.services.Translate;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Ext;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.ButtonConfig;
import com.gwtext.client.widgets.LayoutDialog;
import com.gwtext.client.widgets.LayoutDialogConfig;
import com.gwtext.client.widgets.TabPanel;
import com.gwtext.client.widgets.TabPanelItem;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.event.TabPanelItemListenerAdapter;
import com.gwtext.client.widgets.form.Form;
import com.gwtext.client.widgets.form.FormConfig;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.form.TextFieldConfig;
import com.gwtext.client.widgets.form.VType;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.ContentPanel;
import com.gwtext.client.widgets.layout.ContentPanelConfig;
import com.gwtext.client.widgets.layout.LayoutRegionConfig;

public class LoginFormPanel implements LoginFormView, View {
    private static final Translate t = SiteBarTrans.getInstance().t;

    private static final String NICKOREMAIL_FIELD = "nickOrEmail";
    private static final String PASSWORD_FIELD = "password";
    private static final String NICK_FIELD = "nick";
    private static final String EMAIL_FIELD = "email";
    private static final String LONGNAME_FIELD = "long_name";

    private TextField loginPassField;

    private TextField loginNickOrEmailField;

    private LayoutDialog dialog;

    private final LoginForm presenter;

    private TextField shortNameRegField;

    private TextField longNameRegField;

    private TextField emailRegField;

    private TextField passwdRegField;

    private Form signInForm;

    private Form registerForm;

    public LoginFormPanel(final LoginForm initialPresenter) {

	this.presenter = initialPresenter;
	createPanel();
	// .addStyleName("kune-Default-Form");
	// generalVP.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
    }

    public void reset() {
	signInForm.reset();
	registerForm.reset();
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
	return loginPassField.getValueAsString();
    }

    private void createPanel() {

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
		setHeight(300);
		setShadow(true);
		setResizable(true);
		setClosable(false);
		setProxyDrag(true);
		setTitle(t.SignIn());
	    }
	}, center);

	final BorderLayout layout = dialog.getLayout();
	layout.beginUpdate();

	ContentPanel signInPanel = new ContentPanel(Ext.generateId(), t.SignIn());

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
	layout.add(LayoutRegionConfig.CENTER, signInPanel);

	ContentPanel registerPanel = new ContentPanel(Ext.generateId(), new ContentPanelConfig() {
	    {
		setTitle(t.Register());
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

	layout.endUpdate();

	final Button signInBtn = dialog.addButton(t.SignIn());
	signInBtn.addButtonListener(new ButtonListenerAdapter() {
	    public void onClick(final Button button, final EventObject e) {
		presenter.doLogin();
	    }
	});

	final Button registerBtn = dialog.addButton(t.Register());
	registerBtn.addButtonListener(new ButtonListenerAdapter() {
	    public void onClick(final Button button, final EventObject e) {
		presenter.doRegister();
	    }
	});
	registerBtn.hide();

	dialog.addButton(new Button(new ButtonConfig() {
	    {
		setText(t.Cancel());
		setButtonListener(new ButtonListenerAdapter() {
		    public void onClick(final Button button, final EventObject e) {
			presenter.doCancel();
		    }
		});
	    }
	}));

	TabPanel tabPanel = layout.getRegion(LayoutRegionConfig.CENTER).getTabs();

	tabPanel.getTab(0).addTabPanelItemListener(new TabPanelItemListenerAdapter() {
	    public void onActivate(final TabPanelItem tab) {
		dialog.setTitle(t.SignIn());
		registerBtn.hide();
		signInBtn.show();
	    }
	});

	tabPanel.getTab(1).addTabPanelItemListener(new TabPanelItemListenerAdapter() {
	    public void onActivate(final TabPanelItem tab) {
		dialog.setTitle(t.Register());
		signInBtn.hide();
		registerBtn.show();
		tab.getTextEl().highlight();
	    }
	});

	// Button button = new Button(new ButtonConfig() {
	// {
	// // i18n
	// setText("Login / Register");
	// }
	// });
	// button.addButtonListener(new ButtonListenerAdapter() {
	// public void onClick(final Button button, final EventObject e) {
	// dialog.show(button.getEl());
	// }
	// });
    }

    private Form createSignInForm() {

	Form form = new Form(new FormConfig() {
	    {
		setWidth(300);
		setLabelWidth(75);
		setLabelAlign("right");
	    }
	});
	form.fieldset(t.SignIn());
	loginNickOrEmailField = new TextField(new TextFieldConfig() {
	    {
		setFieldLabel(t.UserNameOrEmail());
		setName(NICKOREMAIL_FIELD);
		setWidth(175);
		setAllowBlank(false);
		setMsgTarget("side");
	    }
	});
	form.add(loginNickOrEmailField);

	loginPassField = new TextField(new TextFieldConfig() {
	    {
		setFieldLabel(t.Password());
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

    public Form createRegistrationForm() {
	Form form = new Form(new FormConfig() {
	    {
		setWidth(300);
		setLabelWidth(75);
		setLabelAlign("right");
	    }
	});

	form.fieldset(t.Register());

	shortNameRegField = new TextField(new TextFieldConfig() {
	    {
		setFieldLabel(t.NickName());
		setName(NICK_FIELD);
		setWidth(200);
		setAllowBlank(false);
		setMsgTarget("side");
		setMinLength(3);
		setMaxLength(15);
		// TODO: exclude spaces in nickname
		// //setRegex("/^[a-zA-Z0-9_]+$/");
		// i18n
		setMinLengthText("Must be between 3 and 15 lowercase characters. Can only contain characters, numbers, and dashes");
		setMaxLengthText("Must be between 3 and 15 lowercase characters. Can only contain characters, numbers, and dashes");
		// //setRegexText("Must be between 3 and 15 lowercase
		// characters. Can only contain characters, numbers, and
		// dashes");
	    }
	});
	form.add(shortNameRegField);

	longNameRegField = new TextField(new TextFieldConfig() {
	    {
		setFieldLabel(t.FullName());
		setName(LONGNAME_FIELD);
		setWidth(200);
		setAllowBlank(false);
		setMsgTarget("side");
	    }
	});
	form.add(longNameRegField);

	passwdRegField = new TextField(new TextFieldConfig() {
	    {
		setFieldLabel(t.Password());
		setName(PASSWORD_FIELD);
		setPassword(true);
		setAllowBlank(false);
		setMinLength(6);
		setWidth(200);
		setMsgTarget("side");
	    }
	});
	form.add(passwdRegField);

	emailRegField = new TextField(new TextFieldConfig() {
	    {
		setFieldLabel(t.Email());
		setName(EMAIL_FIELD);
		setVtype(VType.EMAIL);
		setWidth(200);
		setMsgTarget("side");
	    }
	});
	form.add(emailRegField);
	form.end();
	form.render();
	return form;
    }

    public void show() {
	dialog.show();
    }

    public void hide() {
	dialog.hide();
    }
}
