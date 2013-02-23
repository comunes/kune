package cc.kune.common.client.utils;

/**
 * The Class I18nBasicUtils. Some code from Apache Wave SessionLocale.java.
 */
public class I18nBasicUtils {
    /**
     * RFC 3066 pattern for language (primary subtag).
     */
    private static final String LANG_PATTERN = "[a-zA-Z]{1,8}";

    private static final String DEFAULT_LANG = "en";

    /**
     * Calculate the language fragment from a RFC 3066 locale.
     *
     * @param localeString
     *            the locale string (en_US, en, es_AR, etc)
     * @return the language fragment (en, en, es)
     */
    public static String getLanguage(String localeString) {
        localeString = localeString == null || localeString.equals("default") ? "en" : localeString;
        String[] split = localeString.split("[_-]");
        if ((split.length > 0) && split[0].matches(LANG_PATTERN)) {
            return split[0];
        } else {
            return DEFAULT_LANG;
        }
    }
}
