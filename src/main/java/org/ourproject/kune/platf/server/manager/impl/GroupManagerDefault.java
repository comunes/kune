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

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.Query;
import org.hibernate.exception.ConstraintViolationException;
import org.ourproject.kune.platf.server.manager.GroupManager;
import org.ourproject.kune.platf.server.manager.LicenseManager;
import org.ourproject.kune.platf.server.properties.DatabaseProperties;
import org.ourproject.kune.platf.server.properties.KuneProperties;
import org.ourproject.kune.platf.server.tool.ServerTool;
import org.ourproject.kune.platf.server.tool.ServerToolRegistry;

import cc.kune.core.client.errors.AccessViolationException;
import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.client.errors.EmailAddressInUseException;
import cc.kune.core.client.errors.GroupNameInUseException;
import cc.kune.core.client.errors.UserMustBeLoggedException;
import cc.kune.core.shared.domain.AdmissionType;
import cc.kune.core.shared.domain.GroupListMode;
import cc.kune.core.shared.dto.GroupType;
import cc.kune.domain.AccessLists;
import cc.kune.domain.Content;
import cc.kune.domain.Group;
import cc.kune.domain.License;
import cc.kune.domain.SocialNetwork;
import cc.kune.domain.ToolConfiguration;
import cc.kune.domain.User;
import cc.kune.domain.finders.GroupFinder;
import cc.kune.domain.finders.LicenseFinder;
import cc.kune.domain.finders.UserFinder;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class GroupManagerDefault extends DefaultManager<Group, Long> implements GroupManager {

    private final GroupFinder finder;
    private final KuneProperties kuneProperties;
    private final LicenseFinder licenseFinder;
    private final LicenseManager licenseManager;
    private final DatabaseProperties properties;
    private final ServerToolRegistry registry;
    private final ServerToolRegistry serverToolRegistry;
    private final UserFinder userFinder;

    @Inject
    public GroupManagerDefault(final Provider<EntityManager> provider, final GroupFinder finder,
            final UserFinder userFinder, final KuneProperties kuneProperties, final DatabaseProperties properties,
            final ServerToolRegistry registry, final LicenseManager licenseManager, final LicenseFinder licenseFinder,
            final ServerToolRegistry serverToolRegistry) {
        super(provider, Group.class);
        this.finder = finder;
        this.userFinder = userFinder;
        this.kuneProperties = kuneProperties;
        this.properties = properties;
        this.registry = registry;
        this.licenseManager = licenseManager;
        this.licenseFinder = licenseFinder;
        this.serverToolRegistry = serverToolRegistry;
    }

    @Override
    public void changeDefLicense(final User user, final Group group, final String licName) {
        final License license = licenseFinder.findByShortName(licName);
        if (license == null) {
            throw new ServerManagerException("Unknown license");
        }
        group.setDefaultLicense(license);
    }

    @Override
    public void changeWsTheme(final User user, final Group group, final String theme) throws AccessViolationException {
        // TODO: check theme
        group.setWorkspaceTheme(theme);
    }

    @Override
    public void clearGroupBackImage(final Group group) {
        group.setGroupBackImage(null);
    }

    @Override
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

    @Override
    public Group createUserGroup(final User user) throws GroupNameInUseException, EmailAddressInUseException {
        return createUserGroup(user, true);
    }

    @Override
    public Group createUserGroup(final User user, final boolean wantPersonalHomepage) throws GroupNameInUseException,
            EmailAddressInUseException {
        final String defaultSiteWorkspaceTheme = kuneProperties.get(KuneProperties.WS_THEMES_DEF);
        final License licenseDef = licenseManager.getDefLicense();
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
        } catch (final PersistenceException e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                throw new GroupNameInUseException();
            }
            throw e;
        }
        return userGroup;
    }

    @Override
    public List<Group> findAdminInGroups(final Long groupId) {
        return finder.findAdminInGroups(groupId);
    }

    @Override
    public Group findByShortName(final String shortName) {
        return finder.findByShortName(shortName);
    }

    @Override
    public List<Group> findCollabInGroups(final Long groupId) {
        return finder.findCollabInGroups(groupId);
    }

    @Override
    public List<String> findEnabledTools(final Long id) {
        return finder.findEnabledTools(id);
    }

    @Override
    public Group getGroupOfUserWithId(final Long userId) {
        return userId != null ? find(User.class, userId).getUserGroup() : null;
    }

    @Override
    public Group getSiteDefaultGroup() {
        final String shortName = properties.getDefaultSiteShortName();
        return findByShortName(shortName);
    }

    private void initGroup(final User user, final Group group, final Collection<String> toolsToEnable)
            throws GroupNameInUseException {
        try {
            persist(group);
        } catch (final IllegalStateException e) {
            e.printStackTrace();
        } catch (final PersistenceException e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                throw new GroupNameInUseException();
            }
            throw e;
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

    @Override
    public SearchResult<Group> search(final String search) {
        return this.search(search, null, null);
    }

    @Override
    public SearchResult<Group> search(final String search, final Integer firstResult, final Integer maxResults) {
        final MultiFieldQueryParser parser = new MultiFieldQueryParser(new String[] { "longName", "shortName",
                "publicDesc" }, new StandardAnalyzer());
        Query query;
        try {
            query = parser.parse(search);
        } catch (final ParseException e) {
            throw new ServerManagerException("Error parsing search");
        }
        return super.search(query, firstResult, maxResults);
    }

    @Override
    public void setDefaultContent(final String groupShortName, final Content content) {
        final Group group = findByShortName(groupShortName);
        group.setDefaultContent(content);
    }

    @Override
    public void setGroupBackImage(final Group group, final Content content) {
        if (content.getMimeType().isImage()) {
            group.setGroupBackImage(content);
        } else {
            throw new DefaultException("Trying to set not a image as group logo");
        }
    }

    @Override
    public void setToolEnabled(final User userLogged, final String groupShortName, final String tool,
            final boolean enabled) {
        final Group group = findByShortName(groupShortName);
        ToolConfiguration toolConfiguration = group.getToolConfiguration(tool);
        if (toolConfiguration == null) {
            toolConfiguration = serverToolRegistry.get(tool).initGroup(userLogged, group).getToolConfiguration(tool);
        }
        toolConfiguration.setEnabled(enabled);
    }

}
