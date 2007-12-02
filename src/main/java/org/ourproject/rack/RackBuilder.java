package org.ourproject.rack;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ourproject.kune.platf.server.manager.GroupManager;
import org.ourproject.rack.dock.Dock;
import org.ourproject.rack.dock.RegexDock;
import org.ourproject.rack.filters.gwts.GWTServiceFilter;
import org.ourproject.rack.filters.rest.RESTServiceFilter;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.inject.Module;

public class RackBuilder {
	public static final Log log = LogFactory.getLog(RackBuilder.class);
	
	public static class RackDockBuilder {
		private String regex;
		private final RackBuilder builder;

		public RackDockBuilder(RackBuilder builder, String regex) {
			this.builder = builder;
			this.regex = regex;
		}

		public RackDockBuilder install(Filter filter) {
			RegexDock dock = new RegexDock(regex);
			dock.setFilter(filter);
			builder.add(dock);
			return this;
		}
		
	}

	private final ArrayList<Dock> docks;
	private final ArrayList<Module> modules;
	private final ArrayList<Class<? extends ContainerListener>> listeners;
	
	public RackBuilder() {
		this.docks = new ArrayList<Dock>();
		this.modules = new ArrayList<Module>();
		this.listeners = new ArrayList<Class<? extends ContainerListener>>();
	}
	
	public void add(Dock dock) {
		log.debug("INSTALLING: " + dock.toString());
		docks.add(dock);
	}



	public RackBuilder use(Module... list) {
		for (Module m : list) {
			modules.add(m);
		}
		return this;
	}

	public RackDockBuilder at(String regex) {
		return new RackDockBuilder(this, regex);
	}

	public List<Dock> getDocks() {
		return docks;
	}

	public List<Module> getGuiceModules(){
		return modules;
	}

	public void addListener(Class<? extends ContainerListener> listenerType) {
		listeners.add(listenerType);
	}

	public List<Class<? extends ContainerListener>> getListeners() {
		return listeners;
	}

	public RackBuilder installGWTServices(String root, Class<? extends RemoteService>... serviceClasses) {
		
		for (Class<? extends RemoteService> serviceClass : serviceClasses) {
			String simpleName = serviceClass.getSimpleName();
			RegexDock dock = new RegexDock(root + simpleName + "$");
			dock.setFilter(new GWTServiceFilter(serviceClass));
			add(dock);
		}
		
		return this;
	}

	public void installRESTServices(String root, Class<?>... serviceClasses) {
		for (Class<?> serviceClass : serviceClasses) {
			String simpleName = serviceClass.getSimpleName();
			String pattern = root + simpleName + "/(.*)$";
			RegexDock dock = new RegexDock(pattern);
			dock.setFilter(new RESTServiceFilter(pattern, serviceClass));
			add(dock);
		}
	}

}
