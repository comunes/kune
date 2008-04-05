package org.ourproject.kune.workspace.client.socialnet;

import org.ourproject.kune.platf.client.View;

public interface EntityLiveSearchListener extends View {

    void onSelection(String shortName, String longName);

}
