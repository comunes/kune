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
package cc.kune.events.server;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cc.kune.core.client.errors.ContentNotFoundException;
import cc.kune.core.server.auth.ActionLevel;
import cc.kune.core.server.auth.Authenticated;
import cc.kune.core.server.auth.Authorizated;
import cc.kune.core.server.content.ContainerManager;
import cc.kune.core.server.content.ContentUtils;
import cc.kune.core.server.manager.file.FileDownloadManagerUtils;
import cc.kune.core.server.manager.file.FileUtils;
import cc.kune.core.server.properties.KuneProperties;
import cc.kune.core.shared.FileConstants;
import cc.kune.core.shared.domain.AccessRol;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.domain.Container;
import cc.kune.events.server.utils.EventsServerConversionUtil;
import cc.kune.events.shared.EventsToolConstants;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class EventsServlet.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class EventsServlet extends HttpServlet {

  /** The Constant LOG. */
  private static final Log LOG = LogFactory.getLog(EventsServlet.class);

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -5559665146847701343L;

  /** The container manager. */
  @Inject
  ContainerManager containerManager;

  /** The file utils. */
  @Inject
  FileUtils fileUtils;

  /** The kune properties. */
  @Inject
  KuneProperties kuneProperties;

  /*
   * (non-Javadoc)
   * 
   * @see
   * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
   * javax.servlet.http.HttpServletResponse)
   */
  @SuppressWarnings("unchecked")
  @Override
  protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
      throws ServletException, IOException {

    final String userHash = req.getParameter(FileConstants.HASH);
    final StateToken token = new StateToken(req.getParameter(FileConstants.TOKEN));

    final Calendar calendar = new Calendar();
    calendar.getProperties().add(new ProdId("-//Ben Fortuna//iCal4j 1.0//EN"));
    calendar.getProperties().add(Version.VERSION_2_0);
    calendar.getProperties().add(CalScale.GREGORIAN);
    final List<VEvent> events = new ArrayList<VEvent>();

    try {
      final Container cnt = getContainer(userHash, token);
      if (cnt != null && cnt.getTypeId().equals(EventsToolConstants.TYPE_ROOT)) {
        final List<Map<String, String>> listOfProps = EventsServerConversionUtil.getAppointmentsUsingCache(cnt);
        for (final Map<String, String> props : listOfProps) {
          try {
            final VEvent vEvent = EventsServerConversionUtil.toVEvent(props);
            events.add(vEvent);
          } catch (final Exception e) {
            LOG.warn("Invalid appointments in " + token, e);
            for (final Entry<String, String> val : props.entrySet()) {
              LOG.warn(String.format("Prop: %s, value: %s", val.getKey(), val.getValue()));
            }
          }
        }
      } else {
        return404(resp);
        return;
      }
      calendar.getComponents().addAll(events);
      resp.setContentType("text/calendar; charset=UTF-8");
      final OutputStream out = resp.getOutputStream();
      final CalendarOutputter outputter = new CalendarOutputter();
      outputter.output(calendar, out);
    } catch (final ContentNotFoundException e) {
      return404(resp);
      return;
    } catch (final ValidationException e) {
      LOG.warn("Invalid calendar conversion in " + token, e);
    }

  }

  /**
   * Gets the container.
   * 
   * @param userHash
   *          the user hash
   * @param stateToken
   *          the state token
   * @return the container
   * @throws ContentNotFoundException
   *           the content not found exception
   */
  @Authenticated(mandatory = false)
  @Authorizated(accessRolRequired = AccessRol.Viewer, actionLevel = ActionLevel.container)
  private Container getContainer(final String userHash, final StateToken stateToken)
      throws ContentNotFoundException {
    return containerManager.find(ContentUtils.parseId(stateToken.getFolder()));
  }

  /**
   * Return404.
   * 
   * @param resp
   *          the resp
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  private void return404(final HttpServletResponse resp) throws IOException {
    FileDownloadManagerUtils.returnNotFound404(resp);
  }

}
