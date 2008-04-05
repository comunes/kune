
package org.ourproject.kune.platf.client.dto;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ContainerDTO implements IsSerializable {
    private Long parentFolderId;
    private Long id;
    private String name;
    private ContainerSimpleDTO[] absolutePath;
    private String typeId;
    private List<ContainerDTO> childs;
    private List<ContentDTO> contents;

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

    public ContainerSimpleDTO[] getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(final ContainerSimpleDTO[] absolutePath) {
        this.absolutePath = absolutePath;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public List<ContainerDTO> getChilds() {
        return childs;
    }

    public void setChilds(final List<ContainerDTO> childs) {
        this.childs = childs;
    }

    public List<ContentDTO> getContents() {
        return contents;
    }

    public void setContents(final List<ContentDTO> contents) {
        this.contents = contents;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(final String typeId) {
        this.typeId = typeId;
    }

}
