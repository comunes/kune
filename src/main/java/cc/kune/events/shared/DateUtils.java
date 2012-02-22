package cc.kune.events.shared;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;

/**
 * The Class DateUtils try to follow RFC 2445 date-time formats.
 * 
 * DTSTART;TZID=US-Eastern:19970714T133000 ;Local time and time ; zone reference
 */
public class DateUtils {

  private static String RFC_DATE_TIME_FORMAT = "'DTSTART':yyyyMMdd'T'hhmmss";
  // This is not tested
  private static String RFC_DATE_TIME_FORMAT_TZ = "'DTSTART;TZID='v:yyyyMMdd'T'hhmmss";
  private static DateTimeFormat rfcDateFormat = DateTimeFormat.getFormat(RFC_DATE_TIME_FORMAT);

  public static Date toDate(final String date) {
    return rfcDateFormat.parse(date);
  }

  public static String toString(final Date date) {
    return rfcDateFormat.format(date);
  }
}
