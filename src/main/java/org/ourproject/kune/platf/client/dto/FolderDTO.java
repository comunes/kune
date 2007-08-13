package org.ourproject.kune.platf.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class FolderDTO implements IsSerializable {
    Long id;

    public Long getId() {
	return id;
    }

    public void setId(final Long id) {
	this.id = id;
    }

}
