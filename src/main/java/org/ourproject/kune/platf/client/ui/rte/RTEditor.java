package org.ourproject.kune.platf.client.ui.rte;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.actions.ActionCollection;
import org.ourproject.kune.platf.client.actions.ActionDescriptor;
import org.ourproject.kune.platf.client.actions.toolbar.ActionToolbar;
import org.ourproject.kune.platf.client.ui.rte.RTEditorPresenter.ActionPosition;

import com.calclab.suco.client.events.Listener0;

public interface RTEditor {

    void addAction(ActionDescriptor<Object> action, ActionPosition position);

    void addActions(ActionCollection<Object> actions, ActionPosition position);

    void addOnEditListener(Listener0 listener);

    void attach();

    void editContent(String content);

    View getEditorArea();

    ActionToolbar<Object> getSndBar();

    ActionToolbar<Object> getTopBar();

    void setExtended(boolean extended);

}
