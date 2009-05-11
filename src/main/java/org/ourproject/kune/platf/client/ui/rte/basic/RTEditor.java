package org.ourproject.kune.platf.client.ui.rte.basic;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.actions.ActionAddCondition;
import org.ourproject.kune.platf.client.actions.ActionCollection;
import org.ourproject.kune.platf.client.actions.ActionDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarPosition;
import org.ourproject.kune.platf.client.actions.toolbar.ActionToolbar;

import com.calclab.suco.client.events.Listener0;

public interface RTEditor {

    public final ActionToolbarPosition topbarPosition = new ActionToolbarPosition("rte-topbar");
    public final ActionToolbarPosition sndbarPosition = new ActionToolbarPosition("rte-sndbar");;
    public final ActionToolbarPosition linkCtxPosition = new ActionToolbarPosition("rte-linkctx");;

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

    void reset();

    void setExtended(boolean extended);

    void setFocus(boolean focus);

    void setHtml(String html);

    void setText(String text);
}
