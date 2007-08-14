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

    public Folder setRoot(final Folder root) {
	this.root = root;
	return root;
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

}
