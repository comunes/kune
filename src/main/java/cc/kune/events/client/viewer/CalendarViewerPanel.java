/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package cc.kune.events.client.viewer;

import java.util.ArrayList;
import java.util.Date;

import cc.kune.common.client.actions.ui.GuiProvider;
import cc.kune.common.client.tooltip.Tooltip;
import cc.kune.common.client.ui.UiUtils;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.dnd.FolderContainerDropController;
import cc.kune.core.client.dnd.FolderContentDropController;
import cc.kune.core.client.dnd.KuneDragController;
import cc.kune.core.client.registry.ContentCapabilitiesRegistry;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.events.client.viewer.CalendarViewerPresenter.CalendarViewerView;
import cc.kune.events.shared.EventsToolConstants;
import cc.kune.gspace.client.armor.GSpaceArmor;
import cc.kune.gspace.client.viewers.AbstractFolderViewerPanel;
import cc.kune.gspace.client.viewers.ContentTitleWidget;
import cc.kune.gspace.client.viewers.items.FolderItemDescriptor;

import com.bradrydzewski.gwt.calendar.client.Appointment;
import com.bradrydzewski.gwt.calendar.client.Calendar;
import com.bradrydzewski.gwt.calendar.client.CalendarSettings;
import com.bradrydzewski.gwt.calendar.client.CalendarViews;
import com.bradrydzewski.gwt.calendar.client.event.CreateHandler;
import com.bradrydzewski.gwt.calendar.client.event.DateRequestHandler;
import com.bradrydzewski.gwt.calendar.client.event.DeleteHandler;
import com.bradrydzewski.gwt.calendar.client.event.MouseOverEvent;
import com.bradrydzewski.gwt.calendar.client.event.MouseOverHandler;
import com.bradrydzewski.gwt.calendar.client.event.TimeBlockClickHandler;
import com.bradrydzewski.gwt.calendar.client.event.UpdateHandler;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.InsertPanel.ForIsWidget;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class CalendarViewerPanel extends AbstractFolderViewerPanel implements CalendarViewerView {

  private final Calendar calendar;
  private int clientX;
  private int clientY;
  private ContentTitleWidget contentTitle;
  private Tooltip tooltip;
  private PopupPanel tooltipPanel;

  @Inject
  public CalendarViewerPanel(final GSpaceArmor gsArmor, final EventBus eventBus,
      final I18nTranslationService i18n, final GuiProvider guiProvider, final CoreResources res,
      final ContentCapabilitiesRegistry capabilitiesRegistry, final KuneDragController dragController,
      final Provider<FolderContentDropController> contentDropControllerProv,
      final Provider<FolderContainerDropController> containerDropControllerProv) {
    super(gsArmor, eventBus, i18n, capabilitiesRegistry, dragController, contentDropControllerProv,
        containerDropControllerProv);
    calendar = new Calendar();
    calendar.setSettings(setCalendarSettings());
    widget = calendar;
    calendar.sinkEvents(Event.ONMOUSEDOWN | Event.ONDBLCLICK | Event.KEYEVENTS | Event.ONMOUSEOVER
        | Event.ONCLICK);
    Event.addNativePreviewHandler(new NativePreviewHandler() {
      @Override
      public void onPreviewNativeEvent(final NativePreviewEvent eventPrev) {
        // We store click position in onder to show the menu
        final NativeEvent natEvent = eventPrev.getNativeEvent();
        if (Event.getTypeInt(natEvent.getType()) != Event.ONCLICK) {
          clientX = natEvent.getClientX();
          clientY = natEvent.getClientY();
          return;
        }
      }
    });
    contentTitle = new ContentTitleWidget(i18n, gsArmor, capabilitiesRegistry.getIconsRegistryLight());
    Window.addResizeHandler(new ResizeHandler() {
      @Override
      public void onResize(final ResizeEvent event) {
        resizeCalendar();
      }
    });
    tooltipPanel = new PopupPanel();
    tooltip = Tooltip.to(tooltipPanel, "FIXME");
    addMouseOverHandler(new MouseOverHandler<Appointment>() {
      @Override
      public void onMouseOver(final MouseOverEvent<Appointment> event) {
        final Element element = (Element) event.getElement();
        tooltipPanel.setPopupPosition(DOM.getAbsoluteLeft(element), DOM.getAbsoluteTop(element)
            + element.getOffsetHeight());
        tooltipPanel.show();
        if (tooltip.isShowing()) {
          tooltipPanel.hide();
          tooltip.hide();
        } else {
          tooltip.showTemporally();
        }
        // NotifyUser.info("On mouse");
      }
    });
  }

  @Override
  public void addAppointment(final Appointment appointment) {
    calendar.addAppointment(appointment);
  }

  @Override
  public void addAppointments(final ArrayList<Appointment> appointments) {
    calendar.addAppointments(appointments);
  }

  @Override
  public HandlerRegistration addClickHandler(final ClickHandler clickHandler) {
    return calendar.addHandler(clickHandler, ClickEvent.getType());
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
    // Do nothing right now, calendar appointments has a different treatment
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
    calendar.setSettings(setCalendarSettings());
    super.attach();
  }

  @Override
  public void clearAppointments() {
    if (calendar.getAppointments().size() > 0) {
      calendar.clearAppointments();
    }
  }

  @Override
  public void detach() {
    super.detach();

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
  public int getClientX() {
    return clientX;
  }

  @Override
  public int getClientY() {
    return clientY;
  }

  @Override
  public Date getCurrentDate() {
    return calendar.getDate();
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

  protected void resizeCalendar() {
    super.resizeHeight(calendar);
    calendar.doLayout();
  }

  @Override
  public void resumeLayout() {
    calendar.resumeLayout();
  }

  private CalendarSettings setCalendarSettings() {
    final CalendarSettings settings = new CalendarSettings();
    settings.setIntervalsPerHour(4);
    settings.setPixelsPerInterval(10);
    // With Single is very annoying
    // settings.setTimeBlockClickNumber(Click.Single);
    // settings.setOffsetHourLabels(true);
    settings.setScrollToHour(8);
    return settings;
  }

  @Override
  public void setDate(final Date date) {
    calendar.setDate(date);
  }

  @Override
  public void setOnMouseOverTooltipText(final String text) {
    tooltip.setText(text);
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
  public void showEmptyMsg(final String emptyMessage) {
    // Don't do nothing
    // super.showEmptyMsg(emptyMessage);
  }

  @Override
  public void showFolder() {
    super.showFolder();
    gsArmor.enableCenterScroll(false);
    resizeCalendar();
  }

  @Override
  public void suspendLayout() {
    calendar.suspendLayout();
  }

  @Override
  public void updateTitle(final CalendarViews currentCalView) {
    final Date currentDate = getDate();
    DateTimeFormat fmt = null;
    // More info about formats:
    // http://google-web-toolkit.googlecode.com/svn/javadoc/latest/com/google/gwt/i18n/client/DateTimeFormat.html
    switch (currentCalView) {
    case DAY:
    case AGENDA:
      fmt = DateTimeFormat.getFormat("EEEE, MMMM dd, yyyy");
      break;
    case MONTH:
    default:
      fmt = DateTimeFormat.getFormat("MMMM yyyy");
      break;
    }
    final String dateFormatted = fmt.format(currentDate);
    final ForIsWidget docHeader = gsArmor.getDocHeader();
    UiUtils.clear(docHeader);
    contentTitle.setTitle(i18n.t("Events in [%s]", dateFormatted), EventsToolConstants.TYPE_ROOT, null,
        false);
    docHeader.add(contentTitle);
  }

}
