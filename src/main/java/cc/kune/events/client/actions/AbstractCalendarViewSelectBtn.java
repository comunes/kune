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
package cc.kune.events.client.actions;

import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.gspace.client.viewers.ToolbarStyles;

import com.bradrydzewski.gwt.calendar.client.CalendarViews;

public class AbstractCalendarViewSelectBtn extends ButtonDescriptor {

  public AbstractCalendarViewSelectBtn(final CalendarViewSelectAction action, final String text,
      final int days, final CalendarViews view) {
    super(text, action);
    action.setDays(days);
    action.setView(view);
    this.withStyles(ToolbarStyles.CSS_BTN_CENTER);
  }

}
