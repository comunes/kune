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
package cc.kune.common.client.actions.gwtui;

import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.ParentWidget;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.ToolbarSeparatorDescriptor;
import cc.kune.common.client.actions.ui.descrip.ToolbarSeparatorDescriptor.Type;
import cc.kune.common.client.errors.UIException;

public class GwtToolbarSeparatorGui extends AbstractGuiItem {

    @Override
    public AbstractGuiItem create(final GuiActionDescrip descriptor) {
        final GwtToolbarGui toolbar = ((GwtToolbarGui) descriptor.getParent().getValue(ParentWidget.PARENT_UI));
        if (toolbar == null) {
            throw new UIException("To add a toolbar separator you need to add the toolbar before. Item: " + descriptor);
        }
        final Type type = ((ToolbarSeparatorDescriptor) descriptor).getSeparatorType();
        switch (type) {
        case fill:
            toolbar.addFill();
            break;
        case spacer:
            toolbar.addSpacer();
            break;
        case separator:
            toolbar.addSeparator();
            break;
        default:
            break;
        }
        return toolbar;
    }

    @Override
    protected void setEnabled(final boolean enabled) {
    }

    @Override
    protected void setIconStyle(final String style) {
    }

    @Override
    protected void setText(final String text) {
    }

    @Override
    protected void setToolTipText(final String text) {
    }

    @Override
    public boolean shouldBeAdded() {
        return false;
    }
}
