package cc.kune.events.server;

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

import com.bradrydzewski.gwt.calendar.client.Appointment;

public class CalendarServerUtils {

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
