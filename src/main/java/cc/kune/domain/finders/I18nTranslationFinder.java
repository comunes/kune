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

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import cc.kune.domain.I18nLanguage;
import cc.kune.domain.I18nTranslation;

import com.google.inject.name.Named;
import com.google.inject.persist.finder.Finder;
import com.google.inject.persist.finder.FirstResult;
import com.google.inject.persist.finder.MaxResults;

@NamedQueries({
        @NamedQuery(name = "untranslated", query = "SELECT gt FROM I18nTranslation gt WHERE gt.language = :language and text=null"),
        @NamedQuery(name = "translated", query = "SELECT gt FROM I18nTranslation gt WHERE gt.language = :language and text!=null"),
        @NamedQuery(name = "untranslatedcount", query = "SELECT COUNT(gt.id) FROM I18nTranslation gt WHERE gt.language = :language and text=null"),
        @NamedQuery(name = "translatedcount", query = "SELECT COUNT(gt.id) FROM I18nTranslation gt WHERE gt.language = :language and text!=null") })
public interface I18nTranslationFinder {
    String TRANSLATED_COUNT_QUERY = "translatedcount";
    String TRANSLATED_QUERY = "translated";
    String UNTRANSLATED_COUNT_QUERY = "untranslatedcount";
    String UNTRANSLATED_QUERY = "untranslated";

    @Finder(query = "SELECT gt FROM I18nTranslation gt JOIN gt.language gl WHERE gl.code = :language", returnAs = ArrayList.class)
    public List<I18nTranslation> findByLanguage(@Named("language") final String language);

    @Finder(query = "SELECT gt FROM I18nTranslation gt WHERE gt.language = :deflanguage AND gt.trKey NOT IN (SELECT gt.trKey FROM I18nTranslation gt WHERE gt.language = :language)", returnAs = ArrayList.class)
    public List<I18nTranslation> getNonExistentFromDefault(@Named("deflanguage") final I18nLanguage deflanguage,
            @Named("language") final I18nLanguage language);

    @Finder(namedQuery = TRANSLATED_QUERY, returnAs = ArrayList.class)
    public List<I18nTranslation> getTranslatedLexicon(@Named("language") final I18nLanguage language);

    @Finder(namedQuery = TRANSLATED_QUERY, returnAs = ArrayList.class)
    public List<I18nTranslation> getTranslatedLexicon(@Named("language") final I18nLanguage language,
            @FirstResult final int first, @MaxResults final int max);

    @Finder(namedQuery = TRANSLATED_COUNT_QUERY)
    public Long getTranslatedLexiconCount(@Named("language") final I18nLanguage language);

    @Finder(namedQuery = UNTRANSLATED_QUERY, returnAs = ArrayList.class)
    public List<I18nTranslation> getUnstranslatedLexicon(@Named("language") final I18nLanguage language);

    @Finder(namedQuery = UNTRANSLATED_QUERY, returnAs = ArrayList.class)
    public List<I18nTranslation> getUnstranslatedLexicon(@Named("language") final I18nLanguage language,
            @FirstResult final int first, @MaxResults final int max);

    @Finder(namedQuery = UNTRANSLATED_COUNT_QUERY)
    public Long getUnstranslatedLexiconCount(@Named("language") final I18nLanguage language);

}
