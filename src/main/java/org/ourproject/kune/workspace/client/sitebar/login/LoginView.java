package org.ourproject.kune.workspace.client.sitebar.login;

import org.ourproject.kune.platf.client.View;

public interface LoginView extends View {

    public void reset();

    public String getNickOrEmail();

    public String getLoginPassword();

    public String getShortName();

    public String getLongName();

    public String getEmail();

    public String getRegisterPassword();

    public String getRegisterPasswordDup();

    public boolean isSignInFormValid();

    public boolean isRegisterFormValid();

    public String getLanguage();

    public String getCountry();

    public String getTimezone();

    public void showWelcolmeDialog();

    public void unMask();

    public void maskProcessing();

    public void setSignInMessage(String message, int type);

    public void setRegisterMessage(String t, int error);

    public void hideMessages();

}
