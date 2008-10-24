package org.ourproject.kune.workspace.client.signin;

import org.ourproject.kune.platf.client.dto.StateToken;

public interface Register {

    void doRegister(StateToken previousStateToken);

    void hide();

}
