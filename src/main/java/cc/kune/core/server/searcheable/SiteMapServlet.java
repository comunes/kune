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
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cc.kune.common.shared.utils.TextUtils;
import cc.kune.core.client.state.SiteTokens;
import cc.kune.core.server.utils.AbsoluteFileDownloadUtils;
import cc.kune.core.shared.domain.GroupListMode;
import cc.kune.core.shared.dto.GroupType;
import cc.kune.domain.Container;
import cc.kune.domain.Content;
import cc.kune.domain.Group;
import cc.kune.domain.finders.ContainerFinder;
import cc.kune.domain.finders.ContentFinder;
import cc.kune.domain.finders.GroupFinder;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The Class SiteMapServlet generate a sitemap.xml for the kune site
 * http://en.wikipedia.org/wiki/Sitemaps
 * 
 * Based in: http://betweengo.com/category/java/servlet/
 */
@Singleton
public class SiteMapServlet extends HttpServlet {
  enum ChangeFreq {
    always, daily, hourly, monthly, never, weekly, yearly
  }

  private static final String CHANGEFREQ_BEGIN = "<changefreq>";
  private static final String CHANGEFREQ_END = "</changefreq>";
  @Inject
  private static ContainerFinder containerFinder;
  @Inject
  private static ContentFinder contentFinder;
  private static final String CONTENTS_PRIORITY = "0.8";
  @Inject
  private static AbsoluteFileDownloadUtils fileDownloadUtils;
  @Inject
  private static GroupFinder groupFinder;
  private static final String GROUPS_PRIORITY = "0.9";
  private static final String LASTMOD_BEGIN = "<lastmod>";
  private static final String LASTMOD_END = "</lastmod>";
  private static final String LOC_BEGIN = "<loc>";
  private static final String LOC_END = "</loc>";
  private static final String MIME_TYPE_XML = "application/xml";
  private static final String PRIORITY_BEGIN = "<priority>";
  private static final String PRIORITY_END = "</priority>";
  private static final long serialVersionUID = 4564407387676356940L;
  private static final String SITE_MAP_BEGIN = "<urlset\n\txmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\"\n\txmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n\txsi:schemaLocation=\"http://www.sitemaps.org/schemas/sitemap/0.9\n\t\thttp://www.sitemaps.org/schemas/sitemap/0.9/sitemap.xsd\">";
  private static final String SITE_MAP_END = "</urlset>";
  private static final String SITE_MAP_XML_INFO = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
  private static final String SITE_PRIORITY = "1.0";
  public static final String[] siteUrls = new String[] { SiteTokens.HOME, SiteTokens.GROUP_HOME,
      SiteTokens.ABOUT_KUNE, SiteTokens.ASK_RESET_PASSWD, SiteTokens.NEW_GROUP, SiteTokens.REGISTER,
      SiteTokens.RESET_PASSWD, SiteTokens.SIGN_IN };
  private static final String URL_BEGIN = "<url>";
  private static final String URL_END = "</url>";

  /*
   * (non-Javadoc)
   * 
   * @see
   * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
   * javax.servlet.http.HttpServletResponse)
   */
  @Override
  public void doGet(final HttpServletRequest request, final HttpServletResponse response)
      throws IOException {
    response.setContentType(MIME_TYPE_XML);
    final PrintWriter out = response.getWriter();
    out.println(SITE_MAP_XML_INFO);
    out.println(SITE_MAP_BEGIN);

    final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    final String nowAsString = df.format(new Date());

    for (final String siteHash : siteUrls) {
      final String siteUri = fileDownloadUtils.getUrl(siteHash);
      outputPage(out, siteUri, SITE_PRIORITY, nowAsString, ChangeFreq.weekly);
    }

    // Groups
    final List<Group> groups = groupFinder.getAllExcept(GroupType.CLOSED);
    for (final Group group : groups) {
      final String groupUri = fileDownloadUtils.getUrl(group.getStateToken().toString());
      outputPage(out, groupUri, GROUPS_PRIORITY, nowAsString, ChangeFreq.daily);

      // Contents
      for (final Content content : contentFinder.allContentsInUserGroup(group.getId())) {
        if (content.getAccessLists().getViewers().getMode().equals(GroupListMode.EVERYONE)) {
          final String contentUri = fileDownloadUtils.getUrl(content.getStateToken().toString());
          outputPage(out, contentUri, CONTENTS_PRIORITY, df.format(content.getModifiedOn()),
              ChangeFreq.daily);
        }
      }

      // Containers
      for (final Container container : containerFinder.allContainersInUserGroup(group.getId())) {
        if (container.getAccessLists().getViewers().getMode().equals(GroupListMode.EVERYONE)) {
          final String containerUri = fileDownloadUtils.getUrl(container.getStateToken().toString());
          outputPage(out, containerUri, CONTENTS_PRIORITY, nowAsString, ChangeFreq.daily);
        }
      }
    }

    // FIXME maybe output public waves in the future

    out.println(SITE_MAP_END);
    out.close();
  }

  /**
   * Output page.
   * 
   * @param out
   *          the out
   * @param urlStart
   *          the url start
   * @param uri
   *          the uri
   * @param priority
   *          the priority
   */
  private void outputPage(final PrintWriter out, final String uri, final String priority,
      final String lastMod, final ChangeFreq changeFreq) {
    out.println(URL_BEGIN);
    out.println(LOC_BEGIN + uri + LOC_END);
    if (TextUtils.notEmpty(lastMod)) {
      out.println(LASTMOD_BEGIN + lastMod + LASTMOD_END);
    }
    out.println(CHANGEFREQ_BEGIN + changeFreq + CHANGEFREQ_END);
    out.println(PRIORITY_BEGIN + priority + PRIORITY_END);
    out.println(URL_END);
  }
}