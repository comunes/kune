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
package org.ourproject.kune.platf.client.ui;

import com.google.gwt.user.client.ui.HasVerticalAlignment.VerticalAlignmentConstant;
import com.google.gwt.user.client.ui.Widget;

public interface AbstractToolbar {

    void add(final Widget widget);

    void add(final Widget widget, VerticalAlignmentConstant valign);

    Widget addFill();

    Widget addSeparator();

    Widget addSpacer();

    int getOffsetHeight();

    void insert(final Widget widget, int position);

    boolean isAttached();

    void remove(final Widget widget);

    void removeAll();

    void setBlankStyle();

    void setHeight(String height);

    void setNormalStyle();

}