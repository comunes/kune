package org.ourproject.rack;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ourproject.rack.dock.Dock;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class RackServletFilter implements Filter {
	private static final String MODULE_PARAMETER = RackModule.class.getName();
	private static final Log log = LogFactory.getLog(RackServletFilter.class);
	public static final String INJECTOR_ATTRIBUTE = Injector.class.getName();
	private List<Dock> docks;
	

	public static class DockChain implements FilterChain {
		private final Iterator<Dock> iterator;

		public DockChain(Iterator<Dock> iterator) {
			this.iterator = iterator;
		}

		public void doFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException {
			Dock dock = null;
			boolean matched = false;
			
			while (!matched && iterator.hasNext()) {
				dock = iterator.next();
				matched = dock.matches(request);
			}
			if (matched) {
				execute(dock.getFilter(), request, response);
			}
		}

		private void execute(Filter filter, ServletRequest request, ServletResponse response) throws IOException,
				ServletException {
			log.debug("RACK FILTER: " + filter.getClass().getSimpleName());
			filter.doFilter(request, response, this);
		}
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {

//		log.debug("RACK: " + RackHelper.getURI(request));
		chain = new DockChain(docks.iterator());
		chain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		log.debug("INITIALIZING RackServletFilter...");
		RackModule module = getModule(filterConfig);
		RackBuilder builder = new RackBuilder();
		module.configure(builder);
		
		Rack rack = builder.getRack();
		Injector injector = installInjector(filterConfig, rack);
		startContainerListeners(rack.getListeners(), injector);
		
		docks = rack.getDocks();
		initFilters(filterConfig);
		log.debug("INITIALIZATION DONE!");
	}

	private void startContainerListeners(List<Class<? extends ContainerListener>> listenerClasses, Injector injector) {
		log.debug("STARTING CONTAINER LISTENERS...");
		for (Class<? extends ContainerListener> listenerClass : listenerClasses) {
			ContainerListener listener = injector.getInstance(listenerClass);
			listener.start();
		}
	}

	private void stopContainerListeners(List<Class<? extends ContainerListener>> listenerClasses, Injector injector) {
		log.debug("STOPING CONTAINER LISTENERS...");
		for (Class<? extends ContainerListener> listenerClass : listenerClasses) {
			ContainerListener listener = injector.getInstance(listenerClass);
			listener.stop();
		}
	}

	private Injector installInjector(FilterConfig filterConfig, Rack rack) {
		Injector injector = Guice.createInjector(rack.getGuiceModules());
		filterConfig.getServletContext().setAttribute(INJECTOR_ATTRIBUTE, injector);
		return injector;
	}

	private void initFilters(FilterConfig filterConfig) throws ServletException {
		for (Dock dock : docks) {
			dock.getFilter().init(filterConfig);
		}
 	}

	public void destroy() {
		for (Dock dock : docks) {
			dock.getFilter().destroy();
		}
	}


	private RackModule getModule(FilterConfig filterConfig) {
		String moduleName = getModuleName(filterConfig);
		try {
			Class<?> clazz = Class.forName(moduleName);
			RackModule module = (RackModule) clazz.newInstance();
			return module;
		} catch (Exception e) {
			throw new RuntimeException("couldn't instantiate the rack module", e);
		}
	}

	private String getModuleName(FilterConfig filterConfig) {
		String moduleName = filterConfig.getInitParameter(MODULE_PARAMETER);
		if (moduleName == null) {
			throw new RuntimeException("Rack module name can't be null!");
		}
		return moduleName;
	}

}
