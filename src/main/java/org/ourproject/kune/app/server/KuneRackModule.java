package org.ourproject.kune.app.server;

import org.ourproject.kune.chat.server.ChatServerModule;
import org.ourproject.kune.docs.server.DocumentServerModule;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.client.rpc.GroupService;
import org.ourproject.kune.platf.client.rpc.I18nService;
import org.ourproject.kune.platf.client.rpc.SiteService;
import org.ourproject.kune.platf.client.rpc.SocialNetworkService;
import org.ourproject.kune.platf.server.LoggerMethodInterceptor;
import org.ourproject.kune.platf.server.PlatformServerModule;
import org.ourproject.kune.platf.server.properties.PropertiesFileName;
import org.ourproject.kune.platf.server.rest.GroupJSONService;
import org.ourproject.kune.platf.server.rest.I18nTranslationJSONService;
import org.ourproject.kune.platf.server.rest.TestJSONService;
import org.ourproject.kune.sitebar.client.rpc.UserService;
import org.ourproject.rack.RackBuilder;
import org.ourproject.rack.RackModule;
import org.ourproject.rack.filters.ForwardFilter;
import org.ourproject.rack.filters.ListenerFilter;
import org.ourproject.rack.filters.LogFilter;
import org.ourproject.rack.filters.RedirectFilter;
import org.ourproject.rack.filters.rest.RESTServicesModule;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Scope;
import com.google.inject.matcher.Matchers;
import com.google.inject.servlet.GuiceFilter;
import com.google.inject.servlet.ServletModule;
import com.google.inject.servlet.SessionScoped;
import com.wideplay.warp.jpa.JpaUnit;

public class KuneRackModule implements RackModule {

    private final Module configModule;

    public KuneRackModule(final String jpaUnit, final String propertiesFileName, final Scope sessionScope) {
        configModule = new AbstractModule() {
            public void configure() {
                bindInterceptor(Matchers.any(), new NotInObject(), new LoggerMethodInterceptor());
                bindConstant().annotatedWith(JpaUnit.class).to(jpaUnit);
                bindConstant().annotatedWith(PropertiesFileName.class).to(propertiesFileName);
                if (sessionScope != null) {
                    bindScope(SessionScoped.class, sessionScope);
                }
            }
        };
    }

    public KuneRackModule() {
        this("development", "kune.properties", null);
    }

    public void configure(final RackBuilder builder) {
        builder.use(new ServletModule());
        builder.use(new PlatformServerModule());
        builder.use(new DocumentServerModule());
        builder.use(new ChatServerModule());
        builder.use(new RESTServicesModule());
        builder.use(configModule);

        builder.add(KuneContainerListener.class);

        builder.at(".*").install(new LogFilter());
        builder.at(".*").install(new GuiceFilter());

        builder.at("^/kune$").install(new RedirectFilter("/kune/"));

        builder.at("^/kune/$").install(new ListenerFilter(KuneApplicationListener.class),
                new ForwardFilter("/gwt/org.ourproject.kune.app.Kune/Kune.html"));

        builder.installGWTServices("^/kune/", SiteService.class, GroupService.class, ContentService.class,
                UserService.class, SocialNetworkService.class, I18nService.class);
        builder.installRESTServices("^/kune/json/", TestJSONService.class, GroupJSONService.class,
                I18nTranslationJSONService.class);

        builder.at("^/kune/(.*)$").install(new ForwardFilter("^/kune/(.*)$", "/gwt/org.ourproject.kune.app.Kune/{0}"));
    }

}
