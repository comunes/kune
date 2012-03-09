package cc.kune.common.client.utils;

import java.util.Date;

import cc.kune.common.shared.utils.DateFormatConstants;

import com.google.gwt.i18n.client.DateTimeFormat;

/**
 * The Class DateUtils try to follow ISO_8601 (previously RFC 2445 date-time
 * formats).
 */
public class DateUtils extends DateFormatConstants {

  private static final DateTimeFormat iso_8601 = DateTimeFormat.getFormat(DATE_EXPORT_FORMAT);

  public static Date toDate(final String date) {
    try {
      return iso_8601.parse(date);
    } catch (final IllegalArgumentException e) {
      try {
        // try old (buggy) hour formats
        // Sample DTSTART:19980118T230000
        return DateTimeFormat.getFormat(OLD1_DATE_EXPORT_FORMAT).parse(date);
      } catch (final IllegalArgumentException e1) {
        // DTSTART:20120229T120000
        try {
          return DateTimeFormat.getFormat(OLD2_DATE_EXPORT_FORMAT).parse(date);
        } catch (final IllegalArgumentException e2) {
          // DTSTART:20120225T1300
          return DateTimeFormat.getFormat(OLD3_DATE_EXPORT_FORMAT).parse(date);
        }
      }
    }
  }

  public static String toString(final Date date) {
    return iso_8601.format(date);
  }
}
