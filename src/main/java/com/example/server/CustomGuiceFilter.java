package com.example.server;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.waveprotocol.box.server.CoreSettings;
import org.waveprotocol.box.server.rpc.AuthenticationServlet;
import org.waveprotocol.box.server.rpc.ServerRpcProvider;
import org.waveprotocol.box.server.rpc.SignOutServlet;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.google.inject.servlet.GuiceFilter;
import com.google.inject.servlet.ServletModule;

public class CustomGuiceFilter extends GuiceFilter {

  public static class HelloWorldInjected {

    private final List<String> httpAddresses;

    /**
     * A Constructor with some sample WIAB instances and params injected
     */
    @Inject
    public HelloWorldInjected(final AuthenticationServlet auth, final SignOutServlet signOut,
        @Named(CoreSettings.HTTP_FRONTEND_ADDRESSES) final List<String> httpAddresses) {
      this.httpAddresses = httpAddresses;
    }

    public List<String> getHttpAddresses() {
      return httpAddresses;
    }
  }

  /**
   * An additional sample Guice Module
   * 
   */
  public static class HelloWorldModule extends AbstractModule {
    @Override
    protected void configure() {
      bind(HelloWorldInjected.class).in(Singleton.class);
    }
  }

  /**
   * A sample servlet that uses {@link HelloWorldModule} and
   * {@link HelloWorldInjected}
   * 
   */
  @Singleton
  @SuppressWarnings("serial")
  public static class HelloWorldServlet extends HttpServlet {

    private final HelloWorldInjected hw;

    @Inject
    public HelloWorldServlet(final HelloWorldInjected hw) {
      this.hw = hw;
    }

    @Override
    protected void doGet(final javax.servlet.http.HttpServletRequest req,
        final javax.servlet.http.HttpServletResponse resp) throws ServletException, java.io.IOException {
      final PrintWriter writer = resp.getWriter();
      writer.println("Hello world " + hw.getHttpAddresses().get(0) + "!");
      writer.close();
    };

  }

  @Override
  public void init(final FilterConfig filterConfig) throws ServletException {
    super.init(filterConfig);
    // We retrieve the WIAB injector from the context
    final Injector injector = (Injector) filterConfig.getServletContext().getAttribute(
        ServerRpcProvider.INJECTOR_ATTRIBUTE);
    // We register additional Guice modules
    injector.createChildInjector(new HelloWorldModule(), new ServletModule() {
      @Override
      protected void configureServlets() {
        // Additional servlets
        serve("/hw/*").with(HelloWorldServlet.class);
      }
    });
  }
}
