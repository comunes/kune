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

import static com.google.inject.name.Names.named;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.aopalliance.intercept.MethodInterceptor;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.matcher.Matcher;

// TODO: Auto-generated Javadoc
/**
 * An extension of AbstractModule that provides support for member injection of
 * instances constructed at bind-time; in particular, itself and
 * MethodInterceptors.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */

/*
 * See:
 * http://tembrel.blogspot.com/2007/09/injecting-method-interceptors-in-guice
 * .html
 */

public abstract class AbstractExtendedModule extends AbstractModule {

  /** The Constant COUNT. */
  private static final AtomicInteger COUNT = new AtomicInteger();

  /**
   * Hack to ensure unique Keys for binding different instances of
   * ExtendedModule. The prefix is chosen to reduce the chances of a conflict
   * with some other use of
   * 
   * @return the unique annotation
   * @Named. A better solution would be to invent an Annotation for just this
   *         purpose.
   */
  private static Annotation getUniqueAnnotation() {
    return named("ExtendedModule-" + COUNT.incrementAndGet());
  }

  /** The self injected. */
  private boolean selfInjected = false;

  /** The to be injected. */
  private final Set<Object> toBeInjected = new HashSet<Object>();

  /**
   * Overridden version of bindInterceptor that, in addition to the standard
   * behavior, arranges for field and method injection of each MethodInterceptor
   * in {@code interceptors}.
   * 
   * @param classMatcher
   *          the class matcher
   * @param methodMatcher
   *          the method matcher
   * @param interceptors
   *          the interceptors
   */
  @Override
  public void bindInterceptor(final Matcher<? super Class<?>> classMatcher,
      final Matcher<? super Method> methodMatcher, final MethodInterceptor... interceptors) {
    registerForInjection(interceptors);
    super.bindInterceptor(classMatcher, methodMatcher, interceptors);
  }

  /**
   * Ensure self injection.
   */
  private void ensureSelfInjection() {
    if (!selfInjected) {
      bind(AbstractExtendedModule.class).annotatedWith(getUniqueAnnotation()).toInstance(this);
      selfInjected = true;
    }
  }

  /**
   * Inject registered objects.
   * 
   * @param injector
   *          the injector
   */
  @SuppressWarnings("unused")
  @Inject
  private void injectRegisteredObjects(final Injector injector) {
    for (final Object injectee : toBeInjected) {
      injector.injectMembers(injectee);
    }
  }

  /**
   * Arranges for this module and each of the given objects (if any) to be field
   * and method injected when the Injector is created. It is safe to call this
   * method more than once, and it is safe to call it more than once on the same
   * object(s).
   * 
   * @param <T>
   *          the generic type
   * @param objects
   *          the objects
   */
  protected <T> void registerForInjection(final T... objects) {
    ensureSelfInjection();
    if (objects != null) {
      for (final T object : objects) {
        if (object != null) {
          toBeInjected.add(object);
        }
      }
    }
  }
}
