package org.ourproject.kune.app.server.servlet;

import java.util.ArrayList;

import com.google.inject.Module;

public class ApplicationBuilder {
    private final ArrayList<Module> modules;
    private final ArrayList<Application> apps;

    public ApplicationBuilder() {
	modules = new ArrayList<Module>();
	apps = new ArrayList<Application>();
    }

    public void use(final Module module) {
	modules.add(module);
    }

    public Module[] getModules() {
	return modules.toArray(new Module[modules.size()]);
    }

    public Application create(final String appName, final String defaultFile, final String homePath) {
	Application app = new Application(appName, defaultFile, homePath);
	apps.add(app);
	return app;
    }

    public Application getApplication() {
	return apps.get(0);
    }

}
