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
package cc.kune.hspace.client;

import cc.kune.core.client.state.impl.HistoryUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

// TODO: Auto-generated Javadoc
/**
 * The Class GroupContentHomeLink.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class GroupContentHomeLink extends Composite {

  /**
   * The Interface GroupContentHomeLinkUiBinder.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  interface GroupContentHomeLinkUiBinder extends UiBinder<Widget, GroupContentHomeLink> {
  }

  /** The ui binder. */
  private static GroupContentHomeLinkUiBinder uiBinder = GWT.create(GroupContentHomeLinkUiBinder.class);

  /** The icon. */
  @UiField
  Image icon;

  /** The link. */
  @UiField
  Hyperlink link;

  /**
   * Instantiates a new group content home link.
   */
  public GroupContentHomeLink() {
    initWidget(uiBinder.createAndBindUi(this));
  }

  /**
   * Sets the values.
   * 
   * @param logoImageUrl
   *          the logo image url
   * @param text
   *          the text
   * @param historyToken
   *          the history token
   */
  public void setValues(final String logoImageUrl, final String text, final String historyToken) {
    Scheduler.get().scheduleDeferred(new ScheduledCommand() {
      @Override
      public void execute() {
        icon.setUrl(logoImageUrl);
      }
    });
    link.setText(text);
    link.setTargetHistoryToken(HistoryUtils.PREFIX + historyToken);
  }

}
