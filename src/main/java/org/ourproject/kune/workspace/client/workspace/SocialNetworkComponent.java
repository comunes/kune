package org.ourproject.kune.workspace.client.workspace;

import org.ourproject.kune.platf.client.Component;
import org.ourproject.kune.platf.client.dto.AccessRightsDTO;
import org.ourproject.kune.platf.client.dto.SocialNetworkDTO;

public interface SocialNetworkComponent extends Component {

    public void setSocialNetwork(SocialNetworkDTO socialNetwork, final AccessRightsDTO rights);

}
