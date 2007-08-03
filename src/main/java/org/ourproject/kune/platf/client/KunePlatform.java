package org.ourproject.kune.platf.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.dispatch.DefaultDispatcher;
import org.ourproject.kune.platf.client.extend.ClientModule;
import org.ourproject.kune.platf.client.extend.Register;
import org.ourproject.kune.platf.client.services.Services;
import org.ourproject.kune.platf.client.utils.PrefetchUtilites;
import org.ourproject.kune.platf.client.workspace.actions.DefaultActionInjector;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.History;

public class KunePlatform implements Register {
    private List tools;
    private HashMap actions;

    public KunePlatform() {
	this.tools = new ArrayList();
	this.actions = new HashMap();
    }

    public void addTool(Tool tool) {
	tools.add(tool);
	actions.put(tool.getName(), tool.getStateAction());
    }
    public void addAction(String eventName, Action action) {
	actions.put(eventName, action);
    }

    public void install(ClientModule module) {
	module.configure(this);
    }

    public Application buildApplication(String userHash) {
	State state = new State(userHash);
	Services services = new Services();
	DefaultApplication app = new DefaultApplication(state, services);
	DefaultDispatcher dispatcher = new DefaultDispatcher(new DefaultActionInjector(app));
	app.setDispatcher(dispatcher);
	History.addHistoryListener(dispatcher);
	app.init(tools);
	dispatcher.subscribeAll(actions);
	DeferredCommand.addCommand(new Command() {
	    public void execute() {
		PrefetchUtilites.preFetchImpImages();
	    }
	});
	return app;
    }

}
