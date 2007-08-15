package org.ourproject.kune.sitebar.client.login;

import org.ourproject.kune.platf.client.View;

public interface LoginFormView extends View {

    public void clearData();

    public String getNickOrEmail();

    public String getPassword();

}
