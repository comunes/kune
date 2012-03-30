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
package cc.kune.events.server.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.TimeZone;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.parameter.Value;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.DtEnd;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.Summary;
import net.fortuna.ical4j.model.property.Uid;

import org.waveprotocol.wave.model.waveref.InvalidWaveRefException;
import org.waveprotocol.wave.util.escapers.jvm.JavaWaverefEncoder;

import cc.kune.common.shared.res.ICalConstants;
import cc.kune.domain.Container;
import cc.kune.domain.Content;
import cc.kune.events.server.EventsServerTool;
import cc.kune.events.shared.EventsSharedConversionUtil;
import cc.kune.wave.server.kspecific.KuneWaveService;

import com.bradrydzewski.gwt.calendar.client.Appointment;
import com.google.inject.Inject;
import com.google.wave.api.Gadget;

/**
 * The Class EventsConversionUtil is used to convert Appointments to Gadgets
 * properties and viceversa
 */
public class EventsServerConversionUtil extends EventsSharedConversionUtil {

  @Inject
  static EventsCache eventsCache;

  @Inject
  private static EventsServerTool eventTool;

  @Inject
  private static KuneWaveService kuneWaveService;

  private static final Logger LOG = Logger.getLogger(EventsServerConversionUtil.class.getName());

  public static List<Map<String, String>> getAppointments(final Container container) {
    final List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    assert eventTool != null;
    for (final Content content : container.getContents()) {
      final String waveId = content.getWaveId();
      try {
        final String shortName = content.getAuthors().get(0).getShortName();
        final Gadget gadget = kuneWaveService.getGadget(
            JavaWaverefEncoder.decodeWaveRefFromPath(waveId), shortName, eventTool.getGadgetUrl());
        final Map<String, String> gadgetProps = gadget.getProperties();
        final HashMap<String, String> map = new HashMap<String, String>();
        for (final String var : ICalConstants.ZTOTAL_LIST) {
          final String value = gadgetProps.get(var);
          if (value != null) {
            map.put(var, value);
          }
        }
        map.put(ICalConstants.UID, content.getStateToken().toString());
        map.put(ICalConstants._INTERNAL_ID, content.getStateToken().toString());
        list.add(map);
      } catch (final InvalidWaveRefException e) {
        LOG.log(Level.SEVERE, "Invalid Waveref: " + waveId, e);
      } catch (final Exception e2) {
        LOG.log(Level.SEVERE, "Invalid Wave in content: " + content.getStateToken() + " waveref: "
            + waveId, e2);
      }
    }
    eventsCache.put(container, list);
    return list;
  }

  public static List<Map<String, String>> getAppointmentsUsingCache(final Container container) {
    final List<Map<String, String>> cached = eventsCache.get(container);
    if (cached != null) {
      return cached;
    }
    return getAppointments(container);
  }

  public static Appointment to(final VEvent event) {
    // http://build.mnode.org/projects/ical4j/apidocs/
    final Appointment app = new Appointment();
    // FIXME: see spec
    app.setDescription(event.getDescription().getValue());
    app.setStart(new java.util.Date(event.getStartDate().getDate().getTime()));
    app.setEnd(new java.util.Date(event.getEndDate().getDate().getTime()));

    // FIXME: see spec
    app.setLocation(event.getLocation().getValue());
    final Uid uid = event.getUid();
    if (uid != null) {
      app.setId(uid.getValue());
    }
    app.setAllDay(!(event.getStartDate().getDate() instanceof DateTime));
    app.setTitle(event.getSummary().getValue());
    return app;
  }

  public static Appointment toApp(final Map<String, String> properties) throws Exception {
    final Appointment app = EventsSharedConversionUtil.toApp(properties);
    final String start = properties.get(ICalConstants.DATE_TIME_START);
    if (start != null) {
      app.setStart(DateUtils.toDate(start));
    }
    final String end = properties.get(ICalConstants.DATE_TIME_END);
    if (end != null) {
      app.setEnd(DateUtils.toDate(end));
    }
    return app;
  }

  public static Map<String, String> toMap(final Appointment app) {
    final Map<String, String> properties = EventsSharedConversionUtil.toMap(app);
    properties.put(ICalConstants.DATE_TIME_START, DateUtils.toString(app.getStart()));
    properties.put(ICalConstants.DATE_TIME_END, DateUtils.toString(app.getEnd()));
    return properties;
  }

  public static VEvent toVEvent(final Appointment app) {
    // http://build.mnode.org/projects/ical4j/apidocs/

    // final TimeZoneRegistry registry =
    // TimeZoneRegistryFactory.getInstance().createRegistry();
    // final TimeZone timezone = registry.getTimeZone("GMT");
    // final VTimeZone tz = timezone.getVTimeZone();
    // FIXME here v timezone!!!

    final DateTime start = new DateTime(app.getStart().getTime());
    final TimeZone timezone = start.getTimeZone();
    // start.setTimeZone(timezone);
    final DateTime end = new DateTime(app.getEnd().getTime());
    // end.setTimeZone(timezone);
    VEvent event;
    if (app.isAllDay()) {
      event = new VEvent();
      event.getProperties().add(new Summary(app.getTitle()));
      final DtStart eventStart = new DtStart(new Date(app.getStart().getTime()));
      eventStart.setTimeZone(timezone);
      event.getProperties().add(eventStart);
      final DtEnd eventEnd = new DtEnd(new Date(app.getEnd().getTime()));
      eventEnd.setTimeZone(timezone);
      event.getProperties().add(eventEnd);
      // event.getProperties().getProperty(Property.DTSTART).getParameters().add(Value.DATE);
      // event.getProperties().getProperty(Property.DTEND).getParameters().add(Value.DATE);
    } else {
      event = new VEvent(start, end, app.getTitle());
      event.getProperties().getProperty(Property.DTSTART).getParameters().add(Value.DATE_TIME);
      event.getProperties().getProperty(Property.DTEND).getParameters().add(Value.DATE_TIME);
    }
    event.getProperties().add(new Description(app.getDescription()));
    event.getProperties().add(new Location(app.getLocation()));
    event.getProperties().add(new Uid(app.getId()));
    return event;
  }

}
