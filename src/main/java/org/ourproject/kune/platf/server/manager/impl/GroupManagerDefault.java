
package org.ourproject.kune.platf.server.manager.impl;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

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
    private final User userFinder;

    @Inject
    public GroupManagerDefault(final Provider<EntityManager> provider, final Group finder, final User userFinder,
            final KuneProperties kuneProperties, final DatabaseProperties properties, final ToolRegistry registry,
            final License licenseFinder) {
        super(provider, Group.class);
        this.finder = finder;
        this.userFinder = userFinder;
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
        User userSameEmail = null;
        try {
            userSameEmail = userFinder.getByEmail(user.getEmail());
        } catch (NoResultException e) {
            // Ok, no more with this email
        }
        if (userSameEmail != null) {
            throw new EmailAddressInUseException();
        }
        group.setAdmissionType(AdmissionType.Closed);
        group.setWorkspaceTheme(defaultSiteWorkspaceTheme);
        user.setUserGroup(group);
        initSocialNetwork(group, group);
        try {
            initGroup(user, group);
            super.persist(user, User.class);
        } catch (final EntityExistsException e) {
            throw new GroupNameInUseException();
        }
        return group;
    }

    public Group createGroup(final Group group, final User user) throws GroupNameInUseException,
            UserMustBeLoggedException {
        String defaultSiteWorkspaceTheme = kuneProperties.get(KuneProperties.WS_THEMES_DEF);
        if (User.isKnownUser(user)) {
            if (group.getType().equals(GroupType.COMMUNITY)) {
                group.setAdmissionType(AdmissionType.Open);
            } else if (group.getType().equals(GroupType.ORGANIZATION)) {
                group.setAdmissionType(AdmissionType.Moderated);
            } else if (group.getType().equals(GroupType.PROJECT)) {
                group.setAdmissionType(AdmissionType.Moderated);
            } else if (group.getType().equals(GroupType.ORPHANED_PROJECT)) {
                group.setAdmissionType(AdmissionType.Open);
            }
            String licName = group.getDefaultLicense().getShortName();
            License license = licenseFinder.findByShortName(licName);
            group.setDefaultLicense(license);
            group.setWorkspaceTheme(defaultSiteWorkspaceTheme);
            initSocialNetwork(group, user.getUserGroup());
            initGroup(user, group);
            return group;
        } else {
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

    public SearchResult<Group> search(final String search) {
        return this.search(search, null, null);
    }

    public SearchResult<Group> search(final String search, final Integer firstResult, final Integer maxResults) {
        MultiFieldQueryParser parser = new MultiFieldQueryParser(
                new String[] { "longName", "shortName", "publicDesc" }, new StandardAnalyzer());
        Query query;
        try {
            query = parser.parse(search);
        } catch (ParseException e) {
            throw new RuntimeException("Error parsing search");
        }
        return super.search(query, firstResult, maxResults);
    }

    private void initSocialNetwork(final Group group, final Group userGroup) {
        final SocialNetwork network = group.getSocialNetwork();
        final AccessLists lists = network.getAccessLists();
        lists.getEditors().setMode(GroupListMode.NOBODY);
        lists.getViewers().setMode(GroupListMode.EVERYONE);
        if (!group.getType().equals(GroupType.ORPHANED_PROJECT)) {
            network.addAdmin(userGroup);
        }
    }

    private void initGroup(final User user, final Group group) throws GroupNameInUseException {
        try {
            persist(group);
        } catch (final EntityExistsException e) {
            throw new GroupNameInUseException();
        }
        for (final ServerTool tool : registry.all()) {
            tool.initGroup(user, group);
        }
    }

}
