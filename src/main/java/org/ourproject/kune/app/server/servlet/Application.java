package org.ourproject.kune.app.server.servlet;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.inject.Module;

public class Application {
    private final ArrayList<SimpleFilter> filters;
    private final ArrayList<Module> modules;
    private final ArrayList<LifeCycleListener> cycleListeners;
    private String currentAppName;
    private ApplicationFilter currentApp;

    public Application() {
	filters = new ArrayList<SimpleFilter>();
	modules = new ArrayList<Module>();
	cycleListeners = new ArrayList<LifeCycleListener>();
    }

    public SimpleFilter[] getFilters() {
	return filters.toArray(new SimpleFilter[filters.size()]);
    }

    public void use(final Module module) {
	modules.add(module);
    }

    public Application create(final String appName, final String defaultFile, final String homePath) {
	currentAppName = appName;
	currentApp = new ApplicationFilter(appName, defaultFile, homePath);
	filters.add(currentApp);
	return this;
    }

    public Application useService(final String serviceName, final Class<? extends RemoteService> serviceClass) {
	filters.add(new ServiceFilter(currentAppName, serviceName, serviceClass));
	return this;
    }

    public void add(final LifeCycleListener listener) {
	cycleListeners.add(listener);
    }

    public Module[] getModules() {
	return modules.toArray(new Module[modules.size()]);
    }

    public LifeCycleListener[] getCycleListeners() {
	return cycleListeners.toArray(new LifeCycleListener[cycleListeners.size()]);
    }

    public void with(final ApplicationListener appListener) {
	currentApp.setListener(appListener);
    }

}
