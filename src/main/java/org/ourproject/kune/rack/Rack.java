package org.ourproject.kune.rack;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ourproject.kune.rack.dock.Dock;
import org.ourproject.kune.rack.dock.RequestMatcher;

import com.google.inject.Module;

public class Rack {
    public static final Log log = LogFactory.getLog(Rack.class);
    private final ArrayList<Dock> docks;
    private final ArrayList<Module> modules;
    private final ArrayList<RequestMatcher> excludes;
    private final ArrayList<Class<? extends ContainerListener>> listeners;

    public Rack() {
        this.docks = new ArrayList<Dock>();
        this.modules = new ArrayList<Module>();
        this.excludes = new ArrayList<RequestMatcher>();
        this.listeners = new ArrayList<Class<? extends ContainerListener>>();
    }

    public void add(final Dock dock) {
        log.debug("INSTALLING: " + dock.toString());
        docks.add(dock);
    }

    public void add(final Module module) {
        modules.add(module);
    }

    public List<Dock> getDocks() {
        return docks;
    }

    public List<Module> getGuiceModules() {
        return modules;
    }

    public void add(final Class<? extends ContainerListener> listenerType) {
        listeners.add(listenerType);
    }

    public List<Class<? extends ContainerListener>> getListeners() {
        return listeners;
    }

	public void addExclusion(RequestMatcher matcher) {
		excludes.add(matcher);
	}

	public List<RequestMatcher> getExcludes() {
		return excludes;
	}

}
