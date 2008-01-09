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

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

@Entity
@Table(name = "revisions")
@Indexed
public class Revision {
    @Id
    @GeneratedValue
    @DocumentId
    private Long id;

    @OneToOne
    private User editor;

    @Basic(optional = false)
    private Long createdOn;

    @IndexedEmbedded
    @OneToOne(cascade = { CascadeType.ALL })
    private Data data;

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
        this.data = new Data(content);
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

    public Data getData() {
        return data;
    }

    public void setData(final Data content) {
        this.data = content;
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

    public void setDataContent(final String text) {
        this.data.setBody(text.toCharArray());
    }

    public void setDataTitle(final String text) {
        this.data.setTitle(text);
    }

    public void setTitle(final String title) {
        data.setTitle(title);
    }

    public Content getContent() {
        return content;
    }

    public void setContent(final Content content) {
        this.content = content;
    }

}
