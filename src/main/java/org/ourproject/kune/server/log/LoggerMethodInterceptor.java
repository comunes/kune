/*
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 2 dated June, 1991.
 * 
 * This package is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 *
 */

package org.ourproject.kune.server.log;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LoggerMethodInterceptor implements MethodInterceptor {
    private static final Log log = LogFactory.getLog(LoggerMethodInterceptor.class);
    
    public Object invoke(MethodInvocation invocation) throws Throwable {
	logInvocation(invocation);
	
        // INVOKE THE METHOD!!!
	try {
        Object result = invocation.proceed();
        logResult(result);
        return result;
	} catch (Throwable e) {
	    logException(e);
	    throw e;
	}
    }

    private void logException(Throwable e) {
	StringBuffer buffer= new StringBuffer("EXCEPTION => ");
	buffer.append(e.toString());
	log.debug(buffer);
    }

    private void logInvocation(MethodInvocation invocation) {
	StringBuffer buffer = new StringBuffer("INVOKING on [");
	addTargetCllassName(invocation, buffer);
	buffer.append ("] => ");
        addMethodName(invocation, buffer);
        addMethodParameters(invocation, buffer);
        log.debug(buffer.toString());
    }

    private void addTargetCllassName(MethodInvocation invocation, StringBuffer buffer) {
	buffer.append(invocation.getThis().getClass().getName());
    }

    private void addMethodParameters(MethodInvocation invocation, StringBuffer buffer) {
	buffer.append(" [ ");
        Object[] arguments = invocation.getArguments();
        for (Object arg : arguments) {
            buffer.append(getValue(arg)).append(" |\n ");
        }
        buffer.append(" ] ");
    }

    private void addMethodName(MethodInvocation invocation, StringBuffer buffer) {
	buffer.append(invocation.getMethod().getName());
    }
    
    private void logResult(Object result) {
	StringBuffer buffer = new StringBuffer("RESULT => ");
        buffer.append(getValue(result));
        log.debug(buffer.toString());
    }

    private String getValue(Object result) {
        if (result == null) return "[null]";
        else return result.toString();
    }
}
