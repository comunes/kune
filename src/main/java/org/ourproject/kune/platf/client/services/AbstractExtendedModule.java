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
package org.ourproject.kune.platf.client.services;

import com.calclab.suco.client.ioc.Provider;
import com.calclab.suco.client.ioc.module.AbstractModule;

public abstract class AbstractExtendedModule extends AbstractModule {

    /**
     * Get a instance of the specified component key
     * 
     * @param <T>
     *            The component key
     * @param componentType
     *            The component key
     * @return The component instance
     */
    protected <T> T i(final Class<T> componentType) { // NOPMD by vjrj on
                                                      // 11/06/09 18:34
        return $(componentType);
    }

    /**
     * Get a provider of the specified component key
     * 
     * @param <T>
     *            The component key
     * @param componentType
     *            The component key
     * @return The provider of that component key
     */
    protected <T> Provider<T> p(final Class<T> componentType) {// NOPMD by vjrj
                                                               // on 11/06/09
                                                               // 18:34
        return $$(componentType);
    }

}
