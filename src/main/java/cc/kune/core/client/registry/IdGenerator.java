package cc.kune.core.client.registry;

import cc.kune.common.client.utils.TextUtils;

import com.google.gxp.com.google.common.base.Nullable;

public class IdGenerator {
  protected static final String SEPARATOR = "|";

  /**
   * Generates a id concatenating two strings
   * 
   * @param one
   *          String
   * @param two
   *          String
   * @return
   */
  public static String generate(@Nullable final String one, @Nullable final String two) {
    if (TextUtils.empty(one)) {
      return TextUtils.empty(two) ? "" : two;
    } else {
      return TextUtils.empty(two) ? one : one + SEPARATOR + two;
    }
  }
}
