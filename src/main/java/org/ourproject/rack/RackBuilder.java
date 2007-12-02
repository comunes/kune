package org.ourproject.rack;

import javax.servlet.Filter;

import org.ourproject.rack.dock.RegexDock;
import org.ourproject.rack.filters.gwts.GWTServiceFilter;
import org.ourproject.rack.filters.rest.RESTServiceFilter;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.inject.Module;

public class RackBuilder {

	public static class RackDockBuilder {
		private String regex;
		private final Rack rack;

		public RackDockBuilder(Rack rack, String regex) {
			this.rack = rack;
			this.regex = regex;
		}

		public RackDockBuilder install(Filter... filters) {
			for (Filter filter : filters) {
				RegexDock dock = new RegexDock(regex);
				dock.setFilter(filter);
				rack.add(dock);
			}
			return this;
		}

	}

	private final Rack rack;

	public RackBuilder() {
		this.rack = new Rack();
	}

	public RackBuilder use(Module... list) {
		for (Module m : list) {
			rack.add(m);
		}
		return this;
	}

	public RackDockBuilder at(String regex) {
		return new RackDockBuilder(rack, regex);
	}

	public RackBuilder installGWTServices(String root, Class<? extends RemoteService>... serviceClasses) {

		for (Class<? extends RemoteService> serviceClass : serviceClasses) {
			String simpleName = serviceClass.getSimpleName();
			RegexDock dock = new RegexDock(root + simpleName + "$");
			dock.setFilter(new GWTServiceFilter(serviceClass));
			rack.add(dock);
		}

		return this;
	}

	public void installRESTServices(String root, Class<?>... serviceClasses) {
		for (Class<?> serviceClass : serviceClasses) {
			String simpleName = serviceClass.getSimpleName();
			String pattern = root + simpleName + "/(.*)$";
			RegexDock dock = new RegexDock(pattern);
			dock.setFilter(new RESTServiceFilter(pattern, serviceClass));
			rack.add(dock);
		}
	}

	public RackBuilder add(Class<? extends ContainerListener> listener) {
		rack.add(listener);
		return this;
	}

	public Rack getRack() {
		return rack;
	}
}
