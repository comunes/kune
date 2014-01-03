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

import cc.kune.core.shared.dto.ReservedWordsRegistryDTO;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * Some common history tokens like {@link #SIGN_IN} and {@link #NEW_GROUP}.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public final class SiteTokens {

  /** The Constant ABOUT_KUNE. */
  public static final String ABOUT_KUNE = "about";

  /** The Constant ASK_RESET_PASSWD. */
  public final static String ASK_RESET_PASSWD = "askresetpasswd";

  /** The Constant GROUP_HOME. */
  public final static String GROUP_HOME = "";

  /** The Constant GROUP_PREFS. */
  public final static String GROUP_PREFS = "gprefs";

  /** The Constant HOME. */
  public final static String HOME = "";

  /** The Constant INVITATION. */
  public final static String INVITATION = "invitation";

  /** The Constant NEW_GROUP. */
  public final static String NEW_GROUP = "newgroup";

  /** The Constant PREFS. */
  public final static String PREFS = "prefs";

  /** The Constant PREVIEW. */
  public static final String PREVIEW = "preview";

  /** The Constant REGISTER. */
  public final static String REGISTER = "register";

  /** The Constant RESET_PASSWD. */
  public final static String RESET_PASSWD = "resetpasswd";

  /** The Constant SIGN_IN. */
  public final static String SIGN_IN = "signin";

  /** The Constant SUBTITLES. */
  public final static String SUBTITLES = "sub";

  /** The Constant TRANSLATE. */
  public final static String TRANSLATE = "translate";

  /** The Constant TUTORIAL. */
  public static final String TUTORIAL = "tutorial";

  /** The Constant VERIFY_EMAIL. */
  public final static String VERIFY_EMAIL = "verifyemail";

  /** The Constant WAVE_INBOX. */
  public final static String WAVE_INBOX = "inbox";

  /**
   * Instantiates a new site tokens.
   * 
   * @param reserverdWords
   *          the reserverd words
   */
  @Inject
  public SiteTokens(final ReservedWordsRegistryDTO reserverdWords) {
    reserverdWords.add(ABOUT_KUNE);
    reserverdWords.add(GROUP_HOME);
    reserverdWords.add(HOME);
    reserverdWords.add(NEW_GROUP);
    reserverdWords.add(PREVIEW);
    reserverdWords.add(TUTORIAL);
    reserverdWords.add(REGISTER);
    reserverdWords.add(SIGN_IN);
    reserverdWords.add(TRANSLATE);
    reserverdWords.add(WAVE_INBOX);
    reserverdWords.add(SUBTITLES);
    reserverdWords.add(PREFS);
    reserverdWords.add(GROUP_PREFS);
    reserverdWords.add(VERIFY_EMAIL);
    reserverdWords.add(RESET_PASSWD);
  }
}
