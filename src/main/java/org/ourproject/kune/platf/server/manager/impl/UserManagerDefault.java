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
import org.jivesoftware.smack.packet.RosterPacket.ItemType;
import org.ourproject.kune.chat.server.managers.ChatConnection;
import org.ourproject.kune.chat.server.managers.XmppManager;
import org.ourproject.kune.platf.server.manager.I18nCountryManager;
import org.ourproject.kune.platf.server.manager.I18nLanguageManager;
import org.ourproject.kune.platf.server.manager.UserManager;
import org.ourproject.kune.platf.server.properties.ChatProperties;
import org.waveprotocol.box.server.authentication.PasswordDigest;

import cc.kune.core.client.errors.I18nNotFoundException;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.domain.I18nCountry;
import cc.kune.domain.I18nLanguage;
import cc.kune.domain.User;
import cc.kune.domain.utils.UserBuddiesData;
import cc.kune.wave.server.CustomUserRegistrationServlet;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class UserManagerDefault extends DefaultManager<User, Long> implements UserManager {
    private final I18nCountryManager countryManager;
    private final User finder;
    private final I18nTranslationService i18n;
    private final I18nLanguageManager languageManager;
    private final ChatProperties properties;
    // private final PropertiesManager propManager;
    private final CustomUserRegistrationServlet waveUserRegister;
    private final XmppManager xmppManager;

    @Inject
    public UserManagerDefault(final Provider<EntityManager> provider, final User finder,
            final I18nLanguageManager languageManager, final I18nCountryManager countryManager,
            final XmppManager xmppManager, final ChatProperties properties, final I18nTranslationService i18n,
            final CustomUserRegistrationServlet waveUserRegister) {
        super(provider, User.class);
        this.finder = finder;
        this.languageManager = languageManager;
        this.countryManager = countryManager;
        this.xmppManager = xmppManager;
        this.properties = properties;
        this.i18n = i18n;
        this.waveUserRegister = waveUserRegister;
    }

    @Override
    public User createUser(final String shortName, final String longName, final String email, final String passwd,
            final String langCode, final String countryCode, final String timezone) throws I18nNotFoundException {
        I18nLanguage language;
        I18nCountry country;
        TimeZone tz;
        try {
            language = languageManager.findByCode(langCode);
            country = countryManager.findByCode(countryCode);
            tz = TimeZone.getTimeZone(timezone);
        } catch (final NoResultException e) {
            throw new I18nNotFoundException();
        }
        final PasswordDigest passwdDigest = new PasswordDigest(passwd.toCharArray());

        waveUserRegister.tryCreateUser(shortName, passwdDigest);
        // if (userPropGroup == null) {
        // userPropGroup = propGroupManager.find(User.PROPS_ID);
        // }
        // final Properties userProp = new Properties(userPropGroup);
        // propManager.persist(userProp);
        final User user = new User(shortName, longName, email, passwd, passwdDigest.getDigest(),
                passwdDigest.getSalt(), language, country, tz);
        return user;

    }

    @Override
    public User find(final Long userId) {
        return userId != null ? super.find(userId) : User.UNKNOWN_USER;
    }

    @Override
    public User findByShortname(final String shortName) {
        return finder.getByShortName(shortName);
    }

    public User findByShortName(final String shortName) {
        return finder.getByShortName(shortName);
    }

    public List<User> getAll() {
        return finder.getAll();
    }

    @Override
    @Deprecated
    public UserBuddiesData getUserBuddies(final String shortName) {
        // XEP-133 get roster by admin part is not implemented in openfire
        // also access to the openfire db is not easy with hibernate (the use of
        // two db at the same time). This compromise solution is server
        // independent.
        // In the future cache this.
        final String domain = "@" + properties.getDomain();
        final UserBuddiesData buddiesData = new UserBuddiesData();

        final User user = finder.getByShortName(shortName);
        final ChatConnection connection = xmppManager.login(user.getShortName() + domain, user.getPassword(), "kserver");
        final Collection<RosterEntry> roster = xmppManager.getRoster(connection);
        xmppManager.disconnect(connection);
        for (final RosterEntry entry : roster) {
            if (entry.getType().equals(ItemType.both)) {
                // only show buddies with subscription 'both'
                final int index = entry.getUser().indexOf(domain);
                if (index > 0) {
                    // local user
                    try {
                        final User buddie = finder.getByShortName(entry.getUser().substring(0, index));
                        buddiesData.getBuddies().add(buddie);
                    } catch (final NoResultException e) {
                        // No existent buddie, skip
                    }
                } else {
                    // ext user (only count)
                    buddiesData.setOtherExtBuddies(buddiesData.getOtherExtBuddies() + 1);
                }
            }
        }
        return buddiesData;
    }

    @Override
    public User login(final String nickOrEmail, final String passwd) {
        User user;
        try {
            user = finder.getByShortName(nickOrEmail);
        } catch (final NoResultException e) {
            try {
                user = finder.getByEmail(nickOrEmail);
            } catch (final NoResultException e2) {
                return null;
            }
        }
        if (user.getPassword().equals(passwd)) {
            if (user.getLastLogin() == null) {
                xmppManager.sendMessage(
                        user.getShortName(),
                        i18n.t("This is the chat window. \n"
                                + "\n"
                                + "Here you can communicate with other users of this site but also with other users with compatible accounts (like gmail accounts). \n"
                                + "\n" + "Just add some buddie and start to chat."));
            }
            user.setLastLogin(System.currentTimeMillis());
            return user;
        } else {
            return null;
        }
    }

    @Override
    public SearchResult<User> search(final String search) {
        return this.search(search, null, null);
    }

    @Override
    public SearchResult<User> search(final String search, final Integer firstResult, final Integer maxResults) {
        final MultiFieldQueryParser parser = new MultiFieldQueryParser(new String[] { "name", "shortName" },
                new StandardAnalyzer());
        Query query;
        try {
            query = parser.parse(search);
        } catch (final ParseException e) {
            throw new ServerManagerException("Error parsing search", e);
        }
        return super.search(query, firstResult, maxResults);
    }

}
