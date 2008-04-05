package org.ourproject.kune.rack.filters;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.ourproject.kune.rack.RackHelper;

public class ForwardFilter extends InjectedFilter {
    // private static final Log log = LogFactory.getLog(ForwardFilter.class);

    private final String forward;
    private final Pattern pattern;

    public ForwardFilter(final String forward) {
        this(".*", forward);
    }

    public ForwardFilter(final String pattern, final String forward) {
        this.forward = forward;
        this.pattern = Pattern.compile(pattern);
    }

    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {

        String relativeURL = RackHelper.getRelativeURL(request);
        Matcher matcher = pattern.matcher(relativeURL);
        matcher.matches();

        int groupCount = matcher.groupCount();
        // log.debug("GROUP COUNT: " + groupCount);
        String replaced = forward;
        for (int index = 0; index < groupCount; index++) {
            String tag = "{" + index + "}";
            String group = matcher.group(index + 1);
            // log.debug("REPLACING " + tag + " WITH + " + group);
            replaced = replaced.replace(tag, group);
        }

        String forwardString = RackHelper.buildForwardString(request, replaced);
        forward(request, response, forwardString);
    }

    private void forward(final ServletRequest request, final ServletResponse response, final String forwardString)
            throws ServletException, IOException {
        // log.debug("FORWADING TO: " + forwardString);
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        httpRequest.getRequestDispatcher(forwardString).forward(request, response);
    }

}
