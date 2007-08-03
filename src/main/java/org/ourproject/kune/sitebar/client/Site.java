package org.ourproject.kune.sitebar.client;

import org.ourproject.kune.sitebar.client.msg.SiteMessage;

public class Site {
    public static SiteMessage message;

    public static void info(final String value) {
	message.info(value);
    }

    // TODO: etcétera
}
