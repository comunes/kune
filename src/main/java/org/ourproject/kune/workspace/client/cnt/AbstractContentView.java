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
package org.ourproject.kune.workspace.client.cnt;

import com.google.gwt.user.client.ui.Widget;

public interface AbstractContentView {

    public void setRawContent(final String content);

    void attach();

    void detach();

    void setContent(String content, boolean showPreviewMsg);

    void setInfo(String info);

    void setInfoMessage(String text);

    void setNoPreview();

    void setWidgetAsContent(final Widget widget, boolean setDefMargins);

    void showImage(String imageUrl, String imageResizedUrl, boolean showPreviewMsg);
}
