package org.ourproject.kune.workspace.client.i18n;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A helper class for implementers of the SourcesI18nChangeEvents interface.
 * This subclass of {@link ArrayList} assumes that all objects added to it will
 * be of type {@link com.google.gwt.user.client.ui.I18nChangeListener}.
 */
public class I18nChangeListenerCollection extends ArrayList<I18nChangeListener> {

    private static final long serialVersionUID = 1L;

    /**
     * Fires a locale change event to all listeners.
     * 
     */
    public void fireI18nLanguageChange() {
        for (Iterator<I18nChangeListener> it = iterator(); it.hasNext();) {
            I18nChangeListener listener = it.next();
            listener.onI18nLanguageChange();
        }
    }
}
