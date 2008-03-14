/*
 *
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.platf.client.rpc;

import java.util.HashMap;

import org.ourproject.kune.platf.client.dto.I18nLanguageDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface I18nServiceAsync {

    void getLexicon(String language, AsyncCallback<HashMap<String, String>> callback);

    void getTranslation(String userHash, String language, String text, AsyncCallback<String> callback);

    void setTranslation(String userHash, String id, String translation, AsyncCallback<?> asyncCallback);

    void getInitialLanguage(String localeParam, AsyncCallback<I18nLanguageDTO> callback);

}
