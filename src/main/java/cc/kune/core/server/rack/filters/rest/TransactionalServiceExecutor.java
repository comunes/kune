/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package cc.kune.core.server.rack.filters.rest;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

public class TransactionalServiceExecutor {
    private final RESTMethodFinder methodFinder;
    private final RESTSerializer serializer;

    @Inject
    public TransactionalServiceExecutor(RESTMethodFinder methodFinder, RESTSerializer serializer) {
        this.methodFinder = methodFinder;
        this.serializer = serializer;
    }

    @Transactional
    public String doService(Class<?> serviceClass, String methodName, ParametersAdapter parameters,
            Object serviceInstance) {
        String output = null;
        RESTMethod rest = methodFinder.findMethod(methodName, parameters, serviceClass);
        if (rest != null && rest.invoke(serviceInstance)) {
            output = serializer.serialize(rest.getResponse(), rest.getFormat());
        }
        return output;
    }
}
