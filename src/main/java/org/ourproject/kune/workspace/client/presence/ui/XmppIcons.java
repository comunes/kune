package org.ourproject.kune.workspace.client.presence.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;

public interface XmppIcons extends ImageBundle {

    public static class App {
	private static XmppIcons ourInstance = null;

	public static synchronized XmppIcons getInstance() {
	    if (ourInstance == null) {
		ourInstance = (XmppIcons) GWT.create(XmppIcons.class);
	    }
	    return ourInstance;
	}
    }

    /**
     * @gwt.resource away.png
     */
    AbstractImagePrototype away();

    /**
     * @gwt.resource busy.png
     */
    AbstractImagePrototype busy();

    /**
     * @gwt.resource message.png
     */
    AbstractImagePrototype message();

    /**
     * @gwt.resource invisible.png
     */
    AbstractImagePrototype invisible();

    /**
     * @gwt.resource xa.png
     */
    AbstractImagePrototype extendedAway();

    /**
     * @gwt.resource offline.png
     */
    AbstractImagePrototype offline();

    /**
     * @gwt.resource online.png
     */
    AbstractImagePrototype online();

}
