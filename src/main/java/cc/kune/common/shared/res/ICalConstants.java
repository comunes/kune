package cc.kune.common.shared.res;

public class ICalConstants {

  /* Note that all day events is not supported by ICalendar */
  public static final String _ALL_DAY = "ALLDAY";
  public static final String _INTERNAL_ID = "INTERNALID";
  // VTIMEZONE ?? See: TimeZoneConstants in GWT for names and values
  public static final String DATE_TIME_END = "DTEND";
  public static final String DATE_TIME_START = "DTSTART";
  public static final String DESCRIPTION = "DESCRIPTION";
  public static final String LOCATION = "LOCATION";
  public static final String ORGANIZER = "ORGANIZER";
  public static final String SUMMARY = "SUMMARY";
  public static final String UID = "UID";
  public static final String[] ZTOTAL_LIST = new String[] { DATE_TIME_START, DATE_TIME_END, DESCRIPTION,
      LOCATION, ORGANIZER, SUMMARY, _ALL_DAY, _INTERNAL_ID, UID };
}