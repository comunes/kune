package org.ourproject.kune.platf.server.domain;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "revisions")
public class Revision {
    @Id
    @GeneratedValue
    private Long id;
    private User editor;

    @Basic(optional = false)
    private Long modifiedOn;

    private Text content;

    @Version
    private int version;

    @OneToOne(fetch = FetchType.LAZY)
    private Revision previous;

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

    public Long getModifiedOn() {
	return modifiedOn;
    }

    public void setModifiedOn(final Long modifiedOn) {
	this.modifiedOn = modifiedOn;
    }

    public Text getContent() {
	return content;
    }

    public void setContent(final Text content) {
	this.content = content;
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

}
