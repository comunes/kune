
package org.ourproject.kune.platf.server.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;

@Entity
@Indexed
@Table(name = "containers")
public class Container implements HasId {
    @Id
    @GeneratedValue
    @DocumentId
    Long id;

    @Field(index = Index.TOKENIZED, store = Store.NO)
    private String name;

    private String toolName;

    private String typeId;

    @ManyToOne
    private I18nLanguage language;

    @IndexedEmbedded
    @OneToOne
    private Group owner;

    // Parent/Child pattern:
    // http://www.hibernate.org/hib_docs/reference/en/html/example-parentchild.html
    // http://www.researchkitchen.co.uk/blog/archives/57
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Container parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Container> childs;

    @ContainedIn
    @OneToMany(mappedBy = "container", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Content> contents;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Container> absolutePath;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ContainerTranslation> containerTranslations;

    public Container(final String title, final Group group, final String toolName) {
        this.name = title;
        owner = group;
        this.toolName = toolName;
        this.contents = new HashSet<Content>();
        this.childs = new HashSet<Container>();
        this.absolutePath = new ArrayList<Container>();
    }

    public Container() {
        this(null, null, null);
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Long getParentFolderId() {
        return parent != null ? parent.getId() : null;
    }

    public Container getParent() {
        return parent;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setParent(final Container parent) {
        this.parent = parent;
    }

    public Set<Container> getChilds() {
        return childs;
    }

    public void setChilds(final Set<Container> childs) {
        this.childs = childs;
    }

    public List<ContainerTranslation> getAliases() {
        return containerTranslations;
    }

    public void setAliases(final List<ContainerTranslation> containerTranslations) {
        this.containerTranslations = containerTranslations;
    }

    public Group getOwner() {
        return owner;
    }

    public void setOwner(final Group owner) {
        this.owner = owner;
    }

    public String getToolName() {
        return toolName;
    }

    public void setToolName(final String toolName) {
        this.toolName = toolName;
    }

    public void addContent(final Content descriptor) {
        // FIXME: something related with lazy initialization (workaround using
        // size())
        contents.size();
        contents.add(descriptor);
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(final String typeId) {
        this.typeId = typeId;
    }

    public Set<Content> getContents() {
        return contents;
    }

    public void addChild(final Container child) {
        // childs.size();
        // childs.add(container);
        child.setParent(this);
        childs.add(child);
    }

    public I18nLanguage getLanguage() {
        return language;
    }

    public void setLanguage(final I18nLanguage language) {
        this.language = language;
    }

    public List<ContainerTranslation> getContainerTranslations() {
        return containerTranslations;
    }

    public void setContainerTranslations(final List<ContainerTranslation> containerTranslations) {
        this.containerTranslations = containerTranslations;
    }

    public void setContents(final HashSet<Content> contents) {
        this.contents = contents;
    }

    public List<Container> getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(final List<Container> absolutePath) {
        this.absolutePath = absolutePath;
    }

    @Transient
    public boolean isLeaf() {
        return childs.size() == 0 && contents.size() == 0;
    }

    @Transient
    public boolean isRoot() {
        return parent == null;
    }
}
