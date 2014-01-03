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
package cc.kune.core.server.testhelper.ctx;

import cc.kune.domain.SocialNetwork;

// TODO: Auto-generated Javadoc
/**
 * The Class SocialNetworkOperator.
 * 
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class SocialNetworkOperator {

  /** The ctx. */
  private final DomainContext ctx;

  /** The social network. */
  private final SocialNetwork socialNetwork;

  /**
   * Instantiates a new social network operator.
   * 
   * @param ctx
   *          the ctx
   * @param socialNetwork
   *          the social network
   */
  public SocialNetworkOperator(final DomainContext ctx, final SocialNetwork socialNetwork) {
    this.ctx = ctx;
    this.socialNetwork = socialNetwork;
  }

  /**
   * Adds the as administrator.
   * 
   * @param userName
   *          the user name
   */
  public void addAsAdministrator(final String userName) {
    socialNetwork.addAdmin(ctx.getGroupOf(userName));
  }

  /**
   * Adds the as collaborator.
   * 
   * @param userName
   *          the user name
   */
  public void addAsCollaborator(final String userName) {
    socialNetwork.addCollaborator(ctx.getGroupOf(userName));
  }

}
