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
package cc.kune.core.client.state;

/**
 * Some common url params like {@link ?locale}
 * 
 */
public final class SiteParameters {

  /** The Constant LOCALE force the use of some language. */
  public static final String LOCALE = "locale";

  /**
   * The Constant WAVE_AVATARS indicates if we should use avatars in wave panel
   * (useful for debugging).
   */
  public static final String WAVE_AVATARS_DISABLED = "waveavatarsdisabled";

  /**
   * The Constant ONLY_WEBCLIENT indicates if we should only open waves in Wave
   * webclient (not in the group space). Useful for debugging.
   */
  public static final String ONLY_WEBCLIENT = "onlywebclient";

  public SiteParameters() {
  }
}
