/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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

// TODO: Auto-generated Javadoc
/**
 * The Class CalendarViewerPresenter.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class CalendarViewerPresenter extends
    Presenter<CalendarViewerPresenter.CalendarViewerView, CalendarViewerPresenter.CalendarViewerProxy>
    implements CalendarViewer {

  /**
   * The Interface CalendarViewerProxy.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  @ProxyCodeSplit
  public interface CalendarViewerProxy extends Proxy<CalendarViewerPresenter> {
  }

  /**
   * The Interface CalendarViewerView.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface CalendarViewerView extends HasSelectionHandlers<Appointment>,
      HasDeleteHandlers<Appointment>, HasOpenHandlers<Appointment>, HasTimeBlockClickHandlers<Date>,
      HasUpdateHandlers<Appointment>, HasDateRequestHandlers<Date>, HasMouseOverHandlers<Appointment>,
      HasLayout, HasAppointments, AbstractFolderViewerView {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.bradrydzewski.gwt.calendar.client.HasAppointments#addAppointment(
     * com.bradrydzewski.gwt.calendar.client.Appointment)
     */
    @Override
    void addAppointment(Appointment app);

    /**
     * Adds the click handler.
     * 
     * @param clickHandler
     *          the click handler
     * @return the handler registration
     */
    HandlerRegistration addClickHandler(ClickHandler clickHandler);

    /**
     * Adds the create handler.
     * 
     * @param handler
     *          the handler
     * @return the handler registration
     */
    HandlerRegistration addCreateHandler(CreateHandler<Appointment> handler);

    /**
     * Gets the client x.
     * 
     * @return the client x
     */
    int getClientX();

    /**
     * Gets the client y.
     * 
     * @return the client y
     */
    int getClientY();

    /**
     * Gets the current date.
     * 
     * @return the current date
     */
    Date getCurrentDate();

    /**
     * Gets the date.
     * 
     * @return the date
     */
    Date getDate();

    /**
     * Go today.
     */
    void goToday();

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.bradrydzewski.gwt.calendar.client.HasAppointments#removeAppointment
     * (com.bradrydzewski.gwt.calendar.client.Appointment)
     */
    @Override
    void removeAppointment(Appointment app);

    /**
     * Sets the date.
     * 
     * @param date
     *          the new date
     */
    void setDate(Date date);

    /**
     * Sets the on mouse over tooltip text.
     * 
     * @param text
     *          the new on mouse over tooltip text
     */
    void setOnMouseOverTooltipText(String text);

    /**
     * Sets the view.
     * 
     * @param view
     *          the new view
     */
    void setView(CalendarViews view);

    /**
     * Sets the view.
     * 
     * @param view
     *          the view
     * @param days
     *          the days
     */
    void setView(CalendarViews view, int days);

    /**
     * Update title.
     * 
     * @param currentCalView
     *          the current cal view
     */
    void updateTitle(CalendarViews currentCalView);
  }

  /** The Constant DEF_VIEW. */
  private static final CalendarViews DEF_VIEW = CalendarViews.DAY;

  /** The app to edit. */
  public Appointment appToEdit = NO_APPOINT;

  /** The content service. */
  private final Provider<ContentServiceAsync> contentService;

  /** The current cal view. */
  private CalendarViews currentCalView;

  /** The current days view. */
  private int currentDaysView = 7;

  /** The folder viewer utils. */
  private final FolderViewerUtils folderViewerUtils;

  /** The i18n. */
  private final I18nTranslationService i18n;

  /** The on over date. */
  private Date onOverDate;

  /** The on over menu. */
  private final CalendarOnOverMenu onOverMenu;

  /** The session. */
  private final Session session;

  /**
   * Instantiates a new calendar viewer presenter.
   * 
   * @param eventBus
   *          the event bus
   * @param view
   *          the view
   * @param proxy
   *          the proxy
   * @param folderViewerUtils
   *          the folder viewer utils
   * @param onOverMenu
   *          the on over menu
   * @param session
   *          the session
   * @param i18n
   *          the i18n
   * @param contentService
   *          the content service
   */
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

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.events.client.viewer.CalendarViewer#addAppointment(com.bradrydzewski
   * .gwt.calendar.client.Appointment)
   */
  @Override
  public void addAppointment(final Appointment app) {
    getView().addAppointment(app);
  }

  /**
   * Adds the listeners.
   */
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

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.tool.ContentViewer#attach()
   */
  @Override
  public void attach() {
    getView().attach();
    updateTitle();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.events.client.viewer.CalendarViewer#decrement()
   */
  @Override
  public void decrement() {
    incrementDate(false);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.tool.ContentViewer#detach()
   */
  @Override
  public void detach() {
    getView().detach();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.events.client.viewer.CalendarViewer#getAppToEdit()
   */
  @Override
  public Appointment getAppToEdit() {
    return appToEdit;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.events.client.viewer.CalendarViewer#getDate()
   */
  @Override
  public Date getDate() {
    return getView().getDate();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.events.client.viewer.CalendarViewer#getOnOverDate()
   */
  @Override
  public Date getOnOverDate() {
    return onOverDate;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.events.client.viewer.CalendarViewer#goToday()
   */
  @Override
  public void goToday() {
    getView().goToday();
    updateTitle();
  }

  /**
   * Hide menu.
   */
  private void hideMenu() {
    onOverMenu.get().hide();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.events.client.viewer.CalendarViewer#increment()
   */
  @Override
  public void increment() {
    incrementDate(true);
  }

  /**
   * Increment date.
   * 
   * @param positive
   *          the positive
   */
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

  /**
   * Checks if is valid.
   * 
   * @param app
   *          the app
   * @return true, if is valid
   */
  private boolean isValid(final Appointment app) {
    return app.getStart() != null && app.getEnd() != null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.events.client.viewer.CalendarViewer#removeAppointment(com.bradrydzewski
   * .gwt.calendar.client.Appointment)
   */
  @Override
  public void removeAppointment(final Appointment app) {
    getView().removeAppointment(app);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.gwtplatform.mvp.client.Presenter#revealInParent()
   */
  @Override
  protected void revealInParent() {
    RevealRootContentEvent.fire(this, this);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.tool.ContentViewer#setContent(cc.kune.core.shared
   * .dto.HasContent)
   */
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

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.events.client.viewer.CalendarViewer#setDate(java.util.Date)
   */
  @Override
  public void setDate(final Date date) {
    getView().setDate(date);
  }

  /**
   * Sets the menu position.
   * 
   * @param x
   *          the x
   * @param y
   *          the y
   */
  private void setMenuPosition(final int x, final int y) {
    onOverMenu.get().setMenuPosition(new Position(x, y));
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.events.client.viewer.CalendarViewer#setView(com.bradrydzewski.gwt
   * .calendar.client.CalendarViews)
   */
  @Override
  public void setView(final CalendarViews calView) {
    setViewImpl(calView);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.events.client.viewer.CalendarViewer#setView(com.bradrydzewski.gwt
   * .calendar.client.CalendarViews, int)
   */
  @Override
  public void setView(final CalendarViews calView, final int days) {
    setViewImpl(calView, days);
  }

  /**
   * Sets the view impl.
   * 
   * @param calView
   *          the new view impl
   */
  private void setViewImpl(final CalendarViews calView) {
    this.currentCalView = calView;
    this.currentDaysView = 1;
    getView().setView(calView);
    updateTitle();
  }

  /**
   * Sets the view impl.
   * 
   * @param calView
   *          the cal view
   * @param days
   *          the days
   */
  private void setViewImpl(final CalendarViews calView, final int days) {
    this.currentCalView = calView;
    this.currentDaysView = days;
    getView().setView(calView, days);
    updateTitle();
  }

  /**
   * Show menu.
   */
  private void showMenu() {
    setMenuPosition(getView().getClientX(), getView().getClientY());
    onOverMenu.get().show();
  }

  /**
   * Update menu items.
   */
  private void updateMenuItems() {
    CalendarStateChangeEvent.fire(getEventBus());
  }

  /**
   * Update title.
   */
  private void updateTitle() {
    getView().updateTitle(currentCalView);
  }
}
