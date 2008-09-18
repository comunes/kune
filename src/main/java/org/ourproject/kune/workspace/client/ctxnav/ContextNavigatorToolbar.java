package org.ourproject.kune.workspace.client.ctxnav;

import org.ourproject.kune.platf.client.actions.ActionManager;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.workspace.client.component.EntityToolbar;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.calclab.suco.client.provider.Provider;

public class ContextNavigatorToolbar extends EntityToolbar {
    public ContextNavigatorToolbar(final Session session, final Provider<ActionManager> actionManagerProvider,
	    final WorkspaceSkeleton ws) {
	super(ToolbarPosition.context, session, actionManagerProvider, ws);
    }
}
