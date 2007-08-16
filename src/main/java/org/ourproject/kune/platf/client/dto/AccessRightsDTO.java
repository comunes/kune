package org.ourproject.kune.platf.client.dto;

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

    public boolean isAdministrable() {
	return isAdministrable;
    }

    public void setAdministrable(final boolean isAdministrable) {
	this.isAdministrable = isAdministrable;
    }

    public boolean isEditable() {
	return isEditable;
    }

    public void setEditable(final boolean isEditable) {
	this.isEditable = isEditable;
    }

    public boolean isVisible() {
	return isVisible;
    }

    public void setVisible(final boolean isVisible) {
	this.isVisible = isVisible;
    }

    public String toString() {
	return "[" + isAdministrable + ", " + isEditable + ", " + isVisible + "]";
    }

    public boolean equals(final Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	final AccessRightsDTO other = (AccessRightsDTO) obj;
	if (isAdministrable != other.isAdministrable) {
	    return false;
	}
	if (isEditable != other.isEditable) {
	    return false;
	}
	if (isVisible != other.isVisible) {
	    return false;
	}
	return true;
    }

}
