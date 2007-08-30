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

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.google.inject.name.Named;
import com.wideplay.warp.persist.dao.Finder;

@Entity
@Table(name = "kusers")
public class User implements HasId {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    private Group userGroup;

    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private String shortName;

    public User(final String shortName, final String name, final String email, final String password) {
	this.shortName = shortName;
	this.name = name;
	this.email = email;
	this.password = password;
	this.userGroup = null;
    }

    public User() {
	this(null, null, null, null);
    }

    @Finder(query = "from User")
    public List<User> getAll() {
	return null;
    }

    @Finder(query = "from User where email = :email")
    public User getByEmail(@Named("email")
    final String email) {
	return null;
    }

    @Finder(query = "from User where shortName = :shortName")
    public User getByShortName(@Named("shortName")
    final String shortName) {
	return null;
    }

    public Long getId() {
	return id;
    }

    public void setId(final Long id) {
	this.id = id;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(final String email) {
	this.email = email;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(final String password) {
	this.password = password;
    }

    // mapping to DTO
    public String getChatPassword() {
	return password;
    }

    public Group getUserGroup() {
	return userGroup;
    }

    public void setUserGroup(final Group userGroup) {
	this.userGroup = userGroup;
    }

    public String getName() {
	return name;
    }

    public void setName(final String name) {
	this.name = name;
    }

    public String getShortName() {
	return shortName;
    }

    public void setShortName(final String shortName) {
	this.shortName = shortName;
    }

}
