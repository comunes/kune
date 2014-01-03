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
package cc.kune.core.client.state;

import cc.kune.core.shared.domain.utils.StateToken;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class StateTokenUtils.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class StateTokenUtils {

  /** The Constant SEPARATOR. */
  private static final String SEPARATOR = ".";

  /** The session. */
  @Inject
  private static Session session;

  /**
   * Gets the group space url.
   * 
   * @param token
   *          the token
   * @return the group space url
   */
  public static String getGroupSpaceUrl(final StateToken token) {
    return session.getSiteUrl() + "/#" + token.getEncoded();
  }

  /**
   * Gets the public space url.
   * 
   * @param token
   *          the token
   * @return the public space url
   */
  public static String getPublicSpaceUrl(final StateToken token) {
    String publicUrl = "";

    final String group = token.getGroup();
    final String tool = token.getTool();
    final String folder = token.getFolder();
    final String document = token.getDocument();

    publicUrl += session.getSiteUrl() + "/public";

    if (group != null) {
      publicUrl += "/" + group;
    }
    if (tool != null) {
      publicUrl += SEPARATOR + tool;
    }
    if (folder != null) {
      publicUrl += SEPARATOR + folder;
    }
    if (document != null) {
      publicUrl += SEPARATOR + document;
    }

    return publicUrl;
  }

  /**
   * Instantiates a new state token utils.
   */
  public StateTokenUtils() {
  }
}
