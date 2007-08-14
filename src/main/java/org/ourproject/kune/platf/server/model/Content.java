package org.ourproject.kune.platf.server.model;

import org.ourproject.kune.platf.server.domain.AccessLists;
import org.ourproject.kune.platf.server.domain.ContentDescriptor;
import org.ourproject.kune.platf.server.domain.Folder;
import org.ourproject.kune.platf.server.domain.Group;

public class Content {

    // Mapped fields from ContentDescriptor
    private String docRef;
    private String content;
    private String title;

    // Mapped fields from Folder
    private String toolName;
    private Group group;

    // Other mapped fields
    private Folder folder;
    private AccessLists accessLists;
    private AccessRights accessRights;
    private Double rate;
    private Integer rateByUsers;

    // Unmapped fields
    private ContentDescriptor descriptor;

    public void prepare() {
	docRef = descriptor.getId().toString();
	title = descriptor.getLastRevision().getData().getTitle();
	// TODO: take mime in consideration
	char[] data = descriptor.getLastRevision().getData().getContent();
	content = data != null ? data.toString() : null;
	toolName = folder.getToolName();
	group = folder.getOwner();
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

    public String getDocRef() {
	return docRef;
    }

    public void setDocRef(final String docRef) {
	this.docRef = docRef;
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

    public Content(final ContentDescriptor descriptor, final Folder folder) {
	this.descriptor = descriptor;
	this.folder = folder;
    }

    public ContentDescriptor getDescriptor() {
	return descriptor;
    }

    public void setDescriptor(final ContentDescriptor descriptor) {
	this.descriptor = descriptor;
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
