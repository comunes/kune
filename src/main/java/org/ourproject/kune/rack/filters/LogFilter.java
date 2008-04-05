package org.ourproject.kune.rack.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ourproject.kune.rack.RackHelper;

public class LogFilter implements Filter {
    public static final Log log = LogFactory.getLog(LogFilter.class);

    public void destroy() {
    }

    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        long start = System.currentTimeMillis();
        log.debug("REQUEST: " + RackHelper.getURI(request));
        chain.doFilter(request, response);
        long finish = System.currentTimeMillis();
        log.debug("TOTAL TIME: " + (finish - start) + " miliseconds");
    }

    public void init(final FilterConfig filterConfig) throws ServletException {
    }

}
