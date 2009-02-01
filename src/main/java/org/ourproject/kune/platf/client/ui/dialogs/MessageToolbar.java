/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.platf.client.ui.dialogs;

import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.workspace.client.newgroup.SiteErrorType;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.gwtext.client.widgets.Toolbar;

public class MessageToolbar {
    private final Toolbar toolbar;
    private final Image errorIcon;
    private final Label errorLabel;

    public MessageToolbar(Images images, String errorLabelId) {
        toolbar = new Toolbar();
        errorLabel = new Label("");
        errorLabel.ensureDebugId(errorLabelId);
        errorIcon = new Image();
        images.error().applyTo(errorIcon);
        toolbar.addSpacer();
        toolbar.addElement(errorIcon.getElement());
        toolbar.setCls("k-error-tb");
        toolbar.addSpacer();
        toolbar.addSpacer();
        toolbar.addElement(errorLabel.getElement());
        errorIcon.setVisible(false);
        toolbar.setVisible(false);
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public void hideErrorMessage() {
        errorIcon.setVisible(false);
        errorLabel.setText("");
        toolbar.setVisible(false);
    }

    public void setErrorMessage(final String message, final SiteErrorType type) {
        errorLabel.setText(message);
        errorIcon.setVisible(true);
        toolbar.setVisible(true);
    }
}
