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

package org.ourproject.kune.platf.server.content;

import java.util.Date;

import org.ourproject.kune.platf.client.errors.ContentNotFoundException;
import org.ourproject.kune.platf.client.errors.I18nNotFoundException;
import org.ourproject.kune.platf.client.errors.UserNotFoundException;
import org.ourproject.kune.platf.server.domain.Container;
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.Manager;

public interface ContentManager extends Manager<Content, Long> {

    public Content createContent(String title, User user, Container container);

    public Content save(User editor, Content descriptor, String content);

    public void rateContent(User rater, Long contentId, Double value) throws ContentNotFoundException;

    public Double getRateContent(User user, Content content);

    public Long getRateByUsers(Content content);

    public Double getRateAvg(Content content);

    public void setTitle(User user, Long contentId, String newTitle) throws ContentNotFoundException;

    public void setLanguage(User user, Long contentId, String languageCode) throws ContentNotFoundException,
            I18nNotFoundException;

    public void setPublishedOn(User user, Long contentId, Date publishedOn) throws ContentNotFoundException;

    public void setTags(User user, Long contentId, String tags) throws ContentNotFoundException;

    public void addAuthor(User user, Long contentId, String authorShortName) throws ContentNotFoundException,
            UserNotFoundException;

    public void removeAuthor(User user, Long contentId, String authorShortName) throws ContentNotFoundException,
            UserNotFoundException;

    public void delContent(User user, Long contentId) throws ContentNotFoundException;

}
