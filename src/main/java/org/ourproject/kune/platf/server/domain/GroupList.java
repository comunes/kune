/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
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

package org.ourproject.kune.platf.server.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "group_list")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class GroupList {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    List<Group> list;

    @Enumerated(EnumType.ORDINAL)
    @Basic
    private GroupListMode mode;

    public GroupList() {
        this(new ArrayList<Group>());
        this.mode = GroupListMode.NORMAL;
    }

    public GroupList(final List<Group> list) {
        this.list = list;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public List<Group> getList() {
        return list;
    }

    public void setList(final List<Group> list) {
        this.list = list;
    }

    public void add(final Group group) {
        // No group duplicate
        // TODO: Get this outside Domain?
        if (!list.contains(group)) {
            list.add(group);
        }
        // TODO: Get this outside Domain?
        if (getMode() == GroupListMode.NOBODY) {
            setMode(GroupListMode.NORMAL);
        }
    }

    public boolean includes(final Group group) {
        return mode.checkIfIncludes(group, this.list);
    }

    public ArrayList<Group> duplicate() {
        return new ArrayList<Group>(list);
    }

    public boolean isEmpty() {
        return list.size() == 0;
    }

    public GroupListMode getMode() {
        return mode;
    }

    public void setMode(final GroupListMode mode) {
        this.mode = mode;
    }

    public void remove(final Group group) {
        list.remove(group);
        // TODO: Get this outside Domain?
        if (list.isEmpty()) {
            setMode(GroupListMode.NOBODY);
        }
    }

}
