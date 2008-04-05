
package org.ourproject.kune.platf.client.rpc;

import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.StateToken;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.SerializableException;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface GroupService extends RemoteService {

    StateToken createNewGroup(String userHash, GroupDTO group) throws SerializableException;

    void changeGroupWsTheme(String userHash, String groupShortName, String theme) throws SerializableException;

    public class App {
        private static GroupServiceAsync ourInstance = null;

        public static synchronized GroupServiceAsync getInstance() {
            if (ourInstance == null) {
                ourInstance = (GroupServiceAsync) GWT.create(GroupService.class);
                ((ServiceDefTarget) ourInstance).setServiceEntryPoint(GWT.getModuleBaseURL() + "GroupService");
            }
            return ourInstance;
        }

    }

}
