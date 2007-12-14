package org.ourproject.kune.workspace.client.i18n;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A helper class for implementers of the SourcesI18nChangeEvents interface.
 * This subclass of {@link ArrayList} assumes that all objects added to it will
 * be of type {@link com.google.gwt.user.client.ui.I18nChangeListener}.
 */
public class I18nChangeListenerCollection extends ArrayList {

    private static final long serialVersionUID = 1L;

    /**
     * Fires a i18n change event to all listeners.
     * 
     */
    public void fireI18nLanguageChange() {
        for (Iterator it = iterator(); it.hasNext();) {
            I18nChangeListener listener = (I18nChangeListener) it.next();
            listener.onI18nLanguageChange();
        }
    }
}
