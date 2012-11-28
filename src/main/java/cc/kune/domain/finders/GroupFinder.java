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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cc.kune.core.shared.dto.GroupType;
import cc.kune.domain.Group;

import com.google.inject.name.Named;
import com.google.inject.persist.finder.Finder;
import com.google.inject.persist.finder.FirstResult;
import com.google.inject.persist.finder.MaxResults;

public interface GroupFinder {

  @Finder(query = "SELECT count(*) FROM Group g")
  public Long count();

  @Finder(query = "SELECT count(*) FROM Group g WHERE g.longName = :longName")
  public Long countByLongName(@Named("longName") final String longName);

  @Finder(query = "SELECT count(*) FROM Group g WHERE g.shortName = :shortName")
  public Long countByShortName(@Named("shortName") final String shortName);

  @Finder(query = "SELECT count(*) FROM Group g WHERE(g.groupType != :notgrouptype)")
  public Long countExceptType(@Named("notgrouptype") final GroupType excludedGroupType);

  @Finder(query = "SELECT count(*) FROM Group g " + " WHERE (g.groupType != :notgrouptype)")
  public Long countGroups(@Named("notgrouptype") final GroupType excludedGroupType);

  @Finder(query = "FROM Group g WHERE g.id IN (SELECT g.id "
      + "FROM g.socialNetwork.accessLists.admins.list adm WHERE adm.id = :groupid) ORDER BY g.shortName ASC", returnAs = HashSet.class)
  public Set<Group> findAdminInGroups(@Named("groupid") final Long groupId);

  @Finder(query = "FROM Group g WHERE g.longName = :longName")
  public Group findByLongName(@Named("longName") final String longName);

  @Finder(query = "FROM Group g WHERE g.shortName = :shortName")
  public Group findByShortName(@Named("shortName") final String shortName);

  @Finder(query = "FROM Group g WHERE g.id IN (SELECT g.id FROM "
      + "g.socialNetwork.accessLists.editors.list AS ed WHERE ed.id = :groupid) ORDER BY g.shortName ASC", returnAs = HashSet.class)
  public Set<Group> findCollabInGroups(@Named("groupid") final Long groupId);

  @Finder(query = "SELECT t.root.toolName FROM ToolConfiguration t "
      + "WHERE t.enabled=true AND t.root.owner.id = :groupid", returnAs = ArrayList.class)
  public List<String> findEnabledTools(@Named("groupid") final Long groupId);

  @Finder(query = "FROM Group g WHERE g.id IN (SELECT g.id FROM "
      + "g.socialNetwork.accessLists.editors.list AS ed WHERE ed.id = :groupid ORDER BY g.longName ASC)"
      + "OR g.id IN (SELECT g.id "
      + "FROM g.socialNetwork.accessLists.admins.list adm WHERE adm.id = :groupid ORDER BY g.longName ASC)"
      + "ORDER BY g.longName ASC", returnAs = ArrayList.class)
  public List<Group> findParticipatingInGroups(@Named("groupid") final Long groupId);

  @Finder(query = "FROM Group g ORDER BY g.shortName ASC", returnAs = ArrayList.class)
  public List<Group> getAll();

  @Finder(query = "FROM Group g WHERE (g.groupType != :notgrouptype)" + " ORDER BY createdOn DESC", returnAs = ArrayList.class)
  public List<Group> getAllExcept(@MaxResults final int limit, @FirstResult final int init,
      @Named("notgrouptype") final GroupType excludedGroupType);

  @Finder(query = "FROM Group g "
      + " WHERE (g.groupType != :notgrouptype1 AND g.groupType != :notgrouptype2)"
      + " ORDER BY createdOn DESC", returnAs = ArrayList.class)
  public List<Group> lastGroups(@MaxResults final int limit,
      @Named("notgrouptype1") final GroupType excludedGroupType1,
      @Named("notgrouptype2") final GroupType excludedGroupType2);
}
