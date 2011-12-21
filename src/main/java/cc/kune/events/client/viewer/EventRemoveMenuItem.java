package cc.kune.events.client.viewer;

import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.ui.dialogs.PromptTopDialog;
import cc.kune.common.client.ui.dialogs.PromptTopDialog.Builder;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.actions.RolAction;
import cc.kune.core.client.resources.nav.NavResources;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.events.client.actions.CalendarOnOverMenu;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class EventRemoveMenuItem extends MenuItemDescriptor {
  public static class EventEditAction extends RolAction {
    private final Provider<CalendarViewer> calendar;
    private PromptTopDialog dialog;
    private final I18nTranslationService i18n;

    @Inject
    public EventEditAction(final NavResources res, final I18nTranslationService i18n,
        final Provider<CalendarViewer> calendar, final EventBus eventBus) {
      super(AccessRolDTO.Editor, true);
      this.i18n = i18n;
      this.calendar = calendar;
      withText(i18n.t("Edit the appointment")).withIcon(res.calendarAdd());
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
      if (dialog == null) {
        final Builder builder = new PromptTopDialog.Builder(CREATE_APP_ID,
            i18n.t("Description of the appointment"), true, true, i18n.getDirection());
        builder.firstButtonTitle(i18n.t("Add")).firstButtonId(CREATE_APP_ADD_ID);
        builder.sndButtonTitle(i18n.t("Cancel")).sndButtonId(CREATE_APP_CANCEL_ID);
        dialog = builder.build();
        dialog.getFirstBtn().addClickHandler(new ClickHandler() {
          @Override
          public void onClick(final ClickEvent event) {
            if (dialog.isValid()) {
              calendar.get().addAppointment(dialog.getTextFieldValue(), calendar.get().getOnOverDate());
              NotifyUser.info("Appointment should be edited (this is under development)");
              dialog.hide();
            }
          }
        });
        dialog.getSecondBtn().addClickHandler(new ClickHandler() {
          @Override
          public void onClick(final ClickEvent event) {
            dialog.hide();
          }
        });
      }
      dialog.clearTextFieldValue();
      dialog.showCentered();
      dialog.focusOnTextBox();
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
