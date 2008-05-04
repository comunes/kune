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

import java.util.HashMap;

import org.ourproject.kune.platf.client.dto.I18nLanguageDTO;
import org.ourproject.kune.platf.client.errors.DefaultException;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface I18nService extends RemoteService {

    public class App {
	private static I18nServiceAsync ourInstance = null;

	public static synchronized I18nServiceAsync getInstance() {
	    if (ourInstance == null) {
		ourInstance = (I18nServiceAsync) GWT.create(I18nService.class);
		((ServiceDefTarget) ourInstance).setServiceEntryPoint(GWT.getModuleBaseURL() + "I18nService");
	    }
	    return ourInstance;
	}

    }

    I18nLanguageDTO getInitialLanguage(String localeParam);

    HashMap<String, String> getLexicon(String language);

    String getTranslation(String userHash, String language, String text);

    String setTranslation(String userHash, String id, String translation) throws DefaultException;

}
