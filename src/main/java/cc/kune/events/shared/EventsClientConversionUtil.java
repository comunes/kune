package cc.kune.events.shared;

import java.util.Map;

import cc.kune.common.shared.res.ICalConstants;

import com.bradrydzewski.gwt.calendar.client.Appointment;

/**
 * The Class EventsConversionUtil is used to convert Appointments to Gadgets
 * properties and viceversa
 */
public class EventsClientConversionUtil extends EventsSharedConversionUtil {

  public static Appointment toApp(final Map<String, String> properties) {
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

}
