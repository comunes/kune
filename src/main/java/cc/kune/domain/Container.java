/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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
package cc.kune.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;

import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.domain.utils.HasId;
import cc.kune.domain.utils.HasStateToken;

// TODO: Auto-generated Javadoc
/**
 * The Class Container.
 *
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Entity
@Indexed
@Table(name = "containers")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Container implements HasId, HasStateToken {

  /** The access lists. */
  @OneToOne(cascade = CascadeType.ALL)
  private AccessLists accessLists;

  /** The childs. */
  @LazyCollection(LazyCollectionOption.FALSE)
  @Fetch(FetchMode.JOIN)
  @OrderBy("createdOn DESC")
  @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  private Set<Container> childs;

  /** The container translations. */
  @OneToMany(cascade = CascadeType.ALL)
  private List<ContainerTranslation> containerTranslations;

  /** The contents. */
  @LazyCollection(LazyCollectionOption.FALSE)
  @Fetch(FetchMode.JOIN)
  @ContainedIn
  @OrderBy("createdOn DESC")
  @OneToMany(mappedBy = "container", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  private Set<Content> contents;

  /** The created on. */
  @org.hibernate.annotations.Index(name="createdOn")
  @Basic(optional = false)
  private Long createdOn;

  /** The deleted on. */
  @org.hibernate.annotations.Index(name="deletedOn")
  @Basic(optional = true)
  private Date deletedOn;

  /** The id. */
  @Id
  @GeneratedValue
  @DocumentId
  Long id;

  /** The language. */
  @ManyToOne
  private I18nLanguage language;

  /** The name. */
  @Column
  @Field(index = Index.YES, store = Store.NO)
  private String name;

  /** The owner. */
  @IndexedEmbedded(depth = 1, prefix = "owner_")
  @OneToOne
  private Group owner;

  // Parent/Child pattern:
  // http://www.hibernate.org/hib_docs/reference/en/html/example-parentchild.html
  // http://www.researchkitchen.co.uk/blog/archives/57
  /** The parent. */
  @LazyCollection(LazyCollectionOption.FALSE)
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn
  private Container parent;

  /** The tool name. */
  private String toolName;

  /** The type id. */
  private String typeId;

  /**
   * Instantiates a new container.
   */
  public Container() {
    this(null, null, null);
  }

  /**
   * Instantiates a new container.
   *
   * @param title the title
   * @param group the group
   * @param toolName the tool name
   */
  public Container(final String title, final Group group, final String toolName) {
    this.name = title;
    owner = group;
    this.toolName = toolName;
    this.contents = new HashSet<Content>();
    this.childs = new HashSet<Container>();
    this.createdOn = System.currentTimeMillis();
  }

  /**
   * Adds the child.
   *
   * @param child the child
   */
  public void addChild(final Container child) {
    child.setParent(this);
    childs.add(child);
  }

  /**
   * Adds the content.
   *
   * @param descriptor the descriptor
   */
  public void addContent(final Content descriptor) {
    // FIXME: something related with lazy initialization (workaround using
    // size())
    contents.size();
    contents.add(descriptor);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Container other = (Container) obj;
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!name.equals(other.name)) {
      return false;
    }
    if (toolName == null) {
      if (other.toolName != null) {
        return false;
      }
    } else if (!toolName.equals(other.toolName)) {
      return false;
    }
    if (typeId == null) {
      if (other.typeId != null) {
        return false;
      }
    } else if (!typeId.equals(other.typeId)) {
      return false;
    }
    return true;
  }

  /**
   * Gets the absolute path.
   *
   * @return the absolute path
   */
  @Transient
  public List<Container> getAbsolutePath() {
    ArrayList<Container> path;
    if (isRoot()) {
      path = new ArrayList<Container>();
    } else {
      path = new ArrayList<Container>((getParent().getAbsolutePath()));
    }
    path.add(this);
    return path;
  }

  /**
   * Gets the access lists.
   *
   * @return the access lists
   */
  @Transient
  public AccessLists getAccessLists() {
    return hasAccessList() ? accessLists : isRoot() ? getOwner().getAccessLists()
        : getParent().getAccessLists();
  }

  /**
   * Gets the aliases.
   *
   * @return the aliases
   */
  public List<ContainerTranslation> getAliases() {
    return containerTranslations;
  }

  /**
   * Gets the childs.
   *
   * @return the childs
   */
  public Set<Container> getChilds() {
    return childs;
  }

  /**
   * Gets the container translations.
   *
   * @return the container translations
   */
  public List<ContainerTranslation> getContainerTranslations() {
    return containerTranslations;
  }

  /**
   * Gets the contents.
   *
   * @return the contents
   */
  public Set<Content> getContents() {
    return contents;
  }

  /**
   * Gets the created on.
   *
   * @return the created on
   */
  public Long getCreatedOn() {
    return createdOn;
  }

  /**
   * Gets the deleted on.
   *
   * @return the deleted on
   */
  public Date getDeletedOn() {
    return deletedOn;
  }

  /* (non-Javadoc)
   * @see cc.kune.domain.utils.HasId#getId()
   */
  @Override
  public Long getId() {
    return id;
  }

  /**
   * Gets the language.
   *
   * @return the language
   */
  public I18nLanguage getLanguage() {
    return language;
  }

  /**
   * Gets the name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the owner.
   *
   * @return the owner
   */
  public Group getOwner() {
    return owner;
  }

  /**
   * Gets the parent.
   *
   * @return the parent
   */
  public Container getParent() {
    return parent;
  }

  /**
   * Gets the parent token.
   *
   * @return the parent token
   */
  public StateToken getParentToken() {
    return parent != null ? parent.getStateToken() : null;
  }

  /* (non-Javadoc)
   * @see cc.kune.domain.utils.HasStateToken#getStateToken()
   */
  @Override
  @Transient
  public StateToken getStateToken() {
    return new StateToken(getOwner().getShortName(), getToolName(), getId());
  }

  /**
   * Gets the state token encoded.
   *
   * @return the state token encoded
   */
  @Transient
  public String getStateTokenEncoded() {
    return getStateToken().getEncoded();
  }

  /**
   * Gets the tool name.
   *
   * @return the tool name
   */
  public String getToolName() {
    return toolName;
  }

  /**
   * Gets the type id.
   *
   * @return the type id
   */
  public String getTypeId() {
    return typeId;
  }

  /**
   * Checks for access list.
   *
   * @return true, if successful
   */
  @Transient
  public boolean hasAccessList() {
    return accessLists != null;
  }

  /**
   * Checks if is leaf.
   *
   * @return true, if is leaf
   */
  @Transient
  public boolean isLeaf() {
    return childs.size() == 0 && contents.size() == 0;
  }

  /**
   * Checks if is root.
   *
   * @return true, if is root
   */
  @Transient
  public boolean isRoot() {
    return parent == null;
  }

  /**
   * Removes the child.
   *
   * @param child the child
   */
  public void removeChild(final Container child) {
    // Adding hash and equals to Container breaks move and delete
    child.setParent(null);
    childs.remove(child);
  }

  /**
   * Removes the content.
   *
   * @param content the content
   */
  public void removeContent(final Content content) {
    contents.size();
    contents.remove(content);
  }

  /**
   * Sets the access lists.
   *
   * @param accessLists the new access lists
   */
  public void setAccessLists(final AccessLists accessLists) {
    this.accessLists = accessLists;
  }

  /**
   * Sets the aliases.
   *
   * @param containerTranslations the new aliases
   */
  public void setAliases(final List<ContainerTranslation> containerTranslations) {
    this.containerTranslations = containerTranslations;
  }

  /**
   * Sets the childs.
   *
   * @param childs the new childs
   */
  public void setChilds(final Set<Container> childs) {
    this.childs = childs;
  }

  /**
   * Sets the container translations.
   *
   * @param containerTranslations the new container translations
   */
  public void setContainerTranslations(final List<ContainerTranslation> containerTranslations) {
    this.containerTranslations = containerTranslations;
  }

  /**
   * Sets the contents.
   *
   * @param contents the new contents
   */
  public void setContents(final HashSet<Content> contents) {
    this.contents = contents;
  }

  /**
   * Sets the created on.
   *
   * @param createdOn the new created on
   */
  public void setCreatedOn(final Long createdOn) {
    this.createdOn = createdOn;
  }

  /**
   * Sets the deleted on.
   *
   * @param deletedOn the new deleted on
   */
  public void setDeletedOn(final Date deletedOn) {
    this.deletedOn = deletedOn;
  }

  /* (non-Javadoc)
   * @see cc.kune.domain.utils.HasId#setId(java.lang.Long)
   */
  @Override
  public void setId(final Long id) {
    this.id = id;
  }

  /**
   * Sets the language.
   *
   * @param language the new language
   */
  public void setLanguage(final I18nLanguage language) {
    this.language = language;
  }

  /**
   * Sets the name.
   *
   * @param name the new name
   */
  public void setName(final String name) {
    this.name = name;
  }

  /**
   * Sets the owner.
   *
   * @param owner the new owner
   */
  public void setOwner(final Group owner) {
    this.owner = owner;
  }

  /**
   * Sets the parent.
   *
   * @param parent the new parent
   */
  public void setParent(final Container parent) {
    this.parent = parent;
  }

  /**
   * Sets the tool name.
   *
   * @param toolName the new tool name
   */
  public void setToolName(final String toolName) {
    this.toolName = toolName;
  }

  /**
   * Sets the type id.
   *
   * @param typeId the new type id
   */
  public void setTypeId(final String typeId) {
    this.typeId = typeId;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "Container[(" + getStateTokenEncoded() + "): " + getName() + "]";
  }
}
