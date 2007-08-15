package org.ourproject.kune.workspace.client.dto;

import org.ourproject.kune.platf.client.dto.AccessListsDTO;
import org.ourproject.kune.platf.client.dto.AccessRightsDTO;
import org.ourproject.kune.platf.client.dto.FolderDTO;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.StateToken;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ContentDTO implements IsSerializable {
    private String documentId;
    private String content;
    private String title;
    private String toolName;
    private GroupDTO group;
    private FolderDTO folder;
    private AccessListsDTO accessLists;
    private AccessRightsDTO contentRights;
    private AccessRightsDTO folderRights;
    private Double rate;
    private Integer rateByUsers;

    public ContentDTO() {
	this(null, null, null);
    }

    public ContentDTO(final String docRef, final String title, final String content) {
	this.documentId = docRef;
	this.title = title;
	this.content = content;
    }

    public void setDocumentId(final String docRef) {
	this.documentId = docRef;
    }

    public void setContent(final String content) {
	this.content = content;
    }

    public String getDocumentId() {
	return documentId;
    }

    public String getContent() {
	return content;
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

    public GroupDTO getGroup() {
	return this.group;
    }

    public void setGroup(final GroupDTO group) {
	this.group = group;
    }

    public AccessRightsDTO getContentRights() {
	return this.contentRights;
    }

    public void setContentRights(final AccessRightsDTO accessRights) {
	this.contentRights = accessRights;
    }

    public FolderDTO getFolder() {
	return folder;
    }

    public void setFolder(final FolderDTO folder) {
	this.folder = folder;
    }

    public String encodeState() {
	return StateToken.encode(group.getShortName(), toolName, folder.getId().toString(), getDocumentId());
    }

    public boolean hasDocument() {
	return documentId != null;
    }

    public AccessListsDTO getAccessLists() {
	return accessLists;
    }

    public void setAccessLists(final AccessListsDTO accessLists) {
	this.accessLists = accessLists;
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

    public AccessRightsDTO getFolderRights() {
	return folderRights;
    }

    public void setFolderRights(final AccessRightsDTO folderRights) {
	this.folderRights = folderRights;
    }

}
