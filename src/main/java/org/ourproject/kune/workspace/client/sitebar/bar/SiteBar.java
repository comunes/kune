package org.ourproject.kune.workspace.client.sitebar.bar;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.dto.UserInfoDTO;

public interface SiteBar {

    void showProgress(String text);

    void hideProgress();

    View getView();

    void showLoggedUser(UserInfoDTO user);

    void reloadUserInfo(String userHash);

    void doNewGroup(String returnToken);

    void doLogin(String returnToken);

    void showAlertMessage(String message);

    void doLogout();

    void mask();

    void unMask();

    void mask(String message);

    void setState(StateDTO state);

}
