package cc.kune.events.client.actions;

import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.ui.dialogs.PromptTopDialog;
import cc.kune.common.client.utils.TextUtils;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.actions.RolAction;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.events.client.viewer.CalendarStateChangeEvent;
import cc.kune.events.client.viewer.CalendarViewer;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class EventRemoveMenuItem extends MenuItemDescriptor {
  public static class EventEditAction extends RolAction {
    private final Provider<CalendarViewer> calendar;
    private PromptTopDialog dialog;
    private final I18nTranslationService i18n;

    @Inject
    public EventEditAction(final CoreResources res, final I18nTranslationService i18n,
        final Provider<CalendarViewer> calendar, final EventBus eventBus) {
      super(AccessRolDTO.Editor, true);
      this.i18n = i18n;
      this.calendar = calendar;
      withText(i18n.t("Remove this appointment")).withIcon(res.cancel());
      eventBus.addHandler(CalendarStateChangeEvent.getType(),
          new CalendarStateChangeEvent.CalendarStateChangeHandler() {
            @Override
            public void onCalendarStateChange(final CalendarStateChangeEvent event) {
              setEnabled(!calendar.get().getAppToEdit().equals(CalendarViewer.NO_APPOINT));
            }
          });
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
      NotifyUser.info(i18n.t(TextUtils.IN_DEVELOPMENT));
    }
  }

  public static final String CREATE_APP_ADD_ID = "event-add-menu-item-add-btn";
  public static final String CREATE_APP_CANCEL_ID = "event-add-menu-item-add-btn";
  public static final String CREATE_APP_ID = "event-add-menu-item-form";

  @Inject
  public EventRemoveMenuItem(final EventEditAction action, final CalendarOnOverMenu cal) {
    super(action);
    setParent(cal.get());
  }
}
