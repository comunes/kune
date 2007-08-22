/*
 *
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.platf.server.tool;

import org.ourproject.kune.platf.server.domain.Container;
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.User;

/**
 * ATENCIÓN: las tools hay que registrarlas en el módulo correspondiente
 * marcándolas como asEagerSingleton!! y el método register tiene que tener la
 * anotacion de Inject
 * 
 */
public interface ServerTool {

    public void register(ToolRegistry registry);

    String getName();

    Group initGroup(User user, Group group);

    public void onCreateContent(Content content, Container parent);

    public void onCreateContainer(Container container, Container parent);
}
