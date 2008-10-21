/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.platf.client.rpc;

import org.ourproject.kune.platf.client.services.KuneErrorHandler;

import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class AsyncCallbackSimple<T> implements AsyncCallback<T> {

    private static KuneErrorHandler errorHandler;

    public static void init(KuneErrorHandler kuneErrorHandler) {
        errorHandler = kuneErrorHandler;
    }

    public void onFailure(final Throwable caught) {
        errorHandler.process(caught);
    }

}
