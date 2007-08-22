/*
 *
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

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Singleton;

import net.sf.dozer.util.mapping.DozerBeanMapperSingletonWrapper;
import net.sf.dozer.util.mapping.MapperIF;

@Singleton
public class DozerMapper implements Mapper {
    private final MapperIF mapper;

    public DozerMapper() {
	mapper = DozerBeanMapperSingletonWrapper.getInstance();
    }

    public <T> T map(final Object source, final Class<T> type) {
	return (T) mapper.map(source, type);
    }

    public <T> List<T> mapList(final List<?> list, final Class<T> type) {
	ArrayList<T> dest = new ArrayList<T>(list.size());
	for (Object o : list) {
	    dest.add((T) mapper.map(o, type));
	}
	return dest;
    }

}
