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
