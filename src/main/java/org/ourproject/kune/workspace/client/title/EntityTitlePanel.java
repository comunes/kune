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
package org.ourproject.kune.workspace.client.title;

import org.ourproject.kune.platf.client.ui.IconLabelEditable;
import org.ourproject.kune.workspace.client.skel.SimpleToolbar;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.calclab.suco.client.events.Listener2;
import com.google.gwt.user.client.ui.Image;

public class EntityTitlePanel implements EntityTitleView {
    public static final String ENTITY_TITLE_RIGHT_TITLE = "k-entity-title-title";
    private final IconLabelEditable titleLabel;
    private final Image icon;

    public EntityTitlePanel(final WorkspaceSkeleton ws, final EntityTitlePresenter presenter) {
        icon = new Image();
        titleLabel = new IconLabelEditable();
        titleLabel.onEdit(new Listener2<String, String>() {
            public void onEvent(String oldName, String newName) {
                presenter.onTitleRename(oldName, newName);
            }
        });

        final SimpleToolbar wsTitle = ws.getEntityWorkspace().getTitleComponent();
        wsTitle.add(icon);
        wsTitle.add(titleLabel);
        wsTitle.addFill();
        // wsTitle.add(dateLabel);
        icon.addStyleName("kune-Margin-Large-l");
        titleLabel.addStyleName("kune-Margin-Medium-l");
        titleLabel.addStyleName("kune-ft17px");
        titleLabel.ensureDebugId(ENTITY_TITLE_RIGHT_TITLE);
        icon.setVisible(false);
    }

    public void setContentIcon(final String iconUrl) {
        icon.setUrl(iconUrl);
    }

    public void setContentIconVisible(final boolean visible) {
        icon.setVisible(visible);
        if (visible) {
            titleLabel.removeStyleName("kune-Margin-Large-l");
            titleLabel.addStyleName("kune-Margin-Medium-l");
        } else {
            titleLabel.removeStyleName("kune-Margin-Medium-l");
            titleLabel.addStyleName("kune-Margin-Large-l");
        }
    }

    public void setContentTitle(final String title) {
        titleLabel.setText(title);
    }

    public void setContentTitleEditable(final boolean editable) {
        titleLabel.setEditable(editable);
    }

    public void setContentTitleVisible(final boolean visible) {
        titleLabel.setVisible(visible);
    }

}
