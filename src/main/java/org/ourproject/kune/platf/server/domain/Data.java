package org.ourproject.kune.platf.server.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "text")
public class Data implements HasId {
    @Id
    @GeneratedValue
    Long id;

    char[] content;

    public Long getId() {
	return id;
    }

    public void setId(final Long id) {
	this.id = id;
    }

    public char[] getContent() {
	return content;
    }

    public void setContent(final char[] content) {
	this.content = content;
    }

}
