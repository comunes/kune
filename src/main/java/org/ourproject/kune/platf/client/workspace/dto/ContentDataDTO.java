package org.ourproject.kune.platf.client.workspace.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ContentDataDTO implements IsSerializable {
    private String docRef;
    private String content;
    private String title;

    // private SimplifiedAccessDTO access;

    public ContentDataDTO() {
	this(null, null, null);
    }

    public ContentDataDTO(final String docRef, final String title, final String content) {
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

}
