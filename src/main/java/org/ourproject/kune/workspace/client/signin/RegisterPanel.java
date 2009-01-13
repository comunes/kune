package org.ourproject.kune.workspace.client.signin;

import org.ourproject.kune.platf.client.PlatfMessages;
import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.ui.KuneUiUtils;
import org.ourproject.kune.platf.client.ui.dialogs.InfoDialog;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.calclab.suco.client.events.Listener0;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.event.WindowListenerAdapter;

public class RegisterPanel extends SignInAbstractPanel implements RegisterView {

    public static final String ERRMSG = "k-regp-errmsg";

    static RegisterForm registerForm;

    public static final String CANCEL_BUTTON_ID = "k-regp-cb";
    public static final String REGISTER_BUTTON_ID = "k-regp-rb";
    public static final String REGISTER_FORM = "k-regp-p";
    public static final String WELCOME_OK_BUTTON = "k-regp-okbt";
    public static final String WELCOME_DIALOG = "k-regp-wdiag";
    public static final String REGISTER_DIALOG = "k-regp-dialog";

    public RegisterPanel(final RegisterPresenter presenter, I18nUITranslationService i18n, final WorkspaceSkeleton ws,
            Session session, Images images) {
        super(REGISTER_DIALOG, i18n, i18n.t(PlatfMessages.REGISTER_TITLE), true, true, 390, 450, "",
                i18n.t(PlatfMessages.REGISTER_TITLE), REGISTER_BUTTON_ID, i18n.tWithNT("Cancel", "used in button"),
                CANCEL_BUTTON_ID, new Listener0() {
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
                }, images, ERRMSG, 11);
        super.addListener(new WindowListenerAdapter() {
            @Override
            public void onActivate(Panel panel) {
                KuneUiUtils.focusOnField(registerForm.getShortNameField());
            }

            @Override
            public void onHide(Component component) {
                presenter.onClose();
            }

            @Override
            public void onShow(Component component) {
                KuneUiUtils.focusOnField(registerForm.getShortNameField());
            }
        });

        Panel panel = new Panel();
        panel.setBorder(false);
        registerForm = new RegisterForm(i18n, session);
        registerForm.setWidth(360);
        panel.add(registerForm.getFormPanel());
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
        InfoDialog welcomeDialog = new InfoDialog(WELCOME_DIALOG, i18n.t("Welcome"), i18n.t("Thanks for registering"),
                i18n.t("Now you can participate more actively in this site with other people and groups. "
                        + "You can also use your personal space to publish contents. "
                        + "Your email is not verified, please follow the instructions you will receive by email."),
                i18n.t("Ok"), WELCOME_OK_BUTTON, true, true, 380, 210);
        welcomeDialog.show();
    }

    public void showWelcolmeDialogNoHomepage() {
        InfoDialog welcomeDialog = new InfoDialog(WELCOME_DIALOG, i18n.t("Welcome"), i18n.t("Thanks for registering"),
                i18n.t("Now you can participate more actively in this site with other people and groups. "
                        + "Your email is not verified, please follow the instructions you will receive by email."),
                i18n.t("Ok"), WELCOME_OK_BUTTON, true, true, 380, 210);
        welcomeDialog.show();
    }

    public boolean wantPersonalHomepage() {
        return registerForm.wantPersonalHomepage();
    }
}
