package org.ourproject.kune.platf.client.actions;

import org.ourproject.kune.platf.client.dto.AccessRolDTO;
import org.ourproject.kune.platf.client.services.ImageDescriptor;

import com.calclab.suco.client.listener.Listener;

public abstract class ActionDescriptor<T> {

    private String iconUrl;
    private ImageDescriptor iconDescriptor;
    private String textDescription;
    private String toolTip;

    private boolean mustBeConfirmed;
    private String confirmationTitle;
    private String confirmationText;

    private final Listener<T> onPerformCall;
    private final Listener<T> onNotConfirmed;

    private AccessRolDTO accessRol;

    private ActionEnableCondition<T> enableCondition;

    public ActionDescriptor(final AccessRolDTO accessRolDTO, final Listener<T> onPerformCall,
	    final ActionEnableCondition<T> enableCondition) {
	this(accessRolDTO, onPerformCall);
	this.enableCondition = enableCondition;
    }

    public ActionDescriptor(final AccessRolDTO accessRolDTO, final Listener<T> onPerformCall) {
	accessRol = accessRolDTO;
	this.onPerformCall = onPerformCall;
	mustBeConfirmed = false;
	onNotConfirmed = null;
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

    public String getConfirmationText() {
	return confirmationText;
    }

    public String getConfirmationTitle() {
	return confirmationTitle;
    }

    public ActionEnableCondition<T> getEnableCondition() {
	return enableCondition;
    }

    public ImageDescriptor getIconDescriptor() {
	return iconDescriptor;
    }

    public String getIconUrl() {
	return iconUrl;
    }

    public String getText() {
	return textDescription;
    }

    public String getToolTip() {
	return toolTip;
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

    public void setEnableCondition(final ActionEnableCondition<T> enableCondition) {
	this.enableCondition = enableCondition;
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

    public void setTextDescription(final String textDescription) {
	this.textDescription = textDescription;
    }

    public void setToolTip(final String toolTip) {
	this.toolTip = toolTip;
    }

}
