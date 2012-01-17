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
package cc.kune.core.client.state;

import cc.kune.core.shared.dto.ReservedWordsRegistryDTO;

import com.google.inject.Inject;

/**
 * Some common history tokens like {@link #SIGN_IN} and {@link #NEW_GROUP}
 * 
 */
public final class SiteTokens {
  public static final String ABOUT_KUNE = "about";
  public final static String ASK_RESET_PASSWD = "askresetpasswd";
  public final static String GROUP_HOME = "";
  public final static String GROUP_PREFS = "gprefs";
  public final static String HOME = "";
  public final static String NEW_GROUP = "newgroup";
  public final static String PREFS = "prefs";
  public static final String PREVIEW = "preview";
  public final static String REGISTER = "register";
  public final static String RESET_PASSWD = "resetpasswd";
  public final static String SIGN_IN = "signin";
  public final static String SUBTITLES = "sub";
  public final static String TRANSLATE = "translate";
  public final static String VERIFY_EMAIL = "verifyemail";
  public final static String WAVE_INBOX = "inbox";

  @Inject
  public SiteTokens(final ReservedWordsRegistryDTO reserverdWords) {
    reserverdWords.add(ABOUT_KUNE);
    reserverdWords.add(GROUP_HOME);
    reserverdWords.add(HOME);
    reserverdWords.add(NEW_GROUP);
    reserverdWords.add(PREVIEW);
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
