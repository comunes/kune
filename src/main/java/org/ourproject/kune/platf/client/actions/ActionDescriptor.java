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
 */
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

    private boolean mustBeAuthenticated;

    private AccessRolDTO accessRol;

    private ActionEnableCondition<T> enableCondition;

    public ActionDescriptor(final AccessRolDTO accessRolDTO, final Listener<T> onPerformCall) {
        this.accessRol = accessRolDTO;
        this.onPerformCall = onPerformCall;
        this.mustBeConfirmed = false;
        this.onNotConfirmed = null;
        this.mustBeAuthenticated = true;
    }

    public ActionDescriptor(final AccessRolDTO accessRolDTO, final Listener<T> onPerformCall,
            final ActionEnableCondition<T> enableCondition) {
        this(accessRolDTO, onPerformCall);
        this.enableCondition = enableCondition;
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

    public boolean mustBeAuthenticated() {
        return mustBeAuthenticated;
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

    public void setMustBeAuthenticated(final boolean mustBeAuthenticated) {
        this.mustBeAuthenticated = mustBeAuthenticated;
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
