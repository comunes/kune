/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.platf.client.actions.toolbar;

import org.ourproject.kune.platf.client.actions.ActionToolbarDescriptor;
import org.ourproject.kune.platf.client.shortcuts.ShortcutDescriptor;

import com.calclab.suco.client.events.Listener0;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;

public class ActionToolbarButton<T> extends ToolbarButton {

    public ActionToolbarButton(ActionToolbarDescriptor<T> action, final String id, boolean enabled,
            boolean isPushButton, boolean pressed, final Listener0 onclick) {
        final String text = action.getText();
        final String iconUrl = action.getIconUrl();
        final String iconCls = action.getIconCls();
        if (text != null) {
            super.setText(text);
        }
        if (isPushButton) {
            super.setEnableToggle(true);
            super.setPressed(pressed);
        }
        super.addListener(new ButtonListenerAdapter() {
            @Override
            public void onClick(final Button button, final EventObject e) {
                e.stopEvent();
                onclick.onEvent();
            }
        });
        if (iconUrl != null) {
            super.setIcon(iconUrl);
        }
        if (iconCls != null) {
            super.setIconCls(iconCls);
        }
        String toolTip = action.getToolTip();
        ShortcutDescriptor shortcut = action.getShortcut();
        super.setTooltip((toolTip == null ? "" : toolTip) + (shortcut != null ? shortcut.toString() : ""));
        setEnableButton(enabled);
        // Waring: If you put set Id above click listener fires twice
        super.setId(id);
    }

    private void setEnableButton(final boolean enabled) {
        if (enabled) {
            super.enable();
        } else {
            super.disable();
        }
    }

}
