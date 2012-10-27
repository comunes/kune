/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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

import cc.kune.common.client.actions.ActionEvent;
import cc.kune.core.client.actions.RolAction;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.events.client.viewer.CalendarViewer;

import com.bradrydzewski.gwt.calendar.client.CalendarViews;
import com.google.inject.Inject;

public class CalendarViewSelectAction extends RolAction {

  private final CalendarViewer calViewer;
  private int days;
  private CalendarViews view;

  @Inject
  public CalendarViewSelectAction(final CalendarViewer calViewer) {
    super(AccessRolDTO.Viewer, false);
    this.calViewer = calViewer;
  }

  @Override
  public void actionPerformed(final ActionEvent event) {
    if (days != 0) {
      calViewer.setView(view, days);
    } else {
      calViewer.setView(view);
    }
  }

  public void setDays(final int days) {
    this.days = days;
  }

  public void setView(final CalendarViews view) {
    this.view = view;
  }
}
