package org.ourproject.kune.platf.server.domain;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
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

    @OneToOne
    private User editor;

    @Basic(optional = false)
    private Long createdOn;

    @OneToOne(cascade = { CascadeType.ALL })
    private Data data;

    @Version
    private int version;

    @OneToOne(fetch = FetchType.LAZY)
    private Revision previous;

    public Revision() {
	this.data = new Data();
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

    public Data getData() {
	return data;
    }

    public void setData(final Data content) {
	this.data = content;
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

    public void setDataContent(final String text) {
	this.data.setContent(text.toCharArray());
    }

    public void setDataTitle(final String text) {
	this.data.setTitle(text);
    }

}
