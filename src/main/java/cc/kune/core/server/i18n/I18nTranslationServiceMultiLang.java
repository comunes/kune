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
