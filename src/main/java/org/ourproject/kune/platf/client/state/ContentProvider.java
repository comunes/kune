
package org.ourproject.kune.platf.client.state;

import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.dto.StateToken;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ContentProvider {

    void getContent(String user, StateToken newState, AsyncCallback<StateDTO> callback);

    void cache(StateToken encodeState, StateDTO content);

}
