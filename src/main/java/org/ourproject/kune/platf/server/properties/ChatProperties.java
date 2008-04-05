package org.ourproject.kune.platf.server.properties;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * sintactic sugar!
 */
@Singleton
public class ChatProperties {
    private final KuneProperties properties;

    @Inject
    public ChatProperties(final KuneProperties properties) {
	this.properties = properties;
    }

    public String getHttpBase() {
	return properties.get(KuneProperties.CHAT_HTTP_BASE);
    }

    public String getDomain() {
	return properties.get(KuneProperties.CHAT_DOMAIN);
    }

    public String getRoomHost() {
	return properties.get(KuneProperties.CHAT_ROOM_HOST);
    }

}
