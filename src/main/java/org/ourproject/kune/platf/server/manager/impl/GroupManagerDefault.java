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

package org.ourproject.kune.platf.server.manager.impl;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.Query;
import org.ourproject.kune.platf.client.errors.AccessViolationException;
import org.ourproject.kune.platf.client.errors.EmailAddressInUseException;
import org.ourproject.kune.platf.client.errors.GroupNameInUseException;
import org.ourproject.kune.platf.client.errors.UserMustBeLoggedException;
import org.ourproject.kune.platf.server.domain.AccessLists;
import org.ourproject.kune.platf.server.domain.AdmissionType;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.GroupListMode;
import org.ourproject.kune.platf.server.domain.GroupType;
import org.ourproject.kune.platf.server.domain.License;
import org.ourproject.kune.platf.server.domain.SocialNetwork;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.GroupManager;
import org.ourproject.kune.platf.server.properties.DatabaseProperties;
import org.ourproject.kune.platf.server.properties.KuneProperties;
import org.ourproject.kune.platf.server.tool.ServerTool;
import org.ourproject.kune.platf.server.tool.ToolRegistry;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class GroupManagerDefault extends DefaultManager<Group, Long> implements GroupManager {

    private final Group finder;
    private final ToolRegistry registry;
    private final DatabaseProperties properties;
    private final License licenseFinder;
    private final KuneProperties kuneProperties;

    @Inject
    public GroupManagerDefault(final Provider<EntityManager> provider, final Group finder,
            final KuneProperties kuneProperties, final DatabaseProperties properties, final ToolRegistry registry,
            final License licenseFinder) {
        super(provider, Group.class);
        this.finder = finder;
        this.kuneProperties = kuneProperties;
        this.properties = properties;
        this.registry = registry;
        this.licenseFinder = licenseFinder;
    }

    public Group getDefaultGroup() {
        final String shortName = properties.getDefaultSiteShortName();
        return findByShortName(shortName);
    }

    public Group findByShortName(final String shortName) {
        return finder.findByShortName(shortName);
    }

    public List<Group> findAdminInGroups(final Long groupId) {
        return finder.findAdminInGroups(groupId);
    }

    public List<Group> findCollabInGroups(final Long groupId) {
        return finder.findCollabInGroups(groupId);
    }

    public Group createUserGroup(final User user) throws GroupNameInUseException, EmailAddressInUseException {
        String defaultSiteWorkspaceTheme = kuneProperties.get(KuneProperties.WS_THEMES_DEF);
        final String licenseDefId = properties.getDefaultLicense();
        final License licenseDef = licenseFinder.findByShortName(licenseDefId);
        final Group group = new Group(user.getShortName(), user.getName(), licenseDef, GroupType.PERSONAL);
        group.setAdmissionType(AdmissionType.Closed);
        group.setWorkspaceTheme(defaultSiteWorkspaceTheme);
        user.setUserGroup(group);
        initSocialNetwork(group, group);
        initGroup(user, group);
        try {
            super.persist(user, User.class);
        } catch (final EntityExistsException e) {
            throw new EmailAddressInUseException();
        }
        return group;
    }

    public Group createGroup(final Group group, final User user) throws GroupNameInUseException,
            UserMustBeLoggedException {
        String defaultSiteWorkspaceTheme = kuneProperties.get(KuneProperties.WS_THEMES_DEF);
        if (User.isKnownUser(user)) {
            // FIXME: A better way to do this license part?
            String licName = group.getDefaultLicense().getShortName();
            License license = licenseFinder.findByShortName(licName);
            group.setDefaultLicense(license);
            group.setWorkspaceTheme(defaultSiteWorkspaceTheme);
            initSocialNetwork(group, user.getUserGroup());
            initGroup(user, group);
            return group;
        } else {
            // i18n
            throw new UserMustBeLoggedException();
        }

    }

    public void changeWsTheme(final User user, final Group group, final String theme) throws AccessViolationException {
        if (group.getSocialNetwork().isAdmin(user.getUserGroup())) {
            group.setWorkspaceTheme(theme);
            // Check themes...
        } else {
            throw new AccessViolationException();
        }
    }

    public Group getGroupOfUserWithId(final Long userId) {
        return userId != null ? find(User.class, userId).getUserGroup() : null;
    }

    public List<Group> search(final String search) throws ParseException {
        return this.search(search, null, null);
    }

    public List<Group> search(final String search, final Integer firstResult, final Integer maxResults)
            throws ParseException {
        MultiFieldQueryParser parser = new MultiFieldQueryParser(
                new String[] { "longName", "shortName", "publicDesc" }, new StandardAnalyzer());
        Query query = parser.parse(search);
        return super.search(query, firstResult, maxResults);
    }

    private void initSocialNetwork(final Group group, final Group userGroup) {
        final SocialNetwork network = group.getSocialNetwork();
        final AccessLists lists = network.getAccessLists();
        lists.getEditors().setMode(GroupListMode.NOBODY);
        lists.getViewers().setMode(GroupListMode.EVERYONE);
        network.addAdmin(userGroup);
    }

    private void initGroup(final User user, final Group group) throws GroupNameInUseException {
        for (final ServerTool tool : registry.all()) {
            tool.initGroup(user, group);
        }
        try {
            persist(group);
        } catch (final EntityExistsException e) {
            throw new GroupNameInUseException();
        }
    }

}
