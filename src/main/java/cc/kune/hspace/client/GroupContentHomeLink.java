/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package cc.kune.hspace.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class GroupContentHomeLink extends Composite {

  interface GroupContentHomeLinkUiBinder extends UiBinder<Widget, GroupContentHomeLink> {
  }

  private static GroupContentHomeLinkUiBinder uiBinder = GWT.create(GroupContentHomeLinkUiBinder.class);

  @UiField
  Image icon;

  @UiField
  Hyperlink link;

  public GroupContentHomeLink() {
    initWidget(uiBinder.createAndBindUi(this));
  }

  public void setValues(final String logoImageUrl, final String text, final String historyToken) {
    Scheduler.get().scheduleDeferred(new ScheduledCommand() {
      @Override
      public void execute() {
        icon.setUrl(logoImageUrl);
      }
    });
    link.setText(text);
    link.setTargetHistoryToken(historyToken);
  }

}
