package org.ourproject.kune.workspace.client.signin;

public interface RegisterView extends SignInAbstractView {

    String getCountry();

    String getEmail();

    String getLanguage();

    String getLongName();

    String getRegisterPassword();

    String getRegisterPasswordDup();

    String getShortName();

    String getTimezone();

    boolean isRegisterFormValid();

    void showWelcolmeDialog();

    void showWelcolmeDialogNoHomepage();

    boolean wantPersonalHomepage();

}
