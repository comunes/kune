package org.ourproject.kune.platf.client.app;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.ourproject.kune.platf.client.DefaultStateManager;
import org.ourproject.kune.platf.client.KunePlatform;
import org.ourproject.kune.platf.client.State;
import org.ourproject.kune.platf.client.StateManager;
import org.ourproject.kune.platf.client.Tool;
import org.ourproject.kune.platf.client.dispatch.DefaultDispatcher;
import org.ourproject.kune.platf.client.utils.PrefetchUtilites;
import org.ourproject.kune.workspace.client.actions.WorkspaceAction;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.History;

public class ApplicationBuilder {
    private final String userHash;
    private final KunePlatform platform;
    private final HashMap tools;

    public ApplicationBuilder(final String userHash, final KunePlatform platform) {
	this.userHash = userHash;
	this.platform = platform;
	this.tools = new HashMap();
    }

    public Application build() {

	DefaultDispatcher dispatcher = new DefaultDispatcher();
	final State state = new State(userHash);
	String defaultToolName = prepareTools(platform.getTools(), state, dispatcher);
	DefaultApplication application = new DefaultApplication(tools, defaultToolName, dispatcher);

	StateManager stateManager = new DefaultStateManager(application, state);
	prepareActions(dispatcher, platform.getActions(), application, state, stateManager);

	History.addHistoryListener(stateManager);
	DeferredCommand.addCommand(new Command() {
	    public void execute() {
		PrefetchUtilites.preFetchImpImages();
		PrefetchUtilites.preFetchLicenses(state);
	    }
	});
	return application;
    }

    public void prepareActions(final DefaultDispatcher dispatcher, final Map actions,
	    final DefaultApplication application, final State state, final StateManager stateManager) {
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

    private String prepareTools(final List toolList, final State state, final DefaultDispatcher dispatcher) {
	int total = toolList.size();
	for (int index = 0; index < total; index++) {
	    Tool tool = (Tool) toolList.get(index);
	    tool.setEnvironment(dispatcher, state);
	    tools.put(tool.getName(), tool);
	}
	return ((Tool) toolList.get(0)).getName();
    }

}
