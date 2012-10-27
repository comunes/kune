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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

import cc.kune.common.client.actions.ui.descrip.Position;
import cc.kune.common.client.log.Log;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.utils.DateUtils;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.common.shared.res.ICalConstants;
import cc.kune.common.shared.utils.TextUtils;
import cc.kune.core.client.rpcservices.ContentServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.HasContent;
import cc.kune.core.shared.dto.StateEventContainerDTO;
import cc.kune.events.client.actions.CalendarOnOverMenu;
import cc.kune.events.shared.EventsClientConversionUtil;
import cc.kune.events.shared.EventsToolConstants;
import cc.kune.gspace.client.viewers.AbstractFolderViewerView;
import cc.kune.gspace.client.viewers.FolderViewerUtils;

import com.bradrydzewski.gwt.calendar.client.Appointment;
import com.bradrydzewski.gwt.calendar.client.AppointmentStyle;
import com.bradrydzewski.gwt.calendar.client.CalendarViews;
import com.bradrydzewski.gwt.calendar.client.HasAppointments;
import com.bradrydzewski.gwt.calendar.client.HasLayout;
import com.bradrydzewski.gwt.calendar.client.event.CreateHandler;
import com.bradrydzewski.gwt.calendar.client.event.DeleteEvent;
import com.bradrydzewski.gwt.calendar.client.event.DeleteHandler;
import com.bradrydzewski.gwt.calendar.client.event.HasDateRequestHandlers;
import com.bradrydzewski.gwt.calendar.client.event.HasDeleteHandlers;
import com.bradrydzewski.gwt.calendar.client.event.HasMouseOverHandlers;
import com.bradrydzewski.gwt.calendar.client.event.HasTimeBlockClickHandlers;
import com.bradrydzewski.gwt.calendar.client.event.HasUpdateHandlers;
import com.bradrydzewski.gwt.calendar.client.event.MouseOverEvent;
import com.bradrydzewski.gwt.calendar.client.event.MouseOverHandler;
import com.bradrydzewski.gwt.calendar.client.event.TimeBlockClickEvent;
import com.bradrydzewski.gwt.calendar.client.event.TimeBlockClickHandler;
import com.bradrydzewski.gwt.calendar.client.event.UpdateEvent;
import com.bradrydzewski.gwt.calendar.client.event.UpdateHandler;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.HasOpenHandlers;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

public class CalendarViewerPresenter extends
    Presenter<CalendarViewerPresenter.CalendarViewerView, CalendarViewerPresenter.CalendarViewerProxy>
    implements CalendarViewer {

  @ProxyCodeSplit
  public interface CalendarViewerProxy extends Proxy<CalendarViewerPresenter> {
  }

  public interface CalendarViewerView extends HasSelectionHandlers<Appointment>,
      HasDeleteHandlers<Appointment>, HasOpenHandlers<Appointment>, HasTimeBlockClickHandlers<Date>,
      HasUpdateHandlers<Appointment>, HasDateRequestHandlers<Date>, HasMouseOverHandlers<Appointment>,
      HasLayout, HasAppointments, AbstractFolderViewerView {

    @Override
    void addAppointment(Appointment app);

    HandlerRegistration addClickHandler(ClickHandler clickHandler);

    HandlerRegistration addCreateHandler(CreateHandler<Appointment> handler);

    int getClientX();

    int getClientY();

    Date getCurrentDate();

    Date getDate();

    void goToday();

    @Override
    void removeAppointment(Appointment app);

    void setDate(Date date);

    void setView(CalendarViews view);

    void setView(CalendarViews view, int days);

    void updateTitle(CalendarViews currentCalView);

    void setOnMouseOverTooltipText(String text);
  }

  private static final CalendarViews DEF_VIEW = CalendarViews.DAY;
  public Appointment appToEdit = NO_APPOINT;

  private final Provider<ContentServiceAsync> contentService;
  private CalendarViews currentCalView;
  private int currentDaysView = 7;
  private final FolderViewerUtils folderViewerUtils;
  private final I18nTranslationService i18n;
  private Date onOverDate;
  private final CalendarOnOverMenu onOverMenu;
  private final Session session;

  @Inject
  public CalendarViewerPresenter(final EventBus eventBus, final CalendarViewerView view,
      final CalendarViewerProxy proxy, final FolderViewerUtils folderViewerUtils,
      final CalendarOnOverMenu onOverMenu, final Session session, final I18nTranslationService i18n,
      final Provider<ContentServiceAsync> contentService) {
    super(eventBus, view, proxy);
    this.folderViewerUtils = folderViewerUtils;
    this.onOverMenu = onOverMenu;
    this.session = session;
    this.i18n = i18n;
    this.contentService = contentService;
    addListeners();
    setViewImpl(DEF_VIEW, currentDaysView);
  }

  @Override
  public void addAppointment(final Appointment app) {
    getView().addAppointment(app);
  }

  private void addListeners() {
    getView().setOnMouseOverTooltipText(
        i18n.t("Double click to open. Also you can resize or drag&drop this event"));
    getView().addDeleteHandler(new DeleteHandler<Appointment>() {
      @Override
      public void onDelete(final DeleteEvent<Appointment> event) {
        NotifyUser.info(i18n.t(TextUtils.IN_DEVELOPMENT));
        event.setCancelled(true);
      }
    });
    getView().addTimeBlockClickHandler(new TimeBlockClickHandler<Date>() {
      @Override
      public void onTimeBlockClick(final TimeBlockClickEvent<Date> event) {
        // NotifyUser.info("on time block");
        appToEdit = NO_APPOINT;
        onOverDate = event.getTarget();
        updateMenuItems();
        showMenu();
      }
    });
    getView().addMouseOverHandler(new MouseOverHandler<Appointment>() {
      @Override
      public void onMouseOver(final MouseOverEvent<Appointment> event) {
        // NotifyUser.info("on mouse over " +
      }
    });

    getView().addUpdateHandler(new UpdateHandler<Appointment>() {
      @Override
      public void onUpdate(final UpdateEvent<Appointment> event) {
        final boolean editable = session.getCurrentState().getGroupRights().isEditable();
        if (!editable) {
          NotifyUser.error(i18n.t("Only members can update events"));
        }
        event.setCancelled(!editable);
        final Appointment app = event.getTarget();
        final Map<String, String> map = new HashMap<String, String>();
        map.put(ICalConstants.DATE_TIME_START, DateUtils.toString(app.getStart()));
        map.put(ICalConstants.DATE_TIME_END, DateUtils.toString(app.getEnd()));
        contentService.get().setGadgetProperties(session.getUserHash(), new StateToken(app.getId()),
            EventsToolConstants.TYPE_MEETING_DEF_GADGETNAME, map, new AsyncCallback<Void>() {
              @Override
              public void onFailure(final Throwable caught) {
                event.setCancelled(true);
              }

              @Override
              public void onSuccess(final Void result) {
              }
            });
        hideMenu();
      }
    });
    getView().addOpenHandler(new OpenHandler<Appointment>() {
      @Override
      public void onOpen(final OpenEvent<Appointment> event) {
        // NotifyUser.info("open handler");
        updateMenuItems();
        showMenu();
      }
    });
    getView().addSelectionHandler(new SelectionHandler<Appointment>() {
      @Override
      public void onSelection(final SelectionEvent<Appointment> event) {
        appToEdit = event.getSelectedItem();
        onOverDate = event.getSelectedItem().getStart();

        // This is not very usable:
        // updateMenuItems();
        // showMenu();
      }
    });
  }

  @Override
  public void attach() {
    getView().attach();
    updateTitle();
  }

  @Override
  public void decrement() {
    incrementDate(false);
  }

  @Override
  public void detach() {
    getView().detach();
  }

  @Override
  public Appointment getAppToEdit() {
    return appToEdit;
  }

  @Override
  public Date getDate() {
    return getView().getDate();
  }

  @Override
  public Date getOnOverDate() {
    return onOverDate;
  }

  @Override
  public void goToday() {
    getView().goToday();
    updateTitle();
  }

  private void hideMenu() {
    onOverMenu.get().hide();
  }

  @Override
  public void increment() {
    incrementDate(true);
  }

  private void incrementDate(final boolean positive) {
    final Date date = getDate();
    switch (currentCalView) {
    case DAY:
    case AGENDA:
      CalendarUtil.addDaysToDate(date, currentDaysView * (positive ? 1 : -1));
      break;
    case MONTH:
      CalendarUtil.addMonthsToDate(date, (positive ? 1 : -1));
      break;
    }
    setDate(date);
    updateTitle();
  }

  private boolean isValid(final Appointment app) {
    return app.getStart() != null && app.getEnd() != null;
  }

  @Override
  public void removeAppointment(final Appointment app) {
    getView().removeAppointment(app);
  }

  @Override
  protected void revealInParent() {
    RevealRootContentEvent.fire(this, this);
  }

  @Override
  public void setContent(@Nonnull final HasContent state) {
    folderViewerUtils.setContent(getView(), state);
    getView().showFolder();
    final StateEventContainerDTO eventState = (StateEventContainerDTO) state;
    updateMenuItems();
    getView().clearAppointments();
    getView().suspendLayout();
    for (final Map<String, String> map : eventState.getAppointments()) {
      Appointment app;
      try {
        app = EventsClientConversionUtil.toApp(map);
        app.setId(map.get(ICalConstants._INTERNAL_ID));
        app.setStyle(AppointmentStyle.GREEN);
        if (isValid(app)) {
          getView().addAppointment(app);
        } else {
          Log.error("Appointment is not valid: " + app);
        }
      } catch (final Exception e) {
        Log.error("Appointment is not valid");
      }
    }
    getView().resumeLayout();
  }

  @Override
  public void setDate(final Date date) {
    getView().setDate(date);
  }

  private void setMenuPosition(final int x, final int y) {
    onOverMenu.get().setMenuPosition(new Position(x, y));
  }

  @Override
  public void setView(final CalendarViews calView) {
    setViewImpl(calView);
  }

  @Override
  public void setView(final CalendarViews calView, final int days) {
    setViewImpl(calView, days);
  }

  private void setViewImpl(final CalendarViews calView) {
    this.currentCalView = calView;
    this.currentDaysView = 1;
    getView().setView(calView);
    updateTitle();
  }

  private void setViewImpl(final CalendarViews calView, final int days) {
    this.currentCalView = calView;
    this.currentDaysView = days;
    getView().setView(calView, days);
    updateTitle();
  }

  private void showMenu() {
    setMenuPosition(getView().getClientX(), getView().getClientY());
    onOverMenu.get().show();
  }

  private void updateMenuItems() {
    CalendarStateChangeEvent.fire(getEventBus());
  }

  private void updateTitle() {
    getView().updateTitle(currentCalView);
  }
}
