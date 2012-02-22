package cc.kune.events.server;

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

import org.waveprotocol.wave.model.waveref.InvalidWaveRefException;
import org.waveprotocol.wave.util.escapers.jvm.JavaWaverefEncoder;

import cc.kune.common.shared.res.ICalConstants;
import cc.kune.domain.Container;
import cc.kune.domain.Content;
import cc.kune.wave.server.kspecific.KuneWaveService;

import com.bradrydzewski.gwt.calendar.client.Appointment;
import com.google.inject.Inject;
import com.google.wave.api.Gadget;

public class CalendarServerUtils {

  @Inject
  private static EventsServerTool eventTool;

  @Inject
  private static KuneWaveService kuneWaveService;
  private static final Logger LOG = Logger.getLogger(CalendarServerUtils.class.getName());

  public static VEvent from(final Appointment app) {
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
      final DtStart eventStart = new DtStart(new Date(app.getStart().getTime()));
      eventStart.setTimeZone(timezone);
      event.getProperties().add(eventStart);
      final DtEnd eventEnd = new DtEnd(new Date(app.getEnd().getTime()));
      eventStart.setTimeZone(timezone);
      event.getProperties().add(eventEnd);
      event.getProperties().getProperty(Property.DTSTART).getParameters().add(Value.DATE);
      event.getProperties().getProperty(Property.DTEND).getParameters().add(Value.DATE);
    } else {
      event = new VEvent(start, end, app.getTitle());
      event.getProperties().getProperty(Property.DTSTART).getParameters().add(Value.DATE_TIME);
      event.getProperties().getProperty(Property.DTEND).getParameters().add(Value.DATE_TIME);
    }
    event.getProperties().add(new Summary(app.getTitle()));
    event.getProperties().add(new Description(app.getDescription()));
    event.getProperties().add(new Location(app.getLocation()));
    // FIXME uid
    // event.getProperties().add( new UidGenerator()app.getId()));
    return event;
  }

  public static List<Map<String, String>> getAppointments(final Container container) {
    final List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    for (final Content content : container.getContents()) {
      final String waveId = content.getWaveId();
      try {
        final Gadget gadget = kuneWaveService.getGadget(
            JavaWaverefEncoder.decodeWaveRefFromPath(waveId),
            content.getAuthors().get(0).getShortName(), eventTool.getGadgetUrl());
        final Map<String, String> gadgetProps = gadget.getProperties();
        final HashMap<String, String> map = new HashMap<String, String>();
        for (final String var : ICalConstants.TOTAL_LIST) {
          final String value = gadgetProps.get(var);
          if (value != null) {
            map.put(var, value);
          }
        }
        map.put(ICalConstants._INTERNAL_ID, content.getStateToken().toString());
        list.add(map);
      } catch (final InvalidWaveRefException e) {
        LOG.log(Level.SEVERE, "Invalid Waveref", e);
      }
    }
    return list;
  }

  public static Appointment to(final VEvent event) {
    final Appointment app = new Appointment();

    // FIXME: see spec
    app.setDescription(event.getDescription().getValue());
    app.setStart(new java.util.Date(event.getStartDate().getDate().getTime()));
    app.setEnd(new java.util.Date(event.getEndDate().getDate().getTime()));

    // FIXME: see spec
    app.setLocation(event.getLocation().getValue());
    // final Uid uid = event.getUid();
    // if (uid != null) {
    // app.setId(uid.getValue());
    // }
    app.setAllDay(!(event.getStartDate().getDate() instanceof DateTime));
    app.setTitle(event.getSummary().getValue());
    return app;
  }

}
