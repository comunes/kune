/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.workspace.client.cxt;

import java.util.Date;
import java.util.List;

import cc.kune.core.shared.dto.AccessListsDTO;
import cc.kune.core.shared.dto.I18nLanguageDTO;
import cc.kune.core.shared.dto.UserSimpleDTO;

public interface ContextPropEditorView {

    void attach();

    void detach();

    void removeAccessListComponent();

    void removeAuthorsComponent();

    void removeLangComponent();

    void removePublishedOnComponent();

    void removeTagsComponent();

    void reset();

    void setAccessLists(AccessListsDTO accessLists);

    void setAuthors(List<UserSimpleDTO> authors);

    void setLanguage(I18nLanguageDTO language);

    void setPublishedOn(Date publishedOn, String dateFormat);

    void setTags(String tags);

}
