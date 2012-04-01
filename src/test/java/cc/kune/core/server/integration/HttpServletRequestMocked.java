/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package cc.kune.core.server.integration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

public class HttpServletRequestMocked implements HttpServletRequest {

  @Override
  public boolean authenticate(final HttpServletResponse arg0) throws IOException, ServletException {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public AsyncContext getAsyncContext() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object getAttribute(final String arg0) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Enumeration<String> getAttributeNames() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getAuthType() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getCharacterEncoding() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int getContentLength() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public String getContentType() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getContextPath() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Cookie[] getCookies() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public long getDateHeader(final String arg0) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public DispatcherType getDispatcherType() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getHeader(final String arg0) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Enumeration<String> getHeaderNames() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Enumeration<String> getHeaders(final String arg0) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ServletInputStream getInputStream() throws IOException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int getIntHeader(final String arg0) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public String getLocalAddr() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Locale getLocale() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Enumeration<Locale> getLocales() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getLocalName() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int getLocalPort() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public String getMethod() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getParameter(final String arg0) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Map<String, String[]> getParameterMap() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Enumeration<String> getParameterNames() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String[] getParameterValues(final String arg0) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Part getPart(final String arg0) throws IOException, ServletException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Collection<Part> getParts() throws IOException, ServletException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getPathInfo() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getPathTranslated() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getProtocol() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getQueryString() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public BufferedReader getReader() throws IOException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getRealPath(final String arg0) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getRemoteAddr() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getRemoteHost() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int getRemotePort() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public String getRemoteUser() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public RequestDispatcher getRequestDispatcher(final String arg0) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getRequestedSessionId() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getRequestURI() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public StringBuffer getRequestURL() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getScheme() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getServerName() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int getServerPort() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public ServletContext getServletContext() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getServletPath() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public HttpSession getSession() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public HttpSession getSession(final boolean arg0) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Principal getUserPrincipal() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean isAsyncStarted() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isAsyncSupported() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isRequestedSessionIdFromCookie() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isRequestedSessionIdFromUrl() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isRequestedSessionIdFromURL() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isRequestedSessionIdValid() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isSecure() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isUserInRole(final String arg0) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void login(final String arg0, final String arg1) throws ServletException {
    // TODO Auto-generated method stub

  }

  @Override
  public void logout() throws ServletException {
    // TODO Auto-generated method stub

  }

  @Override
  public void removeAttribute(final String arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public void setAttribute(final String arg0, final Object arg1) {
    // TODO Auto-generated method stub

  }

  @Override
  public void setCharacterEncoding(final String arg0) throws UnsupportedEncodingException {
    // TODO Auto-generated method stub

  }

  @Override
  public AsyncContext startAsync() throws IllegalStateException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public AsyncContext startAsync(final ServletRequest arg0, final ServletResponse arg1)
      throws IllegalStateException {
    // TODO Auto-generated method stub
    return null;
  }

}
