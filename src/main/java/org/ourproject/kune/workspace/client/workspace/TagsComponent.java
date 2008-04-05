
package org.ourproject.kune.workspace.client.workspace;

import java.util.List;

import org.ourproject.kune.platf.client.Component;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.dto.TagResultDTO;

public interface TagsComponent extends Component {

    void setState(StateDTO state);

    void setGroupTags(List<TagResultDTO> result);

}
