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

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class HelloWorldPresenter.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class HelloWorldPresenter extends
    Presenter<HelloWorldPresenter.HelloWorldView, HelloWorldPresenter.HelloWorldProxy> {

  /**
   * The Interface HelloWorldProxy.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  @ProxyCodeSplit
  public interface HelloWorldProxy extends Proxy<HelloWorldPresenter> {
  }

  /**
   * The Interface HelloWorldView.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface HelloWorldView extends View {
  }

  /**
   * Instantiates a new hello world presenter.
   * 
   * @param eventBus
   *          the event bus
   * @param view
   *          the view
   * @param proxy
   *          the proxy
   */
  @Inject
  public HelloWorldPresenter(final EventBus eventBus, final HelloWorldView view,
      final HelloWorldProxy proxy) {
    super(eventBus, view, proxy);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.gwtplatform.mvp.client.Presenter#revealInParent()
   */
  @Override
  protected void revealInParent() {
    RevealRootContentEvent.fire(this, this);
  }
}