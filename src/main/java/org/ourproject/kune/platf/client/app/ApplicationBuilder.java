package org.ourproject.kune.platf.client.app;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.ourproject.kune.platf.client.KunePlatform;
import org.ourproject.kune.platf.client.dispatch.DefaultDispatcher;
import org.ourproject.kune.platf.client.dispatch.Dispatcher;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.state.ContentProviderImpl;
import org.ourproject.kune.platf.client.state.State;
import org.ourproject.kune.platf.client.state.StateController;
import org.ourproject.kune.platf.client.state.StateControllerDefault;
import org.ourproject.kune.platf.client.tool.Tool;
import org.ourproject.kune.platf.client.utils.PrefetchUtilites;
import org.ourproject.kune.workspace.client.actions.WorkspaceAction;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.RootPanel;

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
	RootPanel.get("initialstatusbar").setVisible(false);

	final State state = new State(userHash);
	ContentProviderImpl provider = new ContentProviderImpl(ContentService.App.getInstance());
	final StateController stateManager = new StateControllerDefault(provider, application, state);
	History.addHistoryListener(stateManager);

	final DefaultDispatcher dispatcher = new DefaultDispatcher();
	Dispatcher.App.instance = dispatcher;
	prepareActions(dispatcher, platform.getActions(), application, state, stateManager);

	application.init(dispatcher, stateManager);

	DeferredCommand.addCommand(new Command() {
	    public void execute() {
		GWT.log("Prefetching operation", null);
		GWT.log("Locale: " + Kune.getInstance().t.Locale(), null);
		PrefetchUtilites.preFetchImpImages();
		PrefetchUtilites.preFetchLicenses(state);
	    }
	});
	GWT.log("application builded!", null);
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
	    tools.put(tool.getName(), tool);
	}
	return tools;
    }
}
