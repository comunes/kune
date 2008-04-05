
package org.ourproject.kune.platf.server.access;

public class AccessRights {
    boolean isAdministrable;
    boolean isEditable;
    boolean isVisible;

    public AccessRights(final boolean isAdministrable, final boolean isEditable, final boolean isVisible) {
	this.isAdministrable = isAdministrable;
	this.isEditable = isEditable;
	this.isVisible = isVisible;
    }

    public AccessRights() {
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
