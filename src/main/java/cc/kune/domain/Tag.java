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
package cc.kune.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

import cc.kune.domain.utils.HasId;

@Entity
@Indexed
@Table(name = "tags")
public class Tag implements HasId {

  @Basic(optional = false)
  private final Long createdOn;

  @Id
  @GeneratedValue
  @DocumentId
  private Long id;

  @Field(index = Index.YES, store = Store.NO)
  @Column(unique = true)
  private String name;

  public Tag() {
    this(null);
  }

  public Tag(final String name) {
    this.name = name;
    this.createdOn = System.currentTimeMillis();
  }

  public Long getCreatedOn() {
    return createdOn;
  }

  @Override
  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @Override
  public void setId(final Long id) {
    this.id = id;
  }

  public void setName(final String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Tag[" + getName() + "]";
  }

}
