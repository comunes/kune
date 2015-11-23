package org.ourproject.massmob.client.ui.date;

import java.util.Date;

import org.junit.Test;

import cc.kune.common.client.utils.DateUtils;

import com.google.gwt.junit.client.GWTTestCase;

public class DateUtilsTest extends GWTTestCase {
  private final String RFC2445_4_3_4_SAMPLE = "DTSTART:19980119T020000";
  @SuppressWarnings("unused")
  private final String RFC2445_4_3_4_SAMPLE_OTHER_TZ = "DTSTART;TZID=Etc/GMT-1:20100326T021224";
  @SuppressWarnings("unused")
  private final String RFC2445_4_3_4_SAMPLE_TZ = "DTSTART;TZID=US-Eastern:19980119T020000";

  @Override
  public String getModuleName() {
    return "org.ourproject.massmob.MassmobNotGadget";
  }

  @SuppressWarnings("deprecation")
  @Test
  public void testToDate() {
    final Date date = DateUtils.toDate(RFC2445_4_3_4_SAMPLE);
    assertEquals(RFC2445_4_3_4_SAMPLE, DateUtils.toString(date));
    // assertEquals(98, date.getYear());
    // assertEquals(2, date.getHours());
    // assertEquals(19, date.getDay());
    // assertEquals(1, date.getMonth());
    // assertEquals(0, date.getMinutes());
    // assertEquals(0, date.getSeconds());
  }

  @Test
  public void testToString() {
    final String date = DateUtils.toString(new Date());
    // Log.info(date);
    // assertTrue(date.startsWith("DTSTART;TZID="));
    assertTrue(date.contains("201112"));
  }
}
