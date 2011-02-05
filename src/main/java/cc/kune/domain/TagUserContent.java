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

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.validator.NotNull;

import cc.kune.domain.utils.HasId;

import com.google.inject.name.Named;
import com.wideplay.warp.persist.dao.Finder;

//See:
//http://openjpa.apache.org/docs/latest/manual/manual.html#jpa_langref_resulttype
@NamedQueries({
        @NamedQuery(name = "tagsgrouped", query = "SELECT NEW cc.kune.core.shared.domain.TagCount(t.name, COUNT(tuc.content.id)) "
                + "FROM TagUserContent tuc JOIN tuc.tag t WHERE tuc.content.container.owner = :group "
                + "GROUP BY t.name ORDER BY t.name"),
        @NamedQuery(name = "tagsmaxgrouped", query = "SELECT Count(tuc.content.id) FROM TagUserContent tuc JOIN tuc.tag t WHERE tuc.content.container.owner = :group GROUP BY t.name ORDER BY count(*) DESC LIMIT 0,1"),
        @NamedQuery(name = "tagsmingrouped", query = "SELECT Count(tuc.content.id) FROM TagUserContent tuc JOIN tuc.tag t WHERE tuc.content.container.owner = :group GROUP BY t.name ORDER BY count(*) ASC LIMIT 0,1") })
@Entity
@Indexed
@Table(name = "tag_user_content")
public class TagUserContent implements HasId {
    public static final String TAGSGROUPED = "tagsgrouped";
    public static final String TAGSMAXGROUPED = "tagsmaxgrouped";
    public static final String TAGSMINGROUPED = "tagsmingrouped";

    @IndexedEmbedded
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Content content;

    @Basic(optional = false)
    private final Long createdOn;

    @Id
    @GeneratedValue
    @DocumentId
    private Long id;

    @IndexedEmbedded
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Tag tag;

    @IndexedEmbedded
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public TagUserContent() {
        this(null, null, null);
    }

    public TagUserContent(final Tag tag, final User user, final Content content) {
        this.tag = tag;
        this.user = user;
        this.content = content;
        this.createdOn = System.currentTimeMillis();
    }

    @Finder(query = "FROM TagUserContent t WHERE t.user = :user AND t.content = :content")
    public List<TagUserContent> find(@Named("user") final User user, @Named("content") final Content content) {
        return null;
    }

    @Finder(query = "SELECT t.tag FROM TagUserContent t WHERE t.user = :user AND t.content = :content")
    public List<Tag> findTags(@Named("user") final User user, @Named("content") final Content content) {
        return null;
    }

    public Content getContent() {
        return content;
    }

    public Long getCreatedOn() {
        return createdOn;
    }

    @Override
    public Long getId() {
        return id;
    }

    public Tag getTag() {
        return tag;
    }

    public User getUser() {
        return user;
    }

    public void setContent(final Content content) {
        this.content = content;
    }

    @Override
    public void setId(final Long id) {
        this.id = id;
    }

    public void setTag(final Tag tag) {
        this.tag = tag;
    }

    public void setUser(final User user) {
        this.user = user;
    }
}
