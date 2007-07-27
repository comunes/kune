package org.ourproject.kune.app.server;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LoggerMethodInterceptor implements MethodInterceptor {

    private static final Log log = LogFactory.getLog(LoggerMethodInterceptor.class);
    private String methodName;

    public Object invoke(MethodInvocation invocation) throws Throwable {
        this.methodName = invocation.getMethod().getName();
        logInvocation(invocation);

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
        StringBuffer buffer = new StringBuffer("[");
        buffer.append(methodName).append("] EXCEPTION => ");
        buffer.append(e.toString());
        log.debug(buffer);
    }

    private void logInvocation(MethodInvocation invocation) {
        StringBuffer buffer = new StringBuffer("INVOKING on [");
        addTargetCllassName(invocation, buffer);
        buffer.append("] => ");
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
        buffer.append(methodName);
    }

    private void logResult(Object result) {
        StringBuffer buffer = new StringBuffer("[");
        buffer.append(methodName).append("] RESULT => ");
        buffer.append(getValue(result));
        log.debug(buffer.toString());
    }

    private String getValue(Object result) {
        if (result == null)
            return "[null]";
        else
            return result.toString();
    }
}
