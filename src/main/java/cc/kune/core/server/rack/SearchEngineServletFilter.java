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
package cc.kune.core.server.rack;

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

import cc.kune.core.shared.CoreConstants;

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
 */
public class SearchEngineServletFilter implements Filter, OnbeforeunloadHandler, AlertHandler,
    IncorrectnessListener {

  @SuppressWarnings("serial")
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

      final String url = ((HttpServletRequest) request).getRequestURL().toString();
      final URL urlEncoded = new URL(url);

      final String queryString = ((HttpServletRequest) request).getQueryString();

      if ((queryString != null) && (queryString.contains(CoreConstants.ESCAPED_FRAGMENT_PARAMETER))) {
        // rewrite the URL back to the original #! version
        // remember to unescape any %XX characters
        final String urlWithEscapedFragment = request.getParameter(CoreConstants.ESCAPED_FRAGMENT_PARAMETER);
        // FIXME: http/https check
        final String urlWithHashFragment = "http://" + urlEncoded.getHost() + ":" + urlEncoded.getPort()
            + "/#" + urlWithEscapedFragment;
        // final String urlWithHashFragment = "http://twitter.com/vjrj";

        LOG.warn("Url with hash: " + urlWithHashFragment);

        // use the headless browser to obtain an HTML snapshot
        final WebClient client = new WebClient(BrowserVersion.FIREFOX_3_6);

        // webClient.setAjaxController(new AjaxController() {
        // @Override
        // public boolean processSynchron(final HtmlPage page, final WebRequest
        // request,
        // final boolean async) {
        // return true;
        // }
        // });
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
          final WebRequest webReq = new WebRequest(new URL(urlWithHashFragment));
          final HtmlPage page = client.getPage(webReq);

          // client.waitForBackgroundJavaScript(30000);
          // client.waitForBackgroundJavaScriptStartingBefore(30000);

          // important! Give the headless browser enough time to execute
          // JavaScript
          // The exact time to wait may depend on your application.

          // try 20 times to wait .5 second each for filling the page.
          client.waitForBackgroundJavaScriptStartingBefore(20000);
          // for (int i = 0; i < 20; i++) {
          //
          // final HtmlElement btn =
          // page.getElementById(SitebarNewGroupLink.NEW_GROUP_BTN_ID);
          // if (btn != null) {
          // break;
          // }
          // synchronized (page) {
          // try {
          // System.out.println("wait, i: " + i);
          // page.wait(500);
          // } catch (final InterruptedException e) {
          // // TODO Auto-generated catch block
          // e.printStackTrace();
          // }
          // }
          // }
          // final int i =
          // client.waitForBackgroundJavaScriptStartingBefore(10000);
          // while (i > 0) {
          // i = client.waitForBackgroundJavaScriptStartingBefore(10000);
          //
          // if (i == 0) {
          // break;
          // }
          // synchronized (page) {
          // System.out.println("wait, i: " + i);
          // try {
          // page.wait(500);
          // } catch (final InterruptedException e) {
          // // TODO Auto-generated catch block
          // e.printStackTrace();
          // }
          // }
          // }

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
          LOG.warn("Url without hash");
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
    if ("Obsolete content type encountered: 'application/x-javascript'".equals(message)
        || "Obsolete content type encountered: 'application/javascript'.".equals(message)
        || "Obsolete content type encountered: 'text/javascript'.".equals(message)) {
      // silently eat warning about text/javascript MIME type
      return;
    }
    LOG.warn(message);
  }

}
