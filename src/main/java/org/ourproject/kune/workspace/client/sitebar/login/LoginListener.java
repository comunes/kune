package org.ourproject.kune.workspace.client.sitebar.login;

import org.ourproject.kune.platf.client.dto.UserInfoDTO;

public interface LoginListener {
    void userLoggedIn(UserInfoDTO user);

    void onLoginCancelled();

    void onLoginClose();
}
