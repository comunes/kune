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

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "containers")
public class Container implements HasId {
    public static final String SEP = "/";

    @Id
    @GeneratedValue
    Long id;

    @OneToOne
    private Group owner;

    @ManyToOne(fetch = FetchType.LAZY)
    private Container parent;

    @OneToMany(fetch = FetchType.LAZY)
    // , mappedBy = "parent")
    private List<Container> childs;

    @OneToMany(mappedBy = "container")
    private final List<Content> contents;

    @Basic(optional = false)
    private String absolutePath;

    private String typeId;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Alias> aliases;

    private String toolName;

    private String name;

    public Container(final String parentPath, final String title, final Group group, final String toolName) {
	this.name = title;
	this.absolutePath = parentPath + SEP + title;
	owner = group;
	this.toolName = toolName;
	this.contents = new ArrayList<Content>();
	this.childs = new ArrayList<Container>();
    }

    public Container() {
	this(null, null, null, null);
    }

    public String getName() {
	return name;
    }

    public void setName(final String name) {
	this.name = name;
    }

    public Long getParentFolderId() {
	return parent != null ? parent.getId() : null;
    }

    public Container getParent() {
	return parent;
    }

    public Long getId() {
	return id;
    }

    public void setId(final Long id) {
	this.id = id;
    }

    public void setParent(final Container parent) {
	this.parent = parent;
    }

    public List<Container> getChilds() {
	return childs;
    }

    public void setChilds(final List<Container> childs) {
	this.childs = childs;
    }

    public String getAbsolutePath() {
	return absolutePath;
    }

    public void setAbsolutePath(final String absolutePath) {
	this.absolutePath = absolutePath;
    }

    public List<Alias> getAliases() {
	return aliases;
    }

    public void setAliases(final List<Alias> aliases) {
	this.aliases = aliases;
    }

    public Group getOwner() {
	return owner;
    }

    public void setOwner(final Group owner) {
	this.owner = owner;
    }

    public String getToolName() {
	return toolName;
    }

    public void setToolName(final String toolName) {
	this.toolName = toolName;
    }

    public void addContent(final Content descriptor) {
	// FIXME: algo de lazy initialization (con size() se arregla...)
	contents.size();
	contents.add(descriptor);
    }

    public String getTypeId() {
	return typeId;
    }

    public void setTypeId(final String typeId) {
	this.typeId = typeId;
    }

    public List<Content> getContents() {
	return contents;
    }

    public void addChild(final Container container) {
	childs.size();
	childs.add(container);
    }

}
