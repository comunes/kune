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
package cc.kune.common.shared.i18n;

import cc.kune.common.shared.utils.Pair;

import com.google.gwt.i18n.client.HasDirection.Direction;

public abstract class I18nTranslationService implements HasRTL {

  protected static final String RTL = "rtl";

  // Also in I18nTranslation
  protected static final String UNTRANSLATED_VALUE = null;

  public String decodeHtml(final String textToDecode) {
    final String text = textToDecode;
    // text = text.replaceAll("&copy;", "©");
    return text;
  }

  public Direction getDirection() {
    return isRTL() ? Direction.RTL : Direction.LTR;
  }

  @Override
  public abstract boolean isRTL();

  /**
   * Use [%d] to reference the Integer parameters
   * 
   */
  // @PMD:REVIEWED:ShortMethodName: by vjrj on 21/05/09 13:50
  private String t(final Pair<String, String> pair, final Integer... args) {
    String translation = tWithNT(pair.getLeft(), pair.getRight());
    for (final Integer arg : args) {
      translation = translation.replaceFirst("\\[%d\\]", arg.toString());
    }
    return decodeHtml(translation);
  }

  /**
   * Use [%d] to reference the Long parameter
   * 
   */
  // @PMD:REVIEWED:ShortMethodName: by vjrj on 21/05/09 13:50
  private String t(final Pair<String, String> pair, final Long... args) {
    String translation = tWithNT(pair.getLeft(), pair.getRight());
    for (final Long arg : args) {
      translation = translation.replaceFirst("\\[%d\\]", arg.toString());
    }
    return decodeHtml(translation);
  }

  /**
   * Use [%s] to reference the string parameter
   * 
   */
  // @PMD:REVIEWED:ShortMethodName: by vjrj on 21/05/09 13:50
  private String t(final Pair<String, String> pair, final String... args) {
    String translation = tWithNT(pair.getLeft(), pair.getRight());
    for (final String arg : args) {
      translation = translation.replaceFirst("\\[%s\\]", arg);
    }
    return decodeHtml(translation);
  }

  /**
   * In production, this method uses a hashmap. In development, if the text is
   * not in the hashmap, it makes a server petition (that stores the text
   * pending for translation in db).
   * 
   * Warning: text is escaped as html before insert in the db. Don't use html
   * here (o user this method with params).
   * 
   * @param text
   *          some note for facilitate the translation
   * 
   * @return text translated in the current language
   */
  public String t(final String text) {
    return tWithNT(text, "");
  }

  /**
   * Use [%d] to reference the Integer parameters
   * 
   */
  // @PMD:REVIEWED:ShortMethodName: by vjrj on 21/05/09 13:50
  public String t(final String text, final Integer... args) {
    return t(Pair.create(text, ""), args);
  }

  /**
   * Use [%d] to reference the Long parameter
   * 
   */
  // @PMD:REVIEWED:ShortMethodName: by vjrj on 21/05/09 13:50
  public String t(final String text, final Long... args) {
    return t(Pair.create(text, ""), args);
  }

  /**
   * Use [%s] to reference the String parameter.
   * 
   */
  public String t(final String text, final String... args) {
    return t(Pair.create(text, ""), args);
  }

  /**
   * In production, this method uses a hashmap. In development, if the text is
   * not in the hashmap, it makes a server petition (that stores the text
   * pending for translation in db).
   * 
   * Warning: text is escaped as html before insert in the db. Don't use html
   * here (o user this method with params).
   * 
   * @param text
   * @param noteForTranslators
   *          some note for facilitate the translation
   * 
   * @return text translated in the current language
   */
  // @PMD:REVIEWED:ShortMethodName: by vjrj on 21/05/09 13:49
  public abstract String tWithNT(final String text, final String noteForTranslators);

  /**
   * - Use [%d] to reference the Integer parameter.
   * 
   * Also adds [%NT noteForTranslators] at the end of text. This tag is later
   * renderer in the translator panel to inform translator how to do this
   * translation
   * 
   */
  public String tWithNT(final String text, final String noteForTranslators, final Integer... args) {
    return t(Pair.create(text, noteForTranslators), args);
  }

  /**
   * Use [%d] to reference the Long parameter.
   * 
   * Also adds [%NT noteForTranslators] at the end of text. This tag is later
   * renderer in the translator panel to inform translator how to do this
   * translation
   * 
   */
  public String tWithNT(final String text, final String noteForTranslators, final Long... args) {
    return t(Pair.create(text, noteForTranslators), args);
  }

  /**
   * Use [%s] to reference the String parameter.
   * 
   * Also adds [%NT noteForTranslators] at the end of text. This tag is later
   * renderer in the translator panel to inform translator how to do this
   * translation
   * 
   */
  public String tWithNT(final String text, final String noteForTranslators, final String... args) {
    return t(Pair.create(text, noteForTranslators), args);
  }

}
