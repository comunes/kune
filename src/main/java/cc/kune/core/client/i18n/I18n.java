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
package cc.kune.core.client.i18n;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class I18n {

  @Inject
  private static I18nUITranslationService i18n;

  public static boolean isRTL() {
    return i18n.isRTL();
  }

  public static String t(final String text) {
    return i18n.t(text);
  }

  public static String t(final String text, final Integer... args) {
    return i18n.t(text, args);
  }

  public static String t(final String text, final Long... args) {
    return i18n.t(text, args);
  }

  public static String t(final String text, final String... args) {
    return i18n.t(text, args);
  }

  public static String tWithNT(final String text, final String noteForTranslators) {
    return i18n.tWithNT(text, noteForTranslators);
  }

  public static String tWithNT(final String text, final String noteForTranslators, final Integer... args) {
    return i18n.tWithNT(text, noteForTranslators, args);
  }

  public static String tWithNT(final String text, final String noteForTranslators, final Long... args) {
    return i18n.tWithNT(text, noteForTranslators, args);
  }

  public static String tWithNT(final String text, final String noteForTranslators, final String... args) {
    return i18n.tWithNT(text, noteForTranslators, args);
  }
}
