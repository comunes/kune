package org.ourproject.kune.workspace.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class AccessRightsDTO implements IsSerializable {
    public boolean isAdministrable;
    public boolean isEditable;
    public boolean isVisible;

    public AccessRightsDTO(final boolean isAdministrable, final boolean isEditable, final boolean isVisible) {
	this.isAdministrable = isAdministrable;
	this.isEditable = isEditable;
	this.isVisible = isVisible;
    }

    public AccessRightsDTO() {
	this(false, false, false);
    }
}
