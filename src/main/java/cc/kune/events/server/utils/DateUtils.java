package cc.kune.events.server.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cc.kune.common.shared.utils.DateFormatConstants;

public class DateUtils {

  private static final SimpleDateFormat FORMATTER = new SimpleDateFormat(
      DateFormatConstants.DATE_EXPORT_FORMAT);
  public static final String TIMEZONE_REGEXP = "(.*)\\:([0-9][0-9])$";

  public static Date toDate(final String date) throws ParseException {
    try {
      return FORMATTER.parse(date);
    } catch (final ParseException e) {
      return FORMATTER.parse(date.replaceFirst(TIMEZONE_REGEXP, "$1$2"));
    }
  }

  public static String toString(final Date date) {
    return FORMATTER.format(date);
  }

}
