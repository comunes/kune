package org.ourproject.kune.platf.client;

import org.ourproject.kune.platf.client.app.Application;
import org.ourproject.kune.platf.client.dispatch.Dispatcher;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateController;

/**
 * I hope this is the only singleton in the app!!
 * 
 * it could be done by injecting inside the actions, but i want to save memory
 * (Initialized in ApplicationBuilder)
 * 
 */
public class Services {
    private static Services instance;
    public final Application app;
    public final Session session;
    public final StateController stateManager;
    public final Dispatcher dispatcher;
    public final String user;

    private Services(final String userHash, final Application application, final Session session,
	    final StateController stateManager, final Dispatcher dispatcher) {
	this.user = userHash;
	this.app = application;
	this.session = session;
	this.stateManager = stateManager;
	this.dispatcher = dispatcher;
    }

    public static void init(final String userHash, final Application application, final Session session,
	    final StateController stateManager, final Dispatcher dispatcher) {
	instance = new Services(userHash, application, session, stateManager, dispatcher);
    }

    public final static Services get() {
	return instance;
    }
}
