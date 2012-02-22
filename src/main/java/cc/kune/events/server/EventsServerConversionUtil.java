package cc.kune.events.server;

import java.text.ParseException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import cc.kune.common.shared.res.ICalConstants;
import cc.kune.events.shared.EventsSharedConversionUtil;

import com.bradrydzewski.gwt.calendar.client.Appointment;

/**
 * The Class EventsConversionUtil is used to convert Appointments to Gadgets
 * properties and viceversa
 */
public class EventsServerConversionUtil extends EventsSharedConversionUtil {
  private static final Logger LOG = Logger.getLogger(EventsServerConversionUtil.class.getName());

  public static Appointment toApp(final Map<String, String> properties) {
    final Appointment app = EventsSharedConversionUtil.toApp(properties);
    try {
      final String start = properties.get(ICalConstants.DATE_TIME_START);
      if (start != null) {
        app.setStart(DateServerUtils.toDate(start));
      }
      final String end = properties.get(ICalConstants.DATE_TIME_END);
      if (end != null) {
        app.setEnd(DateServerUtils.toDate(end));
      }
    } catch (final ParseException e) {
      LOG.log(Level.SEVERE, "Error parsing event", e);
    }
    return app;
  }

  public static Map<String, String> toMap(final Appointment app) {
    final Map<String, String> properties = EventsSharedConversionUtil.toMap(app);
    properties.put(ICalConstants.DATE_TIME_START, DateServerUtils.toString(app.getStart()));
    properties.put(ICalConstants.DATE_TIME_END, DateServerUtils.toString(app.getEnd()));
    return properties;
  }

}
