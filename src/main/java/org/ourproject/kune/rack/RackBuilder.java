package org.ourproject.kune.rack;

import javax.servlet.Filter;

import org.ourproject.kune.rack.dock.RegexDock;
import org.ourproject.kune.rack.dock.RegexMatcher;
import org.ourproject.kune.rack.filters.gwts.GWTServiceFilter;
import org.ourproject.kune.rack.filters.rest.RESTServiceFilter;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.inject.Module;

public class RackBuilder {

	public static class RackDockBuilder {
		private final String regex;
		private final Rack rack;

		public RackDockBuilder(final Rack rack, final String regex) {
			this.rack = rack;
			this.regex = regex;
		}

		public RackDockBuilder install(final Filter... filters) {
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

	public RackBuilder use(final Module... list) {
		for (Module m : list) {
			rack.add(m);
		}
		return this;
	}

	public RackDockBuilder at(final String regex) {
		return new RackDockBuilder(rack, regex);
	}

	public RackBuilder installGWTServices(final String root, final Class<? extends RemoteService>... serviceClasses) {

		for (Class<? extends RemoteService> serviceClass : serviceClasses) {
			String simpleName = serviceClass.getSimpleName();
			RegexDock dock = new RegexDock(root + simpleName + "$");
			dock.setFilter(new GWTServiceFilter(serviceClass));
			rack.add(dock);
		}

		return this;
	}

	public void installRESTServices(final String root, final Class<?>... serviceClasses) {
		for (Class<?> serviceClass : serviceClasses) {
			String simpleName = serviceClass.getSimpleName();
			String pattern = root + simpleName + "/(.*)$";
			RegexDock dock = new RegexDock(pattern);
			dock.setFilter(new RESTServiceFilter(pattern, serviceClass));
			rack.add(dock);
		}
	}

	public RackBuilder add(final Class<? extends ContainerListener> listener) {
		rack.add(listener);
		return this;
	}

	public Rack getRack() {
		return rack;
	}

	public RackBuilder exclude(final String... excludes) {
		for (String exclude : excludes) {
			rack.addExclusion(new RegexMatcher(exclude));
		}
		return this;
	}
}
