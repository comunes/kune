/*
 *
 * Copyright (C) 2007-2015 Licensed to the Comunes Association (CA) under
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

package cc.kune.kunecli.cmds;

import org.naturalcli.Command;
import org.naturalcli.ExecutionException;
import org.naturalcli.ICommandExecutor;
import org.naturalcli.InvalidSyntaxException;
import org.naturalcli.ParseResult;

import cc.kune.core.server.manager.impl.UserManagerDefaultMBean;
import cc.kune.kunecli.JMXUtils;

public class UsersCount extends Command {

  public static class UsersCountICommand implements ICommandExecutor {

    @Override
    public void execute(final ParseResult result) throws ExecutionException {
      final Object count = JMXUtils.doOperation(UserManagerDefaultMBean.MBEAN_OBJECT_NAME, "count");
      if (count != null) {
        System.out.println("Users registered: " + (long) count);
      }
    }
  }

  public UsersCount() throws InvalidSyntaxException {
    super("users count", "Reindex all groups in Lucene", new UsersCountICommand());
  }
}
