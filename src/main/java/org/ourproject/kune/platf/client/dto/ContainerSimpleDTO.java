
package org.ourproject.kune.platf.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ContainerSimpleDTO implements IsSerializable {
    private Long parentFolderId;
    private Long id;
    private String name;
    private String typeId;

    public ContainerSimpleDTO() {
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

    public void setId(final Long id) {
        this.id = id;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(final String typeId) {
        this.typeId = typeId;
    }

}
