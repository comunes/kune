package org.ourproject.kune.workspace.client.workspace;

import org.ourproject.kune.platf.client.Component;
import org.ourproject.kune.workspace.client.socialnet.EntityLiveSearchListener;

public interface EntityLiveSearchComponent extends Component {

    void addListener(EntityLiveSearchListener presenter);

    void show();

}
