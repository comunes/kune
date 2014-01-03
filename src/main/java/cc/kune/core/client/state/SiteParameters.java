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
package cc.kune.core.client.state;

import cc.kune.common.client.utils.WindowUtils;

// TODO: Auto-generated Javadoc
/**
 * Some common url params like {@link ?locale}.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public final class SiteParameters {

  /** The Constant ESCAPED_FRAGMENT_PARAMETER is used to make kune searcheable. */
  public static final String ESCAPED_FRAGMENT_PARAMETER = "_escaped_fragment_";

  /** The Constant LOCALE force the use of some language. */
  public static final String LOCALE = "locale";

  /** The Constant NO_UA_CHECK, if present, disable the User Agent check. */
  public static final String NO_UA_CHECK = "noua";
  /**
   * The Constant ONLY_WEBCLIENT indicates if we should only open waves in Wave
   * webclient (not in the group space). Useful for debugging.
   */
  public static final String ONLY_WEBCLIENT = "onlywebclient";

  /** The Constant UA_CHECK, if present, enable the User Agent check. */
  public static final String UA_CHECK = "ua";

  /**
   * The Constant WAVE_AVATARS indicates if we should use avatars in wave panel
   * (useful for debugging).
   */
  public static final String WAVE_AVATARS_DISABLED = "waveavatarsdisabled";

  /**
   * Check ua.
   * 
   * @return true, if successful
   */
  public static boolean checkUA() {
    return WindowUtils.getParameter(SiteParameters.NO_UA_CHECK) == null;
  }

  /**
   * Checks if is search robot.
   * 
   * @return true, if is search robot
   */
  public static boolean isSearchRobot() {
    return WindowUtils.getParameter(SiteParameters.ESCAPED_FRAGMENT_PARAMETER) != null;
  }

  /**
   * Instantiates a new site parameters.
   */
  public SiteParameters() {
  }
}
