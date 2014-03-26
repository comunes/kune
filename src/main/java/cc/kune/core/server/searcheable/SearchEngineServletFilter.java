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
package cc.kune.core.server.searcheable;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.client.state.SiteParameters;

import com.google.inject.Singleton;

/**
 * The Class SearchEngineServletFilter. based in <a href=
 * "https://developers.google.com/webmasters/ajax-crawling/docs/html-snapshot"
 * >this</a>, <a href=
 * "http://coding.smashingmagazine.com/2011/09/27/searchable-dynamic-content-with-ajax-crawling/"
 * >this</a> and <a href=
 * "http://code.google.com/p/google-web-toolkit/source/browse/branches/crawlability/samples/showcase/src/com/google/gwt/sample/showcase/server/CrawlServlet.java?r=6231"
 * >this</a>.
 * 
 * This filter intercepts kune.example.com/* looking for _escaped_fragment_
 * parameter. See: src/main/webapp/WEB-INF/web.xml
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class SearchEngineServletFilter implements Filter, SearchEngineServletFilterMBean {

  /** The Constant LOG. */
  public static final Log LOG = LogFactory.getLog(SearchEngineServletFilter.class);

  /** The Constant SEARCH_ENGINE_FILTER_ATTRIBUTE. */
  public static final String SEARCH_ENGINE_FILTER_ATTRIBUTE = "searchEngineFilterAttribute";

  /** The enabled. */
  private boolean enabled;

  /** The filter config. */
  private FilterConfig filterConfig;

  /*
   * (non-Javadoc)
   * 
   * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
   * javax.servlet.ServletResponse, javax.servlet.FilterChain)
   */
  @Override
  public void destroy() {
    this.filterConfig = null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
   * javax.servlet.ServletResponse, javax.servlet.FilterChain)
   */
  @Override
  public void doFilter(final ServletRequest request, final ServletResponse response,
      final FilterChain chain) throws IOException, ServletException, DefaultException {
    if (filterConfig == null) {
      return;
    }

    if (request instanceof HttpServletRequest) {

      final HttpServletRequest httpReq = (HttpServletRequest) request;
      final StringBuffer url = httpReq.getRequestURL();

      final String queryString = httpReq.getQueryString();

      if ((queryString != null) && (queryString.contains(SiteParameters.ESCAPED_FRAGMENT_PARAMETER))) {
        if (!enabled) {
          final HttpServletResponse httpRes = (HttpServletResponse) response;
          httpRes.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE,
              "Search Engine service disabled temporally");
          return;
        }
        // rewrite the URL back to the original #! version
        // remember to unescape any %XX characters
        final String urlWithEscapedFragment = request.getParameter(SiteParameters.ESCAPED_FRAGMENT_PARAMETER);
        final String newUrl = url.append("?").append(queryString).toString().replaceFirst(
            SiteParameters.ESCAPED_FRAGMENT_PARAMETER, SiteParameters.NO_UA_CHECK).replaceFirst("/ws",
            "")
            + "#" + urlWithEscapedFragment;

        LOG.info("New url with hash: " + newUrl);

        final String page = "In development";
        // return the snapshot
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.getOutputStream().write(page.getBytes());
      } else {
        try {
          // not an _escaped_fragment_ URL, so move up the chain of
          // servlet (filters)
          chain.doFilter(request, response);
        } catch (final ServletException e) {
          LOG.error("Servlet exception caught: " + e);
        }
      }
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
   */
  @Override
  public void init(final FilterConfig filterConfig) throws ServletException {
    this.filterConfig = filterConfig;
    enabled = true;
    // As this object is not created by guice, we cannot use injection, so
    // we store this object in context and we can retrieve it later
    filterConfig.getServletContext().setAttribute(SEARCH_ENGINE_FILTER_ATTRIBUTE, this);
  }

}
