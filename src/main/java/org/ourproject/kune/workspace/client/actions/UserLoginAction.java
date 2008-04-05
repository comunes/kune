package org.ourproject.kune.workspace.client.actions;

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.dto.UserDTO;
import org.ourproject.kune.platf.client.dto.UserInfoDTO;
import org.ourproject.kune.platf.client.rpc.ParamCallback;
import org.ourproject.kune.workspace.client.sitebar.rpc.UserService;
import org.ourproject.kune.workspace.client.sitebar.rpc.UserServiceAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class UserLoginAction implements Action<ParamCallback<UserDTO, UserInfoDTO>> {

    public void execute(final ParamCallback<UserDTO, UserInfoDTO> paramCall) {
        onLogin(paramCall.getParam(), paramCall.getCallback());
    }

    private void onLogin(final UserDTO user, final AsyncCallback<UserInfoDTO> callback) {
        UserServiceAsync siteBarService = UserService.App.getInstance();
        siteBarService.login(user.getShortName(), user.getPassword(), callback);
    }
}
