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

import java.lang.management.ManagementFactory;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Class MBeanRegistry is responsible of mbean objects registration into the
 * JVM MBean Server.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class MBeanRegistry {

  /** The Constant LOG. */
  public static final Log LOG = LogFactory.getLog(MBeanRegistry.class);

  /** The mbean server. */
  private final MBeanServer mbeanServer;

  /**
   * Instantiates a new m bean registry.
   */
  public MBeanRegistry() {
    mbeanServer = ManagementFactory.getPlatformMBeanServer();
  }

  /**
   * Register this object itself in the JVM MBean Server.
   * 
   * @param object
   *          the object
   * @param objectName
   *          the object name
   */
  public void registerAsMBean(final Object object, final String objectName) {
    ObjectName mbeanName = null;

    try {
      mbeanName = new ObjectName(objectName);
    } catch (final MalformedObjectNameException e) {
      LOG.error("Error creating MBean ObjectName: " + objectName + ", " + e.getMessage());
    } catch (final NullPointerException e) {
      LOG.error("Error creating MBean ObjectName: " + objectName + ", " + e.getMessage());
    }

    try {
      mbeanServer.registerMBean(object, mbeanName);
    } catch (final InstanceAlreadyExistsException e) {
      LOG.error("Error registering MBean: " + objectName + ", " + e.getMessage());
    } catch (final MBeanRegistrationException e) {
      LOG.error("Error registering MBean: " + objectName + ", " + e.getMessage());
    } catch (final NotCompliantMBeanException e) {
      LOG.error("Error registering MBean: " + objectName + ", " + e.getMessage());
    }

    LOG.info("Registered " + objectName + " as MBean sucessfully");
  }
}
