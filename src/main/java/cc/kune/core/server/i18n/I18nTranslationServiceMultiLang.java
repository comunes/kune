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
package cc.kune.core.server.i18n;

import cc.kune.domain.I18nLanguage;

public interface I18nTranslationServiceMultiLang {
  /**
   * If the text is not in the db, it stores the text pending for translation.
   * 
   * Warning: text is escaped as html before insert in the db. Don't use html
   * here (o user this method with params).
   * 
   * @param text
   * @param noteForTranslators
   *          some note for facilitate the translation
   * @param lang
   *          translation for some specific language
   * @return text translated in the specified language
   */
  public String tWithNT(I18nLanguage lang, final String text, final String noteForTranslators);

  /**
   * Use [%s] to reference the String parameter.
   * 
   * Also adds [%NT noteForTranslators] at the end of text. This tag is later
   * renderer in the translator panel to inform translator how to do this
   * translation
   * 
   */
  public String tWithNT(I18nLanguage lang, final String text, final String noteForTranslators,
      final String... args);

}
