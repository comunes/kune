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

import cc.kune.domain.utils.DataFieldBridge;

@Entity
@Table(name = "revisions")
@Indexed
public class Revision {
  // http://www.hibernate.org/112.html
  @Lob
  @Column(length = 2147483647)
  @Field(index = Index.TOKENIZED, store = Store.NO)
  @FieldBridge(impl = DataFieldBridge.class)
  char[] body;

  @ContainedIn
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn
  private Content content;

  @Basic(optional = false)
  private Long createdOn;

  @OneToOne
  private User editor;

  @Id
  @GeneratedValue
  @DocumentId
  private Long id;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Revision previous;

  @Field(index = Index.TOKENIZED, store = Store.NO)
  String title;

  @Version
  private int version;

  public Revision() {
    this(null);
  }

  public Revision(final Content content) {
    this.content = content;
    createdOn = System.currentTimeMillis();
  }

  public char[] getBody() {
    return body;
  }

  public Content getContent() {
    return content;
  }

  public Long getCreatedOn() {
    return createdOn;
  }

  public User getEditor() {
    return editor;
  }

  public Long getId() {
    return id;
  }

  public Revision getPrevious() {
    return previous;
  }

  public String getTitle() {
    return title;
  }

  public int getVersion() {
    return version;
  }

  @Transient
  public boolean isLast() {
    return content.getLastRevision().equals(this);
  }

  public void setBody(final String body) {
    this.body = body.toCharArray();
  }

  public void setContent(final Content content) {
    this.content = content;
  }

  public void setCreatedOn(final Long modifiedOn) {
    this.createdOn = modifiedOn;
  }

  public void setEditor(final User editor) {
    this.editor = editor;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public void setPrevious(final Revision previous) {
    this.previous = previous;
  }

  public void setTitle(final String title) {
    this.title = title;
  }

  public void setVersion(final int version) {
    this.version = version;
  }

}
