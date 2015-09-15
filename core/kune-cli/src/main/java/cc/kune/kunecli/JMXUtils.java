/*
 *
 * Copyright (C) 2007-2015 Licensed to the Comunes Association (CA) under
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

package cc.kune.kunecli;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

public class JMXUtils {
  private static final String CONNECTOR_ADDRESS = "com.sun.management.jmxremote.localConnectorAddress";
  public static final Log LOG = LogFactory.getLog(JMXUtils.class);

  public static Object doOperation(final String objectName, final String operation) {

    final List<VirtualMachineDescriptor> vms = VirtualMachine.list();

    try {
      for (final VirtualMachineDescriptor vmd : vms) {
        final String id = vmd.id();
        LOG.debug("VM id: " + id);
        final JMXServiceURL url = getURLForPid(id.trim());

        final JMXConnector jmxc = JMXConnectorFactory.connect(url, null);
        final MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();

        final ObjectName mbeanName = new ObjectName(objectName);

        MBeanInfo beanInfo;
        try {
          beanInfo = mbsc.getMBeanInfo(mbeanName);
          for (final MBeanOperationInfo currentOp : beanInfo.getOperations()) {
            if (currentOp.getName().equals(operation)) {
              LOG.info("Doing operation '" + operation + "' over mbean: '" + objectName + "' with id: '"
                  + id + "'.");
              final Object invoke = mbsc.invoke(mbeanName, currentOp.getName(), new Object[] {},
                  new String[] {});
              return invoke;
            }
          }
        } catch (final InstanceNotFoundException e) {
          // Ok, operation not found in this VM or domain
        }
      }
      throw new RuntimeException("JMX operation not found");
    } catch (final Exception e) {
      LOG.error("Error in jmx connection", e);
    }
    throw new RuntimeException("JMX operation failed");
  }

  private static JMXServiceURL getURLForPid(final String pid)
      throws AttachNotSupportedException, IOException, AgentLoadException, AgentInitializationException {

    // attach to the target application
    final VirtualMachine vm = VirtualMachine.attach(pid);

    // get the connector address
    String connectorAddress = vm.getAgentProperties().getProperty(CONNECTOR_ADDRESS);

    // no connector address, so we start the JMX agent
    if (connectorAddress == null) {
      final String agent = vm.getSystemProperties().getProperty("java.home") + File.separator + "lib"
          + File.separator + "management-agent.jar";
      vm.loadAgent(agent);

      // agent is started, get the connector address
      connectorAddress = vm.getAgentProperties().getProperty(CONNECTOR_ADDRESS);
      assert connectorAddress != null;
    }
    return new JMXServiceURL(connectorAddress);
  }
}
