package cc.kune.core.server.utils;

import cc.kune.core.server.properties.KuneBasicProperties;
import cc.kune.core.shared.utils.SharedFileDownloadUtils;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The Class AbsoluteFileDownloadUtils get url of avatars etc, with absolute
 * urls (with domain, etc).
 */
@Singleton
public class AbsoluteFileDownloadUtils extends SharedFileDownloadUtils {

  @Inject
  public AbsoluteFileDownloadUtils(final KuneBasicProperties properties) {
    super(properties.getSiteUrl());
  }
}
