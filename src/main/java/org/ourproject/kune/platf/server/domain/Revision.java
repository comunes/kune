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

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

@Entity
@Table(name = "revisions")
@Indexed
public class Revision {
    @Id
    @GeneratedValue
    @DocumentId
    private Long id;

    @Field(index = Index.TOKENIZED, store = Store.NO)
    String title;

    // http://www.hibernate.org/112.html
    @Lob
    @Column(length = 2147483647)
    @Field(index = Index.TOKENIZED, store = Store.NO)
    @FieldBridge(impl = DataFieldBridge.class)
    char[] body;

    @OneToOne
    private User editor;

    @Basic(optional = false)
    private Long createdOn;

    @Version
    private int version;

    @ContainedIn
    @ManyToOne
    @JoinColumn
    private Content content;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Revision previous;

    public Revision() {
        this(null);
    }

    public Revision(final Content content) {
        this.content = content;
        createdOn = System.currentTimeMillis();
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public User getEditor() {
        return editor;
    }

    public void setEditor(final User editor) {
        this.editor = editor;
    }

    public Long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(final Long modifiedOn) {
        this.createdOn = modifiedOn;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(final int version) {
        this.version = version;
    }

    public Revision getPrevious() {
        return previous;
    }

    public void setPrevious(final Revision previous) {
        this.previous = previous;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(final Content content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public char[] getBody() {
        return body;
    }

    public void setBody(final String body) {
        this.body = body.toCharArray();
    }

    @Transient
    public boolean isLast() {
        return content.getLastRevision().equals(this);
    }

}
