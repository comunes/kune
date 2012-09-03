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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

import cc.kune.core.client.rpcservices.SiteServiceAsync;
import cc.kune.core.client.rpcservices.UserServiceAsync;
import cc.kune.core.shared.dto.InitDataDTO;
import cc.kune.core.shared.dto.UserInfoDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.googlecode.gwtrpccommlayer.client.GwtRpcService;
import com.googlecode.gwtrpccommlayer.client.Module;

public class KuneCliMain {

  private static final String SERVICE_PREFFIX = "http://127.0.0.1/ws/";
  public static final Log LOG = LogFactory.getLog(KuneCliMain.class);
  private static UserServiceAsync userService;
  private static SiteServiceAsync siteService;

  public static void main(String[] args) throws InvalidSyntaxException, ExecutionException,
      MalformedURLException {

    initServices();
    
    // TODO: integrate jline or similar
    // http://jline.sourceforge.net/index.html
    
    //http://sourceforge.net/projects/javacurses/
      //http://massapi.com/class/jcurses/widgets/Button.java.html
    
    Command showDateCommand = new Command("hello world [<name:string>]",
        "Says hello to the world and, maybe, especially to someone.", new ICommandExecutor() {
          public void execute(ParseResult pr) {
            System.out.print("Hello world!");
            String p0 = pr.getParameterValue(0).toString();
            if (p0 == null)
              System.out.println();
            else
              System.out.println(" And hello especially to " + p0);
          }
        });

    Command auth = new Command("auth <user:string> <pass:string>", "auth to kune",
        new ICommandExecutor() {

          public void execute(ParseResult pr) throws ExecutionException {
            String user = pr.getParameterValue(0).toString();
            String pass = pr.getParameterValue(1).toString();
            userService.login(user, pass, "FIXME", new AsyncCallback<UserInfoDTO>() {

              @Override
              public void onSuccess(UserInfoDTO result) {
                // TODO Auto-generated method stub
              }

              @Override
              public void onFailure(Throwable caught) {
                // TODO Auto-generated method stub
              }
            });
          }
        });

    Command init = new Command("siteGetInitData", "gets the initial data", new ICommandExecutor() {
      public void execute(ParseResult parseResult) throws ExecutionException {
        siteService.getInitData("", new AsyncCallback<InitDataDTO>() {
          @Override
          public void onFailure(Throwable caught) {
            // TODO Auto-generated method stub
          }

          @Override
          public void onSuccess(InitDataDTO result) {
            // TODO Auto-generated method stub
          }
        });
      }
    });

    // Create an empty command set
    Set<Command> cs = new HashSet<Command>();

    // Create the interpreter
    NaturalCLI nc = new NaturalCLI(cs);

    // Add the commands that can be understood
    cs.add(showDateCommand);
    cs.add(new HelpCommand(cs)); // help
    cs.add(new HTMLHelpCommand(cs)); // htmlhelp
    cs.add(new SleepCommand()); // sleep <seconds:number>
    
    // A script can be useful for kune  
    cs.add(new ExecuteFileCommand(nc)); // execute file <filename:string>

    cs.add(init);
    cs.add(auth);
    // Execute the command line
    nc.execute(args, 0);
  }

  private static void initServices() throws MalformedURLException {
    // http://code.google.com/p/gwtrpccommlayer/wiki/GettingStarted
    // http://googlewebtoolkit.blogspot.com.es/2010/07/gwtrpccommlayer-extending-gwt-rpc-to-do.html
    Injector injector = Guice.createInjector(new Module());

    GwtRpcService service = injector.getInstance(GwtRpcService.class);

    // TODO javadoc of this services
    userService = service.create(new URL(SERVICE_PREFFIX + "UserService"), UserServiceAsync.class);
    siteService = service.create(new URL(SERVICE_PREFFIX + "SiteService"), SiteServiceAsync.class);
  }
}
