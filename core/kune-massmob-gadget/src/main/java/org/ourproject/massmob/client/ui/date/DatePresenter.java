package org.ourproject.massmob.client.ui.date;

import java.util.Date;

import org.ourproject.massmob.client.CustomConstants;
import org.ourproject.massmob.client.ui.DateManager;

import com.google.gwt.event.dom.client.HasMouseWheelHandlers;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.thezukunft.wave.connector.State;
import com.thezukunft.wave.connector.Wave;

import cc.kune.common.client.utils.DateUtils;
import cc.kune.common.shared.res.ICalConstants;

public class DatePresenter {

  public interface DateView extends IsWidget {

    Date getDate();

    HasMouseWheelHandlers getWheel();

    HasValue<Date> hasValue();

    void setAllTime(boolean allDay);

    void setDate(Date date);

    void setEnabled(boolean editable);

  }

  private boolean allDay = false;

  private final DateManager dateManager;
  private String stateKey;
  private final DateView view;
  private final Wave wave;

  @Inject
  public DatePresenter(final DateView view, final EventBus eventBus, final Wave wave,
      final DateManager dateManager) {
    this.wave = wave;
    this.dateManager = dateManager;
    this.view = view;
    onBind();
  }

  private Date getDate(final State state, final String key) {
    final String dateS = state.get(key);
    return dateS == null ? new Date() : DateUtils.toDate(dateS);
  }

  public IsWidget getView() {
    return view;
  }

  public void init(final String stateKey) {
    this.stateKey = stateKey;
  }

  protected void onBind() {
    final Timer timer = new Timer() {
      @Override
      public void run() {
        submitDate(wave, view.getDate());
      }
    };
    view.getWheel().addMouseWheelHandler(new MouseWheelHandler() {
      @Override
      public void onMouseWheel(final MouseWheelEvent event) {
        if (event.getNativeEvent().getShiftKey()) {
          event.preventDefault();
          final int delta = event.getDeltaY();
          final int relX = event.getRelativeX(((Widget) view).getElement());
          // Log.info("Delta: " + delta + " relative x: " + relX);
          final Date date = view.getDate();
          if (date != null) {
            long increment = 0;
            if (relX <= 25) {
              // month
              increment = (long) (2.62974383 * Math.pow(10, 9));
            } else if (relX <= 50) {
              // day
              increment = 86400000;
            } else if (relX <= 80) {
              // year
              increment = (long) (3.1556926 * Math.pow(10, 10));
            } else if (relX <= 100) {
              // hour
              increment = allDay ? 0 : 3600000;
            } else if (relX > 100) {
              // 15 minutes
              increment = allDay ? 0 : 900000;
            }
            final long newDateL = date.getTime() + ((delta >= 0 ? 1 : -1) * increment);
            final Date newDate = new Date(newDateL);
            view.setDate(newDate);
            // Set date after a delay
            timer.cancel();
            timer.schedule(500);
          }
          event.stopPropagation();
        }
      }
    });
    view.hasValue().addValueChangeHandler(new ValueChangeHandler<Date>() {
      @Override
      public void onValueChange(final ValueChangeEvent<Date> event) {
        final Date value = event.getValue();
        submitDate(wave, value);
      }
    });
  }

  public void setEnabled(final boolean enabled) {
    view.setEnabled(enabled);
  }

  private void submitDate(final Wave wave, final Date dateChanged) {
    final State state = wave.getState();
    final boolean keepDistance = Boolean.valueOf(wave.getState().get(CustomConstants.KEEP_DURATION));
    final Date start = getDate(state, ICalConstants.DATE_TIME_START);
    final Date end = getDate(state, ICalConstants.DATE_TIME_END);
    Date newStart = start;
    Date newEnd = end;
    if (stateKey.equals(ICalConstants.DATE_TIME_START)) {
      newEnd = dateManager.onDateStartSet(newStart = dateChanged, start, end, keepDistance);
    } else {
      newStart = dateManager.onDateEndSet(newEnd = dateChanged, start, end, keepDistance);
    }
    if (!start.equals(newStart)) {
      state.submitValue(ICalConstants.DATE_TIME_START, DateUtils.toString(newStart));
    }
    if (!end.equals(newEnd)) {
      state.submitValue(ICalConstants.DATE_TIME_END, DateUtils.toString(newEnd));
    }
  }

  public void updateView(final State state) {
    final String date = state.get(stateKey);
    allDay = Boolean.parseBoolean(state.get(ICalConstants._ALL_DAY));
    if (date != null) {
      try {
        view.setDate(DateUtils.toDate(date));
        view.setAllTime(allDay);
      } catch (final IllegalArgumentException e) {
        // Hack: Wrong date, lets try to reset
        // submitDate(wave, new Date());
        state.submitValue(stateKey, DateUtils.toString(new Date()));
      }
    } else {
      state.submitValue(stateKey, DateUtils.toString(new Date()));
      // submitDate(wave, new Date());
    }
  }

}
