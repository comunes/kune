package org.ourproject.kune.app.server;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.PropertyConfigurator;
import org.ourproject.kune.chat.server.ChatServerModule;
import org.ourproject.kune.docs.server.DocumentServerModule;
import org.ourproject.kune.docs.server.DocumentServerTool;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.client.rpc.GroupService;
import org.ourproject.kune.platf.client.rpc.SiteService;
import org.ourproject.kune.platf.client.rpc.SocialNetworkService;
import org.ourproject.kune.platf.server.KunePersistenceService;
import org.ourproject.kune.platf.server.LoggerMethodInterceptor;
import org.ourproject.kune.platf.server.PlatformServerModule;
import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.properties.PropertiesFileName;
import org.ourproject.kune.platf.server.rest.GroupJSONService;
import org.ourproject.kune.platf.server.rest.TestJSONService;
import org.ourproject.kune.platf.server.tool.ToolRegistry;
import org.ourproject.kune.sitebar.client.rpc.UserService;
import org.ourproject.rack.ContainerListener;
import org.ourproject.rack.RackBuilder;
import org.ourproject.rack.RackGuiceModule;
import org.ourproject.rack.RackModule;
import org.ourproject.rack.filters.ApplicationListener;
import org.ourproject.rack.filters.ForwardFilter;
import org.ourproject.rack.filters.ListenerFilter;
import org.ourproject.rack.filters.LogFilter;
import org.ourproject.rack.filters.RedirectFilter;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Scope;
import com.google.inject.Scopes;
import com.google.inject.matcher.Matchers;
import com.google.inject.servlet.GuiceFilter;
import com.google.inject.servlet.SessionScoped;
import com.wideplay.warp.jpa.JpaUnit;

public class KuneRackModule implements RackModule {
	private final String jpaUnit;
	private final String propertiesFileName;
	private final Scope sessionScope;

	public KuneRackModule(final String jpaUnit, final String propertiesFileName, final Scope sessionScope) {
		this.jpaUnit = jpaUnit;
		this.propertiesFileName = propertiesFileName;
		this.sessionScope = sessionScope;
	}

	public KuneRackModule() {
		this("development", "kune.properties", Scopes.SINGLETON);
	}

	public void configure(RackBuilder builder) {
		builder.use(new PlatformServerModule());
		builder.use(new DocumentServerModule());
		builder.use(new ChatServerModule());
		builder.use(new RackGuiceModule());
		builder.use(new AbstractModule() {
			public void configure() {
				bindInterceptor(Matchers.any(), new NotInObject(), new LoggerMethodInterceptor());
				bindScope(SessionScoped.class, sessionScope);
				bindConstant().annotatedWith(JpaUnit.class).to(jpaUnit);
				bindConstant().annotatedWith(PropertiesFileName.class).to(propertiesFileName);
			}
		});

		builder.addListener(KuneContainerListener.class);

		builder.at(".*").install(new LogFilter());
		builder.at(".*").install(new GuiceFilter());

		builder.at("^/kune$").install(new RedirectFilter("/kune/"));

		builder.at("^/kune/$").install(new ListenerFilter(KuneApplicationListener.class)).install(
				new ForwardFilter("/gwt/org.ourproject.kune.app.Kune/Kune.html"));
		
		
		builder.installGWTServices("^/kune/", SiteService.class, GroupService.class, ContentService.class, 
				UserService.class, SocialNetworkService.class);
		builder.installRESTServices("^/kune/json/", TestJSONService.class, GroupJSONService.class);
		
		builder.at("^/kune/(.*)$").install(new ForwardFilter("^/kune/(.*)$", "/gwt/org.ourproject.kune.app.Kune/{0}"));
	}

	class KuneApplicationListener implements ApplicationListener {
		final UserSession userSession;

		@Inject
		public KuneApplicationListener(UserSession userSession) {
			this.userSession = userSession;
		}
		
		public void doAfter(HttpServletRequest request, HttpServletResponse httpServletResponse) {
			// Comment this: (only setHash where user isLogged)
			// Also we need the sessionId when the client application is already
			// running (for instance if we restart the server)

			String userSessionId = request.getSession().getId();
			userSession.setHash(userSessionId);
		}

		public void doBefore(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		}
	}

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

}
