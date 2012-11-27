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

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.css.sac.CSSException;
import org.w3c.css.sac.CSSParseException;
import org.w3c.css.sac.ErrorHandler;

import cc.kune.core.client.state.SiteParameters;

import com.gargoylesoftware.htmlunit.AlertHandler;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.IncorrectnessListener;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.OnbeforeunloadHandler;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * The Class SearchEngineServletFilter. based in
 * https://developers.google.com/webmasters/ajax-crawling/docs/html-snapshot
 * http
 * ://coding.smashingmagazine.com/2011/09/27/searchable-dynamic-content-with-
 * ajax-crawling/
 */
public class SearchEngineServletFilter implements Filter, OnbeforeunloadHandler, AlertHandler,
    IncorrectnessListener {

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

  public static final Log LOG = LogFactory.getLog(SearchEngineServletFilter.class);

  private final Object waitForUnload = new Object();

  /*
   * (non-Javadoc)
   * 
   * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
   * javax.servlet.ServletResponse, javax.servlet.FilterChain)
   */
  @Override
  public void destroy() {
  }

  @Override
  public void doFilter(final ServletRequest request, final ServletResponse response,
      final FilterChain chain) throws IOException {
    if (request instanceof HttpServletRequest) {

      final HttpServletRequest httpReq = (HttpServletRequest) request;
      final StringBuffer url = httpReq.getRequestURL();

      final String queryString = httpReq.getQueryString();

      if ((queryString != null) && (queryString.contains(SiteParameters.ESCAPED_FRAGMENT_PARAMETER))) {
        // rewrite the URL back to the original #! version
        // remember to unescape any %XX characters
        final String urlWithEscapedFragment = request.getParameter(SiteParameters.ESCAPED_FRAGMENT_PARAMETER);

        final String newUrl = url.append("?").append(queryString).toString().replaceFirst(
            SiteParameters.ESCAPED_FRAGMENT_PARAMETER, SiteParameters.NO_UA_CHECK).replaceFirst("/ws",
            "")
            + "#" + urlWithEscapedFragment;

        LOG.info("New url with hash: " + newUrl);

        // use the headless browser to obtain an HTML snapshot
        final WebClient client = new WebClient(BrowserVersion.FIREFOX_3_6);

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
        client.setOnbeforeunloadHandler(this);
        client.setAlertHandler(this);
        client.setIncorrectnessListener(this);

        client.setAjaxController(new NicelyResynchronizingAjaxController());
        try {
          final WebRequest webReq = new WebRequest(new URL(newUrl));
          final HtmlPage page = client.getPage(webReq);

          client.waitForBackgroundJavaScriptStartingBefore(30000);

          // return the snapshot
          response.setCharacterEncoding("UTF-8");
          response.setContentType("text/html; charset=UTF-8");
          client.getAjaxController().processSynchron(page, webReq, false);

          response.getOutputStream().println(page.asXml());
          client.closeAllWindows();
        } catch (final IOException e) {
          LOG.debug("Error getting page: ", e);
        }
      } else {
        try {
          // LOG.warn("Url without hash");
          // not an _escaped_fragment_ URL, so move up the chain of servlet
          // (filters)
          chain.doFilter(request, response);
        } catch (final ServletException e) {
          LOG.error("Servlet exception caught: " + e);
        }
      }
    }
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

}
