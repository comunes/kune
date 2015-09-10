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

public class HelloWorldCommand extends Command {

  public static class HelloWorldICommand implements ICommandExecutor {

    @Override
    public void execute(ParseResult result) throws ExecutionException {
      System.out.print("Hello world!");
      if (result.getParameterCount() == 0) {
        System.out.println();
      } else {
        final String p0 = result.getParameterValue(0).toString();
        System.out.println(" And hello especially to " + p0);
      }
    }
  }

  public HelloWorldCommand() throws InvalidSyntaxException {
    super("hello world [<name:string>]", "Says hello to the world and, maybe, especially to someone.",
        new HelloWorldICommand());
  }
}
