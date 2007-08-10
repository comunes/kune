package org.ourproject.kune.platf.server.domain;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "folders")
public class Folder {

    @OneToOne
    private Group owner;

    @OneToOne(fetch = FetchType.LAZY)
    private Folder parent;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Folder> childs;

    @Basic(optional = false)
    private String absolutePath;
    @Basic(optional = false)
    private String idPath;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Alias> aliases;

    public Folder getParent() {
	return parent;
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

    public String getIdPath() {
	return idPath;
    }

    public void setIdPath(final String idPath) {
	this.idPath = idPath;
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
}
