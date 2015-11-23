package org.ourproject.massmob.client.ui;

import java.util.Date;

import com.google.inject.Singleton;

@Singleton
public class DateManager {

  public Date onDateEndSet(final Date newEnd, final Date start, final Date end,
      final boolean keepDuration) {
    if (keepDuration) {
      final Long diff = newEnd.getTime() - end.getTime();
      return new Date(start.getTime() + diff);
    } else {
      if (newEnd.before(start)) {
        return new Date(newEnd.getTime());
      } else {
        return start;
      }
    }
  }

  public Date onDateStartSet(final Date newStart, final Date start, final Date end,
      final boolean keepDuration) {
    if (keepDuration) {
      final Long diff = newStart.getTime() - start.getTime();
      return new Date(end.getTime() + diff);
    } else {
      if (newStart.after(end)) {
        return new Date(newStart.getTime());
      } else {
        return end;
      }
    }
  }

}
