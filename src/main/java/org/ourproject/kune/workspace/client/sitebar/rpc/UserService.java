
package org.ourproject.kune.workspace.client.sitebar.rpc;

import org.ourproject.kune.platf.client.dto.UserDTO;
import org.ourproject.kune.platf.client.dto.UserInfoDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.SerializableException;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface UserService extends RemoteService {

    UserInfoDTO login(String nickOrEmail, String passwd) throws SerializableException;

    UserInfoDTO createUser(UserDTO user) throws SerializableException;

    void logout(String userHash) throws SerializableException;

    UserInfoDTO reloadUserInfo(String userHash) throws SerializableException;

    void onlyCheckSession(String userHash) throws SerializableException;

    public class App {
        private static UserServiceAsync ourInstance = null;

        public static synchronized UserServiceAsync getInstance() {
            if (ourInstance == null) {
                ourInstance = (UserServiceAsync) GWT.create(UserService.class);
                ((ServiceDefTarget) ourInstance).setServiceEntryPoint(GWT.getModuleBaseURL() + "UserService");
            }
            return ourInstance;
        }

        public static void setMock(final UserServiceAsync mock) {
            ourInstance = mock;
        }
    }
}
