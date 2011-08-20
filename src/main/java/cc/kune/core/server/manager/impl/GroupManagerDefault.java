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
package cc.kune.core.server.manager.impl;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.Query;
import org.hibernate.exception.ConstraintViolationException;

import cc.kune.core.client.errors.AccessViolationException;
import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.client.errors.EmailAddressInUseException;
import cc.kune.core.client.errors.GroupLongNameInUseException;
import cc.kune.core.client.errors.GroupShortNameInUseException;
import cc.kune.core.client.errors.UserMustBeLoggedException;
import cc.kune.core.server.manager.FileManager;
import cc.kune.core.server.manager.GroupManager;
import cc.kune.core.server.manager.LicenseManager;
import cc.kune.core.server.manager.file.FileUtils;
import cc.kune.core.server.properties.KuneBasicProperties;
import cc.kune.core.server.properties.KuneProperties;
import cc.kune.core.server.tool.ServerTool;
import cc.kune.core.server.tool.ServerToolRegistry;
import cc.kune.core.shared.SearcherConstants;
import cc.kune.core.shared.domain.AdmissionType;
import cc.kune.core.shared.domain.GroupListMode;
import cc.kune.core.shared.domain.SocialNetworkVisibility;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.GroupType;
import cc.kune.core.shared.i18n.I18nTranslationService;
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

  private final FileManager fileManager;
  private final GroupFinder finder;
  private final I18nTranslationService i18n;
  private final KuneProperties kuneProperties;
  private final LicenseFinder licenseFinder;
  private final LicenseManager licenseManager;
  private final KuneBasicProperties properties;
  private final ServerToolRegistry registry;
  private final ServerToolRegistry serverToolRegistry;
  private final UserFinder userFinder;

  @Inject
  public GroupManagerDefault(final Provider<EntityManager> provider, final GroupFinder finder,
      final UserFinder userFinder, final KuneProperties kuneProperties,
      final KuneBasicProperties properties, final ServerToolRegistry registry,
      final LicenseManager licenseManager, final LicenseFinder licenseFinder,
      final FileManager fileManager, final ServerToolRegistry serverToolRegistry,
      final I18nTranslationService i18n) {
    super(provider, Group.class);
    this.finder = finder;
    this.userFinder = userFinder;
    this.kuneProperties = kuneProperties;
    this.properties = properties;
    this.registry = registry;
    this.licenseManager = licenseManager;
    this.licenseFinder = licenseFinder;
    this.fileManager = fileManager;
    this.serverToolRegistry = serverToolRegistry;
    this.i18n = i18n;
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
  public void changeWsTheme(final User user, final Group group, final String theme)
      throws AccessViolationException {
    // TODO: check theme
    group.setWorkspaceTheme(theme);
  }

  @Override
  public void checkIfLongNameAreInUse(final String longName) {
    if (finder.countByLongName(longName) != 0) {
      throw new GroupLongNameInUseException();
    }
  }

  @Override
  public void checkIfShortNameAreInUse(final String shortName) {
    if (finder.countByShortName(shortName) != 0) {
      throw new GroupShortNameInUseException();
    }
  }

  @Override
  public void clearGroupBackImage(final Group group) {
    final String file = group.getBackgroundImage();
    if (file != null) {
      fileManager.rm(FileUtils.groupToDir(group.getShortName()), file);
    }
    group.setBackgroundImage(null);
    group.setBackgroundMime(null);
  }

  @Override
  public Group createGroup(final Group group, final User user, final String publicDescrip)
      throws GroupShortNameInUseException, GroupLongNameInUseException, UserMustBeLoggedException {
    checkIfShortNameAreInUse(group.getShortName());
    checkIfLongNameAreInUse(group.getLongName());
    final String defaultSiteWorkspaceTheme = kuneProperties.get(KuneProperties.WS_THEMES_DEF);
    if (User.isKnownUser(user)) {
      GroupListMode publicVisibility = GroupListMode.EVERYONE;
      SocialNetworkVisibility snVisibility = SocialNetworkVisibility.anyone;
      if (group.getGroupType().equals(GroupType.COMMUNITY)) {
        group.setAdmissionType(AdmissionType.Open);
      } else if (group.getGroupType().equals(GroupType.CLOSED)) {
        group.setAdmissionType(AdmissionType.Closed);
        publicVisibility = GroupListMode.NORMAL;
        snVisibility = SocialNetworkVisibility.onlymembers;
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
      initSocialNetwork(group, user.getUserGroup(), publicVisibility, snVisibility);
      final String title = i18n.t("About [%s]", group.getLongName());
      initGroup(user, group, serverToolRegistry.getToolsForGroupsKeys(), title, publicDescrip);
      return group;
    } else {
      throw new UserMustBeLoggedException();
    }
  }

  @Override
  public Group createUserGroup(final User user) throws GroupShortNameInUseException,
      EmailAddressInUseException {
    return createUserGroup(user, true);
  }

  @Override
  public Group createUserGroup(final User user, final boolean wantPersonalHomepage)
      throws GroupShortNameInUseException, EmailAddressInUseException {
    final String defaultSiteWorkspaceTheme = kuneProperties.get(KuneProperties.WS_THEMES_DEF);
    final License licenseDef = licenseManager.getDefLicense();
    final Group userGroup = new Group(user.getShortName(), user.getName(), licenseDef,
        GroupType.PERSONAL);
    User userSameEmail = null;
    try {
      userSameEmail = userFinder.findByEmail(user.getEmail());
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
    initSocialNetwork(userGroup, userGroup, GroupListMode.EVERYONE, SocialNetworkVisibility.anyone);

    final String title = i18n.t("[%s] Bio", user.getName());
    final String body = i18n.t("This user has not written its biography yet");
    try {
      initGroup(user, userGroup, wantPersonalHomepage ? serverToolRegistry.getToolsForUserKeys()
          : ServerToolRegistry.emptyToolList, title, body);
      super.persist(user, User.class);
    } catch (final PersistenceException e) {
      if (e.getCause() instanceof ConstraintViolationException) {
        throw new GroupShortNameInUseException();
      }
      throw e;
    }
    return userGroup;
  }

  @Override
  public Set<Group> findAdminInGroups(final Long groupId) {
    return finder.findAdminInGroups(groupId);
  }

  @Override
  public Group findByShortName(final String shortName) {
    return finder.findByShortName(shortName);
  }

  @Override
  public Set<Group> findCollabInGroups(final Long groupId) {
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

  private void initGroup(final User user, final Group group, final Collection<String> toolsToEnable,
      final Object... vars) throws GroupShortNameInUseException {
    try {
      persist(group);
    } catch (final IllegalStateException e) {
      e.printStackTrace();
    } catch (final PersistenceException e) {
      if (e.getCause() instanceof ConstraintViolationException) {
        throw new GroupShortNameInUseException();
      }
      throw e;
    }
    for (final ServerTool tool : registry.all()) {
      if (toolsToEnable.contains(tool.getName())) {
        tool.initGroup(user, group, vars);
      }
    }
  }

  private void initSocialNetwork(final Group group, final Group userGroup,
      final GroupListMode publicVisibility, final SocialNetworkVisibility snVisibility) {
    final SocialNetwork network = group.getSocialNetwork();
    final AccessLists lists = network.getAccessLists();
    lists.getEditors().setMode(GroupListMode.NOBODY);
    lists.getViewers().setMode(publicVisibility);
    if (!group.getGroupType().equals(GroupType.ORPHANED_PROJECT)) {
      network.addAdmin(userGroup);
    }
    network.setVisibility(snVisibility);
  }

  @Override
  public SearchResult<Group> search(final String search) {
    return this.search(search, null, null);
  }

  @Override
  public SearchResult<Group> search(final String search, final Integer firstResult,
      final Integer maxResults) {
    final MultiFieldQueryParser parser = new MultiFieldQueryParser(new String[] { "longName",
        "shortName", "publicDesc" }, new StandardAnalyzer());
    Query query;
    try {
      query = parser.parse(search + SearcherConstants.WILDCARD);
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
  public void setGroupBackgroundImage(final Group group, final String backgroundFileName,
      final String mime) {
    clearGroupBackImage(group);
    group.setBackgroundImage(backgroundFileName);
    group.setBackgroundMime(mime);
  }

  @Override
  public void setToolEnabled(final User userLogged, final String groupShortName, final String tool,
      final boolean enabled) {
    final Group group = findByShortName(groupShortName);
    ToolConfiguration toolConfiguration = group.getToolConfiguration(tool);
    if (toolConfiguration == null) {
      toolConfiguration = serverToolRegistry.get(tool).initGroup(userLogged, group).getToolConfiguration(
          tool);
    }
    toolConfiguration.setEnabled(enabled);
  }

  @Override
  public Group update(final Long groupId, final GroupDTO groupDTO) {
    final Group group = find(groupId);
    final String shortName = groupDTO.getShortName();
    final String longName = groupDTO.getLongName();
    if (!longName.equals(group.getLongName())) {
      checkIfLongNameAreInUse(longName);
      group.setLongName(longName);
    }
    if (!shortName.equals(group.getShortName())) {
      checkIfShortNameAreInUse(shortName);
      final String oldDir = kuneProperties.get(KuneProperties.UPLOAD_LOCATION)
          + FileUtils.groupToDir(group.getShortName());
      final String newDir = kuneProperties.get(KuneProperties.UPLOAD_LOCATION)
          + FileUtils.groupToDir(shortName);
      if (fileManager.exists(oldDir)) {
        if (fileManager.exists(newDir)) {
          throw new DefaultException("Destination group directory exists");
        }
        fileManager.mv(oldDir, newDir);
      }
      group.setShortName(shortName);
    }
    persist(group);
    return group;
  }

}
