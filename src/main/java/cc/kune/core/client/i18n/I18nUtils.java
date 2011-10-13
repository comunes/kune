package cc.kune.core.client.i18n;

import cc.kune.common.client.errors.UIException;
import cc.kune.common.client.log.Log;
import cc.kune.common.client.utils.TextUtils;

/**
 * Copied some methods from org.apache.commons.lang.WordUtils
 * 
 */
public class I18nUtils {

  public static String convertMethodName(final String name) {
    String result = name;
    result = result.replaceAll("\\\n", " ");
    result = result.replaceAll("\\[%d\\]", "N");
    result = result.replaceAll("\\[%s\\]", "Param");
    final char[] delimiters = { ' ', '.', '?', ',', ';', '&', '(', ')', '"', '$', '!', '/', '\'', '-',
        '%', ':', '{', '}', '[', ']', '©', '«', '»' };
    result = TextUtils.capitalizeFully(result, delimiters);
    // result = result.replaceAll("[ \\.\\?,;&\\(\\)\"\\$!\\/\\'\\-%:{}\\[\\]]",
    // "");
    result = result.replaceAll("[^a-zA-Z0-9]", "");
    if (result.isEmpty()) {
      return "";
    }
    try {
      return TextUtils.abbreviate(
          new StringBuffer(result.length()).append(Character.toLowerCase(result.charAt(0))).append(
              result.substring(1)).toString(), 0, 100, "");
    } catch (final Exception e) {
      final String message = "Error trying to get i18n func-name of: " + name;
      Log.error(message);
      throw new UIException(message);
    }

  }

}