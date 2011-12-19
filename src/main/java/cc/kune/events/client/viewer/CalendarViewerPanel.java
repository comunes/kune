package cc.kune.events.client.viewer;

import java.util.ArrayList;
import java.util.Date;

import cc.kune.common.client.actions.ui.bind.GuiProvider;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.dnd.FolderViewerDropController;
import cc.kune.core.client.dnd.KuneDragController;
import cc.kune.core.client.registry.ContentCapabilitiesRegistry;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.events.client.viewer.CalendarViewerPresenter.CalendarViewerView;
import cc.kune.gspace.client.GSpaceArmor;
import cc.kune.gspace.client.viewers.AbstractFolderViewerPanel;
import cc.kune.gspace.client.viewers.FolderItemDescriptor;

import com.bradrydzewski.gwt.calendar.client.Appointment;
import com.bradrydzewski.gwt.calendar.client.AppointmentStyle;
import com.bradrydzewski.gwt.calendar.client.Calendar;
import com.bradrydzewski.gwt.calendar.client.CalendarViews;
import com.bradrydzewski.gwt.calendar.client.event.CreateHandler;
import com.bradrydzewski.gwt.calendar.client.event.DateRequestHandler;
import com.bradrydzewski.gwt.calendar.client.event.DeleteHandler;
import com.bradrydzewski.gwt.calendar.client.event.MouseOverHandler;
import com.bradrydzewski.gwt.calendar.client.event.TimeBlockClickHandler;
import com.bradrydzewski.gwt.calendar.client.event.UpdateHandler;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class CalendarViewerPanel extends AbstractFolderViewerPanel implements CalendarViewerView {

  private final Calendar calendar;

  @Inject
  public CalendarViewerPanel(final GSpaceArmor gsArmor, final I18nTranslationService i18n,
      final GuiProvider guiProvider, final CoreResources res,
      final ContentCapabilitiesRegistry capabilitiesRegistry, final KuneDragController dragController,
      final Provider<FolderViewerDropController> dropControllerProv) {
    super(gsArmor, i18n, capabilitiesRegistry, dragController, dropControllerProv);
    calendar = new Calendar();
    // calendar.setWidth("auto");
    // calendar.setHeight("auto");
    widget = calendar;
  }

  @Override
  public void addAppointment(final Appointment appointment) {
    calendar.addAppointment(appointment);
  }

  @Override
  public void addAppointment(final String title, final Date date) {
    // Should this be used or serialize from server side?
    final Appointment appt = new Appointment();
    appt.setStart(date);
    appt.setEnd(date);
    appt.setTitle(title);
    appt.setStyle(AppointmentStyle.ORANGE);
    calendar.addAppointment(appt);
    // FIXME NotiUser
  }

  @Override
  public void addAppointments(final ArrayList<Appointment> appointments) {
    calendar.addAppointments(appointments);
  }

  @Override
  public HandlerRegistration addCreateHandler(final CreateHandler<Appointment> handler) {
    return calendar.addCreateHandler(handler);
  }

  @Override
  public HandlerRegistration addDateRequestHandler(final DateRequestHandler<Date> handler) {
    return calendar.addDateRequestHandler(handler);
  }

  @Override
  public HandlerRegistration addDeleteHandler(final DeleteHandler<Appointment> handler) {
    return calendar.addDeleteHandler(handler);
  }

  @Override
  public void addItem(final FolderItemDescriptor item, final ClickHandler clickHandler,
      final DoubleClickHandler doubleClickHandler) {
    // TODO Auto-generated method stub
  }

  @Override
  public HandlerRegistration addMouseOverHandler(final MouseOverHandler<Appointment> handler) {
    return calendar.addMouseOverHandler(handler);
  }

  @Override
  public HandlerRegistration addOpenHandler(final OpenHandler<Appointment> handler) {
    return calendar.addOpenHandler(handler);
  }

  @Override
  public HandlerRegistration addSelectionHandler(final SelectionHandler<Appointment> handler) {
    return calendar.addSelectionHandler(handler);
  }

  @Override
  public HandlerRegistration addTimeBlockClickHandler(final TimeBlockClickHandler<Date> handler) {
    return calendar.addTimeBlockClickHandler(handler);
  }

  @Override
  public HandlerRegistration addUpdateHandler(final UpdateHandler<Appointment> handler) {
    return calendar.addUpdateHandler(handler);
  }

  @Override
  public void attach() {
    super.attach();
    gsArmor.enableCenterScroll(false);
  }

  @Override
  public void clearAppointments() {
    calendar.clearAppointments();
  }

  @Override
  public void doLayout() {
    calendar.doLayout();
  }

  @Override
  public void fireEvent(final GwtEvent<?> event) {
    calendar.fireEvent(event);
  }

  @Override
  public Date getDate() {
    return calendar.getDate();
  }

  @Override
  public Appointment getSelectedAppointment() {
    return calendar.getSelectedAppointment();
  }

  @Override
  public void goToday() {
    calendar.setDate(new Date()); // calendar date, not required
  }

  @Override
  public boolean hasAppointmentSelected() {
    return calendar.hasAppointmentSelected();
  }

  @Override
  public void removeAppointment(final Appointment appointment) {
    calendar.removeAppointment(appointment);
  }

  @Override
  public void removeAppointment(final Appointment appointment, final boolean fireEvents) {
    calendar.removeAppointment(appointment, fireEvents);
  }

  @Override
  public void resumeLayout() {
    calendar.resumeLayout();
  }

  @Override
  public void setDate(final Date date) {
    calendar.setDate(date);
  }

  @Override
  public void setSelectedAppointment(final Appointment appointment) {
    calendar.setSelectedAppointment(appointment);
  }

  @Override
  public void setSelectedAppointment(final Appointment appointment, final boolean fireEvents) {
    calendar.setSelectedAppointment(appointment, fireEvents);
  }

  @Override
  public void setView(final CalendarViews view) {
    calendar.setView(view);
    calendar.scrollToHour(6);
  }

  @Override
  public void setView(final CalendarViews view, final int days) {
    calendar.setView(view, days);
    calendar.scrollToHour(6);
  }

  @Override
  public void suspendLayout() {
    calendar.suspendLayout();
  }

}
