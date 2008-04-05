
package org.ourproject.kune.workspace.client.workspace;

import org.ourproject.kune.platf.client.Component;
import org.ourproject.kune.platf.client.dto.StateDTO;

public interface GroupMembersComponent extends Component {

    public void showCollabs();

    public void showAdmins();

    public void setGroupMembers(StateDTO state);

}
