
package org.ourproject.kune.platf.server.properties;

public interface KuneProperties {
    public String DEFAULT_SITE_SHORT_NAME = "kune.default.site.shortName";
    public String SITE_DOMAIN = "kune.site.domain";
    public String CHAT_HTTP_BASE = "kune.chat.httpbase";
    public String CHAT_DOMAIN = "kune.chat.domain";
    public String CHAT_ROOM_HOST = "kune.chat.roomHost";
    public String WS_THEMES_DEF = "kune.wsthemes.default";
    public String WS_THEMES = "kune.wsthemes";

    public String get(String key);

    public String get(String key, String defaultValue);
}
