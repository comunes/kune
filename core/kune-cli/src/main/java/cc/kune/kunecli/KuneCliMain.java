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

package cc.kune.kunecli;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.naturalcli.Command;
import org.naturalcli.ExecutionException;
import org.naturalcli.InvalidSyntaxException;
import org.naturalcli.NaturalCLI;
import org.naturalcli.commands.ExecuteFileCommand;
import org.naturalcli.commands.HTMLHelpCommand;
import org.naturalcli.commands.HelpCommand;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.googlecode.gwtrpccommlayer.client.GwtRpcService;
import com.googlecode.gwtrpccommlayer.client.Module;

import cc.kune.core.client.rpcservices.I18nServiceAsync;
import cc.kune.core.client.rpcservices.SiteServiceAsync;
import cc.kune.core.client.rpcservices.UserServiceAsync;
import cc.kune.kunecli.cmds.AuthCommand;
import cc.kune.kunecli.cmds.GetI18nLangCommand;
import cc.kune.kunecli.cmds.GetInitDataCommand;
import cc.kune.kunecli.cmds.HelloWorldCommand;

/**
 * The Class KuneCliMain.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class KuneCliMain {

  /** The Constant LOG. */
  public static final Log LOG = LogFactory.getLog(KuneCliMain.class);

  /** The Constant SERVICE_PREFFIX. */
  private static final String SERVICE_PREFFIX = "http://127.0.0.1:8888/ws/";

  private static Injector injector;

  /**
   * Inits the services.
   *
   * @throws MalformedURLException
   *           the malformed url exception
   */
  private static void initServices() throws MalformedURLException {
    // http://code.google.com/p/gwtrpccommlayer/wiki/GettingStarted
    // http://googlewebtoolkit.blogspot.com.es/2010/07/gwtrpccommlayer-extending-gwt-rpc-to-do.html
    final Injector partentInjector = Guice.createInjector(new Module() {
    });

    final GwtRpcService service = partentInjector.getInstance(GwtRpcService.class);
    injector = partentInjector.createChildInjector(new Module() {
      protected void configure() {
        try {
          UserServiceAsync userService = service.create(new URL(SERVICE_PREFFIX + "UserService"),
              UserServiceAsync.class);
          SiteServiceAsync siteService = service.create(new URL(SERVICE_PREFFIX + "SiteService"),
              SiteServiceAsync.class);
          I18nServiceAsync i18nService = service.create(new URL(SERVICE_PREFFIX + "I18nService"),
              I18nServiceAsync.class);
          bind(UserServiceAsync.class).toInstance(userService);
          bind(SiteServiceAsync.class).toInstance(siteService);
          bind(I18nServiceAsync.class).toInstance(i18nService);
        } catch (MalformedURLException e) {
          LOG.error("Malformed URL", e);
        }
      };
    });
  }

  /**
   * The main method.
   *
   * @param args
   *          the arguments
   * @throws InvalidSyntaxException
   *           the invalid syntax exception
   * @throws ExecutionException
   *           the execution exception
   * @throws MalformedURLException
   *           the malformed url exception
   */
  public static void main(final String[] args)
      throws InvalidSyntaxException, ExecutionException, MalformedURLException {

    initServices();

    // TODO: integrate jline or similar
    // http://jline.sourceforge.net/index.html

    // http://sourceforge.net/projects/javacurses/
    // http://massapi.com/class/jcurses/widgets/Button.java.html

    // Create an empty command set
    final Set<Command> cs = new HashSet<Command>();

    // Create the interpreter
    final NaturalCLI nc = new NaturalCLI(cs);

    // Add the commands that can be understood
    cs.add(new HelpCommand(cs)); // help
    cs.add(new HTMLHelpCommand(cs)); // htmlhelp
    cs.add(new HelloWorldCommand());
    // cs.add(new SleepCommand()); // sleep <seconds:number>

    // A script can be useful for kune
    cs.add(new ExecuteFileCommand(nc)); // execute file <filename:string>

    // kune specific commands
    cs.add(injector.getInstance(GetInitDataCommand.class));
    cs.add(injector.getInstance(GetI18nLangCommand.class));
    cs.add(injector.getInstance(AuthCommand.class));

    // Execute the command line
    nc.execute(args, 0);
  }
}
