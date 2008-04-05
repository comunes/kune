
package org.ourproject.kune.platf.client.rpc;

import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.StateToken;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GroupServiceAsync {

    void createNewGroup(String userHash, GroupDTO group, AsyncCallback<StateToken> callback);

    void changeGroupWsTheme(String userHash, String groupShortName, String theme, AsyncCallback<?> callback);

}
