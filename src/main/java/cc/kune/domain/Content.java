/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.validator.NotNull;

import cc.kune.core.shared.domain.ContentStatus;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.domain.utils.HasStateToken;

import com.google.inject.name.Named;
import com.wideplay.warp.persist.dao.Finder;
import com.wideplay.warp.persist.dao.FirstResult;
import com.wideplay.warp.persist.dao.MaxResults;

@Entity
@Table(name = "contents")
@Indexed
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Content implements HasStateToken {

    private static final String GROUP = "group";
    private static final String MIMETYPE = "mimetype";
    public static final Content NO_CONTENT = new Content();

    private static final String TITLE = "title";

    @OneToOne(cascade = CascadeType.ALL)
    private AccessLists accessLists;

    @IndexedEmbedded
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<User> authors;

    @ContainedIn
    @OneToMany(mappedBy = "content", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Comment> comments;

    @ManyToOne
    @JoinColumn
    @IndexedEmbedded
    private Container container;

    @Basic(optional = false)
    private Long createdOn;

    @Basic(optional = true)
    private Date deletedOn;

    /**
     * filename if is an uploaded content
     */
    private String filename;

    @Id
    @DocumentId
    @GeneratedValue
    // @PMD:REVIEWED:ShortVariable: by vjrj on 21/05/09 15:28
    private Long id;

    @IndexedEmbedded
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private I18nLanguage language;

    @IndexedEmbedded
    @OneToOne(cascade = { CascadeType.ALL })
    private Revision lastRevision;

    @OneToOne
    private License license;

    @IndexedEmbedded
    @Embedded
    private BasicMimeType mimeType;

    @Basic(optional = true)
    private Date publishedOn;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContentStatus status;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ContentTranslation> translations;

    // @NotNull??
    private String typeId;

    @Version
    private int version;

    public Content() {
        translations = new ArrayList<ContentTranslation>();
        authors = new ArrayList<User>();
        comments = new HashSet<Comment>();
        createdOn = System.currentTimeMillis();
        lastRevision = new Revision(this);
        accessLists = null;
        status = ContentStatus.editingInProgress;
    }

    public void addAuthor(final User user) {
        if (!this.authors.contains(user)) {
            this.authors.add(user);
        }
    }

    public void addComment(final Comment comment) {
        // FIXME: something related with lazy initialization (workaround using
        // size())
        comments.size();
        comments.add(comment);
    }

    public void addRevision(final Revision revision) {
        if (lastRevision == null) {
            lastRevision = revision;
        } else {
            revision.setPrevious(lastRevision);
            lastRevision = revision;
        }
    }

    @Finder(query = "FROM Content WHERE lastRevision.title LIKE :title AND (mimeType.mimetype=:mimetype OR mimeType.mimetype=:mimetype2) AND container.owner.shortName=:group AND deletedOn=null ORDER BY lastRevision.title ASC")
    public List<Content> find2Mime(@Named(GROUP) final String groupShortName, @Named(TITLE) final String title,
            @Named(MIMETYPE) final String mimetype, @Named("mimetype2") final String mimetype2,
            @FirstResult final int offset, @MaxResults final int limit) {
        return null;
    }

    @Finder(query = "SELECT count(id) FROM Content WHERE lastRevision.title LIKE :title AND (mimeType.mimetype=:mimetype OR mimeType.mimetype=:mimetype2) AND container.owner.shortName=:group AND deletedOn=null")
    public int find2MimeCount(@Named(GROUP) final String groupShortName, @Named(TITLE) final String title,
            @Named(MIMETYPE) final String mimetype, @Named("mimetype2") final String mimetype2) {
        return 0;
    }

    @Finder(query = "select count(*) from Container ctx, Content ctn where ctn.container.id = ctx.id and ctx = :container and ctn.lastRevision.title LIKE :title")
    public Long findIfExistsTitle(@Named("container") final Container container, @Named(TITLE) final String title) {
        return null;
    }

    @Finder(query = "FROM Content WHERE lastRevision.title LIKE :title AND mimeType.mimetype=:mimetype AND container.owner.shortName=:group AND deletedOn=null ORDER BY lastRevision.title ASC")
    public List<Content> findMime(@Named(GROUP) final String groupShortName, @Named(TITLE) final String title,
            @Named(MIMETYPE) final String mimetype, @FirstResult final int offset, @MaxResults final int limit) {
        return null;
    }

    @Finder(query = "SELECT count(id) FROM Content WHERE lastRevision.title LIKE :title AND mimeType.mimetype=:mimetype AND container.owner.shortName=:group AND deletedOn=null")
    public int findMimeCount(@Named(GROUP) final String groupShortName, @Named(TITLE) final String title,
            @Named(MIMETYPE) final String mimetype) {
        return 0;
    }

    @Transient
    public AccessLists getAccessLists() {
        return hasAccessList() ? accessLists : getContainer().getAccessLists();
    }

    public List<User> getAuthors() {
        return authors;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public Container getContainer() {
        return container;
    }

    public Long getCreatedOn() {
        return createdOn;
    }

    public Date getDeletedOn() {
        return deletedOn;
    }

    public String getFilename() {
        return filename;
    }

    public Long getId() {
        return id;
    }

    public I18nLanguage getLanguage() {
        return language;
    }

    public Revision getLastRevision() {
        return lastRevision;
    }

    public License getLicense() {
        return license;
    }

    public BasicMimeType getMimeType() {
        return mimeType;
    }

    public Group getOwner(final Group group) {
        return container.getOwner();
    }

    public Date getPublishedOn() {
        return publishedOn;
    }

    @Override
    @Transient
    public StateToken getStateToken() {
        return getContainer().getStateToken().copy().setDocument(getId());
    }

    @Transient
    public String getStateTokenEncoded() {
        return getStateToken().getEncoded();
    }

    public ContentStatus getStatus() {
        return status;
    }

    public String getTitle() {
        return lastRevision.getTitle();
    }

    public List<ContentTranslation> getTranslations() {
        return translations;
    }

    public String getTypeId() {
        return typeId;
    }

    public int getVersion() {
        return version;
    }

    @Transient
    public boolean hasAccessList() {
        return accessLists != null;
    }

    public void removeAuthor(final User user) {
        this.authors.remove(user);
    }

    public void setAccessLists(final AccessLists accessLists) {
        this.accessLists = accessLists;
    }

    public void setAuthors(final List<User> authors) {
        this.authors = authors;
    }

    public void setComments(final Set<Comment> comments) {
        this.comments = comments;
    }

    public void setContainer(final Container container) {
        this.container = container;
    }

    public void setCreatedOn(final Long createdOn) {
        this.createdOn = createdOn;
    }

    public void setDeletedOn(final Date date) {
        this.deletedOn = date;
    }

    public void setFilename(final String filename) {
        this.filename = filename;
    }

    // @PMD:REVIEWED:ShortVariable: by vjrj on 21/05/09 15:28
    public void setId(final Long id) {
        this.id = id;
    }

    public void setLanguage(final I18nLanguage language) {
        this.language = language;
    }

    public void setLastRevision(final Revision revision) {
        this.lastRevision = revision;
    }

    public void setLicense(final License license) {
        this.license = license;
    }

    public void setMimeType(final BasicMimeType mimeType) {
        this.mimeType = mimeType;
    }

    public void setPublishedOn(final Date publishedOn) {
        this.publishedOn = publishedOn;
    }

    public void setStatus(final ContentStatus status) {
        this.status = status;
    }

    public void setTranslations(final List<ContentTranslation> translations) {
        this.translations = translations;
    }

    public void setTypeId(final String typeId) {
        this.typeId = typeId;
    }

    public void setVersion(final int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Content[(" + getStateTokenEncoded() + "): " + getTitle() + "]";
    }

}
