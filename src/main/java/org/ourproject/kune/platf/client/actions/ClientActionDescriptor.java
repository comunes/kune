package org.ourproject.kune.platf.client.actions;

import org.ourproject.kune.platf.client.dto.AccessRolDTO;
import org.ourproject.kune.platf.client.services.ImageDescriptor;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.suco.client.signal.Slot;

public class ClientActionDescriptor<T> {

    private String iconUrl;
    private ImageDescriptor iconDescriptor;
    private String textDescription;
    private String toolTip;

    private boolean mustBeConfirmed;
    private String confirmationTitle;
    private String confirmationText;

    private boolean isMenuAction;
    private String parentMenuTitle;
    private String parentMenuIconUrl;
    private String parentSubMenuTitle;

    private final Slot<T> onPerformCall;
    private final Slot<T> onNotConfirmed;

    private AccessRolDTO accessRol;

    private ActionPosition actionPosition;

    public ClientActionDescriptor(final AccessRolDTO accessRolDTO, final ActionPosition actionPosition,
	    final Slot<T> onPerformCall) {
	accessRol = accessRolDTO;
	this.actionPosition = actionPosition;
	this.onPerformCall = onPerformCall;
	mustBeConfirmed = false;
	onNotConfirmed = null;
	isMenuAction = false;
    }

    @SuppressWarnings("unchecked")
    public void fireOnNotConfirmed(final Object parameter) {
	if (onNotConfirmed != null) {
	    onNotConfirmed.onEvent((T) parameter);
	}
    }

    @SuppressWarnings("unchecked")
    public void fireOnPerformCall(final Object parameter) {
	onPerformCall.onEvent((T) parameter);
    }

    public AccessRolDTO getAccessRol() {
	return accessRol;
    }

    public ActionPosition getActionPosition() {
	return actionPosition;
    }

    public String getConfirmationText() {
	return confirmationText;
    }

    public String getConfirmationTitle() {
	return confirmationTitle;
    }

    public ImageDescriptor getIconDescriptor() {
	return iconDescriptor;
    }

    public String getIconUrl() {
	return iconUrl;
    }

    public String getParentMenuIconUrl() {
	return parentMenuIconUrl;
    }

    public String getParentMenuTitle() {
	return parentMenuTitle;
    }

    public String getParentSubMenuTitle() {
	return parentSubMenuTitle;
    }

    public String getText() {
	return textDescription;
    }

    public String getToolTip() {
	return toolTip;
    }

    public boolean isMenuAction() {
	return isMenuAction;
    }

    public boolean isMustBeConfirmed() {
	return mustBeConfirmed;
    }

    public void setAccessRol(final AccessRolDTO accessRol) {
	this.accessRol = accessRol;
    }

    public void setConfirmationText(final String confirmationText) {
	this.confirmationText = confirmationText;
    }

    public void setConfirmationTitle(final String confirmationTitle) {
	this.confirmationTitle = confirmationTitle;
    }

    public void setIconDescriptor(final ImageDescriptor iconDescriptor) {
	this.iconDescriptor = iconDescriptor;
    }

    public void setIconUrl(final String iconUrl) {
	this.iconUrl = iconUrl;
    }

    public void setMustBeConfirmed(final boolean mustBeConfirmed) {
	this.mustBeConfirmed = mustBeConfirmed;
    }

    public void setActionPosition(final ActionPosition actionPosition) {
	this.actionPosition = actionPosition;
    }

    public void setParentMenuIconUrl(final String parentMenuIconUrl) {
	this.parentMenuIconUrl = parentMenuIconUrl;
    }

    public void setParentMenuTitle(final String parentMenuTitle) {
	isMenuAction = true;
	this.parentMenuTitle = parentMenuTitle;
    }

    public void setParentSubMenuTitle(final String parentSubMenuTitle) {
	if (parentMenuTitle == null) {
	    Log.warn("Please set parentMenuTitle before");
	}
	this.parentSubMenuTitle = parentSubMenuTitle;
    }

    public void setTextDescription(final String textDescription) {
	this.textDescription = textDescription;
    }

    public void setToolTip(final String toolTip) {
	this.toolTip = toolTip;
    }

}
