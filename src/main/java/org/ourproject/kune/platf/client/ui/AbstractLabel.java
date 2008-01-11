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

package org.ourproject.kune.platf.client.ui;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.MouseListener;

public interface AbstractLabel {

    public void addClickListener(final ClickListener listener);

    public void addMouseListener(final MouseListener listener);

    public String getText();

    public void onBrowserEvent(final Event event);

    public void removeClickListener(final ClickListener listener);

    public void removeMouseListener(final MouseListener listener);

    public void setText(final String text);

    public void setColor(final String color);

    public void setTitle(final String title);

    public void addStyleDependentName(String string);

    public void removeStyleDependentName(String string);

    public void setStylePrimaryName(String string);

    public void addDoubleClickListener(ClickListener listener);

    public void removeDoubleClickListener(ClickListener listener);

}