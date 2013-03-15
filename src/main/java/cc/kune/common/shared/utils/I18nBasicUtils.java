package cc.kune.common.shared.utils;

/**
 * The Class I18nBasicUtils. Some code from Apache Wave SessionLocale.java. FIXME use SessionLocale in the future
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

    /**
     * Java locale normalize (uses _ and uppercase countries).
     *
     * @param localeString the locale string
     * @return the string
     */
    public static String javaLocaleNormalize(String localeString) {
        String[] split = localeString.split("[_-]");
        String lang;
        if ((split.length > 0) && split[0].matches(LANG_PATTERN)) {
            lang = split[0];
        } else {
            lang = DEFAULT_LANG;
        }
        if (split.length > 1) {
            return lang + "_" + split[1].toUpperCase();
        } else {
            return lang;
        }
    }
}
