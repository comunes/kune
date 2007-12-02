package org.ourproject.rack;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ourproject.rack.dock.Dock;

import com.google.inject.Module;

public class Rack {
	public static final Log log = LogFactory.getLog(Rack.class);
	private final ArrayList<Dock> docks;
	private final ArrayList<Module> modules;
	private final ArrayList<Class<? extends ContainerListener>> listeners;

	public Rack() {
		this.docks = new ArrayList<Dock>();
		this.modules = new ArrayList<Module>();
		this.listeners = new ArrayList<Class<? extends ContainerListener>>();
	}

	public void add(Dock dock) {
		log.debug("INSTALLING: " + dock.toString());
		docks.add(dock);
	}

	public void add(Module module) {
		modules.add(module);
	}
	
	public List<Dock> getDocks() {
		return docks;
	}

	public List<Module> getGuiceModules(){
		return modules;
	}

	public void add(Class<? extends ContainerListener> listenerType) {
		listeners.add(listenerType);
	}

	public List<Class<? extends ContainerListener>> getListeners() {
		return listeners;
	}


}
