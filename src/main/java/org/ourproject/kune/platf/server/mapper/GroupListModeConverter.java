/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.platf.server.mapper;

import java.text.MessageFormat;
import java.util.HashMap;

import net.sf.dozer.util.mapping.MappingException;
import net.sf.dozer.util.mapping.converters.CustomConverter;
import cc.kune.core.shared.domain.GroupListMode;

public class GroupListModeConverter implements CustomConverter {
    private final HashMap<String, GroupListMode> stringToEnum;
    private final HashMap<GroupListMode, String> enumToString;

    public GroupListModeConverter() {
        this.stringToEnum = new HashMap<String, GroupListMode>();
        this.enumToString = new HashMap<GroupListMode, String>();
        add(GroupListMode.NORMAL);
        add(GroupListMode.EVERYONE);
        add(GroupListMode.NOBODY);
    }

    @SuppressWarnings("rawtypes")
    public Object convert(final Object destination, final Object source, final Class destinationClass,
            final Class sourceClass) {
        if (source == null) {
            return null;
        } else if (sourceClass.equals(String.class) && destinationClass.equals(GroupListMode.class)) {
            return stringToEnum.get(source);
        } else if (sourceClass.equals(GroupListMode.class) && destinationClass.equals(String.class)) {
            return enumToString.get(source);
        } else {
            final String msg = MessageFormat.format("couldn't map {0} ({1}) to {2} ({3})", source, sourceClass,
                    destination, destinationClass);
            throw new MappingException(msg);
        }

    }

    private void add(final GroupListMode mode) {
        enumToString.put(mode, mode.toString());
        stringToEnum.put(mode.toString(), mode);
    }

}
