package org.ourproject.kune.workspace.client.sitebar.rpc;

import org.ourproject.kune.platf.client.dto.UserDTO;
import org.ourproject.kune.platf.client.dto.UserInfoDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserServiceAsync {

    void login(String nickOrEmail, String passwd, AsyncCallback<UserInfoDTO> callback);

    void logout(String userHash, AsyncCallback<?> callback);

    void createUser(UserDTO user, AsyncCallback<UserInfoDTO> asyncCallback);

    void reloadUserInfo(String userHash, AsyncCallback<UserInfoDTO> asyncCallback);

    void onlyCheckSession(String userHash, AsyncCallback<?> asyncCallback);

}
