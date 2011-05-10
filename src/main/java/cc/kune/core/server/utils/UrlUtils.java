package cc.kune.core.server.utils;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UrlUtils {

  private static final Log LOG = LogFactory.getLog(UrlUtils.class);

  public static URL of(final String urlString) {
    URL url = null;
    try {
      url = new URL(urlString);
      return url;
    } catch (final MalformedURLException e) {
      LOG.error("Error creating url with" + urlString);
      e.printStackTrace();
    }
    return url;
  }

}
