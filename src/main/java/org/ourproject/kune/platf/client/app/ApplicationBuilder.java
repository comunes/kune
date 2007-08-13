package org.ourproject.kune.platf.client.app;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.ourproject.kune.platf.client.KunePlatform;
import org.ourproject.kune.platf.client.Tool;
import org.ourproject.kune.platf.client.dispatch.DefaultDispatcher;
import org.ourproject.kune.platf.client.state.State;
import org.ourproject.kune.platf.client.state.StateController;
import org.ourproject.kune.platf.client.state.StateControllerDefault;
import org.ourproject.kune.platf.client.utils.PrefetchUtilites;
import org.ourproject.kune.workspace.client.actions.WorkspaceAction;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.History;

public class ApplicationBuilder {
    private final String userHash;
    private final KunePlatform platform;

    public ApplicationBuilder(final String userHash, final KunePlatform platform) {
	this.userHash = userHash;
	this.platform = platform;
    }

    public Application build() {
	HashMap tools = indexTools(platform.getTools());
	DefaultApplication application = new DefaultApplication(tools);

	final State state = new State(userHash);
	final StateController stateManager = new StateControllerDefault(application, state);
	History.addHistoryListener(stateManager);

	final DefaultDispatcher dispatcher = new DefaultDispatcher();
	prepareActions(dispatcher, platform.getActions(), application, state, stateManager);

	application.init(dispatcher, stateManager);

	DeferredCommand.addCommand(new Command() {
	    public void execute() {
		PrefetchUtilites.preFetchImpImages();
		PrefetchUtilites.preFetchLicenses(state);
	    }
	});
	return application;
    }

    public void prepareActions(final DefaultDispatcher dispatcher, final Map actions,
	    final DefaultApplication application, final State state, final StateController stateManager) {
	WorkspaceAction action;
	String eventName;

	Iterator iterator = actions.keySet().iterator();
	while (iterator.hasNext()) {
	    eventName = (String) iterator.next();
	    action = (WorkspaceAction) actions.get(eventName);
	    action.init(application, state, stateManager);
	    dispatcher.subscribe(eventName, action);
	}
    }

    private HashMap indexTools(final List toolList) {
	HashMap tools = new HashMap();
	int total = toolList.size();
	for (int index = 0; index < total; index++) {
	    Tool tool = (Tool) toolList.get(index);
	    tools.put(tool.getLabel(), tool);
	}
	return tools;
    }
}
