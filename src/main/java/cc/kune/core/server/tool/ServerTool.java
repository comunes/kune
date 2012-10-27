/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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
package cc.kune.core.server.tool;

import cc.kune.domain.Container;
import cc.kune.domain.Content;
import cc.kune.domain.Group;
import cc.kune.domain.User;

/**
 * 
 * Tools must have a corresponding module and must be marked asEagerSingleton.
 * The register method must have the @Inject annotation
 * 
 */
public interface ServerTool {

  void checkTypesBeforeContainerCreation(String parentTypeId, String typeId);

  void checkTypesBeforeContentCreation(String parentTypeId, String typeId);

  String getName();

  String getRootName();

  ServerToolTarget getTarget();

  Group initGroup(User user, Group group, Object... vars);

  void onCreateContainer(Container container, Container parent);

  void onCreateContent(Content content, Container parent);

  void register(ServerToolRegistry registry);
}
