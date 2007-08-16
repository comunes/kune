package org.ourproject.kune.platf.client.dto;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ContainerDTO implements IsSerializable {
    public static final String SEP = "/";
    private Long parentFolderId;
    private Long id;
    private String name;
    private String absolutePath;

    /**
     * @gwt.typeArgs <org.ourproject.kune.platf.client.dto.ContainerDTO>
     */
    private List childs;

    /**
     * @gwt.typeArgs <org.ourproject.kune.platf.client.dto.ContentDTO>
     */
    private List contents;

    public ContainerDTO() {
    }

    public Long getParentFolderId() {
	return parentFolderId;
    }

    public void setParentFolderId(final Long parentFolderId) {
	this.parentFolderId = parentFolderId;
    }

    public Long getId() {
	return id;
    }

    public String getName() {
	return name;
    }

    public void setName(final String name) {
	this.name = name;
    }

    public String getAbsolutePath() {
	return absolutePath;
    }

    public void setAbsolutePath(final String absolutePath) {
	this.absolutePath = absolutePath;
    }

    public void setId(final Long id) {
	this.id = id;
    }

    public List getChilds() {
	return childs;
    }

    public void setChilds(final List childs) {
	this.childs = childs;
    }

    public List getContents() {
	return contents;
    }

    public void setContents(final List contents) {
	this.contents = contents;
    }

}
