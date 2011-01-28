/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.workspace.client.options;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.ui.dialogs.tabbed.AbstractTabbedDialog;

import cc.kune.common.client.noti.NotifyLevel;

public interface EntityOptions extends AbstractTabbedDialog {

    @Override
    public void addTab(View tab);

    public View getView();

    @Override
    public void hideMessages();

    @Override
    public void setErrorMessage(String message, NotifyLevel level);

    @Override
    public void show();

}
