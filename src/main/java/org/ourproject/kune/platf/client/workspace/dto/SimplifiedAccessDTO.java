package org.ourproject.kune.platf.client.workspace.dto;

public class SimplifiedAccessDTO {
    boolean isViewable;
    boolean isEditable;
    boolean isAdministrable;

    public boolean isViewable() {
	return isViewable;
    }

    public void setViewable(final boolean isViewable) {
	this.isViewable = isViewable;
    }

    public boolean isEditable() {
	return isEditable;
    }

    public void setEditable(final boolean isEditable) {
	this.isEditable = isEditable;
    }

    public boolean isAdministrable() {
	return isAdministrable;
    }

    public void setAdministrable(final boolean isAdministrable) {
	this.isAdministrable = isAdministrable;
    }

}
