/*
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.workspace.client.workspace.ui;

import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.workspace.client.WorkspaceEvents;

import com.google.gwt.user.client.ui.AbstractImagePrototype;

public class StackSubItemAction {
    private final static Images img = Images.App.getInstance();
    public final static StackSubItemAction DEFAULT_VISIT_GROUP = new StackSubItemAction(img.groupHome(), Kune.I18N
            .t("Visit this member homepage"), WorkspaceEvents.GOTO);

    private final AbstractImagePrototype icon;
    private final String text;
    private final String action;

    public StackSubItemAction(final AbstractImagePrototype icon, final String text, final String action) {
        this.icon = icon;
        this.text = text;
        this.action = action;
    }

    public AbstractImagePrototype getIcon() {
        return icon;
    }

    public String getText() {
        return text;
    }

    public String getAction() {
        return action;
    }
}
