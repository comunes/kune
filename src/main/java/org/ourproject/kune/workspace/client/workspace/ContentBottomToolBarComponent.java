
package org.ourproject.kune.workspace.client.workspace;

import org.ourproject.kune.platf.client.Component;
import org.ourproject.kune.platf.client.dto.StateDTO;

public interface ContentBottomToolBarComponent extends Component {

    void setEnabledRateIt(boolean enabled);

    void setRate(StateDTO state, boolean logged);

}
