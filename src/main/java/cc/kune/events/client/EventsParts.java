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
package cc.kune.events.client;

import cc.kune.core.shared.SessionConstants;
import cc.kune.events.client.actions.EventsClientActions;
import cc.kune.events.client.viewer.CalendarViewer;
import cc.kune.events.shared.EventsToolConstants;
import cc.kune.gspace.client.tool.ContentViewerSelector;
import cc.kune.gspace.client.viewers.ContentViewerPresenter;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class EventsParts {

  @Inject
  public EventsParts(final SessionConstants session, final Provider<EventsClientTool> clientTool,
      final ContentViewerSelector viewerSelector, final EventsClientActions meetsActions,
      final ContentViewerPresenter contentViewer, final CalendarViewer calendarViewer) {
    clientTool.get();
    // remove this...
    viewerSelector.register(contentViewer, true, EventsToolConstants.TYPE_MEETING);
    viewerSelector.register(calendarViewer, true, EventsToolConstants.TYPE_ROOT);
  }
}