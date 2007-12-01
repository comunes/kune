package org.ourproject.rack.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ourproject.rack.RackHelper;

public class LogDocker implements Filter {
	public static final Log log = LogFactory.getLog(LogDocker.class);
	
	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		long start = System.currentTimeMillis();
		log.debug("REQUEST: " + RackHelper.getURI(request));
		chain.doFilter(request, response);
		long finish = System.currentTimeMillis();
		log.debug("TOTAL TIME: " + (finish - start) + " miliseconds");
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}

}
