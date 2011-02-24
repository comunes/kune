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
package cc.kune.common.client.actions;

import com.google.gwt.resources.client.ImageResource;

public abstract class AbstractExtendedAction extends AbstractAction {
    public static final String NO_ICON = null;
    public static final String NO_TEXT = null;

    public AbstractExtendedAction() {
        super();
    }

    public AbstractExtendedAction(final String text) {
        this(text, null, null);
    }

    public AbstractExtendedAction(final String text, final String iconCls) {
        this(text, null, iconCls);
    }

    public AbstractExtendedAction(final String text, final String tooltip, final String iconCls) {
        super();
        super.putValue(Action.NAME, text);
        super.putValue(Action.SHORT_DESCRIPTION, tooltip);
        super.putValue(Action.SMALL_ICON, iconCls);
    }

    public AbstractExtendedAction withIcon(final ImageResource icon) {
        super.putValue(Action.SHORT_DESCRIPTION, icon);
        return this;
    }

    public AbstractExtendedAction withIconCls(final String icon) {
        super.putValue(Action.SHORT_DESCRIPTION, icon);
        return this;
    }

    public AbstractExtendedAction withText(final String text) {
        super.putValue(Action.NAME, text);
        return this;
    }

    public AbstractExtendedAction withToolTip(final String tooltip) {
        super.putValue(Action.SHORT_DESCRIPTION, tooltip);
        return this;
    }
}