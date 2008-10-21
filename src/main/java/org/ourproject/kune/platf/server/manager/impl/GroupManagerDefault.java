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

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.Query;
import org.ourproject.kune.platf.client.dto.GroupType;
import org.ourproject.kune.platf.client.errors.AccessViolationException;
import org.ourproject.kune.platf.client.errors.DefaultException;
import org.ourproject.kune.platf.client.errors.EmailAddressInUseException;
import org.ourproject.kune.platf.client.errors.GroupNameInUseException;
import org.ourproject.kune.platf.client.errors.UserMustBeLoggedException;
import org.ourproject.kune.platf.server.domain.AccessLists;
import org.ourproject.kune.platf.server.domain.AdmissionType;
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.GroupListMode;
import org.ourproject.kune.platf.server.domain.License;
import org.ourproject.kune.platf.server.domain.SocialNetwork;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.GroupManager;
import org.ourproject.kune.platf.server.properties.DatabaseProperties;
import org.ourproject.kune.platf.server.properties.KuneProperties;
import org.ourproject.kune.platf.server.tool.ServerTool;
import org.ourproject.kune.platf.server.tool.ServerToolRegistry;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class GroupManagerDefault extends DefaultManager<Group, Long> implements GroupManager {

    private final Group finder;
    private final ServerToolRegistry registry;
    private final DatabaseProperties properties;
    private final License licenseFinder;
    private final KuneProperties kuneProperties;
    private final User userFinder;
    private final ServerToolRegistry serverToolRegistry;

    @Inject
    public GroupManagerDefault(final Provider<EntityManager> provider, final Group finder, final User userFinder,
            final KuneProperties kuneProperties, final DatabaseProperties properties,
            final ServerToolRegistry registry, final License licenseFinder, ServerToolRegistry serverToolRegistry) {
        super(provider, Group.class);
        this.finder = finder;
        this.userFinder = userFinder;
        this.kuneProperties = kuneProperties;
        this.properties = properties;
        this.registry = registry;
        this.licenseFinder = licenseFinder;
        this.serverToolRegistry = serverToolRegistry;
    }

    public void changeWsTheme(final User user, final Group group, final String theme) throws AccessViolationException {
        // TODO: check theme
        group.setWorkspaceTheme(theme);
    }

    public Group createGroup(final Group group, final User user) throws GroupNameInUseException,
            UserMustBeLoggedException {
        final String defaultSiteWorkspaceTheme = kuneProperties.get(KuneProperties.WS_THEMES_DEF);
        if (User.isKnownUser(user)) {
            if (group.getGroupType().equals(GroupType.COMMUNITY)) {
                group.setAdmissionType(AdmissionType.Open);
            } else if (group.getGroupType().equals(GroupType.ORGANIZATION)) {
                group.setAdmissionType(AdmissionType.Moderated);
            } else if (group.getGroupType().equals(GroupType.PROJECT)) {
                group.setAdmissionType(AdmissionType.Moderated);
            } else if (group.getGroupType().equals(GroupType.ORPHANED_PROJECT)) {
                group.setAdmissionType(AdmissionType.Open);
            }
            final String licName = group.getDefaultLicense().getShortName();
            final License license = licenseFinder.findByShortName(licName);
            group.setDefaultLicense(license);
            group.setWorkspaceTheme(defaultSiteWorkspaceTheme);
            initSocialNetwork(group, user.getUserGroup());
            initGroup(user, group, serverToolRegistry.getToolsForGroupsKeys());
            return group;
        } else {
            throw new UserMustBeLoggedException();
        }
    }

    public Group createUserGroup(final User user) throws GroupNameInUseException, EmailAddressInUseException {
        return createUserGroup(user, true);
    }

    public Group createUserGroup(User user, boolean wantPersonalHomepage) throws GroupNameInUseException,
            EmailAddressInUseException {
        final String defaultSiteWorkspaceTheme = kuneProperties.get(KuneProperties.WS_THEMES_DEF);
        final String licenseDefId = properties.getDefaultLicense();
        final License licenseDef = licenseFinder.findByShortName(licenseDefId);
        final Group userGroup = new Group(user.getShortName(), user.getName(), licenseDef, GroupType.PERSONAL);
        User userSameEmail = null;
        try {
            userSameEmail = userFinder.getByEmail(user.getEmail());
        } catch (final NoResultException e) {
            // Ok, no more with this email
        }
        if (userSameEmail != null) {
            throw new EmailAddressInUseException();
        }
        userGroup.setAdmissionType(AdmissionType.Closed);
        userGroup.setWorkspaceTheme(defaultSiteWorkspaceTheme);
        userGroup.setDefaultContent(null);
        user.setUserGroup(userGroup);
        initSocialNetwork(userGroup, userGroup);
        try {
            initGroup(user, userGroup, wantPersonalHomepage ? serverToolRegistry.getToolsForUserKeys()
                    : ServerToolRegistry.emptyToolList);
            super.persist(user, User.class);
        } catch (final EntityExistsException e) {
            throw new GroupNameInUseException();
        }
        return userGroup;
    }

    public List<Group> findAdminInGroups(final Long groupId) {
        return finder.findAdminInGroups(groupId);
    }

    public Group findByShortName(final String shortName) {
        return finder.findByShortName(shortName);
    }

    public List<Group> findCollabInGroups(final Long groupId) {
        return finder.findCollabInGroups(groupId);
    }

    public List<String> findEnabledTools(Long id) {
        return finder.findEnabledTools(id);
    }

    public Group getDefaultGroup() {
        final String shortName = properties.getDefaultSiteShortName();
        return findByShortName(shortName);
    }

    public Group getGroupOfUserWithId(final Long userId) {
        return userId != null ? find(User.class, userId).getUserGroup() : null;
    }

    public SearchResult<Group> search(final String search) {
        return this.search(search, null, null);
    }

    public SearchResult<Group> search(final String search, final Integer firstResult, final Integer maxResults) {
        final MultiFieldQueryParser parser = new MultiFieldQueryParser(new String[] { "longName", "shortName",
                "publicDesc" }, new StandardAnalyzer());
        Query query;
        try {
            query = parser.parse(search);
        } catch (final ParseException e) {
            throw new RuntimeException("Error parsing search");
        }
        return super.search(query, firstResult, maxResults);
    }

    public void setDefaultContent(final String groupShortName, final Content content) {
        final Group group = findByShortName(groupShortName);
        group.setDefaultContent(content);
    }

    public void setGroupLogo(final Group group, final Content content) {
        if (content.getMimeType().getType().equals("image")) {
            group.setGroupFullLogo(content);
        } else {
            new DefaultException("Trying to set not a image as group logo");
        }
    }

    private void initGroup(final User user, final Group group, Collection<String> toolsToEnable)
            throws GroupNameInUseException {
        try {
            persist(group);
        } catch (final EntityExistsException e) {
            throw new GroupNameInUseException();
        }
        for (final ServerTool tool : registry.all()) {
            if (toolsToEnable.contains(tool.getName())) {
                tool.initGroup(user, group);
            }
        }
    }

    private void initSocialNetwork(final Group group, final Group userGroup) {
        final SocialNetwork network = group.getSocialNetwork();
        final AccessLists lists = network.getAccessLists();
        lists.getEditors().setMode(GroupListMode.NOBODY);
        lists.getViewers().setMode(GroupListMode.EVERYONE);
        if (!group.getGroupType().equals(GroupType.ORPHANED_PROJECT)) {
            network.addAdmin(userGroup);
        }
    }

}
