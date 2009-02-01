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
package org.ourproject.kune.rack.filters.rest;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DefaultRESTMethodFinder implements RESTMethodFinder {
    private static final Log log = LogFactory.getLog(DefaultRESTMethodFinder.class);

    private HashMap<Class<?>, RESTServiceDefinition> definitionCache;

    public DefaultRESTMethodFinder() {
        this.definitionCache = new HashMap<Class<?>, RESTServiceDefinition>();
    }

    public RESTMethod findMethod(String methodName, Parameters parameters, Class<?> serviceType) {
        RESTServiceDefinition serviceDefinition = getServiceDefinition(serviceType);
        Method[] serviceMethods = serviceDefinition.getMethods();
        log.debug("SERVICE METHODS: " + Arrays.toString(serviceMethods));
        for (Method method : serviceMethods) {
            log.debug("CHEKING: " + method.toString());
            if (method.getName().equals(methodName)) {
                REST methodAnnotation = method.getAnnotation(REST.class);
                if (checkParams(methodAnnotation, parameters)) {
                    return new RESTMethod(method, methodAnnotation.params(), parameters, methodAnnotation.format());
                }
            }
        }
        return null;
    }

    private RESTServiceDefinition getServiceDefinition(Class<?> serviceType) {
        RESTServiceDefinition serviceDefinition = definitionCache.get(serviceType);
        if (serviceDefinition == null) {
            serviceDefinition = new RESTServiceDefinition(serviceType);
            definitionCache.put(serviceType, serviceDefinition);
        }
        return serviceDefinition;
    }

    private boolean checkParams(REST methodAnnotation, Parameters parameters) {
        for (String name : methodAnnotation.params()) {
            if (parameters.get(name) == null) {
                return false;
            }
        }
        return true;
    }
}
