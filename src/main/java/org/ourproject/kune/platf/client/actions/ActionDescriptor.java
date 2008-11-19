/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */package org.ourproject.kune.platf.client.actions;

import org.ourproject.kune.platf.client.dto.AccessRolDTO;
import org.ourproject.kune.platf.client.services.ImageDescriptor;

import com.calclab.suco.client.listener.Listener;

/**
 * The Class ActionDescriptor.
 */
public abstract class ActionDescriptor<T> {

    /** The icon url. */
    private String iconUrl;

    /** The icon descriptor. */
    private ImageDescriptor iconDescriptor;

    /** The text description. */
    private String textDescription;

    /** The tool tip. */
    private String toolTip;

    /** The must be confirmed. */
    private boolean mustBeConfirmed;

    /** The confirmation title. */
    private String confirmationTitle;

    /** The confirmation text. */
    private String confirmationText;

    /** The on perform call. */
    private final Listener<T> onPerformCall;

    /** The on not confirmed. */
    private final Listener<T> onNotConfirmed;

    /** The must be authenticated. */
    private boolean mustBeAuthenticated;

    /** The access rol. */
    private AccessRolDTO accessRol;

    /** The enable condition. */
    private ActionEnableCondition<T> enableCondition;

    /** The id. */
    private String id;

    /**
     * Instantiates a new action descriptor.
     * 
     * @param accessRolDTO
     *            the access rol dto
     * @param onPerformCall
     *            the on perform call
     */
    public ActionDescriptor(final AccessRolDTO accessRolDTO, final Listener<T> onPerformCall) {
        this.accessRol = accessRolDTO;
        this.onPerformCall = onPerformCall;
        this.mustBeConfirmed = false;
        this.onNotConfirmed = null;
        this.mustBeAuthenticated = true;
    }

    /**
     * Instantiates a new action descriptor.
     * 
     * @param accessRolDTO
     *            the access rol dto
     * @param onPerformCall
     *            the on perform call
     * @param enableCondition
     *            the enable condition
     */
    public ActionDescriptor(final AccessRolDTO accessRolDTO, final Listener<T> onPerformCall,
            final ActionEnableCondition<T> enableCondition) {
        this(accessRolDTO, onPerformCall);
        this.enableCondition = enableCondition;
    }

    /**
     * Fire on not confirmed.
     * 
     * @param parameter
     *            the parameter
     */
    @SuppressWarnings("unchecked")
    public void fireOnNotConfirmed(final Object parameter) {
        if (onNotConfirmed != null) {
            onNotConfirmed.onEvent((T) parameter);
        }
    }

    /**
     * Fire on perform call.
     * 
     * @param parameter
     *            the parameter
     */
    @SuppressWarnings("unchecked")
    public void fireOnPerformCall(final Object parameter) {
        onPerformCall.onEvent((T) parameter);
    }

    /**
     * Gets the access rol.
     * 
     * @return the access rol
     */
    public AccessRolDTO getAccessRol() {
        return accessRol;
    }

    /**
     * Gets the confirmation text.
     * 
     * @return the confirmation text
     */
    public String getConfirmationText() {
        return confirmationText;
    }

    /**
     * Gets the confirmation title.
     * 
     * @return the confirmation title
     */
    public String getConfirmationTitle() {
        return confirmationTitle;
    }

    /**
     * Gets the enable condition.
     * 
     * @return the enable condition
     */
    public ActionEnableCondition<T> getEnableCondition() {
        return enableCondition;
    }

    /**
     * Gets the icon descriptor.
     * 
     * @return the icon descriptor
     */
    public ImageDescriptor getIconDescriptor() {
        return iconDescriptor;
    }

    /**
     * Gets the icon url.
     * 
     * @return the icon url
     */
    public String getIconUrl() {
        return iconUrl;
    }

    /**
     * Gets the DOM unique id (used for debug)
     * 
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the text.
     * 
     * @return the text
     */
    public String getText() {
        return textDescription;
    }

    /**
     * Gets the tool tip.
     * 
     * @return the tool tip
     */
    public String getToolTip() {
        return toolTip;
    }

    /**
     * Checks if is must be confirmed.
     * 
     * @return true, if is must be confirmed
     */
    public boolean isMustBeConfirmed() {
        return mustBeConfirmed;
    }

    /**
     * Must be authenticated.
     * 
     * @return true, if successful
     */
    public boolean mustBeAuthenticated() {
        return mustBeAuthenticated;
    }

    /**
     * Sets the access rol.
     * 
     * @param accessRol
     *            the new access rol
     */
    public void setAccessRol(final AccessRolDTO accessRol) {
        this.accessRol = accessRol;
    }

    /**
     * Sets the confirmation text.
     * 
     * @param confirmationText
     *            the new confirmation text
     */
    public void setConfirmationText(final String confirmationText) {
        this.confirmationText = confirmationText;
    }

    /**
     * Sets the confirmation title.
     * 
     * @param confirmationTitle
     *            the new confirmation title
     */
    public void setConfirmationTitle(final String confirmationTitle) {
        this.confirmationTitle = confirmationTitle;
    }

    /**
     * Sets the enable condition.
     * 
     * @param enableCondition
     *            the new enable condition
     */
    public void setEnableCondition(final ActionEnableCondition<T> enableCondition) {
        this.enableCondition = enableCondition;
    }

    /**
     * Sets the icon descriptor.
     * 
     * @param iconDescriptor
     *            the new icon descriptor
     */
    public void setIconDescriptor(final ImageDescriptor iconDescriptor) {
        this.iconDescriptor = iconDescriptor;
    }

    /**
     * Sets the icon url.
     * 
     * @param iconUrl
     *            the new icon url
     */
    public void setIconUrl(final String iconUrl) {
        this.iconUrl = iconUrl;
    }

    /**
     * Sets the DOM unique id (used for debug)
     * 
     * @param id
     *            the new id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Sets the must be authenticated.
     * 
     * @param mustBeAuthenticated
     *            the new must be authenticated
     */
    public void setMustBeAuthenticated(final boolean mustBeAuthenticated) {
        this.mustBeAuthenticated = mustBeAuthenticated;
    }

    /**
     * Sets the must be confirmed.
     * 
     * @param mustBeConfirmed
     *            the new must be confirmed
     */
    public void setMustBeConfirmed(final boolean mustBeConfirmed) {
        this.mustBeConfirmed = mustBeConfirmed;
    }

    /**
     * Sets the text description.
     * 
     * @param textDescription
     *            the new text description
     */
    public void setTextDescription(final String textDescription) {
        this.textDescription = textDescription;
    }

    /**
     * Sets the tool tip.
     * 
     * @param toolTip
     *            the new tool tip
     */
    public void setToolTip(final String toolTip) {
        this.toolTip = toolTip;
    }
}
