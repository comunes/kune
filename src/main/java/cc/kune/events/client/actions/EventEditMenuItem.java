package cc.kune.events.client.actions;

import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.ui.dialogs.PromptTopDialog;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.actions.RolAction;
import cc.kune.core.client.resources.nav.NavResources;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.events.client.viewer.CalendarStateChangeEvent;
import cc.kune.events.client.viewer.CalendarViewer;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class EventEditMenuItem extends MenuItemDescriptor {
  public static class EventEditAction extends RolAction {
    private final Provider<CalendarViewer> calendar;
    private PromptTopDialog dialog;
    private final I18nTranslationService i18n;
    private final StateManager stateManager;

    @Inject
    public EventEditAction(final NavResources res, final I18nTranslationService i18n,
        final Provider<CalendarViewer> calendar, final EventBus eventBus, final StateManager stateManager) {
      super(AccessRolDTO.Administrator, true);
      this.i18n = i18n;
      this.calendar = calendar;
      this.stateManager = stateManager;
      withText(i18n.t("Edit the appointment")).withIcon(res.go());
      eventBus.addHandler(CalendarStateChangeEvent.getType(),
          new CalendarStateChangeEvent.CalendarStateChangeHandler() {
            @Override
            public void onCalendarStateChange(final CalendarStateChangeEvent event) {
              // if the calendar is not selecting a appointment don't show this
              setEnabled(!calendar.get().getAppToEdit().equals(CalendarViewer.NO_APPOINT));
            }
          });
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
