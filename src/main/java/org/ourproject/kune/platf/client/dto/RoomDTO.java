package org.ourproject.kune.platf.client.dto;

public class RoomDTO {

    private String name;

    private String subject;

    public RoomDTO() {

    }

    RoomDTO(final String name) {
	this();
	this.name = name;
    }

    public String getName() {
	return name;
    }

    public void setName(final String name) {
	this.name = name;
    }

    public String getSubject() {
	return subject;
    }

    public void setSubject(final String subject) {
	this.subject = subject;
    }

}
