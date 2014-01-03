/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
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
package com.example.server;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.waveprotocol.box.server.CoreSettings;
import org.waveprotocol.box.server.rpc.AuthenticationServlet;
import org.waveprotocol.box.server.rpc.ServerRpcProvider;
import org.waveprotocol.box.server.rpc.SignOutServlet;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.google.inject.servlet.GuiceFilter;
import com.google.inject.servlet.ServletModule;

// TODO: Auto-generated Javadoc
/**
 * The Class CustomGuiceFilter.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class CustomGuiceFilter extends GuiceFilter {

  /**
   * The Class HelloWorldInjected.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public static class HelloWorldInjected {

    /** The http addresses. */
    private final List<String> httpAddresses;

    /**
     * A Constructor with some sample WIAB instances and params injected.
     * 
     * @param auth
     *          the auth
     * @param signOut
     *          the sign out
     * @param httpAddresses
     *          the http addresses
     */
    @Inject
    public HelloWorldInjected(final AuthenticationServlet auth, final SignOutServlet signOut,
        @Named(CoreSettings.HTTP_FRONTEND_ADDRESSES) final List<String> httpAddresses) {
      this.httpAddresses = httpAddresses;
    }

    /**
     * Gets the http addresses.
     * 
     * @return the http addresses
     */
    public List<String> getHttpAddresses() {
      return httpAddresses;
    }
  }

  /**
   * An additional sample Guice Module.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public static class HelloWorldModule extends AbstractModule {

    /*
     * (non-Javadoc)
     * 
     * @see com.google.inject.AbstractModule#configure()
     */
    @Override
    protected void configure() {
      bind(HelloWorldInjected.class).in(Singleton.class);
    }
  }

  /**
   * A sample servlet that uses {@link HelloWorldModule} and.
   * 
   * {@link HelloWorldInjected}
   */
  @Singleton
  @SuppressWarnings("serial")
  public static class HelloWorldServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /** The hw. */
    private final HelloWorldInjected hw;

    /**
     * Instantiates a new hello world servlet.
     * 
     * @param hw
     *          the hw
     */
    @Inject
    public HelloWorldServlet(final HelloWorldInjected hw) {
      this.hw = hw;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
     * , javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doGet(final javax.servlet.http.HttpServletRequest req,
        final javax.servlet.http.HttpServletResponse resp) throws ServletException, java.io.IOException {
      final PrintWriter writer = resp.getWriter();
      writer.println("Hello world " + hw.getHttpAddresses().get(0) + "!");
      writer.close();
    };

  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.inject.servlet.GuiceFilter#init(javax.servlet.FilterConfig)
   */
  @Override
  public void init(final FilterConfig filterConfig) throws ServletException {
    super.init(filterConfig);
    // We retrieve the WIAB injector from the context
    final Injector injector = (Injector) filterConfig.getServletContext().getAttribute(
        ServerRpcProvider.INJECTOR_ATTRIBUTE);
    // We register additional Guice modules
    injector.createChildInjector(new HelloWorldModule(), new ServletModule() {
      @Override
      protected void configureServlets() {
        // Additional servlets
        serve("/hw/*").with(HelloWorldServlet.class);
      }
    });
  }
}
