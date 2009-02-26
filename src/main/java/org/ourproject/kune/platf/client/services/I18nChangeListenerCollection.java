/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.ourproject.kune.platf.client.services;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A helper class for implementers of the SourcesI18nChangeEvents interface.
 * This subclass of {@link ArrayList} assumes that all objects added to it will
 * be of type {@link org.ourproject.kune.platf.client.services.I18nChangeListener}.
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
