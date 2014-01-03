/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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

// TODO: Auto-generated Javadoc
/**
 * The Class EventsParts.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class EventsParts {

  /**
   * Instantiates a new events parts.
   * 
   * @param session
   *          the session
   * @param clientTool
   *          the client tool
   * @param viewerSelector
   *          the viewer selector
   * @param meetsActions
   *          the meets actions
   * @param contentViewer
   *          the content viewer
   * @param calendarViewer
   *          the calendar viewer
   */
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
