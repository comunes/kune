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
package cc.kune.core.server.i18n;

import cc.kune.domain.I18nLanguage;

// TODO: Auto-generated Javadoc
/**
 * The Interface I18nTranslationServiceMultiLang.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface I18nTranslationServiceMultiLang {

  /**
   * If the text is not in the db, it stores the text pending for translation.
   * 
   * Warning: text is escaped as html before insert in the db. Don't use html
   * here (o user this method with params).
   * 
   * @param lang
   *          translation for some specific language
   * @param text
   *          the text
   * @param noteForTranslators
   *          some note for facilitate the translation
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
   * @param lang
   *          the lang
   * @param text
   *          the text
   * @param noteForTranslators
   *          the note for translators
   * @param args
   *          the args
   * @return the string
   */
  public String tWithNT(I18nLanguage lang, final String text, final String noteForTranslators,
      final String... args);

}
