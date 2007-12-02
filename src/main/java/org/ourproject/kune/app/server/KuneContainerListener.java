/**
 * 
 */
package org.ourproject.kune.app.server;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.log4j.PropertyConfigurator;
import org.ourproject.kune.docs.server.DocumentServerTool;
import org.ourproject.kune.platf.server.KunePersistenceService;
import org.ourproject.kune.platf.server.tool.ToolRegistry;
import org.ourproject.rack.ContainerListener;

import com.google.inject.Inject;

class KuneContainerListener implements ContainerListener {
	KunePersistenceService persistenceService;
	ToolRegistry toolRegistry;
	DocumentServerTool documentTool;
	Logger logger;

	@Inject
	public KuneContainerListener(KunePersistenceService persistenceService, ToolRegistry toolRegistry, DocumentServerTool documentTool, Logger logger) {
		this.persistenceService = persistenceService;
		this.toolRegistry = toolRegistry;
		this.documentTool = documentTool;
		this.logger = logger;
	}
	
	public void start() {
		configureLog4j();
		logger.log(Level.INFO, "starting Kune...");
		toolRegistry.register(documentTool);
		persistenceService.start();
		logger.log(Level.INFO, "started");
	}

	private void configureLog4j() {
		try {
			Properties properties = new Properties();
			InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(
					"log4j.dev.properties");
			properties.load(input);
			PropertyConfigurator.configure(properties);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		logger.log(Level.INFO, "stoping Kune...");
		logger.log(Level.INFO, "stoped");
	}
}