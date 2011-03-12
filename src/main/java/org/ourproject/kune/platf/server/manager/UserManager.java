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
package org.ourproject.kune.platf.server.manager;

import org.ourproject.kune.platf.server.manager.impl.SearchResult;
import org.waveprotocol.box.server.authentication.PasswordDigest;

import cc.kune.core.client.errors.I18nNotFoundException;
import cc.kune.domain.User;
import cc.kune.domain.utils.UserBuddiesData;

public interface UserManager {
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

    void createWaveAccount(String shortName, PasswordDigest passwdDigest);

    /**
     * IMPORTANT: if userId == null, it returns User.UNKNOWN_USER
     * 
     * @param userId
     * @return
     */
    User find(Long userId);

    User findByShortname(String shortName);

    UserBuddiesData getUserBuddies(String shortName);

    User login(String nickOrEmail, String passwd);

    void reIndex();

    SearchResult<User> search(String search);

    SearchResult<User> search(String search, Integer firstResult, Integer maxResults);

}
