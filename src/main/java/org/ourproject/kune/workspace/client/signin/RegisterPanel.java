package org.ourproject.kune.workspace.client.signin;

import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.ui.dialogs.InfoDialog;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.calclab.suco.client.listener.Listener0;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.gwtext.client.widgets.Panel;

public class RegisterPanel extends SignInAbstractPanel implements RegisterView {

    public static final String ERRMSG = "k-regp-errmsg";

    static RegisterForm registerForm;

    public static final String CANCEL_BUTTON_ID = "k-regp-cb";
    public static final String REGISTER_BUTTON_ID = "k-regp-rb";
    public static final String REGISTER_FORM = "k-regp-p";

    public static final String WELCOME_OK_BUTTON = "k-regp-okbt";

    public RegisterPanel(final RegisterPresenter presenter, I18nUITranslationService i18n, final WorkspaceSkeleton ws,
            Session session, Images images) {
        super(i18n, i18n.t("Register"), true, true, 390, 450, "", i18n.t("Register"), REGISTER_BUTTON_ID, i18n.tWithNT(
                "Cancel", "used in button"), CANCEL_BUTTON_ID, new Listener0() {
            public void onEvent() {
                registerForm.validate();
                if (registerForm.isValid()) {
                    presenter.onFormRegister();
                }
            }
        }, new Listener0() {
            public void onEvent() {
                presenter.onCancel();
            }
        }, images, ERRMSG);

        Panel panel = new Panel();
        panel.setBorder(false);
        registerForm = new RegisterForm(i18n, session);
        registerForm.setWidth(360);
        panel.add(registerForm.getForm());
        add(panel);
        panel.setId(REGISTER_FORM);
    }

    public String getCountry() {
        return registerForm.getCountry();
    }

    public String getEmail() {
        return registerForm.getEmail();
    }

    public String getLanguage() {
        return registerForm.getLanguage();
    }

    public String getLongName() {
        return registerForm.getLongName();
    }

    public String getRegisterPassword() {
        return registerForm.getRegisterPassword();
    }

    public String getRegisterPasswordDup() {
        return registerForm.getRegisterPasswordDup();
    }

    public String getShortName() {
        return registerForm.getShortName();
    }

    public String getTimezone() {
        return registerForm.getTimezone();
    }

    public boolean isRegisterFormValid() {
        return registerForm.isValid();
    }

    public void reset() {
        DeferredCommand.addCommand(new Command() {
            public void execute() {
                registerForm.reset();
            }
        });
    }

    public void showWelcolmeDialog() {
        InfoDialog welcomeDialog = new InfoDialog(i18n.t("Welcome"), i18n.t("Thanks for registering"),
                i18n.t("Now you can participate more actively in this site with other people and groups. "
                        + "You can also use your personal space to publish contents. "
                        + "Your email is not verified, please follow the instructions you will receive by email."),
                i18n.t("Ok"), WELCOME_OK_BUTTON, true, true, 400, 210);
        welcomeDialog.show();
    }

    public void showWelcolmeDialogNoHomepage() {
        InfoDialog welcomeDialog = new InfoDialog(i18n.t("Welcome"), i18n.t("Thanks for registering"),
                i18n.t("Now you can participate more actively in this site with other people and groups. "
                        + "Your email is not verified, please follow the instructions you will receive by email."),
                i18n.t("Ok"), WELCOME_OK_BUTTON, true, true, 400, 210);
        welcomeDialog.show();
    }

    public boolean wantPersonalHomepage() {
        return registerForm.wantPersonalHomepage();
    }
}
