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
package cc.kune.events.shared;

import cc.kune.common.shared.res.KuneIcon;

// TODO: Auto-generated Javadoc
/**
 * The Class EventsToolConstants.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public final class EventsToolConstants {

  /** The Constant ICON_TYPE_MEETING. */
  public static final KuneIcon ICON_TYPE_MEETING = new KuneIcon('h');

  /** The Constant ICON_TYPE_ROOT. */
  public static final KuneIcon ICON_TYPE_ROOT = new KuneIcon('p');

  /** The Constant ROOT_NAME. */
  public static final String ROOT_NAME = "events";

  /** The Constant TOOL_NAME. */
  public static final String TOOL_NAME = "events";

  /** The Constant TYPE_MEETING. */
  public static final String TYPE_MEETING = TOOL_NAME + "." + "meeting";

  /** The Constant TYPE_MEETING_DEF_GADGETNAME. */
  public static final String TYPE_MEETING_DEF_GADGETNAME = "massmob";

  /** The Constant TYPE_ROOT. */
  public static final String TYPE_ROOT = TOOL_NAME + "." + "root";

  /**
   * Instantiates a new events tool constants.
   */
  private EventsToolConstants() {
  }
}
