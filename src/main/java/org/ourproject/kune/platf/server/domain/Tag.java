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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

import com.google.inject.name.Named;
import com.wideplay.warp.persist.dao.Finder;

@Entity
@Indexed
@Table(name = "tags")
// See:
// http://openjpa.apache.org/docs/latest/manual/manual.html#jpa_langref_resulttype
@NamedQueries( { @NamedQuery(name = "tagsgrouped", query = "SELECT NEW org.ourproject.kune.platf.server.domain.TagResult(t.name, COUNT(c.id)) "
        + "FROM Content c JOIN c.tags t WHERE c.container.owner = :group " + "GROUP BY t.name ORDER BY t.name") })
public class Tag implements HasId {
    public static final String TAGSGROUPED = "tagsgrouped";

    @Id
    @GeneratedValue
    @DocumentId
    private Long id;

    @Field(index = Index.TOKENIZED, store = Store.NO)
    @Column(unique = true)
    private String name;

    public Tag() {
        this(null);
    }

    public Tag(final String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Finder(query = "FROM Tag g WHERE g.name = :name")
    public Tag findByTagName(@Named("name")
    final String tag) {
        return null;
    }

}
