package org.ourproject.kune.sitebar.client;

import org.ourproject.kune.sitebar.client.msg.SiteMessage;

public class Site {
    public static SiteMessage message;

    public static void info(final String value) {
	message.setValue(value, SiteMessage.INFO);
    }

    public static void important(final String value) {
	message.setValue(value, SiteMessage.IMP);
    }

    public static void veryImportant(final String value) {
	message.setValue(value, SiteMessage.VERYIMP);
    }

    public static void error(final String value) {
	message.setValue(value, SiteMessage.ERROR);
    }

}
