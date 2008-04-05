
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
