package org.ourproject.kune.platf.server.properties;

public interface KuneProperties {
    public String DEFAULT_SITE_SHORT_NAME = "kune.default.site.shortName";

    public String get(String key);

    public String get(String key, String defaultValue);
}
