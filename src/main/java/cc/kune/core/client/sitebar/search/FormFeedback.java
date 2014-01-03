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
/*******************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************/
package cc.kune.core.client.sitebar.search;

import com.google.gwt.user.client.ui.Label;

// TODO: Auto-generated Javadoc
/**
 * This widget shows an icon to give feedback on form fields.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class FormFeedback extends Label {

  /** Feedback status error. */
  public static final int ERROR = 2;

  /** The Constant ERROR_STYLE. */
  private static final String ERROR_STYLE = "error";

  /** Feedback status loading. */
  public static final int LOADING = 4;

  /** The Constant LOADING_STYLE. */
  private static final String LOADING_STYLE = "loading";

  /** Feedback status none. */
  public static final int NONE = 5;

  /** Feedback status valid. */
  public static final int VALID = 3;

  /** The Constant VALID_STYLE. */
  private static final String VALID_STYLE = "valid";

  /** Feedback status warning. */
  public static final int WARNING = 1;

  /** The Constant WARNING_STYLE. */
  private static final String WARNING_STYLE = "warning";

  /** The mstatus. */
  private int mstatus = NONE;

  /**
   * Creates a new FormFeedback widget.
   */
  public FormFeedback() {
    this(true);
  }

  /**
   * Creates a new FormFeedback widget.
   * 
   * @param inform
   *          true if this feedback widget needs our standard for styles and
   *          false otherwise
   */
  public FormFeedback(final boolean inform) {
    getElement().addClassName("rpt-formfeedback");
    if (inform) {
      getElement().addClassName("rpt-formfeedback-inform");
    }
  }

  /**
   * Gets the status of this FormFeedback.
   * 
   * @return the form feedback
   */
  public int getStatus() {
    return mstatus;
  }

  /**
   * Removes the styles.
   */
  private void removeStyles() {
    getElement().removeClassName(WARNING_STYLE);
    getElement().removeClassName(ERROR_STYLE);
    getElement().removeClassName(LOADING_STYLE);
    getElement().removeClassName(VALID_STYLE);
  }

  /**
   * Sets the status for this FormFeedback.
   * 
   * @param status
   *          the status
   */
  public void setStatus(final int status) {
    mstatus = status;
    removeStyles();

    switch (status) {
    case WARNING:
      getElement().addClassName(WARNING_STYLE);
      setTitle("Warning");
      return;
    case ERROR:
      getElement().addClassName(ERROR_STYLE);
      setTitle("Error");
      return;
    case LOADING:
      getElement().addClassName(LOADING_STYLE);
      setTitle("Loading");
      return;
    case VALID:
      getElement().addClassName(VALID_STYLE);
      setTitle("Valid");
      return;
    default:
      setTitle("");
      removeStyles();
      return;
    }
  }
}
