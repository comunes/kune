package org.ourproject.kune.platf.server.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "folders")
public class Folder implements HasId {
    public static final String SEP = "/";

    @Id
    @GeneratedValue
    Long id;

    @OneToOne
    private Group owner;

    @ManyToOne(fetch = FetchType.LAZY)
    private Folder parent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    private List<Folder> childs;

    @OneToMany(mappedBy = "folder")
    private final List<Content> contents;

    @Basic(optional = false)
    private String absolutePath;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Alias> aliases;

    private String toolName;

    private String name;

    public Folder(final String parentPath, final String title, final Group group, final String toolName) {
	this.name = title;
	this.absolutePath = parentPath + SEP + title;
	owner = group;
	this.toolName = toolName;
	this.contents = new ArrayList<Content>();
	this.childs = new ArrayList<Folder>();
    }

    public Folder() {
	this(null, null, null, null);
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

    public Folder getParent() {
	return parent;
    }

    public Long getId() {
	return id;
    }

    public void setId(final Long id) {
	this.id = id;
    }

    public void setParent(final Folder parent) {
	this.parent = parent;
    }

    public List<Folder> getChilds() {
	return childs;
    }

    public void setChilds(final List<Folder> childs) {
	this.childs = childs;
    }

    public String getAbsolutePath() {
	return absolutePath;
    }

    public void setAbsolutePath(final String absolutePath) {
	this.absolutePath = absolutePath;
    }

    public List<Alias> getAliases() {
	return aliases;
    }

    public void setAliases(final List<Alias> aliases) {
	this.aliases = aliases;
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
	// FIXME: algo de lazy initialization (con size() se arregla...)
	contents.size();
	contents.add(descriptor);
    }

    public List<Content> getContents() {
	return contents;
    }

    public void addFolder(final Folder folder) {
	childs.add(folder);
    }

}
