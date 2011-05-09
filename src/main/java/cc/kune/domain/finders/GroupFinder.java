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
package cc.kune.domain.finders;

import java.util.ArrayList;
import java.util.List;

import cc.kune.domain.Group;

import com.google.inject.name.Named;
import com.google.inject.persist.finder.Finder;

public interface GroupFinder {

    @Finder(query = "FROM Group g WHERE g.id IN (SELECT g.id FROM g.socialNetwork.accessLists.admins.list adm WHERE adm.id = :groupid)", returnAs = ArrayList.class)
    public List<Group> findAdminInGroups(@Named("groupid") final Long groupId);

    @Finder(query = "FROM Group g WHERE g.shortName = :shortName")
    public Group findByShortName(@Named("shortName") final String shortName);

    @Finder(query = "FROM Group g WHERE g.id IN (SELECT g.id FROM g.socialNetwork.accessLists.editors.list AS ed WHERE ed.id = :groupid)", returnAs = ArrayList.class)
    public List<Group> findCollabInGroups(@Named("groupid") final Long groupId);

    @Finder(query = "SELECT t.root.toolName FROM ToolConfiguration t WHERE t.enabled=true AND t.root.owner.id = :groupid", returnAs = ArrayList.class)
    public List<String> findEnabledTools(@Named("groupid") final Long groupId);

    @Finder(query = "FROM Group", returnAs = ArrayList.class)
    public List<Group> getAll();
}
