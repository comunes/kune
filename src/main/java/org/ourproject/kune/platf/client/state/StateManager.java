
package org.ourproject.kune.platf.client.state;

import org.ourproject.kune.platf.client.dto.SocialNetworkResultDTO;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.dto.StateToken;

import com.google.gwt.user.client.HistoryListener;

public interface StateManager extends HistoryListener {

    void reload();

    void setSocialNetwork(SocialNetworkResultDTO socialNet);

    void setRetrievedState(StateDTO content);

    void setState(StateToken state);

    void reloadContextAndTitles();

}
