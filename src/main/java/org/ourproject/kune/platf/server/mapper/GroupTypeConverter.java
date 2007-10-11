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

import org.ourproject.kune.platf.server.domain.GroupType;

public class GroupTypeConverter implements CustomConverter {
    private final HashMap<String, GroupType> stringToEnum;
    private final HashMap<GroupType, String> enumToString;

    public GroupTypeConverter() {
	this.stringToEnum = new HashMap<String, GroupType>();
	this.enumToString = new HashMap<GroupType, String>();
	add(GroupType.PERSONAL);
	add(GroupType.PROJECT);
	add(GroupType.COMMUNITY);
	add(GroupType.ORGANIZATION);
    }

    private void add(final GroupType mode) {
	enumToString.put(mode, mode.toString());
	stringToEnum.put(mode.toString(), mode);
    }

    public Object convert(final Object destination, final Object source, final Class destinationClass,
	    final Class sourceClass) {
	if (source == null) {
	    return null;
	} else if (sourceClass.equals(String.class) && destinationClass.equals(GroupType.class)) {
	    return stringToEnum.get(source);
	} else if (sourceClass.equals(GroupType.class) && destinationClass.equals(String.class)) {
	    return enumToString.get(source);
	} else {
	    String msg = MessageFormat.format("couldn't map {0} ({1}) to {2} ({3})", source, sourceClass, destination,
		    destinationClass);
	    throw new MappingException(msg);
	}

    }

}
