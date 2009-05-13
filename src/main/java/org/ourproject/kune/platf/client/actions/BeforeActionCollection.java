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
package org.ourproject.kune.platf.client.actions;

import java.util.ArrayList;

/**
 * The Class BeforeActionCollection.
 */
public class BeforeActionCollection extends ArrayList<BeforeActionListener> {

    private static final long serialVersionUID = -1508664709628420137L;

    /**
     * Check before action listeners.
     * 
     * @return true, if all listener returns true
     */
    public boolean checkBeforeAction() {
        for (BeforeActionListener listener : this) {
            if (!listener.beforeAction()) {
                return false;
            }
        }
        return true;
    }

}
