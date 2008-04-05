
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
