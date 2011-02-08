/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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

/**
 *
 \*/
package org.ourproject.kune.app.server;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.log4j.PropertyConfigurator;
import org.ourproject.kune.platf.server.KunePersistenceService;
import org.ourproject.kune.rack.ContainerListener;

import com.google.inject.Inject;

public class KuneContainerListener implements ContainerListener {
    private final KunePersistenceService persistenceService;
    private final Logger logger; // NOPMD by vjrj on 16/07/09 22:38

    @Inject
    public KuneContainerListener(final KunePersistenceService persistenceService, final Logger logger) {
        this.persistenceService = persistenceService;
        this.logger = logger;
    }

    public Logger getLogger() {
        return logger;
    }

    public void start() {
        configureLog4j();
        logger.log(Level.INFO, "starting Kune...");
        persistenceService.start();
        logger.log(Level.INFO, "Kune server started");
    }

    public void stop() {
        logger.log(Level.INFO, "stopping Kune...");
        logger.log(Level.INFO, "Kune server stopped");
    }

    private void configureLog4j() {
        try {
            final Properties properties = new Properties();
            final InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(
                    "log4j.dev.properties");
            properties.load(input);
            PropertyConfigurator.configure(properties);
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}