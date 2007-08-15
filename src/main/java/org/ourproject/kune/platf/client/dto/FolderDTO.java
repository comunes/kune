package org.ourproject.kune.platf.client.dto;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class FolderDTO implements IsSerializable {
    private Long id;

    /**
     * @gwt.typeArgs <org.ourproject.kune.platf.client.dto.FolderDTO>
     */
    private List childs;

    /**
     * @gwt.typeArgs <org.ourproject.kune.platf.client.dto.ContentDescriptorDTO>
     */
    private List contents;

    public FolderDTO() {
    }

    public Long getId() {
	return id;
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
