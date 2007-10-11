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

package org.ourproject.kune.platf.server.mapper;

import java.text.MessageFormat;
import java.util.HashMap;

import net.sf.dozer.util.mapping.MappingException;
import net.sf.dozer.util.mapping.converters.CustomConverter;

import org.ourproject.kune.platf.server.domain.GroupListMode;

public class GroupListModeConverter implements CustomConverter {
    private HashMap<String, GroupListMode> stringToEnum;
    private HashMap<GroupListMode, String> enumToString;

    public GroupListModeConverter() {
	this.stringToEnum = new HashMap<String, GroupListMode>();
	this.enumToString = new HashMap<GroupListMode, String>();
	add(GroupListMode.NORMAL);
	add(GroupListMode.EVERYONE);
	add(GroupListMode.NOBODY);
    }

    private void add(GroupListMode mode) {
	enumToString.put(mode, mode.toString());
	stringToEnum.put(mode.toString(), mode);
    }

    public Object convert(Object destination, Object source, Class destinationClass, Class sourceClass) {
	if (source == null) {
	    return null;
	} else if (sourceClass.equals(String.class) && destinationClass.equals(GroupListMode.class)) {
	    return stringToEnum.get(source);
	} else if (sourceClass.equals(GroupListMode.class) && destinationClass.equals(String.class)) {
	    return enumToString.get(source);
	} else {
	    String msg = MessageFormat.format("couldn't map {0} ({1}) to {2} ({3})", source, sourceClass, destination,
		    destinationClass);
	    throw new MappingException(msg);
	}

    }

}
