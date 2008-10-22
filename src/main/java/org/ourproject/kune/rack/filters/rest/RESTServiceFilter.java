/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
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
