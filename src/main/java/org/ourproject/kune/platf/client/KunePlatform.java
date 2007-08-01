package org.ourproject.kune.platf.client;

import java.util.ArrayList;

import org.ourproject.kune.platf.client.dispatch.DefaultDispatcher;
import org.ourproject.kune.platf.client.dispatch.Dispatcher;
import org.ourproject.kune.platf.client.extend.ClientModule;
import org.ourproject.kune.platf.client.extend.ContentProviderAsync;
import org.ourproject.kune.platf.client.extend.Register;
import org.ourproject.kune.platf.client.extend.ViewFactory;
import org.ourproject.kune.platf.client.inject.DefaultInjector;
import org.ourproject.kune.platf.client.services.Services;
import org.ourproject.kune.platf.client.utils.PrefetchUtilites;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.History;

public class KunePlatform implements Register {
    private ArrayList tools;
    private final App app;
    private final DefaultDispatcher dispatcher;

    public KunePlatform() {
	State state = new State();
	Services services = new Services();
	app = new App();
	this.dispatcher = new DefaultDispatcher(new DefaultInjector(state, app, services));
	History.addHistoryListener(dispatcher);
	this.tools = new ArrayList();
    }

    public void registerTool(String name, ContentProviderAsync provider, ViewFactory factory) {
	tools.add(new Tool(name, provider, factory));
    }

    public Tool getTool(int index) {
	return (Tool) tools.get(index);
    }

    public int getToolCount() {
	return tools.size();
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
