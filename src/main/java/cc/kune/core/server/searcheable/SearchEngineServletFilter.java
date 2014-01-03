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
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

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
import org.w3c.css.sac.CSSException;
import org.w3c.css.sac.CSSParseException;
import org.w3c.css.sac.ErrorHandler;

import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.client.state.SiteParameters;

import com.gargoylesoftware.htmlunit.AlertHandler;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.Cache;
import com.gargoylesoftware.htmlunit.IncorrectnessListener;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.OnbeforeunloadHandler;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebClientOptions;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Class SearchEngineServletFilter. based in
 * https://developers.google.com/webmasters/ajax-crawling/docs/html-snapshot
 * http
 * ://coding.smashingmagazine.com/2011/09/27/searchable-dynamic-content-with-
 * ajax-crawling/ and
 * http://code.google.com/p/google-web-toolkit/source/browse/branches
 * /crawlability
 * /samples/showcase/src/com/google/gwt/sample/showcase/server/CrawlServlet
 * .java?r=6231
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class SearchEngineServletFilter implements Filter, OnbeforeunloadHandler, AlertHandler,
    IncorrectnessListener, SearchEngineServletFilterMBean {

  /**
   * The Class QuietCssErrorHandler.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public class QuietCssErrorHandler implements ErrorHandler {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.w3c.css.sac.ErrorHandler#error(org.w3c.css.sac.CSSParseException)
     */
    @Override
    public void error(final CSSParseException e) throws CSSException {

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.w3c.css.sac.ErrorHandler#fatalError(org.w3c.css.sac.CSSParseException
     * )
     */
    @Override
    public void fatalError(final CSSParseException e) throws CSSException {
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.w3c.css.sac.ErrorHandler#warning(org.w3c.css.sac.CSSParseException)
     */
    @Override
    public void warning(final CSSParseException e) throws CSSException {
    }
  }

  /** The Constant INIT_CACHE_SIZE. */
  private static final int INIT_CACHE_SIZE = 100;

  /** The Constant LOG. */
  public static final Log LOG = LogFactory.getLog(SearchEngineServletFilter.class);

  /** The Constant SEARCH_ENGINE_FILTER_ATTRIBUTE. */
  public static final String SEARCH_ENGINE_FILTER_ATTRIBUTE = "searchEngineFilterAttribute";

  /** The Constant THREADS. */
  private static final int THREADS = 2;

  /** The Constant TIMEOUT. */
  private static final int TIMEOUT = 20000;

  /** The cache. */
  private Cache cache;

  /** The client. */
  private WebClient client;

  /** The enabled. */
  private boolean enabled;

  /** The executor. */
  private ExecutorService executor;

  /** The executor size. */
  private int executorSize;

  /** The filter config. */
  private FilterConfig filterConfig;

  /** The wait for unload. */
  private final Object waitForUnload = new Object();

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.searcheable.SearchEngineServletFilterMBean#clearCache()
   */
  @Override
  public void clearCache() {
    LOG.info("Clearing cache");
    cache.clear();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.searcheable.SearchEngineServletFilterMBean#closeAllWindows
   * ()
   */
  @Override
  public void closeAllWindows() {
    client.closeAllWindows();
  }

  /*
   * (non-Javadoc)
   * 
   * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
   * javax.servlet.ServletResponse, javax.servlet.FilterChain)
   */
  @Override
  public void destroy() {
    this.filterConfig = null;
    shutdownAndAwaitTermination(executor);
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
        final Future<String> result = executor.submit(new Callable<String>() {

          @Override
          public String call() throws Exception {
            // rewrite the URL back to the original #! version
            // remember to unescape any %XX characters
            final String urlWithEscapedFragment = request.getParameter(SiteParameters.ESCAPED_FRAGMENT_PARAMETER);
            final String newUrl = url.append("?").append(queryString).toString().replaceFirst(
                SiteParameters.ESCAPED_FRAGMENT_PARAMETER, SiteParameters.NO_UA_CHECK).replaceFirst(
                "/ws", "")
                + "#" + urlWithEscapedFragment;

            LOG.info("New url with hash: " + newUrl);
            try {
              final WebRequest webReq = new WebRequest(new URL(newUrl));
              final HtmlPage page = client.getPage(webReq);

              client.waitForBackgroundJavaScriptStartingBefore(18000);

              // return the snapshot
              response.setCharacterEncoding("UTF-8");
              response.setContentType("text/html; charset=UTF-8");
              client.getAjaxController().processSynchron(page, webReq, false);
              final String pageAsXml = page.asXml().toString();
              page.cleanUp();
              page.remove();
              client.closeAllWindows();
              return pageAsXml;
            } catch (final IOException e) {
              LOG.debug("Error getting page: ", e);
              throw e;
            }
          }

        });
        try {
          final String page = result.get();
          // return the snapshot
          response.setCharacterEncoding("UTF-8");
          response.setContentType("text/html; charset=UTF-8");
          response.getOutputStream().write(page.getBytes());
        } catch (final InterruptedException e) {
          LOG.error(e);
        } catch (final ExecutionException e) {
          LOG.error(e);
          // e.printStackTrace();
        }
      } else {
        try {
          // LOG.warn("Url without hash");
          // not an _escaped_fragment_ URL, so move up the chain of
          // servlet
          // (filters)
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
   * @see
   * cc.kune.core.server.searcheable.SearchEngineServletFilterMBean#getCacheMaxSize
   * ()
   */
  @Override
  public int getCacheMaxSize() {
    return cache.getMaxSize();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.searcheable.SearchEngineServletFilterMBean#getCacheSize
   * ()
   */
  @Override
  public int getCacheSize() {
    return cache.getSize();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.searcheable.SearchEngineServletFilterMBean#
   * getExecutorThreadSize()
   */
  @Override
  public int getExecutorThreadSize() {
    return executorSize;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.gargoylesoftware.htmlunit.AlertHandler#handleAlert(com.gargoylesoftware
   * .htmlunit.Page, java.lang.String)
   */
  @Override
  public void handleAlert(final Page page, final String message) {
    LOG.error("Alert: " + message);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.gargoylesoftware.htmlunit.OnbeforeunloadHandler#handleEvent(com.
   * gargoylesoftware.htmlunit.Page, java.lang.String)
   */
  @Override
  public boolean handleEvent(final Page page, final String returnValue) {
    synchronized (waitForUnload) {
      waitForUnload.notifyAll();
    }
    return true;
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
    cache = new Cache();
    setCacheMaxSize(INIT_CACHE_SIZE);
    initWebClient();
    executor = Executors.newFixedThreadPool(THREADS);
    executorSize = THREADS;
    // As this object is not created by guice, we cannot use injection, so
    // we store this object in context and we can retrieve it later
    filterConfig.getServletContext().setAttribute(SEARCH_ENGINE_FILTER_ATTRIBUTE, this);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.searcheable.SearchEngineServletFilterMBean#initWebClient
   * ()
   */
  @Override
  public void initWebClient() {
    LOG.info("Initializing web client");
    client = new WebClient(BrowserVersion.FIREFOX_17);
    client.setCache(cache);
    final WebClientOptions options = client.getOptions();
    options.setUseInsecureSSL(true);
    client.setCssErrorHandler(new QuietCssErrorHandler());
    options.setCssEnabled(true);
    client.setJavaScriptTimeout(20000);
    options.setThrowExceptionOnScriptError(true);
    options.setThrowExceptionOnFailingStatusCode(false);
    options.setJavaScriptEnabled(true);
    options.setRedirectEnabled(true);
    client.setOnbeforeunloadHandler(SearchEngineServletFilter.this);
    client.setAlertHandler(SearchEngineServletFilter.this);
    client.setIncorrectnessListener(SearchEngineServletFilter.this);
    options.setTimeout(TIMEOUT);
    client.setAjaxController(new NicelyResynchronizingAjaxController());
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.searcheable.SearchEngineServletFilterMBean#isEnabled()
   */
  @Override
  public boolean isEnabled() {
    return enabled;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.gargoylesoftware.htmlunit.IncorrectnessListener#notify(java.lang.String
   * , java.lang.Object)
   */
  @Override
  public void notify(final String message, final Object origin) {
    if ("Obsolete content type encountered: 'application/x-javascript'.".equals(message)
        || "Obsolete content type encountered: 'application/javascript'.".equals(message)
        || "Obsolete content type encountered: 'text/javascript'.".equals(message)) {
      // silently eat warning about text/javascript MIME type
      return;
    }
    LOG.warn(message);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.searcheable.SearchEngineServletFilterMBean#setCacheMaxSize
   * (int)
   */
  @Override
  public void setCacheMaxSize(final int size) {
    cache.setMaxSize(size);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.searcheable.SearchEngineServletFilterMBean#setEnabled
   * (boolean)
   */
  @Override
  public void setEnabled(final boolean enabled) {
    this.enabled = enabled;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.searcheable.SearchEngineServletFilterMBean#
   * setExecutorThreadSize(int)
   */
  @Override
  public void setExecutorThreadSize(final int size) {
    LOG.info("Setting executors size: " + size);
    shutdownAndAwaitTermination(executor);
    executor = Executors.newFixedThreadPool(size);
    executorSize = size;
  }

  /**
   * Shutdown and await termination.
   * 
   * @param pool
   *          the pool
   */
  void shutdownAndAwaitTermination(final ExecutorService pool) {
    pool.shutdown(); // Disable new tasks from being submitted
    try {
      // Wait a while for existing tasks to terminate
      if (!pool.awaitTermination(30, TimeUnit.SECONDS)) {
        pool.shutdownNow(); // Cancel currently executing tasks
        // Wait a while for tasks to respond to being cancelled
        if (!pool.awaitTermination(30, TimeUnit.SECONDS)) {
          System.err.println("Pool did not terminate");
        }
      }
    } catch (final InterruptedException ie) {
      // (Re-)Cancel if current thread also interrupted
      pool.shutdownNow();
      // Preserve interrupt status
      Thread.currentThread().interrupt();
    }
  }

}
