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

public class EntityLiveSearchPresenter extends AbstractPresenter {

    private EntityLiveSearchView view;
    private EntityLiveSearchListener listener;

    public void init(final EntityLiveSearchView view) {
        this.view = view;
    }

    public View getView() {
        return view;
    }

    public void addListener(final EntityLiveSearchListener listener) {
        this.listener = listener;
    }

    public void show() {
        view.show();
    }

    public void hide() {
        view.hide();
    }

    public void fireListener(final String shortName, final String longName) {
        listener.onSelection(shortName, longName);
        hide();
    }

}
