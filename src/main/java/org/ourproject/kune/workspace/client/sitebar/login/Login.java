package org.ourproject.kune.workspace.client.sitebar.login;

import org.ourproject.kune.platf.client.View;

public interface Login {

    public void onCancel();

    public void doLogin();

    public View getView();

    public void doRegister();
}
