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

package org.ourproject.kune.workspace.client.tags.ui;

import org.ourproject.kune.platf.client.ui.DropDownPanel;
import org.ourproject.kune.workspace.client.tags.TagsPresenter;
import org.ourproject.kune.workspace.client.tags.TagsView;

import com.google.gwt.user.client.ui.Label;

public class TagsPanel extends DropDownPanel implements TagsView {

    public TagsPanel(final TagsPresenter presenter) {
        super("Tags", true);
        addStyleName("kune-Margin-Medium-t");
    }

    public void setTags(final String thisIsOnlyForTests) {
        super.setContent(new Label(thisIsOnlyForTests));
    }

}
