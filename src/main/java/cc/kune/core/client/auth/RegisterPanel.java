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
package cc.kune.core.client.auth;

import cc.kune.common.client.noti.NotifyLevelImages;
import cc.kune.core.client.resources.CoreMessages;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.inject.Inject;

public class RegisterPanel extends SignInAbstractPanel implements RegisterView {

    public static final String CANCEL_BUTTON_ID = "k-regp-cb";

    public static final String ERRMSG = "k-regp-errmsg";

    public static final String REGISTER_BUTTON_ID = "k-regp-rb";
    public static final String REGISTER_DIALOG = "k-regp-dialog";
    public static final String REGISTER_FORM = "k-regp-p";
    public static final String WELCOME_DIALOG = "k-regp-wdiag";
    public static final String WELCOME_OK_BUTTON = "k-regp-okbt";
    private final RegisterForm registerForm;

    @Inject
    public RegisterPanel(final I18nTranslationService i18n, final Session session, final NotifyLevelImages images) {
        super(REGISTER_DIALOG, i18n, i18n.t(CoreMessages.REGISTER_TITLE), true, true, true, 400, 420, "",
                i18n.t(CoreMessages.REGISTER_TITLE), REGISTER_BUTTON_ID, i18n.tWithNT("Cancel", "used in button"),
                CANCEL_BUTTON_ID, images, ERRMSG, 11);
        // super.addListener(new WindowListenerAdapter() {
        // @Override
        // public void onActivate(final Panel panel) {
        // KuneUiUtils.focusOnField(registerForm.getShortNameField());
        // }
        //
        // @Override
        // public void onShow(final Component component) {
        // KuneUiUtils.focusOnField(registerForm.getShortNameField());
        // }
        // });

        final VerticalPanel panel = new VerticalPanel();
        // panel.setBorder(false);
        registerForm = new RegisterForm(i18n, session);
        registerForm.setWidth(370);
        panel.add(registerForm.getFormPanel());
        super.getInnerPanel().add(panel);
        panel.setId(REGISTER_FORM);
    }

    @Override
    public String getEmail() {
        return registerForm.getEmail();
    }

    @Override
    public String getLongName() {
        return registerForm.getLongName();
    }

    @Override
    public String getRegisterPassword() {
        return registerForm.getRegisterPassword();
    }

    @Override
    public String getShortName() {
        return registerForm.getShortName();
    }

    @Override
    public boolean isRegisterFormValid() {
        return registerForm.isValid();
    }

    @Override
    public boolean isValid() {
        return registerForm.isValid();
    }

    @Override
    public void reset() {
        Scheduler.get().scheduleDeferred(new ScheduledCommand() {
            @Override
            public void execute() {
                registerForm.reset();
            }
        });
    }

    @Override
    public void showWelcolmeDialog() {
        // final InfoDialog welcomeDialog = new InfoDialog(WELCOME_DIALOG,
        // i18n.t("Welcome"),
        // i18n.t("Thanks for registering"),
        // i18n.t("Now you can participate more actively in this site with other people and groups. "
        // + "You can also use your personal space to publish contents. "
        // +
        // "Your email is not verified, please follow the instructions you will receive by email."),
        // i18n.t("Ok"), WELCOME_OK_BUTTON, true, true, 380, 210);
        // welcomeDialog.show();
    }

    @Override
    public void showWelcolmeDialogNoHomepage() {
        // final InfoDialog welcomeDialog = new InfoDialog(WELCOME_DIALOG,
        // i18n.t("Welcome"),
        // i18n.t("Thanks for registering"),
        // i18n.t("Now you can participate more actively in this site with other people and groups. "
        // +
        // "Your email is not verified, please follow the instructions you will receive by email."),
        // i18n.t("Ok"), WELCOME_OK_BUTTON, true, true, 380, 210);
        // welcomeDialog.show();
    }

    @Override
    public void validate() {
        registerForm.validate();
    }

}
