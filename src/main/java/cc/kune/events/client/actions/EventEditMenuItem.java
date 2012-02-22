package cc.kune.events.client.actions;

import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.resources.nav.NavResources;
import cc.kune.core.client.state.AccessRightsClientManager;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.events.client.viewer.CalendarViewer;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class EventEditMenuItem extends MenuItemDescriptor {
  public static class EventEditAction extends CalendarRolAction {
    private final Provider<CalendarViewer> calendar;
    private final StateManager stateManager;

    @Inject
    public EventEditAction(final NavResources res, final I18nTranslationService i18n,
        final Provider<CalendarViewer> calendar, final EventBus eventBus,
        final StateManager stateManager, final Session session, final AccessRightsClientManager rightsMan) {
      super(eventBus, session, calendar, AccessRolDTO.Editor, true, true);
      this.calendar = calendar;
      this.stateManager = stateManager;
      withText(i18n.t("Edit the appointment")).withIcon(res.go());
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
      stateManager.gotoHistoryToken(calendar.get().getAppToEdit().getId());
    }
  }

  @Inject
  public EventEditMenuItem(final EventEditAction action, final CalendarOnOverMenu cal) {
    super(action);
    setParent(cal.get());
  }
}
