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
package org.ourproject.kune.workspace.client.site.msg;

import org.ourproject.kune.platf.client.ui.noti.NotifyUser;
import org.ourproject.kune.platf.client.ui.noti.NotifyUser.Level;

import com.google.gwt.user.client.ui.HTML;
import com.gwtextux.client.widgets.window.ToastWindow;

public class SiteToastMessagePanel implements SiteToastMessageView {

    public SiteToastMessagePanel() {
    }

    public void showMessage(String title, String message, Level level) {
        HTML html = new HTML(message);
        html.addStyleName("kune-Margin-Medium-trbl");
        ToastWindow toastWindow = new ToastWindow(title, html.toString());
        String iconCls = NotifyUser.getCls(level);
        toastWindow.setIconCls(iconCls);
        toastWindow.show();
    }

}
