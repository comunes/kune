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
package cc.kune.core.server.access;

import cc.kune.core.client.errors.AccessViolationException;
import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.shared.domain.AccessRol;
import cc.kune.core.shared.domain.utils.AccessRights;
import cc.kune.domain.Container;
import cc.kune.domain.Content;
import cc.kune.domain.Group;
import cc.kune.domain.ToolConfiguration;
import cc.kune.domain.User;

import com.google.inject.Inject;
import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Class AccessServiceDefault.
 * 
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class AccessServiceDefault implements AccessService {

  /** The access rights service. */
  private final AccessRightsService accessRightsService;

  /** The finder. */
  private final FinderService finder;

  /**
   * Instantiates a new access service default.
   * 
   * @param finder
   *          the finder
   * @param accessRightsService
   *          the access rights service
   */
  @Inject
  public AccessServiceDefault(final FinderService finder, final AccessRightsService accessRightsService) {
    this.finder = finder;
    this.accessRightsService = accessRightsService;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.access.AccessService#accessToContainer(cc.kune.domain
   * .Container, cc.kune.domain.User, cc.kune.core.shared.domain.AccessRol)
   */
  @Override
  public Container accessToContainer(final Container container, final User user,
      final AccessRol accessRol) {
    checkToolIsEnabled(container.getOwner(), container.getToolName());
    check(accessRightsService.get(user, container.getAccessLists()), accessRol);
    return container;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.access.AccessService#accessToContainer(java.lang.Long,
   * cc.kune.domain.User, cc.kune.core.shared.domain.AccessRol)
   */
  @Override
  public Container accessToContainer(final Long folderId, final User user, final AccessRol accessRol)
      throws DefaultException {
    final Container container = finder.getFolder(folderId);
    return accessToContainer(container, user, accessRol);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.access.AccessService#accessToContent(cc.kune.domain
   * .Content, cc.kune.domain.User, cc.kune.core.shared.domain.AccessRol)
   */
  @Override
  public Content accessToContent(final Content content, final User user, final AccessRol accessRol) {
    checkToolIsEnabled(content.getContainer().getOwner(), content.getContainer().getToolName());
    check(accessRightsService.get(user, content.getAccessLists()), accessRol);
    return content;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.access.AccessService#accessToContent(java.lang.Long,
   * cc.kune.domain.User, cc.kune.core.shared.domain.AccessRol)
   */
  @Override
  public Content accessToContent(final Long contentId, final User user, final AccessRol accessRol)
      throws DefaultException {
    final Content content = finder.getContent(contentId);
    return accessToContent(content, user, accessRol);
  }

  /**
   * Check.
   * 
   * @param rights
   *          the rights
   * @param accessRol
   *          the access rol
   * @throws AccessViolationException
   *           the access violation exception
   */
  private void check(final AccessRights rights, final AccessRol accessRol)
      throws AccessViolationException {
    if (!isValid(accessRol, rights)) {
      throw new AccessViolationException();
    }
  }

  /**
   * Check tool is enabled.
   * 
   * @param group
   *          the group
   * @param toolName
   *          the tool name
   */
  private void checkToolIsEnabled(final Group group, final String toolName) {
    final ToolConfiguration toolConf = group.getToolConfiguration(toolName);
    if (toolConf == null || !toolConf.isEnabled()) {
      throw new AccessViolationException();
    }
  }

  /**
   * Checks if is valid.
   * 
   * @param accessRol
   *          the access rol
   * @param rights
   *          the rights
   * @return true, if is valid
   */
  private boolean isValid(final AccessRol accessRol, final AccessRights rights) {
    switch (accessRol) {
    case Viewer:
      return rights.isVisible();
    case Editor:
      return rights.isEditable();
    case Administrator:
      return rights.isAdministrable();
    default:
      return false;
    }
  }

}
