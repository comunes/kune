package org.ourproject.kune.workspace.client.dto;

import org.ourproject.kune.platf.client.dto.GroupDTO;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ContentDTO implements IsSerializable {
    private String docRef;
    private String content;
    private String title;
    private String toolName;
    private GroupDTO group;
    private String text;
    private AccessRightsDTO accessRights;

    // private SimplifiedAccessDTO access;

    public ContentDTO() {
	this(null, null, null);
    }

    public ContentDTO(final String docRef, final String title, final String content) {
	this.docRef = docRef;
	this.title = title;
	this.content = content;
    }

    public void setDocRef(final String docRef) {
	this.docRef = docRef;
    }

    public void setContent(final String content) {
	this.content = content;
    }

    public String getDocRef() {
	return docRef;
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

    public String getText() {
	return this.text;
    }

    public void setText(final String text) {
	this.text = text;
    }

    public AccessRightsDTO getAccessRights() {
	return this.accessRights;
    }

    public void setAccessRights(final AccessRightsDTO accessRights) {
	this.accessRights = accessRights;
    }

}
