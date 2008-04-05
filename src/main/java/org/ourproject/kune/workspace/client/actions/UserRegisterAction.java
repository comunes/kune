package org.ourproject.kune.workspace.client.actions;

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.dto.UserDTO;
import org.ourproject.kune.platf.client.dto.UserInfoDTO;
import org.ourproject.kune.platf.client.rpc.ParamCallback;
import org.ourproject.kune.workspace.client.sitebar.rpc.UserService;
import org.ourproject.kune.workspace.client.sitebar.rpc.UserServiceAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class UserRegisterAction implements Action<ParamCallback<UserDTO, UserInfoDTO>> {

    @SuppressWarnings("unchecked")
    public void execute(final ParamCallback<UserDTO, UserInfoDTO> paramCall) {
        onRegister(paramCall.getParam(), (AsyncCallback<UserInfoDTO>) paramCall.getParam());
    }

    private void onRegister(final UserDTO user, final AsyncCallback<UserInfoDTO> callback) {
        UserServiceAsync userService = UserService.App.getInstance();
        userService.createUser(user, callback);
    }
}
