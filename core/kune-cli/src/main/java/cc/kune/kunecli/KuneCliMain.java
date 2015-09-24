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
import java.util.LinkedHashSet;
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
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.googlecode.gwtrpccommlayer.client.GwtRpcService;
import com.googlecode.gwtrpccommlayer.client.Module;

import cc.kune.core.client.rpcservices.ContentServiceAsync;
import cc.kune.core.client.rpcservices.GroupServiceAsync;
import cc.kune.core.client.rpcservices.I18nServiceAsync;
import cc.kune.core.client.rpcservices.InvitationServiceAsync;
import cc.kune.core.client.rpcservices.SiteServiceAsync;
import cc.kune.core.client.rpcservices.SocialNetServiceAsync;
import cc.kune.core.client.rpcservices.UserServiceAsync;
import cc.kune.kunecli.cmds.AuthCommand;
import cc.kune.kunecli.cmds.GroupsCount;
import cc.kune.kunecli.cmds.GroupsReindex;
import cc.kune.kunecli.cmds.InviteCommand;
import cc.kune.kunecli.cmds.ReloadPropertiesCommand;
import cc.kune.kunecli.cmds.UsersCount;
import cc.kune.kunecli.cmds.UsersReindex;
import cc.kune.kunecli.cmds.WaveToDirCommand;

/**
 * The Class KuneCliMain.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class KuneCliMain {

  private static Injector injector;

  /** The Constant LOG. */
  public static final Log LOG = LogFactory.getLog(KuneCliMain.class);

  /** The Constant SERVICE_PREFFIX. */
  public static String SERVER_PREFFIX = "http://127.0.0.1:8888";
  public static String SERVICE_PREFFIX = setServicePrefix();

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
      @Override
      protected void configure() {
        super.configure();
        bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);

      }
    });

    final GwtRpcService service = partentInjector.getInstance(GwtRpcService.class);
    injector = partentInjector.createChildInjector(new Module() {
      @Override
      protected void configure() {
        try {
          bind(UserServiceAsync.class).toInstance(
              service.create(new URL(SERVICE_PREFFIX + "UserService"), UserServiceAsync.class));
          bind(GroupServiceAsync.class).toInstance(
              service.create(new URL(SERVICE_PREFFIX + "GroupService"), GroupServiceAsync.class));
          bind(SocialNetServiceAsync.class).toInstance(service.create(
              new URL(SERVICE_PREFFIX + "SocialNetService"), SocialNetServiceAsync.class));
          bind(SiteServiceAsync.class).toInstance(
              service.create(new URL(SERVICE_PREFFIX + "SiteService"), SiteServiceAsync.class));
          bind(I18nServiceAsync.class).toInstance(
              service.create(new URL(SERVICE_PREFFIX + "I18nService"), I18nServiceAsync.class));
          bind(ContentServiceAsync.class).toInstance(
              service.create(new URL(SERVICE_PREFFIX + "ContentService"), ContentServiceAsync.class));
          bind(InvitationServiceAsync.class).toInstance(service.create(
              new URL(SERVICE_PREFFIX + "InvitationService"), InvitationServiceAsync.class));
          // TODO: Add its dependencies:
          // bind(UpDownServiceAsync.class).toInstance(
          // service.create(new URL(SERVICE_PREFFIX + "UpDownService"),
          // UpDownServiceAsync.class));
        } catch (final MalformedURLException e) {
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

    final String serverPrefix = System.getenv("KUNE_SERVER_URL");
    if (serverPrefix != null) {
      SERVER_PREFFIX = serverPrefix;
      SERVICE_PREFFIX = setServicePrefix();
      LOG.debug("Using server URL: " + SERVER_PREFFIX);
      LOG.debug("Using service URL: " + SERVICE_PREFFIX);
    }

    initServices();

    // TODO: integrate jline or similar?
    // http://jline.sourceforge.net/index.html
    // http://sourceforge.net/projects/javacurses/
    // http://massapi.com/class/jcurses/widgets/Button.java.html

    // Create an empty command set
    final Set<Command> cs = new LinkedHashSet<Command>();

    // Create the interpreter
    final NaturalCLI nc = new NaturalCLI(cs);

    // Add the commands that can be understood
    cs.add(new HelpCommand(cs)); // help
    cs.add(new HTMLHelpCommand(cs)); // htmlhelp
    // A script can be useful for kune
    cs.add(new ExecuteFileCommand(nc)); // execute file <filename:string>
    // cs.add(new HelloWorldCommand());

    // kune specific commands
    cs.add(injector.getInstance(AuthCommand.class));
    cs.add(injector.getInstance(InviteCommand.class));
    // Not working right now:
    // cs.add(injector.getInstance(SiteReindex.class));
    cs.add(injector.getInstance(GroupsCount.class));
    cs.add(injector.getInstance(GroupsReindex.class));
    cs.add(injector.getInstance(UsersCount.class));
    cs.add(injector.getInstance(UsersReindex.class));
    cs.add(injector.getInstance(ReloadPropertiesCommand.class));
    cs.add(injector.getInstance(WaveToDirCommand.class));

    // As the return type of these commands are not java.io.Serializable (and
    // instead GWT's IsSerializable) the return part of this cmds fails
    // cs.add(injector.getInstance(GetInitDataCommand.class));
    // cs.add(injector.getInstance(GetI18nLangCommand.class));

    // Execute the command line
    nc.execute(args, 0);
  }

  private static String setServicePrefix() {
    return SERVER_PREFFIX + "/ws/";
  }
}
