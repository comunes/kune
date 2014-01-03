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
package cc.kune.core.server.mbean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.jmx.HierarchyDynamicMBean;
import org.apache.log4j.spi.LoggerRepository;

import cc.kune.core.server.properties.KuneProperties;
import cc.kune.core.server.properties.KunePropertiesDefaultMBean;
import cc.kune.core.server.searcheable.SearchEngineServletFilter;
import cc.kune.core.server.searcheable.SearchEngineServletFilterMBean;
import cc.kune.core.server.searcheable.SiteMapGenerator;
import cc.kune.core.server.searcheable.SiteMapGeneratorMBean;

import com.google.inject.Inject;
import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Class MBeanRegister.
 * 
 * @author pablojan <pablojan@gmail.com>
 */
@Singleton
public class MBeanRegister {

  /** The Constant LOG. */
  public static final Log LOG = LogFactory.getLog(MBeanRegister.class);

  /**
   * Register mbeans objects in the {@link MBeanRegistry} for other objects that
   * cannot inject and use {@link MBeanRegistry.registerAsMBean} directly
   * 
   * @param registry
   *          the registry
   * @param kuneProperties
   *          the kune properties
   * @param siteMapGenerator
   *          the site map generator
   * @param searchEngineServletFilter
   *          the search engine servlet filter
   */
  @Inject
  public MBeanRegister(final MBeanRegistry registry, final KuneProperties kuneProperties,
      final SiteMapGenerator siteMapGenerator, final SearchEngineServletFilter searchEngineServletFilter) {
    // Since KuneProperties is not created via Guice, we need to do a manual
    // injection
    registry.registerAsMBean(kuneProperties, KunePropertiesDefaultMBean.MBEAN_OBJECT_NAME);
    // As SiteMapGenerator is instantiated by cron, we register the mbean
    // here
    // so it's there since the server start
    registry.registerAsMBean(siteMapGenerator, SiteMapGeneratorMBean.MBEAN_OBJECT_NAME);

    registry.registerAsMBean(searchEngineServletFilter, SearchEngineServletFilterMBean.MBEAN_OBJECT_NAME);

    // At this point log4j must be up, and configured, so let's register
    // as MBeans
    registerLog4jMBeans(registry);

  }

  /**
   * Add MBean support for Log4j Resolves #536.
   * 
   * @param registry
   *          MBeanRegistry instance
   */
  private void registerLog4jMBeans(final MBeanRegistry registry) {

    try {

      // http://www.jroller.com/ray/entry/managing_log4j_logging_levels_for
      final HierarchyDynamicMBean hdm = new HierarchyDynamicMBean();

      registry.registerAsMBean(hdm, MBeanConstants.LOG4J_PREFIX_DEFAULT);

      // Add the root logger to the Hierarchy MBean
      hdm.addLoggerMBean(Logger.getRootLogger().getName());

      // Get each logger from the Log4J Repository and add it to
      // the Hierarchy MBean created above.
      final LoggerRepository r = LogManager.getLoggerRepository();

      @SuppressWarnings("rawtypes")
      final java.util.Enumeration loggers = r.getCurrentLoggers();

      while (loggers.hasMoreElements()) {

        final String name = ((Logger) loggers.nextElement()).getName();

        if (LOG.isDebugEnabled()) {
          LOG.debug("Registering Log4j logger  in JMX" + name);
        }

        hdm.addLoggerMBean(name);
      }

    } catch (final Exception e) {
      LOG.error("Error registering log4j Mbeans", e);
    }
  }

}
