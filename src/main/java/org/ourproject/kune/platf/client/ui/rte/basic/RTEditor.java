package org.ourproject.kune.platf.client.ui.rte.basic;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.actions.ActionAddCondition;
import org.ourproject.kune.platf.client.actions.ActionCollection;
import org.ourproject.kune.platf.client.actions.ActionDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarPosition;
import org.ourproject.kune.platf.client.actions.toolbar.ActionToolbar;

import com.calclab.suco.client.events.Listener0;

public interface RTEditor {

    public final ActionToolbarPosition topbar = new ActionToolbarPosition("rte-topbar");
    public final ActionToolbarPosition sndbar = new ActionToolbarPosition("rte-sndbar");;

    void addAction(ActionDescriptor<Object> action);

    void addActions(ActionCollection<Object> actions);

    void addOnEditListener(Listener0 listener);

    void attach();

    ActionAddCondition<Object> canBeBasic();

    ActionAddCondition<Object> canBeExtended();

    void detach();

    View getEditorArea();

    String getHtml();

    ActionToolbar<Object> getSndBar();

    String getText();

    ActionToolbar<Object> getTopBar();

    void setExtended(boolean extended);

    void setHtml(String html);

    void setText(String text);
}
