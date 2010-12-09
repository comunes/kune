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
 */
package org.ourproject.kune.workspace.client.signin;

import org.ourproject.kune.platf.client.PlatfMessages;
import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.platf.client.ui.KuneUiUtils;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import cc.kune.core.shared.i18n.I18nTranslationService;

import com.calclab.suco.client.events.Listener0;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.event.WindowListenerAdapter;
import com.gwtext.client.widgets.form.Field;

public class SignInPanel extends SignInAbstractPanel implements SignInView {

    public static final String ERROR_MSG = "k-sigp-errmsg";
    public static final String CANCEL_BUTTON_ID = "k-signinp-cb";
    public static final String SIGN_IN_BUTTON_ID = "k-signinp-sib";
    public static final String CREATE_ONE = "k-signinp-create";
    public static final String SIGNIN_DIALOG = "k-signinp-dialog";
    private final SignInForm signInForm;
    private final SignInPresenter presenter;

    public SignInPanel(final SignInPresenter presenter, final I18nTranslationService i18n, final WorkspaceSkeleton ws,
            final Images images) {
        super(SIGNIN_DIALOG, i18n, i18n.t(PlatfMessages.SIGN_IN_TITLE), true, true, 340, 240, "",
                i18n.t(PlatfMessages.SIGN_IN_TITLE), SIGN_IN_BUTTON_ID, i18n.tWithNT("Cancel", "used in button"),
                CANCEL_BUTTON_ID, new Listener0() {
                    public void onEvent() {
                        presenter.onFormSignIn();
                    }
                }, new Listener0() {
                    public void onEvent() {
                        presenter.onCancel();
                    }
                }, images, ERROR_MSG, 102);
        this.presenter = presenter;

        super.addListener(new WindowListenerAdapter() {
            @Override
            public void onHide(final Component component) {
                presenter.onClose();
            }

            @Override
            public void onShow(final Component component) {
                KuneUiUtils.focusOnField(getNickname());
            }
        });

        final Panel panel = new Panel();
        panel.setBorder(false);
        signInForm = new SignInForm(presenter, i18n);
        signInForm.setWidth(310);
        panel.add(signInForm.getFormPanel());
        panel.add(createNoAccountRegister());
        add(panel);
    }

    public void focusOnNickname() {
        signInForm.focusLogin();
    }

    public String getLoginPassword() {
        return signInForm.getLoginPassword();
    }

    public String getNickOrEmail() {
        return signInForm.getNickOrEmail();
    }

    public boolean isSignInFormValid() {
        return signInForm.isValid();
    }

    public void reset() {
        signInForm.reset();
    }

    public void validate() {
        signInForm.validate();
    }

    private Panel createNoAccountRegister() {
        final Panel noAccRegisterPanel = new Panel();
        noAccRegisterPanel.setBorder(false);
        noAccRegisterPanel.setMargins(0, 20, 0, 0);
        final HorizontalPanel hpanel = new HorizontalPanel();
        final Label dontHaveAccount = new Label(i18n.t("Don't you have an account?"));
        final Label registerLabel = new Label(i18n.t("Create one."));
        registerLabel.ensureDebugId(CREATE_ONE);
        registerLabel.addClickHandler(new ClickHandler() {
            public void onClick(final ClickEvent event) {
                presenter.onAccountRegister();
            }
        });
        registerLabel.addStyleName("kune-Margin-Medium-l");
        registerLabel.addStyleName("kune-link");
        hpanel.add(dontHaveAccount);
        hpanel.add(registerLabel);
        noAccRegisterPanel.add(hpanel);
        return noAccRegisterPanel;
    }

    private Field getNickname() {
        return signInForm.getNickOrEmailField();
    }

}
