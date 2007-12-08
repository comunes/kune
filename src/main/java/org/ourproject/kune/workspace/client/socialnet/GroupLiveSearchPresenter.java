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

package org.ourproject.kune.workspace.client.socialnet;

import org.ourproject.kune.platf.client.AbstractPresenter;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.workspace.client.workspace.GroupLiveSearchComponent;

public class GroupLiveSearchPresenter extends AbstractPresenter implements GroupLiveSearchComponent {

    private GroupLiveSearchView view;
    private GroupLiveSearchListener listener;

    public void init(final GroupLiveSearchView view) {
        this.view = view;
    }

    public View getView() {
        return view;
    }

    public void addListener(final GroupLiveSearchListener listener) {
        this.listener = listener;
    }

    public void reset() {
        this.listener = null;
        view.reset();
    }

    public void show() {
        view.show();
    }

    public void hide() {
        view.hide();
    }

    public void fireListener(final String groupShortName) {
        listener.onSelection(groupShortName);
        hide();
    }

}
