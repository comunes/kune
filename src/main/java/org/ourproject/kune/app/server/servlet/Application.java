package org.ourproject.kune.app.server.servlet;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;

public class Application {
    private final ArrayList<SimpleFilter> filters;
    private final ArrayList<LifeCycleListener> cycleListeners;
    private final String currentAppName;
    private final ApplicationFilter currentApp;

    public Application(final String appName, final String defaultFile, final String homePath) {
	filters = new ArrayList<SimpleFilter>();
	cycleListeners = new ArrayList<LifeCycleListener>();
	currentAppName = appName;
	currentApp = new ApplicationFilter(appName, defaultFile, homePath);
	filters.add(currentApp);
    }

    public SimpleFilter[] getFilters() {
	return filters.toArray(new SimpleFilter[filters.size()]);
    }

    public Application useService(final String serviceName, final Class<? extends RemoteService> serviceClass) {
	filters.add(new ServiceFilter(currentAppName, serviceName, serviceClass));
	return this;
    }

    public void add(final LifeCycleListener listener) {
	cycleListeners.add(listener);
    }

    public LifeCycleListener[] getCycleListeners() {
	return cycleListeners.toArray(new LifeCycleListener[cycleListeners.size()]);
    }

    public void with(final Class<? extends ApplicationListener> appListenerType) {
	currentApp.setListener(appListenerType);
    }

}
