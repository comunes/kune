/*
 *
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.platf.server;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LoggerMethodInterceptor implements MethodInterceptor {

    private static final Log log = LogFactory.getLog(LoggerMethodInterceptor.class);
    private final boolean simplifyNames;

    public LoggerMethodInterceptor(final boolean simplifyNames) {
	this.simplifyNames = simplifyNames;
    }

    public LoggerMethodInterceptor() {
	this(true);
    }

    public Object invoke(final MethodInvocation invocation) throws Throwable {
	logInvocation(invocation);
	try {
	    Object result = invocation.proceed();
	    logResult(invocation, result);
	    return result;
	} catch (Throwable e) {
	    logException(invocation, e);
	    throw e;
	}
    }

    protected void log(final String output) {
	log.debug(output);
    }

    protected void logInvocation(final MethodInvocation invocation) {
	StringBuffer buffer = createBuffer(invocation);
	addMethodName(invocation, buffer);
	addMethodParameters(invocation, buffer);
	log(buffer.toString());
    }

    protected void logResult(final MethodInvocation invocation, final Object result) {
	StringBuffer buffer = createBuffer(invocation);
	addMethodName(invocation, buffer);
	if (invocation.getMethod().getReturnType() != null) {
	    buffer.append(" => ");
	    buffer.append(getValue(result));
	}
	log(buffer.toString());
    }

    protected void logException(final MethodInvocation invocation, final Throwable e) {
	StringBuffer buffer = createBuffer(invocation);
	addMethodName(invocation, buffer);
	buffer.append(" EXCEPTION => ");
	buffer.append(e.toString());
	log(buffer.toString());
    }

    private StringBuffer createBuffer(final MethodInvocation invocation) {
	StringBuffer buffer = new StringBuffer();
	addTargetCllassName(invocation, buffer);
	return buffer;
    }

    private void addTargetCllassName(final MethodInvocation invocation, final StringBuffer buffer) {
	buffer.append(getSimpleName(invocation.getThis().getClass()));
    }

    private void addMethodParameters(final MethodInvocation invocation, final StringBuffer buffer) {
	buffer.append("(");
	Object[] arguments = invocation.getArguments();
	for (Object arg : arguments) {
	    buffer.append(getValue(arg)).append(", ");
	}
	buffer.append(")");
    }

    private void addMethodName(final MethodInvocation invocation, final StringBuffer buffer) {
	buffer.append(".");
	buffer.append(invocation.getMethod().getName());
    }

    private String getSimpleName(final Class<? extends Object> type) {
	String simpleName = type.getSimpleName();
	if (simplifyNames == true) {
	    int index = simpleName.indexOf('$');
	    if (index > 0) {
		simpleName = simpleName.substring(0, index);
	    }
	}
	return simpleName;
    }

    private String getValue(final Object result) {
	if (result == null) {
	    return "null";
	} else {
	    return result.toString();
	}
    }
}
