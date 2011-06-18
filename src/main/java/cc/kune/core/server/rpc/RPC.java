/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package cc.kune.core.server.rpc;

import com.google.inject.Singleton;

/**
 * marker interface (should not contain any method!)
 * 
 * all the RPC objects should:
 * <ul>
 * <li>implement a RemoteService interface in the server side</li>
 * <li>convert from domain objects to dto objects</li>
 * <li>has userHash protected methods</li>
 * <li>contains little (or nothing) bussines logic (should delegate to other
 * services/managers)</li>
 * </ul>
 * 
 */
@Singleton
public interface RPC {

}
