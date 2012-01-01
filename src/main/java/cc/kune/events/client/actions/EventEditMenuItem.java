package cc.kune.events.client.actions;

import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.ui.dialogs.PromptTopDialog;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.common.shared.utils.TextUtils;
import cc.kune.core.client.actions.RolAction;
import cc.kune.core.client.resources.nav.NavResources;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.events.client.viewer.CalendarStateChangeEvent;
import cc.kune.events.client.viewer.CalendarViewer;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class EventEditMenuItem extends MenuItemDescriptor {
  public static class EventEditAction extends RolAction {
    private final Provider<CalendarViewer> calendar;
    private PromptTopDialog dialog;
    private final I18nTranslationService i18n;

    @Inject
    public EventEditAction(final NavResources res, final I18nTranslationService i18n,
        final Provider<CalendarViewer> calendar, final EventBus eventBus) {
      super(AccessRolDTO.Administrator, true);
      this.i18n = i18n;
      this.calendar = calendar;
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
      NotifyUser.info(i18n.t(TextUtils.IN_DEVELOPMENT));
    }
  }

  @Inject
  public EventEditMenuItem(final EventEditAction action, final CalendarOnOverMenu cal) {
    super(action);
    setParent(cal.get());
  }
}
