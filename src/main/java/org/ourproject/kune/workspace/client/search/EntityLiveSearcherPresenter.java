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
package org.ourproject.kune.workspace.client.search;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.LinkDTO;

import com.calclab.suco.client.listener.Listener;

public class EntityLiveSearcherPresenter implements EntityLiveSearcher {

    private EntityLiveSearcherView view;
    private Listener<LinkDTO> listener;

    public EntityLiveSearcherPresenter() {
    }

    public View getView() {
        return view;
    }

    public void init(final EntityLiveSearcherView view) {
        this.view = view;
    }

    public void onSelection(final LinkDTO link) {
        listener.onEvent(link);
        view.hide();
    }

    public void onSelection(final Listener<LinkDTO> listener) {
        this.listener = listener;
    }

    public void show() {
        view.show();
    }
}
