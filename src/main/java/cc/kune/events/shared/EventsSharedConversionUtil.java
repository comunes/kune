package cc.kune.events.shared;

import java.util.HashMap;
import java.util.Map;

import cc.kune.common.shared.res.ICalConstants;

import com.bradrydzewski.gwt.calendar.client.Appointment;

/**
 * The Class EventsSharedConversionUtil is used to convert Appointments to
 * Gadgets properties and viceversa
 */
public abstract class EventsSharedConversionUtil {

  public static Appointment toApp(final Map<String, String> properties) throws Exception {
    final Appointment app = new Appointment();
    app.setDescription(properties.get(ICalConstants.DESCRIPTION));
    app.setTitle(properties.get(ICalConstants.SUMMARY));
    app.setLocation(properties.get(ICalConstants.LOCATION));
    app.setCreatedBy(properties.get(ICalConstants.ORGANIZER));
    final String allDay = properties.get(ICalConstants._ALL_DAY);
    app.setId(properties.get(ICalConstants.UID));
    if (allDay != null) {
      app.setAllDay(Boolean.parseBoolean(allDay));
    }
    return app;
  }

  public static Map<String, String> toMap(final Appointment app) {
    final Map<String, String> properties = new HashMap<String, String>();
    properties.put(ICalConstants.SUMMARY, app.getTitle());
    properties.put(ICalConstants.DESCRIPTION, app.getDescription());
    properties.put(ICalConstants.LOCATION, app.getLocation());
    properties.put(ICalConstants.ORGANIZER, app.getCreatedBy());
    properties.put(ICalConstants._ALL_DAY, Boolean.toString(app.isAllDay()));
    properties.put(ICalConstants.UID, app.getId());
    return properties;
  }

}
