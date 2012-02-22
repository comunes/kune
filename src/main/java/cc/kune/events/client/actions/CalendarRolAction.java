package cc.kune.events.client.actions;

import cc.kune.core.client.actions.RolAction;
import cc.kune.core.client.actions.RolComparator;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.domain.utils.AccessRights;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.events.client.viewer.CalendarStateChangeEvent;
import cc.kune.events.client.viewer.CalendarViewer;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Provider;

public abstract class CalendarRolAction extends RolAction {

  public CalendarRolAction(final EventBus eventBus, final Session session,
      final Provider<CalendarViewer> calendar, final AccessRolDTO rolRequired, final boolean authNeed,
      final boolean onlyOnApp) {
    super(rolRequired, authNeed);
    eventBus.addHandler(CalendarStateChangeEvent.getType(),
        new CalendarStateChangeEvent.CalendarStateChangeHandler() {
          @Override
          public void onCalendarStateChange(final CalendarStateChangeEvent event) {
            // if the calendar is not selecting a appointment don't show this
            final AccessRights rights = session.getContainerState().getContainerRights();
            final boolean isEnabled = RolComparator.isEnabled(rolRequired, rights);
            final boolean isMember = RolComparator.isMember(rights);
            final boolean isOnApp = !calendar.get().getAppToEdit().equals(CalendarViewer.NO_APPOINT);
            final boolean newEnabled = isMember && isEnabled && (!onlyOnApp || isOnApp);
            setEnabled(!newEnabled);
            setEnabled(newEnabled);
          }
        });
  }

}
