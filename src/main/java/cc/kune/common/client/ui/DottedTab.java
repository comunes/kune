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
package cc.kune.common.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

// TODO: Auto-generated Javadoc
/**
 * The Class DottedTab.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class DottedTab extends Composite {

  /**
   * The Interface DottedTabUiBinder.
   *
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  interface DottedTabUiBinder extends UiBinder<Widget, DottedTab> {
  }

  /** The ui binder. */
  private static DottedTabUiBinder uiBinder = GWT.create(DottedTabUiBinder.class);

  /**
   * Instantiates a new dotted tab.
   */
  public DottedTab() {
    initWidget(uiBinder.createAndBindUi(this));
  }

}
