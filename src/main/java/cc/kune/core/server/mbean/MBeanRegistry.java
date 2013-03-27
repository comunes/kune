/*******************************************************************************
 * Copyright (C) 2007, 2013 The kune development team (see CREDITS for details)
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
 *******************************************************************************/

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

/**
 * The Class MBeanRegistry is responsible of mbean objects registration into the
 * JVM MBean Server.
 */
@Singleton
public class MBeanRegistry {

  public static final Log LOG = LogFactory.getLog(MBeanRegistry.class);
  private final MBeanServer mbeanServer;

  public MBeanRegistry() {
    mbeanServer = ManagementFactory.getPlatformMBeanServer();
  }

  /**
   * Register this object itself in the JVM MBean Server.
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

    LOG.info("Registered as MBean sucessfully");
  }
}
