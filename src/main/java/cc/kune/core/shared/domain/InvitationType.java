/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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

package cc.kune.core.shared.domain;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * The Enum InvitationType describes types of invitations (usually email
 * invitations)
 */
public enum InvitationType implements IsSerializable {

  /** Invitation to a group */
  TO_GROUP,

  /** Invitation to a group list */
  TO_LISTS,

  /** Invitation to this site. */
  TO_SITE
}
