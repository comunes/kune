package org.ourproject.kune.platf.server.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "folders")
public class Folder implements HasId {
    public static final String SEP = "/";

    @Id
    @GeneratedValue
    Long id;

    @OneToOne
    private Group owner;

    @OneToOne(fetch = FetchType.LAZY)
    private Folder parent;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Folder> childs;

    @OneToMany(mappedBy = "folder")
    private final List<ContentDescriptor> contents;

    @Basic(optional = false)
    private String absolutePath;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Alias> aliases;

    private String toolName;

    public Folder(final String absolutePath, final Group group, final String toolName) {
	this.absolutePath = absolutePath;
	owner = group;
	this.toolName = toolName;
	this.contents = new ArrayList<ContentDescriptor>();
	this.childs = new ArrayList<Folder>();
    }

    public Folder() {
	this(null, null, null);
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

    public void addContent(final ContentDescriptor descriptor) {
	// FIXME: algo de lazy initialization (con size() se arregla...)
	contents.size();
	contents.add(descriptor);
    }

    public List<ContentDescriptor> getContents() {
	return contents;
    }

    public void addFolder(final Folder folder) {
	childs.add(folder);
    }

}
