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

package cc.kune.core.server.metrics;

import java.lang.management.ManagementFactory;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.health.jvm.ThreadDeadlockHealthCheck;
import com.codahale.metrics.jvm.BufferPoolMetricSet;
import com.codahale.metrics.jvm.ClassLoadingGaugeSet;
import com.codahale.metrics.jvm.FileDescriptorRatioGauge;
import com.codahale.metrics.jvm.GarbageCollectorMetricSet;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;
import com.codahale.metrics.jvm.ThreadStatesGaugeSet;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.typesafe.config.Config;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.hibernate.HikariDataSourceRegister;

import cc.kune.core.server.xmpp.XmppHealthCheck;
import cc.kune.wave.server.kspecific.WaveHealthCheck;

@Singleton
public class MetricsManager {

  @Inject
  public MetricsManager(Config config, MetricRegistry metricRegistry, HealthCheckRegistry healthRegistry,
      GroupsHealthCheck groupsHealthCheck, UsersHealthCheck usersHealthCheck,
      XmppHealthCheck xmppHealthCheck, WaveHealthCheck waveHealthCheck) {
    Boolean doMetrics = config.getBoolean("kune.metrics");
    Boolean doHealthChecks = config.getBoolean("kune.healthchecks");

    if (doMetrics) {
      for (HikariDataSource hds: HikariDataSourceRegister.INSTANCE.set()) {
        hds.setMetricRegistry(metricRegistry);
      }
      metricRegistry.register("jvm.buffers",
          new BufferPoolMetricSet(ManagementFactory.getPlatformMBeanServer()));
      metricRegistry.register("jvm.cl", new ClassLoadingGaugeSet());
      metricRegistry.register("jvm.gc", new GarbageCollectorMetricSet());
      metricRegistry.register("jvm.memory", new MemoryUsageGaugeSet());
      metricRegistry.register("jvm.fileDescriptorCountRatio", new FileDescriptorRatioGauge());
      metricRegistry.register("jvm.threads", new ThreadStatesGaugeSet());
    }

    if (doHealthChecks) {
      // https://github.com/brettwooldridge/HikariCP/wiki/Dropwizard-HealthChecks
      healthRegistry.register("kune.groups", groupsHealthCheck);
      healthRegistry.register("kune.users", usersHealthCheck);
      healthRegistry.register("kune.chat", xmppHealthCheck);
      healthRegistry.register("kune.waves", waveHealthCheck);
      for (HikariDataSource hds: HikariDataSourceRegister.INSTANCE.set()) {
        hds.setHealthCheckRegistry(healthRegistry);
      }
      healthRegistry.register("jvm.deadlocks", new ThreadDeadlockHealthCheck());
    }
  }
}
