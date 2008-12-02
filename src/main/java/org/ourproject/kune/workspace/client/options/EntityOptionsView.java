package org.ourproject.kune.workspace.client.options;

import org.ourproject.kune.platf.client.View;

public interface EntityOptionsView extends View, AbstractOptionsView {

    void addOptionTab(View tab);

    void createAndShow();

    void hide();

    void setButtonVisible(boolean visible);

    void setGroupTitle();

    void setPersonalTitle();
}
