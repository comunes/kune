package cc.kune.events.client.actions;

import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.ui.dialogs.PromptTopDialog;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.common.shared.utils.TextUtils;
import cc.kune.core.client.resources.CoreResources;
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
public class EventRemoveMenuItem extends MenuItemDescriptor {
  public static class EventEditAction extends CalendarRolAction {
    private final Provider<CalendarViewer> calendar;
    private PromptTopDialog dialog;
    private final I18nTranslationService i18n;

    @Inject
    public EventEditAction(final CoreResources res, final I18nTranslationService i18n,
        final Provider<CalendarViewer> calendar, final EventBus eventBus, final Session session,
        final StateManager stateManager, final AccessRightsClientManager rightsMan) {
      super(eventBus, session, calendar, AccessRolDTO.Administrator, true, true);
      this.i18n = i18n;
      this.calendar = calendar;
      withText(i18n.t("Remove this appointment")).withIcon(res.cancel());
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
