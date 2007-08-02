package org.ourproject.kune.platf.client.workspace.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ContentDataDTO implements IsSerializable {
    private String docRef;
    private String content;

    public ContentDataDTO(String docRef, String content) {
	this.docRef = docRef;
	this.content = content;
    }

    public void setDocRef(String docRef) {
	this.docRef = docRef;
    }

    public void setContent(String content) {
	this.content = content;
    }

    public String getDocRef() {
	return docRef;
    }

    public String getContent() {
	return content;
    }

}
