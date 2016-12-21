/*
 *
 * Copyright (C) 2007-2015 Licensed to the Comunes Association (CA) under
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

package org.waveprotocol.box.webclient.search;

import com.google.common.base.Preconditions;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.TextBox;

import cc.kune.common.client.tooltip.Tooltip;
import cc.kune.common.client.ui.WrappedFlowPanel;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.gspace.client.armor.GSpaceArmor;
import cc.kune.polymer.client.PolymerId;

public class CustomSearchWidget implements SearchView, ChangeHandler {

  private Listener listener;
  private TextBox query;

  private final static String DEFAULT_QUERY = "";

  public CustomSearchWidget(GSpaceArmor armor) {
    String inputId = PolymerId.SITEBAR_SEARCH_INPUT_INBOX.getId();
    DOM.getElementById(inputId).removeFromParent();
    query = new TextBox();
    query.addStyleName("sitebar_search_hide");
    Tooltip.to(query, I18n.t("Right now, you can only type 'with:simone' to search messages with simone as participant"));
    query.getElement().setId(inputId);
    final WrappedFlowPanel searchDiv = armor.wrapDiv(PolymerId.SITEBAR_SEARCH_GROUP_INBOX);
    query.addChangeHandler(this);
    searchDiv.add(query);
  }

  @Override
  public void onChange(ChangeEvent event) {
    if (query.getValue() == null || query.getValue().isEmpty()) {
      query.setText(DEFAULT_QUERY);
    }
    onQuery();
  }

  private void onQuery() {
    if (listener != null) {
      listener.onQueryEntered();
    }
  }

  @Override
  public void init(Listener listener) {
    Preconditions.checkState(this.listener == null);
    Preconditions.checkArgument(listener != null);
    this.listener = listener;
  }

  @Override
  public void reset() {
    Preconditions.checkState(listener != null);
    listener = null;
  }

  @Override
  public String getQuery() {
    String value = query.getValue();
    GWT.log("Inbox query: " + value);
    return value;
  }

  @Override
  public void setQuery(String text) {
    GWT.log("Set inbox query: " + text);
    query.setValue(text);
  }
}