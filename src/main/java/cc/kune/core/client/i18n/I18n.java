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
