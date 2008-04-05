
package org.ourproject.kune.workspace.client.tags;

import java.util.List;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.TagResultDTO;

public interface TagsView extends View {

    void setTags(List<TagResultDTO> groupTags);

}
