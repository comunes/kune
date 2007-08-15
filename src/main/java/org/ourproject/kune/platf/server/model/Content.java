package org.ourproject.kune.platf.server.model;

import org.ourproject.kune.platf.server.domain.AccessLists;
import org.ourproject.kune.platf.server.domain.Folder;
import org.ourproject.kune.platf.server.domain.Group;

public class Content {
    private String documentId;
    private String content;
    private String title;
    private String toolName;
    private Group group;
    private Folder folder;
    private AccessLists accessLists;
    private AccessRights accessRights;
    private Double rate;
    private Integer rateByUsers;

    public Content() {
    }

    public AccessLists getAccessLists() {
	return accessLists;
    }

    public void setAccessLists(final AccessLists accessLists) {
	this.accessLists = accessLists;
    }

    public AccessRights getAccessRights() {
	return accessRights;
    }

    public String getDocumentId() {
	return documentId;
    }

    public void setDocumentId(final String docRef) {
	this.documentId = docRef;
    }

    public String getContent() {
	return content;
    }

    public void setContent(final String content) {
	this.content = content;
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(final String title) {
	this.title = title;
    }

    public String getToolName() {
	return toolName;
    }

    public void setToolName(final String toolName) {
	this.toolName = toolName;
    }

    public Group getGroup() {
	return group;
    }

    public void setGroup(final Group group) {
	this.group = group;
    }

    public void setAccessRights(final AccessRights accessRights) {
	this.accessRights = accessRights;
    }

    public Folder getFolder() {
	return folder;
    }

    public void setFolder(final Folder folder) {
	this.folder = folder;
    }

    public Double getRate() {
	return rate;
    }

    public void setRate(final Double rate) {
	this.rate = rate;
    }

    public Integer getRateByUsers() {
	return rateByUsers;
    }

    public void setRateByUsers(final Integer rateByUsers) {
	this.rateByUsers = rateByUsers;
    }

}
