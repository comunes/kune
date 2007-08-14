package org.ourproject.kune.sitebar.client.login;

import org.ourproject.kune.platf.client.View;

public interface LoginView extends View {

    void setEnabledLoginButton(boolean enable);

    void clearData();

}
