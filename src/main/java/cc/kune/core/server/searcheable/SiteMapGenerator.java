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

import java.io.File;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.waveprotocol.box.server.persistence.PersistenceException;
import org.waveprotocol.box.server.persistence.file.FileUtils;

import cc.kune.core.client.state.SiteTokens;
import cc.kune.core.server.LogThis;
import cc.kune.core.server.persist.KuneTransactional;
import cc.kune.core.server.properties.KuneProperties;
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
import com.redfin.sitemapgenerator.ChangeFreq;
import com.redfin.sitemapgenerator.WebSitemapGenerator;
import com.redfin.sitemapgenerator.WebSitemapUrl;

// TODO: Auto-generated Javadoc
/**
 * The Class SiteMapServlet generate a sitemap.xml for the kune site
 * http://en.wikipedia.org/wiki/Sitemaps
 * 
 * Inspired in: http://betweengo.com/category/java/servlet/ and for large sites:
 * http://dynamical.biz/blog/seo-technical/sitemap-strategy-large-sites-17.html
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
@LogThis
public class SiteMapGenerator implements SiteMapGeneratorMBean {

  /** The Constant CONTENTS_PRIORITY. */
  private static final Double CONTENTS_PRIORITY = 0.8;

  /** The Constant GROUPS_PRIORITY. */
  private static final Double GROUPS_PRIORITY = 0.9;

  /** The Constant LIMIT_OF_QUERY. */
  private static final int LIMIT_OF_QUERY = 100;

  /** The Constant LOG. */
  public static final Log LOG = LogFactory.getLog(SiteMapGenerator.class);

  /** The Constant SITE_PRIORITY. */
  private static final double SITE_PRIORITY = 1.0;

  /** The Constant siteUrls. */
  public static final String[] siteUrls = new String[] { SiteTokens.HOME, SiteTokens.GROUP_HOME,
      SiteTokens.ABOUT_KUNE, SiteTokens.ASK_RESET_PASSWD, SiteTokens.NEW_GROUP, SiteTokens.REGISTER,
      SiteTokens.RESET_PASSWD, SiteTokens.SIGN_IN };

  /** The container finder. */
  private final ContainerFinder containerFinder;

  /** The content finder. */
  private final ContentFinder contentFinder;

  /** The dir. */
  private File dir;

  /** The file download utils. */
  private final AbsoluteFileDownloadUtils fileDownloadUtils;

  /** The group finder. */
  private final GroupFinder groupFinder;

  /** The site url. */
  private final String siteUrl;

  /**
   * Instantiates a new site map generator.
   * 
   * @param props
   *          the props
   * @param groupFinder
   *          the group finder
   * @param contentFinder
   *          the content finder
   * @param containerFinder
   *          the container finder
   * @param fileDownloadUtils
   *          the file download utils
   */
  @Inject
  public SiteMapGenerator(final KuneProperties props, final GroupFinder groupFinder,
      final ContentFinder contentFinder, final ContainerFinder containerFinder,
      final AbsoluteFileDownloadUtils fileDownloadUtils) {
    this.groupFinder = groupFinder;
    this.contentFinder = contentFinder;
    this.containerFinder = containerFinder;
    this.fileDownloadUtils = fileDownloadUtils;
    siteUrl = props.get(KuneProperties.SITE_URL);
    final String dirName = props.get(KuneProperties.SITEMAP_DIR);
    try {
      dir = FileUtils.createDirIfNotExists(dirName, "");
    } catch (final PersistenceException e) {
      LOG.error("Error generating sitemap", e);
    }
  }

  /**
   * Count not closed groups.
   * 
   * @return the long
   */
  @KuneTransactional
  private Long countNotClosedGroups() {
    return groupFinder.countExceptType(GroupType.CLOSED);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.searcheable.SiteMapGeneratorMBean#generate()
   */
  @Override
  public void generate() {
    LOG.info("Starting to generate sitemap");
    final Date now = new Date();

    try {
      final WebSitemapGenerator wsg = new WebSitemapGenerator(siteUrl, dir);

      LOG.info("Initialize sitemap");
      // Site urls hashs (#signin, #register, etc)
      for (final String siteHash : siteUrls) {
        final String siteUri = fileDownloadUtils.getUrl(siteHash);
        final WebSitemapUrl url = new WebSitemapUrl.Options(siteUri).lastMod(now).priority(SITE_PRIORITY).changeFreq(
            ChangeFreq.WEEKLY).build();
        wsg.addUrl(url);
      }

      // Groups
      LOG.info("Start groups procesing for sitema generation");
      final Long count = countNotClosedGroups();
      int i = 0;
      while (i < count) {

        LOG.info("Procesing groups for site map til: " + i + LIMIT_OF_QUERY);
        final List<Group> groups = getGroups(i);
        for (final Group group : groups) {
          final String groupUri = fileDownloadUtils.getUrl(group.getStateToken().toString());
          final WebSitemapUrl groupUrl = new WebSitemapUrl.Options(groupUri).lastMod(now).priority(
              GROUPS_PRIORITY).changeFreq(ChangeFreq.DAILY).build();
          wsg.addUrl(groupUrl);

          // Containers
          for (final Container container : getContainers(group)) {
            if (container.getAccessLists().getViewers().getMode().equals(GroupListMode.EVERYONE)) {
              final String containerUri = fileDownloadUtils.getUrl(container.getStateToken().toString());
              final WebSitemapUrl containerUrl = new WebSitemapUrl.Options(containerUri).lastMod(now).priority(
                  CONTENTS_PRIORITY).changeFreq(ChangeFreq.DAILY).build();
              wsg.addUrl(containerUrl);
            }
          }

          // Contents
          for (final Content content : getContents(group)) {
            if (content.getAccessLists().getViewers().getMode().equals(GroupListMode.EVERYONE)) {
              final String contentUri = fileDownloadUtils.getUrl(content.getStateToken().toString());
              final WebSitemapUrl contentUrl = new WebSitemapUrl.Options(contentUri).lastMod(
                  new Date(content.getModifiedOn())).priority(CONTENTS_PRIORITY).changeFreq(
                  ChangeFreq.DAILY).build();
              wsg.addUrl(contentUrl);
            }
          }

          i += LIMIT_OF_QUERY;
        }
      }

      wsg.write();
    } catch (final MalformedURLException e) {
      LOG.error("Error generating sitemap. Malformed URL.", e);
    }

  }

  /**
   * Gets the containers.
   * 
   * @param group
   *          the group
   * @return the containers
   */
  @KuneTransactional
  private List<Container> getContainers(final Group group) {
    return containerFinder.allContainersInUserGroup(group.getId());
  }

  /**
   * Gets the contents.
   * 
   * @param group
   *          the group
   * @return the contents
   */
  @KuneTransactional
  private List<Content> getContents(final Group group) {
    return contentFinder.allContentsInUserGroup(group.getId());
  }

  /**
   * Gets the groups.
   * 
   * @param i
   *          the i
   * @return the groups
   */
  @KuneTransactional
  private List<Group> getGroups(final int i) {
    return groupFinder.getAllExcept(LIMIT_OF_QUERY, i, GroupType.CLOSED);
  }
}