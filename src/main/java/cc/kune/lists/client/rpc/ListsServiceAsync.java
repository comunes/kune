/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
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
package cc.kune.lists.client.rpc;

import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.core.shared.dto.StateContentDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

// TODO: Auto-generated Javadoc
/**
 * The Interface ListsServiceAsync.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface ListsServiceAsync {

  /**
   * Creates the list.
   *
   * @param hash the hash
   * @param parentToken the parent token
   * @param name the name
   * @param description the description
   * @param isPublic the is public
   * @param callback the callback
   */
  void createList(String hash, StateToken parentToken, String name, String description,
      boolean isPublic, AsyncCallback<StateContainerDTO> callback);

  /**
   * New post.
   *
   * @param userHash the user hash
   * @param parentToken the parent token
   * @param title the title
   * @param callback the callback
   */
  void newPost(String userHash, StateToken parentToken, String title,
      AsyncCallback<StateContentDTO> callback);

  /**
   * Sets the public.
   *
   * @param hash the hash
   * @param token the token
   * @param isPublic the is public
   * @param callback the callback
   */
  void setPublic(String hash, StateToken token, Boolean isPublic,
      AsyncCallback<StateContainerDTO> callback);

  /**
   * Subscribe to list.
   *
   * @param hash the hash
   * @param token the token
   * @param subscribe the subscribe
   * @param callback the callback
   */
  void subscribeToList(String hash, StateToken token, Boolean subscribe,
      AsyncCallback<StateContainerDTO> callback);

}
