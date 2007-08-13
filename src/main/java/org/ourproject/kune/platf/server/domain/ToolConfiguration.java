package org.ourproject.kune.platf.server.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tool_configurations")
public class ToolConfiguration {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    private Folder root;

    @OneToOne
    private ContentDescriptor welcome;

    public void setRoot(final Folder root) {
	this.root = root;

    }

    public Long getId() {
	return id;
    }

    public void setId(final Long id) {
	this.id = id;
    }

    public Folder getRoot() {
	return root;
    }

    public void setWelcome(final ContentDescriptor descriptor) {
	this.welcome = descriptor;
    }

    public ContentDescriptor getWelcome() {
	return welcome;
    }

}
