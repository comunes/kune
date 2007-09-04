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

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "access_lists")
public class AccessLists {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    protected GroupList admins;
    @OneToOne(cascade = CascadeType.ALL)
    protected GroupList editors;
    @OneToOne(cascade = CascadeType.ALL)
    protected GroupList viewers;

    public AccessLists() {
	this.admins = new GroupList();
	this.editors = new GroupList();
	this.viewers = new GroupList();
    }

    public Long getId() {
	return id;
    }

    public void setId(final Long id) {
	this.id = id;
    }

    public void addAdmin(final Group group) {
	admins.add(group);
    }

    public void addEditor(final Group group) {
	editors.add(group);
    }

    public void addViewer(final Group group) {
	viewers.add(group);
    }

    public GroupList getAdmins() {
	return admins;
    }

    public GroupList getEditors() {
	return editors;
    }

    public GroupList getViewers() {
	return viewers;
    }
}
