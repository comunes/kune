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

package org.ourproject.kune.workspace.client.workspace;

import org.ourproject.kune.platf.client.View;

public class ContentTitlePresenter implements ContentTitleComponent {

    private ContentTitleView view;

    public void init(final ContentTitleView view) {
        this.view = view;
    }

    public void setContentTitle(final String title) {
        view.setContentTitle(title);
    }

    public void setContentDate(final String date) {
        view.setContentDate(date);
    }

    public void setContentDateVisible(final boolean visible) {
        view.setDateVisible(visible);
    }

    public View getView() {
        return view;
    }

    public void onTitleClicked() {
        // TODO Auto-generated method stub

    }

}
