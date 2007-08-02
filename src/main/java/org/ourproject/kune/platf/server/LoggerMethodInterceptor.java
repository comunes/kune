package org.ourproject.kune.platf.server;

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
            logResult(invocation, result);
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
        StringBuffer buffer = createBuffer(invocation);
        addMethodName(buffer);
        addMethodParameters(invocation, buffer);
        log.debug(buffer.toString());
    }

    private StringBuffer createBuffer(MethodInvocation invocation) {
        StringBuffer buffer = new StringBuffer("[");
        addTargetCllassName(invocation, buffer);
        buffer.append("]");
        return buffer;
    }

    private void addTargetCllassName(MethodInvocation invocation, StringBuffer buffer) {
        buffer.append(invocation.getThis().getClass().getSimpleName());
    }

    private void addMethodParameters(MethodInvocation invocation, StringBuffer buffer) {
        buffer.append("(");
        Object[] arguments = invocation.getArguments();
        for (Object arg : arguments) {
            buffer.append(getValue(arg)).append(", ");
        }
        buffer.append(")");
    }

    private void addMethodName(StringBuffer buffer) {
        buffer.append(".");
        buffer.append(methodName);
    }

    private void logResult(MethodInvocation invocation, Object result) {
        StringBuffer buffer = createBuffer(invocation);
        addMethodName(buffer);
        if (invocation.getMethod().getReturnType() != null) {
            buffer.append(" => ");
            buffer.append(getValue(result));
        }
        log.debug(buffer.toString());
    }

    private String getValue(Object result) {
        if (result == null)
            return "null";
        else
            return result.toString();
    }
}
