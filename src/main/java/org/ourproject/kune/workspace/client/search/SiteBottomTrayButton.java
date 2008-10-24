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
 */package org.ourproject.kune.workspace.client.search;

import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.Window;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;

public class SiteBottomTrayButton {

    private final ToolbarButton traybarButton;

    public SiteBottomTrayButton(final String icon, final String tooltip, final Window dialog, final WorkspaceSkeleton ws) {
        traybarButton = new ToolbarButton();
        traybarButton.setTooltip(tooltip);
        traybarButton.setIcon(icon);
        traybarButton.addListener(new ButtonListenerAdapter() {
            @Override
            public void onClick(final Button button, final EventObject e) {
                if (dialog.isVisible()) {
                    dialog.hide();
                } else {
                    dialog.show();
                }
            }

        });
        ws.getSiteTraybar().addButton(traybarButton);
    }

    public void destroy() {
        traybarButton.destroy();
    }

}
