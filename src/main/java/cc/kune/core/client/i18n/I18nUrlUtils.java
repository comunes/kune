package cc.kune.core.client.i18n;

import cc.kune.common.client.utils.TextUtils;
import cc.kune.common.client.utils.Url;
import cc.kune.common.client.utils.UrlParam;

public class I18nUrlUtils {

  private static void addLangParam(final String lang, final Url changedUrl) {
    changedUrl.add(new UrlParam("locale", lang));
  }

  public static String changeLang(final String url, final String lang) {
    final String[] hashSplitted = url.split("#");
    final String hash = hashSplitted.length > 1 ? hashSplitted[1] : "";
    String query = hashSplitted.length >= 1 ? hashSplitted[0] : (url.equals("#") ? "" : url);
    query = query.startsWith("?") ? query.substring(1) : query;
    final String[] params = query.split("&");
    final Url changedUrl = new Url("");
    if (!query.contains("locale")) {
      addLangParam(lang, changedUrl);
    }
    for (final String param : params) {
      if (TextUtils.notEmpty(param)) {
        final String[] pair = param.split("=");
        if (pair[0].equals("locale")) {
          addLangParam(lang, changedUrl);
        } else {
          changedUrl.add(new UrlParam(pair[0], pair[1]));
        }
      }
    }
    return changedUrl.toString() + (url.contains("#") ? "#" + hash : "");
  }

}
