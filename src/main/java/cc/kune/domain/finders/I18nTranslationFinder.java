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
package cc.kune.domain.finders;

import java.util.ArrayList;
import java.util.List;

import cc.kune.core.shared.dto.I18nTranslationDTO;
import cc.kune.domain.I18nLanguage;
import cc.kune.domain.I18nTranslation;

import com.google.inject.name.Named;
import com.google.inject.persist.finder.Finder;
import com.google.inject.persist.finder.FirstResult;
import com.google.inject.persist.finder.MaxResults;

// TODO: Auto-generated Javadoc
/**
 * The Interface I18nTranslationFinder.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface I18nTranslationFinder {

  /** The Constant AND_NOT_TRANSLATED. */
  public static final String AND_NOT_TRANSLATED = " AND gt.text=null ";

  /** The Constant AND_TRANSLATED. */
  public static final String AND_TRANSLATED = " AND gt.text!=null ";

  /** The left join. */
  String LEFT_JOIN = "LEFT JOIN gt.parent gp WHERE gt.language = :language ";

  /** The left join from. */
  String LEFT_JOIN_FROM = ", I18nTranslation gt2, I18nTranslation gt3 "
      + "WHERE gt.id = gt2.parent.id AND gt.id = gt3.parent.id AND gt3.language=:language AND gt2.language=:fromlanguage";

  /** The order by. */
  String ORDER_BY = " ORDER BY gt.parent.trKey";

  /** The select from english. */
  String SELECT_FROM_ENGLISH = "SELECT NEW cc.kune.core.shared.dto.I18nTranslationDTO "
      + "(gt.id, gt.trKey, gt.trKey, gt.text, gt.parent.id, gt.parent.trKey, gt.parent.noteForTranslators) FROM I18nTranslation gt ";

  /** The select from other lang. */
  String SELECT_FROM_OTHER_LANG = "SELECT NEW cc.kune.core.shared.dto.I18nTranslationDTO "
      + "(gt3.id, gt2.text, gt2.text, gt3.text, gt.parent.id, gt.parent.trKey, gt.parent.noteForTranslators) FROM I18nTranslation gt ";

  /** The translated query from english. */
  String TRANSLATED_QUERY_FROM_ENGLISH = SELECT_FROM_ENGLISH + LEFT_JOIN + AND_TRANSLATED + ORDER_BY;

  /** The translated query from other lang. */
  String TRANSLATED_QUERY_FROM_OTHER_LANG = SELECT_FROM_OTHER_LANG + LEFT_JOIN_FROM + "  "
      + " AND gt2.text!=null AND gt3.text!=null" + ORDER_BY;

  /** The untranslated query from english. */
  String UNTRANSLATED_QUERY_FROM_ENGLISH = SELECT_FROM_ENGLISH + LEFT_JOIN + AND_NOT_TRANSLATED
      + ORDER_BY;

  /** The untranslated query from other lang. */
  String UNTRANSLATED_QUERY_FROM_OTHER_LANG = SELECT_FROM_OTHER_LANG + LEFT_JOIN_FROM + "  "
      + " AND gt2.text!=null AND gt3.text=null" + ORDER_BY;

  /**
   * Find by language.
   * 
   * @param language
   *          the language
   * @return the list
   */
  @Finder(query = "SELECT gt FROM I18nTranslation gt JOIN gt.language gl WHERE gl.code = :language", returnAs = ArrayList.class)
  public List<I18nTranslation> findByLanguage(@Named("language") final String language);

  /**
   * Find count.
   * 
   * @param language
   *          the language
   * @param trkey
   *          the trkey
   * @return the long
   */
  @Finder(query = "SELECT count(*) FROM I18nTranslation gt WHERE gt.trKey = :trkey AND gt.language.code = :language")
  public Long findCount(@Named("language") String language, @Named("trkey") String trkey);

  /**
   * Gets the non existent from default.
   * 
   * @param deflanguage
   *          the deflanguage
   * @param language
   *          the language
   * @return the non existent from default
   */
  @Finder(query = "SELECT gt FROM I18nTranslation gt WHERE gt.language = :deflanguage AND gt.id NOT IN (SELECT gt.parent FROM I18nTranslation gt WHERE gt.language = :language)", returnAs = ArrayList.class)
  public List<I18nTranslation> getNonExistentFromDefault(
      @Named("deflanguage") final I18nLanguage deflanguage,
      @Named("language") final I18nLanguage language);

  /**
   * Gets the translated lexicon.
   * 
   * @param language
   *          the language
   * @return the translated lexicon
   */
  @Finder(query = TRANSLATED_QUERY_FROM_ENGLISH, returnAs = ArrayList.class)
  public List<I18nTranslationDTO> getTranslatedLexicon(@Named("language") final I18nLanguage language);

  /**
   * Gets the translated lexicon.
   * 
   * @param language
   *          the language
   * @param first
   *          the first
   * @param max
   *          the max
   * @return the translated lexicon
   */
  @Finder(query = TRANSLATED_QUERY_FROM_ENGLISH, returnAs = ArrayList.class)
  public List<I18nTranslationDTO> getTranslatedLexicon(@Named("language") final I18nLanguage language,
      @FirstResult final int first, @MaxResults final int max);

  /**
   * Gets the translated lexicon with from.
   * 
   * @param language
   *          the language
   * @param fromLanguage
   *          the from language
   * @return the translated lexicon with from
   */
  @Finder(query = TRANSLATED_QUERY_FROM_OTHER_LANG, returnAs = ArrayList.class)
  public List<I18nTranslationDTO> getTranslatedLexiconWithFrom(
      @Named("language") final I18nLanguage language,
      @Named("fromlanguage") final I18nLanguage fromLanguage);

  /**
   * Gets the translated lexicon with from.
   * 
   * @param language
   *          the language
   * @param fromLanguage
   *          the from language
   * @param first
   *          the first
   * @param max
   *          the max
   * @return the translated lexicon with from
   */
  @Finder(query = TRANSLATED_QUERY_FROM_OTHER_LANG, returnAs = ArrayList.class)
  public List<I18nTranslationDTO> getTranslatedLexiconWithFrom(
      @Named("language") final I18nLanguage language,
      @Named("fromlanguage") final I18nLanguage fromLanguage, @FirstResult final int first,
      @MaxResults final int max);

  /**
   * Gets the unstranslated lexicon.
   * 
   * @param language
   *          the language
   * @param first
   *          the first
   * @param max
   *          the max
   * @return the unstranslated lexicon
   */
  @Finder(query = UNTRANSLATED_QUERY_FROM_ENGLISH, returnAs = ArrayList.class)
  public List<I18nTranslationDTO> getUnstranslatedLexicon(
      @Named("language") final I18nLanguage language, @FirstResult final int first,
      @MaxResults final int max);

  /**
   * Gets the untranslated lexicon.
   * 
   * @param language
   *          the language
   * @return the untranslated lexicon
   */
  @Finder(query = UNTRANSLATED_QUERY_FROM_ENGLISH, returnAs = ArrayList.class)
  public List<I18nTranslationDTO> getUntranslatedLexicon(@Named("language") final I18nLanguage language);

  /**
   * Gets the untranslated lexicon with from.
   * 
   * @param language
   *          the language
   * @param fromLanguage
   *          the from language
   * @return the untranslated lexicon with from
   */
  @Finder(query = UNTRANSLATED_QUERY_FROM_OTHER_LANG, returnAs = ArrayList.class)
  public List<I18nTranslationDTO> getUntranslatedLexiconWithFrom(
      @Named("language") final I18nLanguage language,
      @Named("fromlanguage") final I18nLanguage fromLanguage);

  /**
   * Gets the untranslated lexicon with from.
   * 
   * @param language
   *          the language
   * @param fromLanguage
   *          the from language
   * @param first
   *          the first
   * @param max
   *          the max
   * @return the untranslated lexicon with from
   */
  @Finder(query = UNTRANSLATED_QUERY_FROM_OTHER_LANG, returnAs = ArrayList.class)
  public List<I18nTranslationDTO> getUntranslatedLexiconWithFrom(
      @Named("language") final I18nLanguage language,
      @Named("fromlanguage") final I18nLanguage fromLanguage, @FirstResult final int first,
      @MaxResults final int max);

}
