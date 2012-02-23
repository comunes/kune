package cc.kune.common.client.utils;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;

/**
 * The Class DateUtils try to follow ISO_8601 (previously RFC 2445 date-time
 * formats).
 */
public class DateUtils {

  private static DateTimeFormat iso_8601 = DateTimeFormat.getFormat(PredefinedFormat.ISO_8601);
  // Sample DTSTART:19980118T230000
  private static DateTimeFormat rfcDateFormat = DateTimeFormat.getFormat("'DTSTART':yyyyMMdd'T'Hmmss");

  public static Date toDate(final String date) {
    try {
      return iso_8601.parse(date);
    } catch (final IllegalArgumentException e) {
      try {
        return rfcDateFormat.parse(date);
      } catch (final IllegalArgumentException e1) {
        // try old (buggy) hour formats
        // DTSTART:20120229T120000
        try {
          return DateTimeFormat.getFormat("'DTSTART':yyyyMMdd'T'hhmmss").parse(date);
        } catch (final IllegalArgumentException e2) {
          // DTSTART:20120225T1300
          return DateTimeFormat.getFormat("'DTSTART':yyyyMMdd'T'hhmm").parse(date);
        }
      }
    }
  }

  public static String toString(final Date date) {
    return iso_8601.format(date);
  }
}
