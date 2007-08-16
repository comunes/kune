package org.ourproject.kune.platf.server.state;

import org.ourproject.kune.platf.server.access.AccessRights;
import org.ourproject.kune.platf.server.domain.AccessLists;
import org.ourproject.kune.platf.server.domain.Folder;
import org.ourproject.kune.platf.server.domain.Group;

public class State {
    private String documentId;
    private String content;
    private String title;
    private String toolName;
    private Group group;
    private Folder folder;
    private AccessLists accessLists;
    private AccessRights contentRights;
    private AccessRights folderRights;
    private Double rate;
    private Integer rateByUsers;

    public State() {
    }

    public AccessLists getAccessLists() {
	return accessLists;
    }

    public void setAccessLists(final AccessLists accessLists) {
	this.accessLists = accessLists;
    }

    public AccessRights getContentRights() {
	return contentRights;
    }

    public String getDocumentId() {
	return documentId;
    }

    public void setDocumentId(final String docRef) {
	this.documentId = docRef;
    }

    public AccessRights getFolderRights() {
	return folderRights;
    }

    public void setFolderRights(final AccessRights folderRights) {
	this.folderRights = folderRights;
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

    public void setContentRights(final AccessRights accessRights) {
	this.contentRights = accessRights;
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
