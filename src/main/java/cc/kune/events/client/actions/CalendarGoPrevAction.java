package cc.kune.events.client.actions;

import cc.kune.common.client.actions.ActionEvent;
import cc.kune.core.client.actions.RolAction;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.events.client.viewer.CalendarViewer;

import com.google.inject.Inject;

public class CalendarGoPrevAction extends RolAction {

  private final CalendarViewer calViewer;

  @Inject
  public CalendarGoPrevAction(final CalendarViewer calViewer) {
    super(AccessRolDTO.Viewer, false);
    this.calViewer = calViewer;
  }

  @Override
  public void actionPerformed(final ActionEvent event) {
    calViewer.decrement();
  }
}