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
package cc.kune.core.server.i18n;

import cc.kune.common.client.utils.TextUtils;
import cc.kune.core.server.UserSession;
import cc.kune.core.server.manager.I18nTranslationManager;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.domain.I18nTranslation;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class I18nTranslationServiceDefault extends I18nTranslationService {

  private final I18nTranslationManager translationManager;

  // private final Provider<UserSession> userSessionProvider;

  @Inject
  public I18nTranslationServiceDefault(final I18nTranslationManager translationManager,
      final Provider<UserSession> userSessionProvider) {
    this.translationManager = translationManager;
    // this.userSessionProvider = userSessionProvider;
  }

  /**
   * If the text is not in the db, it stores the text pending for translation.
   * 
   * Warning: text is escaped as html before insert in the db. Don't use html
   * here (o user this method with params).
   * 
   * @param text
   * @return text translated in the current language
   */
  @Override
  public String t(final String text) {
    return tWithNT(text, "");
  }

  /**
   * If the text is not in the db, it stores the text pending for translation.
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
  @Override
  public String tWithNT(final String text, final String noteForTranslators) {
    String language;

    // final UserSession userSession = userSessionProvider.get();
    // if (userSession.isUserLoggedIn()) {
    // language = userSession.getUser().getLanguage().getCode();
    // } else {
    language = I18nTranslation.DEFAULT_LANG;
    // }
    final String encodeText = TextUtils.escapeHtmlLight(text);
    String translation = translationManager.getTranslation(language, text, noteForTranslators);
    if (translation == UNTRANSLATED_VALUE) {
      // Not translated but in db, return text
      translation = encodeText;
    }
    return decodeHtml(translation);
  }
}
