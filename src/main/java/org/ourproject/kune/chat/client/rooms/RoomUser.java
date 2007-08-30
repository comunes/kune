/*
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.chat.client.rooms;

public class RoomUser {
    public static final UserType MODERADOR = new UserType();
    public static final UserType PARTICIPANT = new UserType();
    public static final UserType VISITOR = new UserType();
    public static final UserType NONE = new UserType();

    public static class UserType {
	private UserType() {
	}
    }

    private String color;
    private String alias;
    private UserType type;

    public RoomUser(final String alias, final String color, final UserType userType) {
	this.alias = alias;
	this.color = color;
	this.type = userType;
    }

    public String getColor() {
	return color;
    }

    public String getAlias() {
	return alias;
    }

    public UserType getUserType() {
	return type;
    }

    public void setColor(final String color) {
	this.color = color;
    }

    public void setAlias(final String alias) {
	this.alias = alias;
    }

    public void setUserType(final UserType userType) {
	this.type = userType;
    }
}
