package cc.kune.events.client.actions;

import cc.kune.common.client.actions.ActionEvent;
import cc.kune.core.client.actions.RolAction;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.events.client.viewer.CalendarViewer;

import com.bradrydzewski.gwt.calendar.client.CalendarViews;
import com.google.inject.Inject;

public class CalendarViewSelectAction extends RolAction {

  private final CalendarViewer calViewer;
  private int days;
  private CalendarViews view;

  @Inject
  public CalendarViewSelectAction(final CalendarViewer calViewer) {
    super(AccessRolDTO.Viewer, false);
    this.calViewer = calViewer;
  }

  @Override
  public void actionPerformed(final ActionEvent event) {
    if (days != 0) {
      calViewer.setView(view, days);
    } else {
      calViewer.setView(view);
    }
  }

  public void setDays(final int days) {
    this.days = days;
  }

  public void setView(final CalendarViews view) {
    this.view = view;
  }
}