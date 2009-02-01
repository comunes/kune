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
package org.ourproject.kune.platf.client.registry;

import java.util.ArrayList;

public abstract class AbstractContentRegistry {
    private final ArrayList<String> registry;

    public AbstractContentRegistry() {
        registry = new ArrayList<String>();
    }

    public boolean contains(String typeId) {
        return registry.contains(typeId);
    }

    public void register(String... typeIds) {
        for (String typeId : typeIds) {
            registry.add(typeId);
        }
    }

    @Override
    public String toString() {
        return "registry: " + registry;
    }

    public void unregister(String... typeIds) {
        for (String typeId : typeIds) {
            registry.remove(typeId);
        }
    }
}
