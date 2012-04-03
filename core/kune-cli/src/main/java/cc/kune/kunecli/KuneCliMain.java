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

package cc.kune.kunecli;

import java.util.HashSet;
import java.util.Set;

import org.naturalcli.Command;
import org.naturalcli.ExecutionException;
import org.naturalcli.ICommandExecutor;
import org.naturalcli.InvalidSyntaxException;
import org.naturalcli.NaturalCLI;
import org.naturalcli.ParseResult;
import org.naturalcli.commands.ExecuteFileCommand;
import org.naturalcli.commands.HTMLHelpCommand;
import org.naturalcli.commands.HelpCommand;
import org.naturalcli.commands.SleepCommand;

public class KuneCliMain {

  public static void main(String[] args) throws InvalidSyntaxException, ExecutionException {
    Command showDateCommand =
        new Command(
          "hello world [<name:string>]", 
          "Says hello to the world and, may be, especially to some one.", 
          new ICommandExecutor ()
          {
            public void execute(ParseResult pr) 
            {  
              System.out.print("Hello world!");
              String p0 = pr.getParameterValue(0).toString();
              if (p0 == null)
                System.out.println();
              else
                System.out.println(" And hello especially to "+p0);  
            }
          }   
        );
    // Create an empty command set
    Set<Command> cs = new HashSet<Command>();
   
    // Create the interpreter
    NaturalCLI nc = new NaturalCLI(cs);
   
    // Add the commands that can be understood
    cs.add(showDateCommand);
    cs.add(new HelpCommand(cs)); // help
    cs.add(new HTMLHelpCommand(cs)); // htmlhelp
    cs.add(new SleepCommand());  // sleep <seconds:number> 
    cs.add(new ExecuteFileCommand(nc)); // execute file <filename:string>
   
    // Execute the command line 
    nc.execute(args, 0);
     
  }
}
