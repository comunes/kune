package org.ourproject.kune.workspace.client.workspace;

import org.ourproject.kune.platf.client.Component;
import org.ourproject.kune.platf.client.dto.AccessRightsDTO;
import org.ourproject.kune.platf.client.dto.GroupDTO;

public interface ParticipationComponent extends Component {

    void getParticipation(String user, GroupDTO group, AccessRightsDTO accessRightsDTO);

}
