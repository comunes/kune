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

import java.util.List;

import cc.kune.common.client.errors.UIException;

import com.google.gwt.user.client.ui.Composite;

public abstract class AbstractComposedGuiItem extends Composite {
    private final GuiBindingsRegister bindings;
    private GuiActionDescCollection guiItems;

    public AbstractComposedGuiItem(final GuiBindingsRegister bindings) {
        super();
        this.bindings = bindings;
    }

    protected abstract void add(AbstractGuiItem item);

    public void add(final OldGuiActionDescrip... descriptors) {
        for (final OldGuiActionDescrip descriptor : descriptors) {
            addAction(descriptor);
        }
    }

    public void addAction(final OldGuiActionDescrip descriptor) {
        getGuiItems().add(descriptor);
        beforeAddWidget(descriptor);
    }

    public void addAll(final GuiActionDescCollection descriptors) {
        for (final OldGuiActionDescrip descriptor : descriptors) {
            addAction(descriptor);
        }
    }

    public void addAll(final List<OldGuiActionDescrip> descriptors) {
        for (final OldGuiActionDescrip descriptor : descriptors) {
            addAction(descriptor);
        }
    }

    protected void beforeAddWidget(final OldGuiActionDescrip descrip) {
        if (descrip.mustBeAdded()) {
            final GuiBinding binding = bindings.get(descrip.getType());
            if (binding == null) {
                throw new UIException("Unknown binding for: " + descrip);
            } else {
                final AbstractGuiItem item = binding.create(descrip);
                if (binding.isAttachable()) {
                    if (descrip.getPosition() == OldGuiActionDescrip.NO_POSITION) {
                        add(item);
                    } else {
                        insert(item, descrip.getPosition());
                    }
                }
            }
        }
    }

    public GuiActionDescCollection getGuiItems() {
        if (guiItems == null) {
            guiItems = new GuiActionDescCollection();
        }
        return guiItems;
    }

    protected abstract void insert(AbstractGuiItem item, int position);
}
