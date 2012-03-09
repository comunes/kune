package cc.kune.events.server.utils;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;

import org.junit.Test;

public class DateUtilsTest {

  private static final String SAMPLE = "2012-03-05T00:00:00.000+0100";
  private static final String SAMPLE2 = "2012-03-05T00:00:00.000+01:00";

  @Test
  public void basicTest() throws ParseException {

    DateUtils.toDate(SAMPLE);
    DateUtils.toDate(SAMPLE2);

    // Difference from GWT timezone and SimpleDate
    assertEquals("foo+0100", "foo+01:00".replaceFirst(DateUtils.TIMEZONE_REGEXP, "$1$2"));
  }
}
