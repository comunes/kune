package org.ourproject.kune.rack.filters.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ourproject.kune.rack.RackHelper;
import org.ourproject.kune.rack.filters.InjectedFilter;

import com.google.inject.Inject;

public class RESTServiceFilter extends InjectedFilter {
    private static final Log log = LogFactory.getLog(RESTServiceFilter.class);

    private final Pattern pattern;
    private final Class<?> serviceClass;

    @Inject
    private TransactionalServiceExecutor transactionalFilter;

    public RESTServiceFilter(final String pattern, final Class<?> serviceClass) {
        this.serviceClass = serviceClass;
        this.pattern = Pattern.compile(pattern);
    }

    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {

        String methodName = getMethodName(request);
        ParametersAdapter parameters = new ParametersAdapter(request);
        log.debug("JSON METHOD: '" + methodName + "' on: " + serviceClass.getSimpleName());

        response.setCharacterEncoding("utf-8");
        response.setContentType("text/json");
        Object output = transactionalFilter.doService(serviceClass, methodName, parameters, getInstance(serviceClass));
        if (output != null) {
            PrintWriter writer = response.getWriter();
            writer.print(output);
            writer.flush();
        } else {
            chain.doFilter(request, response);
        }
    }

    private String getMethodName(final ServletRequest request) {
        String relativeURL = RackHelper.getRelativeURL(request);
        Matcher matcher = pattern.matcher(relativeURL);
        matcher.find();
        String methodName = matcher.group(1);
        return methodName;
    }
}
