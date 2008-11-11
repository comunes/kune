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
package org.ourproject.kune.docs.client.ctx.admin;

import java.util.Date;
import java.util.List;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.AccessListsDTO;
import org.ourproject.kune.platf.client.dto.I18nLanguageDTO;
import org.ourproject.kune.platf.client.dto.UserSimpleDTO;

public interface DocContextPropEditorView extends View {

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

    void setPublishedOn(Date publishedOn);

    void setTags(String tags);

}
