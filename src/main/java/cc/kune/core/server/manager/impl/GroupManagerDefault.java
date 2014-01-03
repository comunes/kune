/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.apache.lucene.queryParser.QueryParser;
import org.hibernate.exception.ConstraintViolationException;

import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.errors.AccessViolationException;
import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.client.errors.EmailAddressInUseException;
import cc.kune.core.client.errors.GroupLongNameInUseException;
import cc.kune.core.client.errors.GroupShortNameInUseException;
import cc.kune.core.client.errors.ToolIsDefaultException;
import cc.kune.core.client.errors.UserMustBeLoggedException;
import cc.kune.core.server.manager.FileManager;
import cc.kune.core.server.manager.GroupManager;
import cc.kune.core.server.manager.LicenseManager;
import cc.kune.core.server.manager.SearchResult;
import cc.kune.core.server.manager.file.FileUtils;
import cc.kune.core.server.mbean.MBeanRegistry;
import cc.kune.core.server.persist.DataSourceKune;
import cc.kune.core.server.persist.KuneTransactional;
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
import cc.kune.trash.server.TrashServerTool;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Class GroupManagerDefault.
 * 
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class GroupManagerDefault extends DefaultManager<Group, Long> implements GroupManager,
    GroupManagerDefaultMBean {

  /** The file manager. */
  private final FileManager fileManager;

  /** The finder. */
  private final GroupFinder finder;

  /** The i18n. */
  private final I18nTranslationService i18n;

  /** The kune properties. */
  private final KuneProperties kuneProperties;

  /** The license finder. */
  private final LicenseFinder licenseFinder;

  /** The license manager. */
  private final LicenseManager licenseManager;

  /** The properties. */
  private final KuneBasicProperties properties;

  /** The server tool registry. */
  private final ServerToolRegistry serverToolRegistry;

  /** The sn cache. */
  private final SocialNetworkCache snCache;

  /** The trash tool. */
  private final Provider<TrashServerTool> trashTool;

  /** The user finder. */
  private final UserFinder userFinder;

  /**
   * Instantiates a new group manager default.
   * 
   * @param provider
   *          the provider
   * @param finder
   *          the finder
   * @param userFinder
   *          the user finder
   * @param kuneProperties
   *          the kune properties
   * @param properties
   *          the properties
   * @param licenseManager
   *          the license manager
   * @param licenseFinder
   *          the license finder
   * @param fileManager
   *          the file manager
   * @param serverToolRegistry
   *          the server tool registry
   * @param trashTool
   *          the trash tool
   * @param i18n
   *          the i18n
   * @param snCache
   *          the sn cache
   * @param mBeanRegistry
   *          the m bean registry
   */
  @Inject
  public GroupManagerDefault(@DataSourceKune final Provider<EntityManager> provider,
      final GroupFinder finder, final UserFinder userFinder, final KuneProperties kuneProperties,
      final KuneBasicProperties properties, final LicenseManager licenseManager,
      final LicenseFinder licenseFinder, final FileManager fileManager,
      final ServerToolRegistry serverToolRegistry, final Provider<TrashServerTool> trashTool,
      final I18nTranslationService i18n, final SocialNetworkCache snCache,
      final MBeanRegistry mBeanRegistry) {
    super(provider, Group.class);
    mBeanRegistry.registerAsMBean(this, GroupManagerDefaultMBean.MBEAN_OBJECT_NAME);
    this.finder = finder;
    this.userFinder = userFinder;
    this.kuneProperties = kuneProperties;
    this.properties = properties;
    this.licenseManager = licenseManager;
    this.licenseFinder = licenseFinder;
    this.fileManager = fileManager;
    this.serverToolRegistry = serverToolRegistry;
    this.trashTool = trashTool;
    this.i18n = i18n;
    this.snCache = snCache;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.GroupManager#changeDefLicense(cc.kune.domain
   * .User, cc.kune.domain.Group, java.lang.String)
   */
  @Override
  public void changeDefLicense(final User user, final Group group, final String licName) {
    final License license = licenseFinder.findByShortName(licName);
    if (license == null) {
      throw new ServerManagerException("Unknown license");
    }
    group.setDefaultLicense(license);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.GroupManager#changeWsTheme(cc.kune.domain.User,
   * cc.kune.domain.Group, java.lang.String)
   */
  @Override
  public void changeWsTheme(final User user, final Group group, final String theme)
      throws AccessViolationException {
    // TODO: check theme
    group.setWorkspaceTheme(theme);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.GroupManager#checkIfLongNameAreInUse(java.lang
   * .String)
   */
  @Override
  public void checkIfLongNameAreInUse(final String longName) {
    if (finder.countByLongName(longName) != 0) {
      throw new GroupLongNameInUseException();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.GroupManager#checkIfShortNameAreInUse(java.
   * lang.String)
   */
  @Override
  public void checkIfShortNameAreInUse(final String shortName) {
    if (finder.countByShortName(shortName) != 0) {
      throw new GroupShortNameInUseException();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.GroupManager#clearGroupBackImage(cc.kune.domain
   * .Group)
   */
  @Override
  public void clearGroupBackImage(final Group group) {
    final String file = group.getBackgroundImage();
    if (file != null) {
      fileManager.rm(FileUtils.groupToDir(group.getShortName()), file);
    }
    group.setBackgroundImage(null);
    group.setBackgroundMime(null);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.manager.GroupManager#count()
   */
  @Override
  @KuneTransactional
  public Long count() {
    return finder.count();
    // return super.size();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.GroupManager#createGroup(cc.kune.domain.Group,
   * cc.kune.domain.User, java.lang.String)
   */
  @Override
  public Group createGroup(final Group group, final User user, final String publicDescrip)
      throws GroupShortNameInUseException, GroupLongNameInUseException, UserMustBeLoggedException {
    checkIfShortNameAreInUse(group.getShortName());
    checkIfLongNameAreInUse(group.getLongName());
    final String defaultSiteWorkspaceTheme = kuneProperties.get(KuneProperties.WS_THEMES_DEF);
    if (User.isKnownUser(user)) {
      setAdmissionType(group);
      final String licName = group.getDefaultLicense().getShortName();
      final License license = licenseFinder.findByShortName(licName);
      group.setDefaultLicense(license);
      group.setWorkspaceTheme(defaultSiteWorkspaceTheme);
      final boolean isClosed = group.getGroupType().equals(GroupType.CLOSED);
      initSocialNetwork(group, user.getUserGroup(), getDefGroupMode(isClosed),
          getDefSNVisibility(isClosed));
      final String title = i18n.t("About [%s]", group.getLongName());
      initGroup(user, group, serverToolRegistry.getToolsRegisEnabledForGroups(), title, publicDescrip);
      snCache.expire(user.getUserGroup());
      return group;
    } else {
      throw new UserMustBeLoggedException();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.GroupManager#createUserGroup(cc.kune.domain
   * .User)
   */
  @Override
  public Group createUserGroup(final User user) throws GroupShortNameInUseException,
      EmailAddressInUseException {
    return createUserGroup(user, true);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.GroupManager#createUserGroup(cc.kune.domain
   * .User, boolean)
   */
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
    final String body = i18n.t("This is [%s]'s bio, currently empty", user.getName());
    try {
      initGroup(user, userGroup,
          wantPersonalHomepage ? serverToolRegistry.getToolsRegisEnabledForUsers()
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

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.GroupManager#findAdminInGroups(java.lang.Long)
   */
  @Override
  public Set<Group> findAdminInGroups(final Long groupId) {
    return finder.findAdminInGroups(groupId);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.GroupManager#findByShortName(java.lang.String)
   */
  @Override
  public Group findByShortName(final String shortName) {
    return finder.findByShortName(shortName);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.GroupManager#findCollabInGroups(java.lang.Long)
   */
  @Override
  public Set<Group> findCollabInGroups(final Long groupId) {
    return finder.findCollabInGroups(groupId);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.GroupManager#findEnabledTools(java.lang.Long)
   */
  @Override
  public List<String> findEnabledTools(final Long id) {
    return finder.findEnabledTools(id);
  }

  /**
   * Gets the def group mode.
   * 
   * @param isClosed
   *          the is closed
   * @return the def group mode
   */
  private GroupListMode getDefGroupMode(final boolean isClosed) {
    return isClosed ? GroupListMode.NORMAL : GroupListMode.EVERYONE;
  }

  /**
   * Gets the def sn visibility.
   * 
   * @param isClosed
   *          the is closed
   * @return the def sn visibility
   */
  private SocialNetworkVisibility getDefSNVisibility(final boolean isClosed) {
    return isClosed ? SocialNetworkVisibility.onlymembers : SocialNetworkVisibility.anyone;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.GroupManager#getGroupOfUserWithId(java.lang
   * .Long)
   */
  @Override
  public Group getGroupOfUserWithId(final Long userId) {
    return userId != null ? find(User.class, userId).getUserGroup() : null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.manager.GroupManager#getSiteDefaultGroup()
   */
  @Override
  public Group getSiteDefaultGroup() {
    final String shortName = properties.getDefaultSiteShortName();
    return findByShortName(shortName);
  }

  /**
   * Inits the group.
   * 
   * @param user
   *          the user
   * @param group
   *          the group
   * @param toolsToEnable
   *          the tools to enable
   * @param vars
   *          the vars
   * @throws GroupShortNameInUseException
   *           the group short name in use exception
   */
  private void initGroup(final User user, final Group group, final List<String> toolsToEnable,
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
    for (final ServerTool tool : serverToolRegistry.all()) {
      if (toolsToEnable.contains(tool.getName())) {
        tool.initGroup(user, group, vars);
      }
    }
    // Init always the trash
    initTrash(group);
  }

  /**
   * Inits the social network.
   * 
   * @param group
   *          the group
   * @param userGroup
   *          the user group
   * @param publicVisibility
   *          the public visibility
   * @param snVisibility
   *          the sn visibility
   */
  private void initSocialNetwork(final Group group, final Group userGroup,
      final GroupListMode publicVisibility, final SocialNetworkVisibility snVisibility) {
    final SocialNetwork network = setSocialNetwork(group, publicVisibility, snVisibility);
    if (!group.getGroupType().equals(GroupType.ORPHANED_PROJECT)) {
      network.addAdmin(userGroup);
      network.getAccessLists().getEditors().setMode(GroupListMode.NOBODY);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.GroupManager#initTrash(cc.kune.domain.Group)
   */
  @Override
  public void initTrash(final Group group) {
    trashTool.get().initGroup(group);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.manager.GroupManager#search(java.lang.String)
   */
  @Override
  public SearchResult<Group> search(final String search) {
    return this.search(search, null, null);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.manager.GroupManager#search(java.lang.String,
   * java.lang.Integer, java.lang.Integer)
   */
  @Override
  @KuneTransactional
  public SearchResult<Group> search(final String search, final Integer firstResult,
      final Integer maxResults) {
    // Wildcard is not allowed in the first character of the query
    final String escapedQuery = QueryParser.escape(search) + SearcherConstants.WILDCARD;
    return super.search(new String[] { escapedQuery, escapedQuery, escapedQuery }, new String[] {
        "longName", "shortName", "publicDesc" }, firstResult, maxResults);
  }

  /**
   * Sets the admission type.
   * 
   * @param group
   *          the new admission type
   */
  private void setAdmissionType(final Group group) {
    final GroupType groupType = group.getGroupType();
    if (groupType.equals(GroupType.COMMUNITY)) {
      group.setAdmissionType(AdmissionType.Open);
    } else {
      if (groupType.equals(GroupType.CLOSED)) {
        group.setAdmissionType(AdmissionType.Closed);
      } else if (groupType.equals(GroupType.ORGANIZATION)) {
        group.setAdmissionType(AdmissionType.Moderated);
      } else if (groupType.equals(GroupType.PROJECT)) {
        group.setAdmissionType(AdmissionType.Moderated);
      } else if (groupType.equals(GroupType.ORPHANED_PROJECT)) {
        group.setAdmissionType(AdmissionType.Open);
      }
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.GroupManager#setDefaultContent(java.lang.String
   * , cc.kune.domain.Content)
   */
  @Override
  public void setDefaultContent(final String groupShortName, final Content content) {
    final Group group = findByShortName(groupShortName);
    group.setDefaultContent(content);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.GroupManager#setGroupBackgroundImage(cc.kune
   * .domain.Group, java.lang.String, java.lang.String)
   */
  @Override
  public void setGroupBackgroundImage(final Group group, final String backgroundFileName,
      final String mime) {
    clearGroupBackImage(group);
    group.setBackgroundImage(backgroundFileName);
    group.setBackgroundMime(mime);
  }

  /**
   * Sets the social network.
   * 
   * @param group
   *          the group
   * @param publicVisibility
   *          the public visibility
   * @param snVisibility
   *          the sn visibility
   * @return the social network
   */
  private SocialNetwork setSocialNetwork(final Group group, final GroupListMode publicVisibility,
      final SocialNetworkVisibility snVisibility) {
    final SocialNetwork network = group.getSocialNetwork();
    final AccessLists lists = network.getAccessLists();
    lists.getViewers().setMode(publicVisibility);
    network.setVisibility(snVisibility);
    return network;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.GroupManager#setToolEnabled(cc.kune.domain.
   * User, java.lang.String, java.lang.String, boolean)
   */
  @Override
  public void setToolEnabled(final User userLogged, final String groupShortName, final String tool,
      final boolean enabled) throws ToolIsDefaultException {
    final Group group = findByShortName(groupShortName);
    if (group.getDefaultContent().getStateToken().getTool().equals(tool) && !enabled) {
      throw new ToolIsDefaultException();
    }
    ToolConfiguration toolConfiguration = group.getToolConfiguration(tool);
    if (toolConfiguration == null) {
      toolConfiguration = serverToolRegistry.get(tool).initGroup(userLogged, group).getToolConfiguration(
          tool);
    }
    toolConfiguration.setEnabled(enabled);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.manager.GroupManager#update(java.lang.Long,
   * cc.kune.core.shared.dto.GroupDTO)
   */
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
    group.setGroupType(groupDTO.getGroupType());
    setAdmissionType(group);
    final boolean isClosed = group.getGroupType().equals(GroupType.CLOSED);
    setSocialNetwork(group, getDefGroupMode(isClosed), getDefSNVisibility(isClosed));
    persist(group);
    snCache.expire(group);
    return group;
  }

}
