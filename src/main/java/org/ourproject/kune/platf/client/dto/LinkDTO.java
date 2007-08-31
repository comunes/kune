package org.ourproject.kune.platf.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class LinkDTO implements IsSerializable {
    private String name;
    private String link;

    public LinkDTO() {
	this(null, null);
    }

    public LinkDTO(final String name, final String link) {
	this.name = name;
	this.link = link;
    }

    public String getName() {
	return name;
    }

    public void setName(final String name) {
	this.name = name;
    }

    public String getLink() {
	return link;
    }

    public void setLink(final String link) {
	this.link = link;
    }

}
