package cc.kune.events.server;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The Class DateUtils try to follow RFC 2445 date-time formats.
 * 
 * DTSTART;TZID=US-Eastern:19970714T133000 ;Local time and time ; zone reference
 */
public class DateServerUtils {

  private static String RFC_DATE_TIME_FORMAT = "'DTSTART':yyyyMMdd'T'hhmmss";
  // This is not tested: better use some class conversion util...
  private static String RFC_DATE_TIME_FORMAT_TZ = "'DTSTART;TZID='z:yyyyMMdd'T'hhmmss";
  private static SimpleDateFormat rfcDateFormat = new SimpleDateFormat(RFC_DATE_TIME_FORMAT);

  public static Date toDate(final String date) throws ParseException {
    return rfcDateFormat.parse(date);
  }

  public static String toString(final Date date) {
    return rfcDateFormat.format(date);
  }
}
