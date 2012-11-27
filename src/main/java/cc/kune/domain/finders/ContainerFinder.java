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
package cc.kune.domain.finders;

import java.util.ArrayList;
import java.util.List;

import cc.kune.domain.Container;
import cc.kune.domain.Group;

import com.google.inject.name.Named;
import com.google.inject.persist.finder.Finder;

public interface ContainerFinder {
  @Finder(query = "FROM Container c WHERE " + "((:groupid IN (SELECT ed.id FROM "
      + "c.owner.socialNetwork.accessLists.editors.list AS ed)) OR" + "(:groupid IN (SELECT ad.id FROM "
      + "c.owner.socialNetwork.accessLists.admins.list AS ad)))", returnAs = ArrayList.class)
  public List<Container> allContainersInUserGroup(@Named("groupid") Long groupId);

  @Finder(query = "SELECT COUNT(*) FROM Container c WHERE c.parent = :container AND c.name LIKE :title")
  public Long findIfExistsTitle(@Named("container") final Container container,
      @Named("title") final String title);

  @Finder(query = "SELECT COUNT(*) FROM Container c WHERE c.typeId = :typeId AND c.owner = :owner")
  public Long findIfExistsTypeId(@Named("owner") final Group group, @Named("typeId") final String typeId);

  @Finder(query = "FROM Container c WHERE c.typeId = :typeId AND c.owner = :owner")
  public Container findTypeId(@Named("owner") final Group group, @Named("typeId") final String typeId);

}
