package cc.kune.events.client.actions;

import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.ui.dialogs.PromptTopDialog;
import cc.kune.common.client.ui.dialogs.PromptTopDialog.Builder;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.actions.RolAction;
import cc.kune.core.client.resources.nav.NavResources;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.ContentServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.core.shared.dto.StateContentDTO;
import cc.kune.events.client.viewer.CalendarViewer;
import cc.kune.events.shared.EventsConstants;
import cc.kune.events.shared.EventsConversionUtil;

import com.bradrydzewski.gwt.calendar.client.Appointment;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class EventAddMenuItem extends MenuItemDescriptor {
  public static class EventAddAction extends RolAction {
    private final Provider<CalendarViewer> calendar;
    private final Provider<ContentServiceAsync> contService;
    private PromptTopDialog dialog;
    private final I18nTranslationService i18n;
    private final Session session;

    @Inject
    public EventAddAction(final NavResources res, final I18nTranslationService i18n,
        final Provider<CalendarViewer> calendar, final Provider<ContentServiceAsync> contService,
        final Session session) {
      super(AccessRolDTO.Editor, true);
      this.i18n = i18n;
      this.calendar = calendar;
      this.contService = contService;
      this.session = session;
      withText(i18n.t("Add an appointment")).withIcon(res.calendarAdd());
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
              final String title = dialog.getTextFieldValue();
              final Appointment app = calendar.get().addAppointment(title,
                  calendar.get().getOnOverDate());
              contService.get().addNewContentWithGadgetAndState(session.getUserHash(),
                  session.getContainerState().getStateToken(),
                  EventsConstants.TYPE_MEETING_DEF_GADGETNAME, EventsConstants.TYPE_MEETING, "", title,
                  EventsConversionUtil.toMap(app), new AsyncCallbackSimple<StateContentDTO>() {
                    @Override
                    public void onFailure(final Throwable caught) {
                      super.onFailure(caught);
                      // FIXME, remove appointment
                    }

                    @Override
                    public void onSuccess(final StateContentDTO result) {
                    }
                  });
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
  public EventAddMenuItem(final EventAddAction action, final CalendarOnOverMenu cal) {
    super(action);
    setParent(cal.get());
  }
}
