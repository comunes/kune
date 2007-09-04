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

package org.ourproject.kune.platf.server.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
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

    public GroupList() {
	this(new ArrayList<Group>());
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
	list.add(group);
    }

    public boolean contains(final Group group) {
	return list.contains(group);
    }

    public ArrayList<Group> duplicate() {
	return new ArrayList<Group>(list);
    }

    public boolean isEmpty() {
	return list.size() == 0;
    }

}
