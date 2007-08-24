package org.ourproject.kune.sitebar.client.login;

import org.ourproject.kune.platf.client.View;

public interface NewUserFormView extends View {

    String getShortName();

    String getName();

    String getEmail();

    String getPassword();

    void clearData();

}
