/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.app.server;

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

/**
 * An extension of AbstractModule that provides support for member injection of
 * instances constructed at bind-time; in particular, itself and
 * MethodInterceptors.
 */

/*
 * See:
 * http://tembrel.blogspot.com/2007/09/injecting-method-interceptors-in-guice
 * .html
 */

public abstract class AbstractExtendedModule extends AbstractModule {

    /**
     * Hack to ensure unique Keys for binding different instances of
     * ExtendedModule. The prefix is chosen to reduce the chances of a conflict
     * with some other use of
     * 
     * @Named. A better solution would be to invent an Annotation for just this
     *         purpose.
     */
    private static Annotation getUniqueAnnotation() {
        return named("ExtendedModule-" + COUNT.incrementAndGet());
    }

    private final Set<Object> toBeInjected = new HashSet<Object>();

    private boolean selfInjected = false;

    private static final AtomicInteger COUNT = new AtomicInteger();

    /**
     * Overridden version of bindInterceptor that, in addition to the standard
     * behavior, arranges for field and method injection of each
     * MethodInterceptor in {@code interceptors}.
     */
    @Override
    public void bindInterceptor(final Matcher<? super Class<?>> classMatcher,
            final Matcher<? super Method> methodMatcher, final MethodInterceptor... interceptors) {
        registerForInjection(interceptors);
        super.bindInterceptor(classMatcher, methodMatcher, interceptors);
    }

    /**
     * Arranges for this module and each of the given objects (if any) to be
     * field and method injected when the Injector is created. It is safe to
     * call this method more than once, and it is safe to call it more than once
     * on the same object(s).
     */
    protected <T> void registerForInjection(final T... objects) {
        ensureSelfInjection();
        if (objects != null) {
            for (T object : objects) {
                if (object != null) {
                    toBeInjected.add(object);
                }
            }
        }
    }

    private void ensureSelfInjection() {
        if (!selfInjected) {
            bind(AbstractExtendedModule.class).annotatedWith(getUniqueAnnotation()).toInstance(this);
            selfInjected = true;
        }
    }

    @SuppressWarnings("unused")
    @Inject
    private void injectRegisteredObjects(final Injector injector) {
        for (Object injectee : toBeInjected) {
            injector.injectMembers(injectee);
        }
    }
}
