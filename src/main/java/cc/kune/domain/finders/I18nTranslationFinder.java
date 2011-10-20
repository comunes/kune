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

public interface I18nTranslationFinder {
  String TRANSLATED_QUERY = "SELECT NEW cc.kune.core.shared.dto.I18nTranslationDTO"
      + "(gt.id, gt.trKey, gt.text, gt.parent.id, gt.parent.trKey, gt.parent.noteForTranslators) "
      + "FROM I18nTranslation gt LEFT JOIN gt.parent gp WHERE gt.language = :language AND gt.text!=null ORDER BY gt.parent.trKey";
  String UNTRANSLATED_QUERY = "SELECT NEW cc.kune.core.shared.dto.I18nTranslationDTO"
      + "(gt.id, gt.trKey, gt.text, gt.parent.id, gt.parent.trKey, gt.parent.noteForTranslators) "
      + "FROM I18nTranslation gt LEFT JOIN gt.parent gp WHERE gt.language = :language AND gt.text=null  ORDER BY gt.parent.trKey";

  @Finder(query = "SELECT gt FROM I18nTranslation gt JOIN gt.language gl WHERE gl.code = :language", returnAs = ArrayList.class)
  public List<I18nTranslation> findByLanguage(@Named("language") final String language);

  @Finder(query = "SELECT gt FROM I18nTranslation gt WHERE gt.language = :deflanguage AND gt.id NOT IN (SELECT gt.parent FROM I18nTranslation gt WHERE gt.language = :language)", returnAs = ArrayList.class)
  public List<I18nTranslation> getNonExistentFromDefault(
      @Named("deflanguage") final I18nLanguage deflanguage,
      @Named("language") final I18nLanguage language);

  @Finder(query = TRANSLATED_QUERY, returnAs = ArrayList.class)
  public List<I18nTranslationDTO> getTranslatedLexicon(@Named("language") final I18nLanguage language);

  @Finder(query = TRANSLATED_QUERY, returnAs = ArrayList.class)
  public List<I18nTranslationDTO> getTranslatedLexicon(@Named("language") final I18nLanguage language,
      @FirstResult final int first, @MaxResults final int max);

  @Finder(query = UNTRANSLATED_QUERY, returnAs = ArrayList.class)
  public List<I18nTranslationDTO> getUnstranslatedLexicon(
      @Named("language") final I18nLanguage language, @FirstResult final int first,
      @MaxResults final int max);

  @Finder(query = UNTRANSLATED_QUERY, returnAs = ArrayList.class)
  public List<I18nTranslationDTO> getUntranslatedLexicon(@Named("language") final I18nLanguage language);

}
