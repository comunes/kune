package cc.kune.core.client.i18n;

import cc.kune.common.client.utils.TextUtils;

/**
 * Copied some methods from org.apache.commons.lang.WordUtils
 * 
 */
public class I18nUtils {

  public static String convertMethodName(final String name) {
    String result = name;
    result = result.replaceAll("\\[%d\\]", "N");
    result = result.replaceAll("\\[%s\\]", "Param");
    final char[] delimiters = { ' ', '.', '?', ',', ';', '&', '(', ')', '"', '$', '!', '/', '\'', '-',
        '%', ':', '{', '}', '[', ']', '©', '«', '»' };
    result = TextUtils.capitalizeFully(result, delimiters);
    result = result.replaceAll("[ \\.\\?,;&\\(\\)\"\\$!\\/\\'\\-%:{}\\[\\]]", "");
    return TextUtils.abbreviate(
        new StringBuffer(result.length()).append(Character.toLowerCase(result.charAt(0))).append(
            result.substring(1)).toString(), 0, 100, "");

  }

}