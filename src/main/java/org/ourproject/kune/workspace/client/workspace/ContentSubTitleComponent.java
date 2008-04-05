
package org.ourproject.kune.workspace.client.workspace;

import org.ourproject.kune.platf.client.Component;
import org.ourproject.kune.platf.client.dto.StateDTO;

public interface ContentSubTitleComponent extends Component {

    void setState(StateDTO state);

    void setContentLanguage(String langName);

}
