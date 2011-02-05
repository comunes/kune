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
package org.ourproject.kune.platf.client.actions.ui;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.ui.FlowToolbar;

public class ComplexToolbar extends AbstractComposedGuiItem implements View {

    private final FlowToolbar toolbar;

    public ComplexToolbar(final GuiBindingsRegister bindings) {
        super(bindings);
        toolbar = new FlowToolbar();
        initWidget(toolbar);
    }

    public void addFill() {
        toolbar.addFill();
    }

    public void addSeparator() {
        toolbar.addSeparator();
    }

    public void addSpacer() {
        toolbar.addSpacer();
    }

    /**
     * Set the blank style
     */
    public void setCleanStyle() {
        toolbar.setBlankStyle();
    }

    /**
     * Set the normal grey style
     */
    public void setNormalStyle() {
        toolbar.setNormalStyle();
    }

    /**
     * Set the blank style
     */
    public void setTranspStyle() {
        toolbar.setTranspStyle();
    }

    @Override
    protected void add(final AbstractGuiItem item) {
        item.addStyleName("kune-floatleft");
        toolbar.add(item);
    }

    @Override
    protected void insert(final AbstractGuiItem item, final int position) {
        item.addStyleName("kune-floatleft");
        toolbar.insert(item, position);
    }

}
