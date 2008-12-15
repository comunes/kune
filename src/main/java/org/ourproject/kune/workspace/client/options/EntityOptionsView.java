package org.ourproject.kune.workspace.client.options;

import org.ourproject.kune.platf.client.View;

public interface EntityOptionsView extends View, AbstractOptionsView {

    void setButtonVisible(boolean visible);

    void setGroupTitle();

    void setPersonalTitle();
}
