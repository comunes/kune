package org.ourproject.kune.platf.server.model;

public class AccessRight {

    boolean isAdministrable;
    boolean isEditable;
    boolean isVisible;

    public AccessRight(final boolean isAdministrable, final boolean isEditable, final boolean isVisible) {
	this.isAdministrable = isAdministrable;
	this.isEditable = isEditable;
	this.isVisible = isVisible;
    }

    public AccessRight() {
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

}
