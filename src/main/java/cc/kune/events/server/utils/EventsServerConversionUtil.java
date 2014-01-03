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
package cc.kune.events.server.utils;

import java.net.URISyntaxException;
import java.text.ParseException;
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
import cc.kune.wave.server.kspecific.KuneWaveService;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.wave.api.Gadget;

// TODO: Auto-generated Javadoc
/**
 * The Class EventsConversionUtil is used to convert Appointments to Gadgets
 * properties and viceversa.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class EventsServerConversionUtil {

  /** The events cache. */
  @Inject
  static EventsCache eventsCache;

  /** The event tool. */
  @Inject
  private static EventsServerTool eventTool;

  /** The kune wave service. */
  @Inject
  private static KuneWaveService kuneWaveService;

  /** The Constant LOG. */
  private static final Logger LOG = Logger.getLogger(EventsServerConversionUtil.class.getName());

  /**
   * Gets the appointments.
   * 
   * @param container
   *          the container
   * @return the appointments
   */
  public static List<Map<String, String>> getAppointments(final Container container) {
    final List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    assert eventTool != null;
    for (final Content content : container.getContents()) {
      final String waveId = content.getWaveId();
      try {
        final String shortName = content.getAuthors().get(0).getShortName();
        Preconditions.checkNotNull(shortName, "Event author empty!");
        final Gadget gadget = kuneWaveService.getGadget(
            JavaWaverefEncoder.decodeWaveRefFromPath(waveId), shortName, eventTool.getGadgetUrl());
        Preconditions.checkNotNull(gadget, "Gadget not found");
        final Map<String, String> gadgetProps = gadget.getProperties();
        Preconditions.checkNotNull(gadgetProps, "GadgetProps empty");
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

  /**
   * Gets the appointments using cache.
   * 
   * @param container
   *          the container
   * @return the appointments using cache
   */
  public static List<Map<String, String>> getAppointmentsUsingCache(final Container container) {
    final List<Map<String, String>> cached = eventsCache.get(container);
    if (cached != null) {
      return cached;
    }
    return getAppointments(container);
  }

  /**
   * To v event.
   * 
   * @param properties
   *          the properties
   * @return the v event
   * @throws URISyntaxException
   *           the uRI syntax exception
   * @throws ParseException
   *           the parse exception
   */
  public static VEvent toVEvent(final Map<String, String> properties) throws URISyntaxException,
      ParseException {

    // http://build.mnode.org/projects/ical4j/apidocs/

    // final TimeZoneRegistry registry =
    // TimeZoneRegistryFactory.getInstance().createRegistry();
    // final TimeZone timezone = registry.getTimeZone("GMT");
    // final VTimeZone tz = timezone.getVTimeZone();
    // FIXME here v timezone!!!

    final String startS = properties.get(ICalConstants.DATE_TIME_START);
    final DateTime start = new DateTime(DateUtils.toDate(startS));
    final TimeZone timezone = start.getTimeZone();
    // start.setTimeZone(timezone);
    final String endS = properties.get(ICalConstants.DATE_TIME_END);
    // end.setTimeZone(timezone);
    VEvent event;
    final String allDay = properties.get(ICalConstants._ALL_DAY);
    if (allDay != null && Boolean.parseBoolean(allDay)) {
      event = new VEvent();
      event.getProperties().add(new Summary(properties.get(ICalConstants.SUMMARY)));
      final DtStart eventStart = new DtStart(new Date(DateUtils.toDate(startS).getTime()));
      eventStart.setTimeZone(timezone);
      event.getProperties().add(eventStart);
      final DtEnd eventEnd = new DtEnd(new Date(DateUtils.toDate(endS).getTime()));
      eventEnd.setTimeZone(timezone);
      event.getProperties().add(eventEnd);
      event.getProperties().getProperty(Property.DTSTART).getParameters().add(Value.DATE);
      event.getProperties().getProperty(Property.DTEND).getParameters().add(Value.DATE);
    } else {
      event = new VEvent(start, new DateTime(DateUtils.toDate(endS)),
          properties.get(ICalConstants.SUMMARY));
      event.getProperties().getProperty(Property.DTSTART).getParameters().add(Value.DATE_TIME);
      event.getProperties().getProperty(Property.DTEND).getParameters().add(Value.DATE_TIME);
    }
    event.getProperties().add(new Description(properties.get(ICalConstants.DESCRIPTION)));
    event.getProperties().add(new Location(properties.get(ICalConstants.LOCATION)));
    event.getProperties().add(new Uid(properties.get(ICalConstants.UID)));
    // FIXME This give NPE Uat java.net.URI$Parser.parse(URI.java:3003)). Check
    // doc:
    // event.getProperties().add(new
    // Organizer(properties.get(ICalConstants.ORGANIZER)));

    return event;
  }
}
