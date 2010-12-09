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
package cc.kune.core.client.rpcservices;

import java.util.HashMap;

import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.shared.dto.I18nLanguageDTO;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("I18nService")
public interface I18nService extends RemoteService {

    I18nLanguageDTO getInitialLanguage(String localeParam);

    HashMap<String, String> getLexicon(String language);

    String getTranslation(String userHash, String language, String text);

    String setTranslation(String userHash, String id, String translation) throws DefaultException;

}
