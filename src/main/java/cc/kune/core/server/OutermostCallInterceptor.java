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

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.google.inject.Inject;
import com.google.inject.Injector;

// TODO: Auto-generated Javadoc
/*
 * See:
 * http://tembrel.blogspot.com/2007/09/matcher-and-methodinterceptor-for-dwr.html
 *
 */

/**
 * The Class OutermostCallInterceptor.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public final class OutermostCallInterceptor implements MethodInterceptor {

  /**
   * Decorates a MethodInterceptor so that only the outermost invocation using
   * that interceptor will be intercepted and nested invocations willbe ignored.
   * 
   * @param interceptor
   *          the interceptor
   * @return the method interceptor
   */
  public static MethodInterceptor outermostCall(final MethodInterceptor interceptor) {
    return new OutermostCallInterceptor(interceptor);
  }

  /** The count. */
  @SuppressWarnings("rawtypes")
  private final ThreadLocal count = new ThreadLocal() {
    @Override
    protected Integer initialValue() {
      return 0;
    }
  };

  /** The interceptor. */
  private final MethodInterceptor interceptor;

  /**
   * Instantiates a new outermost call interceptor.
   * 
   * @param interceptor
   *          the interceptor
   */
  private OutermostCallInterceptor(final MethodInterceptor interceptor) {
    this.interceptor = interceptor;
  }

  /**
   * Ensure underlying interceptor is injected.
   * 
   * @param injector
   *          the injector
   */
  @Inject
  void injectInterceptor(final Injector injector) {
    injector.injectMembers(interceptor);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept
   * .MethodInvocation)
   */
  @Override
  @SuppressWarnings("unchecked")
  public Object invoke(final MethodInvocation invocation) throws Throwable {
    final int savedCount = (Integer) count.get();
    count.set(savedCount + 1);
    try {
      if ((Integer) count.get() > 1) {
        return invocation.proceed();
      } else {
        return interceptor.invoke(invocation);
      }
    } finally {
      count.set(savedCount);
    }
  }
}
