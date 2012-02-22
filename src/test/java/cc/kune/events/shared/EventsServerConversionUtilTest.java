package cc.kune.events.shared;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

import com.bradrydzewski.gwt.calendar.client.Appointment;
import com.google.gwt.editor.client.Editor.Ignore;

public class EventsServerConversionUtilTest extends BasicCalendarTest {

  @Test
  @Ignore
  public void testToMap() {
    final Appointment app = createAppointment(false);
    final Map<String, String> map = EventsClientConversionUtil.toMap(app);
    final Appointment appReConverted = EventsClientConversionUtil.toApp(map);
    assertEquals(app.getStart(), appReConverted.getStart());
    assertEquals(app.getEnd(), appReConverted.getEnd());
    assertEquals(app.getDescription(), appReConverted.getDescription());
    assertEquals(app.getTitle(), appReConverted.getTitle());
    assertEquals(app.getLocation(), appReConverted.getLocation());
    assertEquals(app.getCreatedBy(), appReConverted.getCreatedBy());
  }

}
