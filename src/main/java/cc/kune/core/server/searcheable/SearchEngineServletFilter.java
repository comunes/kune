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
package cc.kune.core.server.searcheable;

import java.io.IOException;
import java.net.URL;
import java.security.GeneralSecurityException;
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

import cc.kune.core.client.state.SiteParameters;

import com.gargoylesoftware.htmlunit.AlertHandler;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.Cache;
import com.gargoylesoftware.htmlunit.IncorrectnessListener;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.OnbeforeunloadHandler;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.inject.Singleton;

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
 */
@Singleton
public class SearchEngineServletFilter implements Filter, OnbeforeunloadHandler, AlertHandler, IncorrectnessListener,
        SearchEngineServletFilterMBean {

    public class QuietCssErrorHandler implements ErrorHandler {

        @Override
        public void error(final CSSParseException e) throws CSSException {

        }

        @Override
        public void fatalError(final CSSParseException e) throws CSSException {
        }

        @Override
        public void warning(final CSSParseException e) throws CSSException {
        }
    }

    private static final int INIT_CACHE_SIZE = 100;

    public static final Log LOG = LogFactory.getLog(SearchEngineServletFilter.class);

    public static final String SEARCH_ENGINE_FILTER_ATTRIBUTE = "searchEngineFilterAttribute";
    private static final int THREADS = 2;

    private static final int TIMEOUT = 20000;

    private Cache cache;

    private WebClient client;

    private boolean enabled;

    private ExecutorService executor;

    private int executorSize;

    private FilterConfig filterConfig;

    private final Object waitForUnload = new Object();

    @Override
    public void clearCache() {
        LOG.info("Clearing cache");
        cache.clear();
    }

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

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException {
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
                    e.printStackTrace();
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

    @Override
    public int getCacheMaxSize() {
        return cache.getMaxSize();
    }

    @Override
    public int getCacheSize() {
        return cache.getSize();
    }

    @Override
    public int getExecutorThreadSize() {
        return executorSize;
    }

    @Override
    public void handleAlert(final Page page, final String message) {
        LOG.error("Alert: " + message);
    }

    @Override
    public boolean handleEvent(final Page page, final String returnValue) {
        synchronized (waitForUnload) {
            waitForUnload.notifyAll();
        }
        return true;
    }

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

    @Override
    public void initWebClient() {
        LOG.info("Initializing web client");
        client = new WebClient(BrowserVersion.FIREFOX_3_6);
        client.setCache(cache);
        try {
            client.setUseInsecureSSL(true);
        } catch (final GeneralSecurityException e) {
            LOG.error("Servlet exception caught: " + e);
        }
        client.setCssErrorHandler(new QuietCssErrorHandler());
        client.setCssEnabled(true);
        client.setJavaScriptTimeout(20000);
        client.setThrowExceptionOnScriptError(true);
        client.setThrowExceptionOnFailingStatusCode(false);
        client.setJavaScriptEnabled(true);
        client.setRedirectEnabled(true);
        client.setOnbeforeunloadHandler(SearchEngineServletFilter.this);
        client.setAlertHandler(SearchEngineServletFilter.this);
        client.setIncorrectnessListener(SearchEngineServletFilter.this);
        client.setTimeout(TIMEOUT);
        client.setAjaxController(new NicelyResynchronizingAjaxController());
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

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

    @Override
    public void setCacheMaxSize(final int size) {
        cache.setMaxSize(size);
    }

    @Override
    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public void setExecutorThreadSize(final int size) {
        LOG.info("Setting executors size: " + size);
        shutdownAndAwaitTermination(executor);
        executor = Executors.newFixedThreadPool(size);
        executorSize = size;
    }

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
