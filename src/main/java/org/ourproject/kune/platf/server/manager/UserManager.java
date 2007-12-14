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

package org.ourproject.kune.platf.server.manager;

import org.ourproject.kune.platf.client.errors.I18nNotFoundException;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.impl.DefaultManager.SearchResult;

public interface UserManager {
    User login(String nickOrEmail, String passwd);

    /**
     * CreateUser new method with language country and timezone params
     * 
     * @param shortName
     * @param longName
     * @param email
     * @param passwd
     * @param timezone
     * @param country
     * @param language
     * @param timezone
     * @return User
     * @throws I18nNotFoundException
     */
    User createUser(String shortName, String longName, String email, String passwd, String language, String country,
            String timezone) throws I18nNotFoundException;

    /**
     * IMPORTANT: if userId == null, it returns User.NONE
     * 
     * @param userId
     * @return
     */
    User find(Long userId);

    SearchResult search(String search);

    SearchResult search(String search, Integer firstResult, Integer maxResults);

    void reIndex();

}
