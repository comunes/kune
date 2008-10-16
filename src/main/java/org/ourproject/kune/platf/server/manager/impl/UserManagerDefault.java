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

package org.ourproject.kune.platf.server.manager.impl;

import java.util.Collection;
import java.util.List;
import java.util.TimeZone;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.Query;
import org.jivesoftware.smack.RosterEntry;
import org.ourproject.kune.chat.server.managers.ChatConnection;
import org.ourproject.kune.chat.server.managers.XmppManager;
import org.ourproject.kune.platf.client.errors.I18nNotFoundException;
import org.ourproject.kune.platf.server.domain.I18nCountry;
import org.ourproject.kune.platf.server.domain.I18nLanguage;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.I18nCountryManager;
import org.ourproject.kune.platf.server.manager.I18nLanguageManager;
import org.ourproject.kune.platf.server.manager.UserManager;
import org.ourproject.kune.platf.server.properties.ChatProperties;
import org.ourproject.kune.platf.server.sn.UserBuddiesData;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class UserManagerDefault extends DefaultManager<User, Long> implements UserManager {
    private final User finder;
    private final I18nCountryManager countryManager;
    private final I18nLanguageManager languageManager;
    private final XmppManager xmppManager;
    private final ChatProperties properties;

    @Inject
    public UserManagerDefault(final Provider<EntityManager> provider, final User finder,
            final I18nLanguageManager languageManager, final I18nCountryManager countryManager,
            final XmppManager xmppManager, ChatProperties properties) {
        super(provider, User.class);
        this.finder = finder;
        this.languageManager = languageManager;
        this.countryManager = countryManager;
        this.xmppManager = xmppManager;
        this.properties = properties;
    }

    public User createUser(final String shortName, final String longName, final String email, final String passwd,
            final String langCode, final String countryCode, final String timezone) throws I18nNotFoundException {
        try {
            I18nLanguage language = languageManager.findByCode(langCode);
            I18nCountry country = countryManager.findByCode(countryCode);
            TimeZone tz = TimeZone.getTimeZone(timezone);
            User user = new User(shortName, longName, email, passwd, language, country, tz);
            return user;
        } catch (NoResultException e) {
            throw new I18nNotFoundException();
        }
    }

    @Override
    public User find(final Long userId) {
        return userId != null ? super.find(userId) : User.UNKNOWN_USER;
    }

    public List<User> getAll() {
        return finder.getAll();
    }

    public User getByShortName(final String shortName) {
        return finder.getByShortName(shortName);
    }

    public UserBuddiesData getUserBuddies(final String shortName) {
        // XEP-133 get roster by admin part is not implemented in openfire
        // also access to the openfire db is not easy with hibernate (the use of
        // two db at the same time). This compromise solution is server
        // independent.
        // In the future cache this.
        String domain = "@" + properties.getDomain();
        UserBuddiesData buddiesData = new UserBuddiesData();
        User user = finder.getByShortName(shortName);
        ChatConnection connection = xmppManager.login(user.getShortName() + domain, user.getPassword(), "kserver");
        Collection<RosterEntry> roster = xmppManager.getRoster(connection);
        xmppManager.disconnect(connection);
        for (RosterEntry entry : roster) {
            switch (entry.getType()) {
            case both:
                // only show buddies with subscription 'both'
                int index = entry.getUser().indexOf(domain);
                if (index > 0) {
                    // local user
                    User buddie = finder.getByShortName(entry.getUser().substring(0, index));
                    if (buddie != null) {
                        buddiesData.getBuddies().add(buddie);
                    }
                } else {
                    // ext user (only count)
                    buddiesData.setOtherExternalBuddies(buddiesData.getOtherExternalBuddies() + 1);
                }
            }
        }
        return buddiesData;
    }

    public User login(final String nickOrEmail, final String passwd) {
        User user;
        try {
            user = finder.getByShortName(nickOrEmail);
        } catch (NoResultException e) {
            try {
                user = finder.getByEmail(nickOrEmail);
            } catch (NoResultException e2) {
                return null;
            }
        }
        if (user.getPassword().equals(passwd)) {
            return user;
        } else {
            return null;
        }
    }

    public SearchResult<User> search(final String search) {
        return this.search(search, null, null);
    }

    public SearchResult<User> search(final String search, final Integer firstResult, final Integer maxResults) {
        MultiFieldQueryParser parser = new MultiFieldQueryParser(new String[] { "name", "shortName" },
                new StandardAnalyzer());
        Query query;
        try {
            query = parser.parse(search);
        } catch (ParseException e) {
            throw new RuntimeException("Error parsing search");
        }
        return super.search(query, firstResult, maxResults);
    }

}
