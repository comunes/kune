package org.ourproject.kune.workspace.client.signin;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.dto.UserDTO;
import org.ourproject.kune.platf.client.dto.UserInfoDTO;
import org.ourproject.kune.platf.client.errors.UserAuthException;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.newgroup.SiteErrorType;
import org.ourproject.kune.workspace.client.site.Site;
import org.ourproject.kune.workspace.client.site.SiteToken;
import org.ourproject.kune.workspace.client.site.rpc.UserServiceAsync;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.suco.client.ioc.Provider;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class SignInPresenter extends SignInAbstractPresenter implements SignIn {

    SignInView view;
    private final Provider<UserServiceAsync> userServiceProvider;
    private final Provider<Register> registerProvider;

    public SignInPresenter(Session session, StateManager stateManager, I18nUITranslationService i18n,
            Provider<UserServiceAsync> userServiceProvider, Provider<Register> registerProvider) {
        super(session, stateManager, i18n);
        this.userServiceProvider = userServiceProvider;
        this.registerProvider = registerProvider;
    }

    public void doSignIn(StateToken previousStateToken) {
        this.previousStateToken = previousStateToken;
        registerProvider.get().hide();
        if (!session.isLogged()) {
            Site.showProgressProcessing();
            view.show();
            view.center();
            Site.hideProgress();
        } else {
            stateManager.gotoToken(previousStateToken);
        }
    }

    public View getView() {
        return view;
    }

    public void init(SignInView view) {
        this.view = view;
        super.view = view;
    }

    public void onAccountRegister() {
        view.reset();
        view.hideMessages();
        view.hide();
        stateManager.gotoToken(SiteToken.register.toString());
    }

    public void onFormSignIn() {
        if (view.isSignInFormValid()) {
            view.maskProcessing();

            final String nickOrEmail = view.getNickOrEmail();
            final String passwd = view.getLoginPassword();

            final UserDTO user = new UserDTO();
            user.setShortName(nickOrEmail);
            user.setPassword(passwd);

            final AsyncCallback<UserInfoDTO> callback = new AsyncCallback<UserInfoDTO>() {
                public void onFailure(final Throwable caught) {
                    view.unMask();
                    Site.hideProgress();
                    try {
                        throw caught;
                    } catch (final UserAuthException e) {
                        view.setErrorMessage(i18n.t("Incorrect nickname/email or password"), SiteErrorType.error);
                    } catch (final Throwable e) {
                        view.setErrorMessage("Error in login", SiteErrorType.error);
                        Log.error("Other kind of exception in LoginFormPresenter/doLogin");
                    }
                }

                public void onSuccess(final UserInfoDTO userInfoDTO) {
                    onSignIn(userInfoDTO);
                    stateManager.gotoToken(previousStateToken);
                    view.hide();
                    view.unMask();
                }
            };
            userServiceProvider.get().login(user.getShortName(), user.getPassword(), callback);
        }
    }
}
