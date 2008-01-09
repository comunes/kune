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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

@Entity
@Table(name = "data")
@Indexed
public class Data implements HasId {
    @Id
    @GeneratedValue
    @DocumentId
    Long id;

    @Field(index = Index.TOKENIZED, store = Store.NO)
    String title;

    // http://openjpa.apache.org/docs/latest/manual/manual.html#jpa_overview_mapping_lob
    @Lob
    // http://www.hibernate.org/112.html
    @Column(length = 2147483647)
    @Field(index = Index.TOKENIZED, store = Store.NO)
    @FieldBridge(impl = DataFieldBridge.class)
    char[] body;

    @ManyToOne
    @JoinColumn
    @ContainedIn
    private Content content;

    @OneToOne
    @JoinColumn
    private Revision revision;

    public Data() {
        this(null);
    }

    public Data(final Content content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public char[] getBody() {
        return body;
    }

    public void setBody(final char[] body) {
        this.body = body;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(final Content content) {
        this.content = content;
    }

    public Revision getRevision() {
        return revision;
    }

    public void setRevision(final Revision revision) {
        this.revision = revision;
    }

    @Transient
    public boolean isLast() {
        return content.getLastRevision().equals(revision);
    }
}
