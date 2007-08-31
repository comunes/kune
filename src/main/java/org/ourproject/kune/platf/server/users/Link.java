package org.ourproject.kune.platf.server.users;

public class Link {
    private String name;
    private String link;

    public Link() {
	this(null, null);
    }

    public Link(final String name, final String link) {
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
