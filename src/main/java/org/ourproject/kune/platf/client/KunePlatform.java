package org.ourproject.kune.platf.client;

import java.util.ArrayList;
import java.util.List;

import org.ourproject.kune.platf.client.dispatch.DefaultDispatcher;
import org.ourproject.kune.platf.client.dispatch.Dispatcher;
import org.ourproject.kune.platf.client.extend.ClientModule;
import org.ourproject.kune.platf.client.extend.Register;
import org.ourproject.kune.platf.client.inject.DefaultActionInjector;
import org.ourproject.kune.platf.client.services.Services;
import org.ourproject.kune.platf.client.utils.PrefetchUtilites;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.History;

public class KunePlatform implements Register {
    private List tools;
    private final App app;
    private final DefaultDispatcher dispatcher;

    public KunePlatform() {
	State state = new State();
	Services services = new Services();
	app = new App();
	this.dispatcher = new DefaultDispatcher(new DefaultActionInjector(state, app, services));
	History.addHistoryListener(dispatcher);
	this.tools = new ArrayList();
    }

    public void register(Tool tool) {
	tools.add(tool);
	dispatcher.subscribe(tool.getName(), tool.getStateAction());
    }

    public List getTools() {
	return tools;
    }

    public Dispatcher getDispatcher() {
	return dispatcher;
    }

    public void install(ClientModule module) {
	module.configure(this);
	module.registerActions(dispatcher);
    }

    public void prepare() {
	app.init(this);
	DeferredCommand.addCommand(new Command() {
	    public void execute() {
		PrefetchUtilites.preFetchImpImages();
	    }
	});

    }

}
