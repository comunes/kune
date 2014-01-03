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
package cc.kune.core.server.rack.filters.gwts;

import java.lang.reflect.Method;
import java.util.LinkedList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cc.kune.core.client.errors.DefaultException;

import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.server.rpc.RPCRequest;
import com.googlecode.gwtrpccommlayer.server.GwtRpcCommLayerServlet;
import com.googlecode.gwtrpccommlayer.shared.GwtRpcCommLayerPojoRequest;

/**
 * The Class DelegatedRemoteServlet extends GwtRpcCommLayerServlet so allow to
 * call GWT RPC calls from other systems different than the browser. Check :
 * http://googlewebtoolkit.blogspot.com.es/2010_07_01_archive.html
 */
public class DelegatedRemoteServlet extends GwtRpcCommLayerServlet {
  public static final Log LOG = LogFactory.getLog(DelegatedRemoteServlet.class);
  private static final long serialVersionUID = -7646054921925214953L;
  private transient RemoteService service;

  private String serviceName;
  private ServletContext servletContext;

  public DelegatedRemoteServlet(final Object servlet) {
    super(servlet);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.googlecode.gwtrpccommlayer.server.GwtRpcCommLayerServlet#
   * allowGwtRpcPojoRequest()
   */
  @Override
  protected boolean allowGwtRpcPojoRequest() {
    // If we want to restrict the external rpc calls per method or classs
    return super.allowGwtRpcPojoRequest();
  }

  @Override
  protected void doUnexpectedFailure(final Throwable except) {
    final Throwable cause = except.getCause();
    LOG.info("Exception " + except.getMessage() + cause != null ? " cause: " + cause : "");
    super.doUnexpectedFailure(except);
  }

  @Override
  public void executePojoRequest(final GwtRpcCommLayerPojoRequest pojoRequest,
      final HttpServletRequest req, final HttpServletResponse resp) throws Throwable {
    final Method method = getMethod(pojoRequest);
    LOG.info("Method: " + method.getName());
    super.executePojoRequest(pojoRequest, req, resp);
  }

  @SuppressWarnings({ "unused", "rawtypes" })
  @Override
  protected Method getMethod(final GwtRpcCommLayerPojoRequest stressTestRequest)
      throws NoSuchMethodException, ClassNotFoundException {
    final int count = 0;
    final Class<?> paramClasses[] = new Class[stressTestRequest.getMethodParameters().size()];

    final LinkedList<Class<?>> lstParameterClasses = new LinkedList<Class<?>>();
    for (final String methodName : stressTestRequest.getParameterClassNames()) {
      lstParameterClasses.add(Class.forName(methodName));
    }

    final Class[] arrParameterClasses = lstParameterClasses.toArray(new Class[0]);
    // patched here for kune
    return service.getClass().getMethod(stressTestRequest.getMethodName(), arrParameterClasses);
  }

  @Override
  public ServletContext getServletContext() {
    return servletContext;
  }

  @Override
  public String getServletName() {
    return serviceName;
  }

  @Override
  public void log(final String message) {
    // super.log(message);
    LOG.info(message);
  }

  @Override
  public void log(final String message, final Throwable t) {
    LOG.info(message);
    // super.log(message, t);
  }

  @Override
  public String processCall(final String payload) throws SerializationException, DefaultException {
    try {
      final RPCRequest rpcRequest = RPC.decodeRequest(payload, service.getClass());
      return RPC.invokeAndEncodeResponse(service, rpcRequest.getMethod(), rpcRequest.getParameters());
    } catch (final IncompatibleRemoteServiceException ex) {
      return RPC.encodeResponseForFailure(null, ex);
    }
  }

  public void setService(final RemoteService service, final String serviceName) {
    this.service = service;
    this.serviceName = serviceName;
  }

  public void setServletContext(final ServletContext servletContext) {
    this.servletContext = servletContext;
  }
}
