package org.ourproject.kune.platf.server.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;

@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn
    @IndexedEmbedded
    private Content content;

    @Basic(optional = false)
    private Long publishedOn;

    @IndexedEmbedded
    @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private User author;

    // Parent/Child pattern:
    // http://www.hibernate.org/hib_docs/reference/en/html/example-parentchild.html
    // http://www.researchkitchen.co.uk/blog/archives/57
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Comment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Comment> childs;

    @IndexedEmbedded
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<User> positiveVoters;

    @IndexedEmbedded
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<User> negativeVoters;

    @IndexedEmbedded
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<User> abuseInformers;

    @Field(index = Index.TOKENIZED, store = Store.NO)
    private String text;

    public Comment() {
        this.publishedOn = System.currentTimeMillis();
        this.childs = new HashSet<Comment>();
        this.positiveVoters = new ArrayList<User>();
        this.negativeVoters = new ArrayList<User>();
        this.abuseInformers = new ArrayList<User>();
    }

    public void addAbuseInformer(final User informer) {
        if (!abuseInformers.contains(informer)) {
            abuseInformers.add(informer);
        }
    }

    public void addNegativeVoter(final User voter) {
        if (!negativeVoters.contains(voter)) {
            negativeVoters.add(voter);
        }
        if (positiveVoters.contains(voter)) {
            positiveVoters.remove(voter);
        }
    }

    public void addPositiveVoter(final User voter) {
        if (!positiveVoters.contains(voter)) {
            positiveVoters.add(voter);
        }
        if (negativeVoters.contains(voter)) {
            negativeVoters.remove(voter);
        }
    }

    public List<User> getAbuseInformers() {
        return abuseInformers;
    }

    @Transient
    public int getAbuseInformersCount() {
        return abuseInformers.size();
    }

    public User getAuthor() {
        return author;
    }

    public Set<Comment> getChilds() {
        return childs;
    }

    public Content getContent() {
        return content;
    }

    public Long getId() {
        return id;
    }

    public List<User> getNegativeVoters() {
        return negativeVoters;
    }

    @Transient
    public int getNegativeVotersCount() {
        return negativeVoters.size();
    }

    public Comment getParent() {
        return parent;
    }

    public List<User> getPositiveVoters() {
        return positiveVoters;
    }

    @Transient
    public int getPositiveVotersCount() {
        return positiveVoters.size();
    }

    public Long getPublishedOn() {
        return publishedOn;
    }

    public String getText() {
        return text;
    }

    public void setAbuseInformers(final List<User> abuseInformers) {
        this.abuseInformers = abuseInformers;
    }

    public void setAuthor(final User author) {
        this.author = author;
    }

    public void setChilds(final Set<Comment> childs) {
        this.childs = childs;
    }

    public void setContent(final Content content) {
        this.content = content;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setNegativeVoters(final List<User> negativeVoters) {
        this.negativeVoters = negativeVoters;
    }

    public void setParent(final Comment parent) {
        this.parent = parent;
    }

    public void setPositiveVoters(final List<User> positiveVoters) {
        this.positiveVoters = positiveVoters;
    }

    public void setPublishedOn(final Long publishedOn) {
        this.publishedOn = publishedOn;
    }

    public void setText(final String text) {
        this.text = text;
    }
}
