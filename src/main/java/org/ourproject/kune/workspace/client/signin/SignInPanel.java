package org.ourproject.kune.workspace.client.signin;

import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.calclab.suco.client.listener.Listener0;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.widgets.Panel;

public class SignInPanel extends SignInAbstractPanel implements SignInView {

    private static final String CANCEL_BUTTON_ID = "kune-signinp-cb";
    private static final String SIGN_IN_BUTTON_ID = "kune-signinp-sib";
    static SignInForm signInForm;
    private final SignInPresenter presenter;

    public SignInPanel(final SignInPresenter presenter, I18nUITranslationService i18n, final WorkspaceSkeleton ws) {
        super(i18n, i18n.t("Sign in"), true, true, 330, 240, "", i18n.t("Sign in"), SIGN_IN_BUTTON_ID, i18n.tWithNT(
                "Cancel", "used in button"), CANCEL_BUTTON_ID, new Listener0() {
            public void onEvent() {
                signInForm.validate();
                if (signInForm.isValid()) {
                    presenter.onFormSignIn();
                }
            }
        }, new Listener0() {
            public void onEvent() {
                presenter.onCancel();
            }
        });
        this.presenter = presenter;

        Panel panel = new Panel();
        panel.setBorder(false);
        signInForm = new SignInForm(i18n);
        signInForm.setWidth(300);
        panel.add(signInForm.getForm());
        panel.add(createNoAccountRegister());
        add(panel);
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
        DeferredCommand.addCommand(new Command() {
            public void execute() {
                signInForm.reset();
            }
        });
    }

    private Panel createNoAccountRegister() {
        final Panel noAccRegisterPanel = new Panel();
        noAccRegisterPanel.setBorder(false);
        noAccRegisterPanel.setMargins(0, 20, 0, 0);
        HorizontalPanel hp = new HorizontalPanel();
        final Label dontHaveAccountLabel = new Label(i18n.t("Don't have an account?"));
        final Label registerLabel = new Label(i18n.t("Create one."));
        registerLabel.addClickListener(new ClickListener() {
            public void onClick(final Widget arg0) {
                presenter.onAccountRegister();
            }
        });
        registerLabel.addStyleName("kune-Margin-Medium-l");
        registerLabel.addStyleName("kune-link");
        hp.add(dontHaveAccountLabel);
        hp.add(registerLabel);
        noAccRegisterPanel.add(hp);
        return noAccRegisterPanel;
    }

}
