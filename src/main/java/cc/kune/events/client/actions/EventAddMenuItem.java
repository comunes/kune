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
package cc.kune.events.client.actions;

import java.util.Date;

import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.ContentServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.ui.dialogs.PromptTopDialog;
import cc.kune.core.client.ui.dialogs.PromptTopDialog.Builder;
import cc.kune.core.client.ui.dialogs.PromptTopDialog.OnEnter;
import cc.kune.core.shared.SessionConstants;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.core.shared.dto.StateContentDTO;
import cc.kune.events.client.viewer.CalendarViewer;
import cc.kune.events.shared.EventsClientConversionUtil;
import cc.kune.events.shared.EventsToolConstants;

import com.bradrydzewski.gwt.calendar.client.Appointment;
import com.bradrydzewski.gwt.calendar.client.AppointmentStyle;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Class EventAddMenuItem.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class EventAddMenuItem extends MenuItemDescriptor {

  /**
   * The Class EventAddAction.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public static class EventAddAction extends CalendarRolAction {

    /** The calendar. */
    private final Provider<CalendarViewer> calendar;

    /** The cont service. */
    private final Provider<ContentServiceAsync> contService;

    /** The dialog. */
    private PromptTopDialog dialog;

    /** The i18n. */
    private final I18nTranslationService i18n;

    /** The open after creation. */
    private boolean openAfterCreation;

    /** The session. */
    private final Session session;

    /** The state manager. */
    private final StateManager stateManager;

    /**
     * Instantiates a new event add action.
     * 
     * @param res
     *          the res
     * @param i18n
     *          the i18n
     * @param calendar
     *          the calendar
     * @param contService
     *          the cont service
     * @param session
     *          the session
     * @param stateManager
     *          the state manager
     * @param eventBus
     *          the event bus
     */
    @Inject
    public EventAddAction(final IconicResources res, final I18nTranslationService i18n,
        final Provider<CalendarViewer> calendar, final Provider<ContentServiceAsync> contService,
        final Session session, final StateManager stateManager, final EventBus eventBus) {
      super(eventBus, session, calendar, AccessRolDTO.Editor, true, false);
      this.i18n = i18n;
      this.calendar = calendar;
      this.contService = contService;
      this.session = session;
      this.stateManager = stateManager;
      withText(i18n.t("Add an appointment")).withIcon(res.eventAdd());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cc.kune.common.client.actions.ActionListener#actionPerformed(cc.kune.
     * common.client.actions.ActionEvent)
     */
    @Override
    public void actionPerformed(final ActionEvent event) {
      if (dialog == null) {
        final Builder builder = new PromptTopDialog.Builder(CREATE_APP_ID,
            i18n.t("Description of the appointment"), true, true, i18n.getDirection(), new OnEnter() {
              @Override
              public void onEnter() {
                doAction();
              }
            });
        builder.firstButtonTitle(i18n.t("Add")).firstButtonId(CREATE_APP_ADD_ID);
        builder.sndButtonTitle(i18n.t("Cancel")).sndButtonId(CREATE_APP_CANCEL_ID);
        dialog = builder.build();
        dialog.getFirstBtn().addClickHandler(new ClickHandler() {
          @Override
          public void onClick(final ClickEvent event) {
            doAction();
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

    /**
     * Do action.
     */
    private void doAction() {
      if (dialog.isValid()) {

        final String title = dialog.getTextFieldValue();
        final Date onOverDate = calendar.get().getOnOverDate();
        final Appointment appt = new Appointment();
        final Date startDate = onOverDate != null ? onOverDate : new Date();
        if (onOverDate != null) {
          openAfterCreation = false;
        }
        appt.setStart(startDate);
        // http://stackoverflow.com/questions/2527845/how-to-do-calendar-operations-in-java-gwt-how-to-add-days-to-a-dateSessionConstants._AN_HOUR
        final Date endDate = new Date(startDate.getTime() + SessionConstants._1_HOUR);
        appt.setEnd(endDate);
        appt.setTitle(title);
        appt.setStyle(AppointmentStyle.ORANGE);

        contService.get().addNewContentWithGadgetAndState(session.getUserHash(),
            session.getContainerState().getStateToken(),
            EventsToolConstants.TYPE_MEETING_DEF_GADGETNAME, EventsToolConstants.TYPE_MEETING, title,
            "", EventsClientConversionUtil.toMap(appt), new AsyncCallbackSimple<StateContentDTO>() {
              @Override
              public void onFailure(final Throwable caught) {
                super.onFailure(caught);
              }

              @Override
              public void onSuccess(final StateContentDTO result) {
                // FIXME this should be setted for real...
                appt.setId(result.getStateToken().toString());
                // Should this be used or serialize from server side?
                calendar.get().addAppointment(appt);
                if (openAfterCreation) {
                  stateManager.gotoHistoryToken(appt.getId());
                }
              }
            });
        dialog.hide();
      }
    }

    /**
     * Sets the open after creation.
     * 
     * @param openAfterCreation
     *          the new open after creation
     */
    public void setOpenAfterCreation(final boolean openAfterCreation) {
      this.openAfterCreation = openAfterCreation;
    }
  }

  /** The Constant CREATE_APP_ADD_ID. */
  public static final String CREATE_APP_ADD_ID = "event-add-menu-item-add-btn";

  /** The Constant CREATE_APP_CANCEL_ID. */
  public static final String CREATE_APP_CANCEL_ID = "event-add-menu-item-add-btn";

  /** The Constant CREATE_APP_ID. */
  public static final String CREATE_APP_ID = "event-add-menu-item-form";

  /**
   * Instantiates a new event add menu item.
   * 
   * @param action
   *          the action
   * @param cal
   *          the cal
   */
  @Inject
  public EventAddMenuItem(final EventAddAction action, final CalendarOnOverMenu cal) {
    super(action);
    setParent(cal.get());
    action.setOpenAfterCreation(false);
  }
}
