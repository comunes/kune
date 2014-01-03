/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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
package cc.kune.core.server;

import java.util.Date;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class LoggerMethodInterceptor.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class LoggerMethodInterceptor implements MethodInterceptor {

  /** The Constant LOG. */
  private static final Log LOG = LogFactory.getLog(LoggerMethodInterceptor.class);

  /** The simplify names. */
  private final boolean simplifyNames;

  /**
   * Instantiates a new logger method interceptor.
   */
  public LoggerMethodInterceptor() {
    this(true);
  }

  /**
   * Instantiates a new logger method interceptor.
   * 
   * @param simplifyNames
   *          the simplify names
   */
  public LoggerMethodInterceptor(final boolean simplifyNames) {
    this.simplifyNames = simplifyNames;
  }

  /**
   * Adds the method name.
   * 
   * @param invocation
   *          the invocation
   * @param buffer
   *          the buffer
   */
  private void addMethodName(final MethodInvocation invocation, final StringBuffer buffer) {
    buffer.append(".");
    buffer.append(invocation.getMethod().getName());
  }

  /**
   * Adds the method parameters.
   * 
   * @param invocation
   *          the invocation
   * @param buffer
   *          the buffer
   */
  private void addMethodParameters(final MethodInvocation invocation, final StringBuffer buffer) {
    buffer.append("(");
    final Object[] arguments = invocation.getArguments();
    for (final Object arg : arguments) {
      buffer.append(getValue(arg)).append(", ");
    }
    buffer.append(")");
  }

  /**
   * Adds the target class name.
   * 
   * @param invocation
   *          the invocation
   * @param buffer
   *          the buffer
   */
  private void addTargetClassName(final MethodInvocation invocation, final StringBuffer buffer) {
    buffer.append(getSimpleName(invocation.getThis().getClass()));
  }

  /**
   * Creates the buffer.
   * 
   * @param invocation
   *          the invocation
   * @return the string buffer
   */
  private StringBuffer createBuffer(final MethodInvocation invocation) {
    final StringBuffer buffer = new StringBuffer();
    addTargetClassName(invocation, buffer);
    return buffer;
  }

  /**
   * Gets the simple name.
   * 
   * @param type
   *          the type
   * @return the simple name
   */
  private String getSimpleName(final Class<? extends Object> type) {
    String simpleName = type.getSimpleName();
    if (simplifyNames == true) {
      final int index = simpleName.indexOf('$');
      if (index > 0) {
        simpleName = simpleName.substring(0, index);
      }
    }
    return simpleName;
  }

  /**
   * Gets the value.
   * 
   * @param result
   *          the result
   * @return the value
   */
  private String getValue(final Object result) {
    if (result == null) {
      return "null";
    } else {
      return result.toString();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept
   * .MethodInvocation)
   */
  @Override
  public Object invoke(final MethodInvocation invocation) throws Throwable {
    final Date start = new Date();
    logInvocation(invocation);
    try {
      final Object result = invocation.proceed();
      logResult(invocation, result, (new Date()).getTime() - start.getTime());
      return result;
    } catch (final Exception e) {
      logException(invocation, e);
      throw e;
    }
  }

  /**
   * Log.
   * 
   * @param output
   *          the output
   */
  protected void log(final String output) {
    LOG.debug(output);
  }

  /**
   * Log exception.
   * 
   * @param invocation
   *          the invocation
   * @param e
   *          the e
   */
  protected void logException(final MethodInvocation invocation, final Throwable e) {
    final StringBuffer buffer = createBuffer(invocation);
    addMethodName(invocation, buffer);
    buffer.append(" EXCEPTION => ").append(e.getClass()).append(": ").append(
        e.getCause() != null ? e.getCause() : "").append(e.getMessage() != null ? e.getMessage() : "");
    log(buffer.toString());
  }

  /**
   * Log invocation.
   * 
   * @param invocation
   *          the invocation
   */
  protected void logInvocation(final MethodInvocation invocation) {
    final StringBuffer buffer = createBuffer(invocation);
    addMethodName(invocation, buffer);
    addMethodParameters(invocation, buffer);
    log(buffer.toString());
  }

  /**
   * Log result.
   * 
   * @param invocation
   *          the invocation
   * @param result
   *          the result
   * @param mill
   *          the mill
   */
  protected void logResult(final MethodInvocation invocation, final Object result, final long mill) {
    final StringBuffer buffer = createBuffer(invocation);
    addMethodName(invocation, buffer);
    if (invocation.getMethod().getReturnType() != null) {
      buffer.append(" => ");
      buffer.append(getValue(result));
      buffer.append(", time consumed: ");
      buffer.append(mill);
      buffer.append("ms");
    }
    log(buffer.toString());
  }
}
