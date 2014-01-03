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
package com.example.client;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.inject.client.Ginjector;
import com.google.inject.Singleton;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

// TODO: Auto-generated Javadoc
/**
 * Sample of GinModule (but with GWTPlatform).
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class HelloWordGinModule extends AbstractPresenterModule {

  /*
   * You have to add here all the GWTPresenters (as Provider or AsyncProvider)
   * in the injector. See the GWTPlatform doc
   */
  /**
   * The Interface HelloWordGinjector.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface HelloWordGinjector extends Ginjector {

    /**
     * Gets the hello world presenter.
     * 
     * @return the hello world presenter
     */
    AsyncProvider<HelloWorldPresenter> getHelloWorldPresenter();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.gwt.inject.client.AbstractGinModule#configure()
   */
  @Override
  protected void configure() {
    bindPresenter(HelloWorldPresenter.class, HelloWorldPresenter.HelloWorldView.class,
        HelloWorldPanel.class, HelloWorldPresenter.HelloWorldProxy.class);
    bind(HelloWorldActions.class).in(Singleton.class);
  }

}
