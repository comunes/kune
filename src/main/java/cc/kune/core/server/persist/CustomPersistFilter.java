/**
 * Copyright (C) 2010 Google, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cc.kune.core.server.persist;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.UnitOfWork;

/**
 * Apply this filter to enable the HTTP Request unit of work and to have
 * guice-persist manage the lifecycle of active units of work. The filter
 * automatically starts and stops the relevant {@link PersistService} upon
 * {@link javax.servlet.Filter#init(javax.servlet.FilterConfig)} and
 * {@link javax.servlet.Filter#destroy()} respectively.
 * 
 * <p>
 * To be able to use the open session-in-view pattern (i.e. work per request),
 * register this filter <b>once</b> in your Guice {@code ServletModule}. It is
 * important that you register this filter before any other filter.
 * 
 * For multiple providers, you should register this filter once per provider,
 * inside a private module for each persist module installed (this must be the
 * same private module where the specific persist module is itself installed).
 * 
 * <p>
 * Example configuration:
 * 
 * <pre>
 * {
 *   &#064;code
 *   public class MyModule extends ServletModule {
 *     public void configureServlets() {
 *       filter(&quot;/*&quot;).through(PersistFilter.class);
 * 
 *       serve(&quot;/index.html&quot;).with(MyHtmlServlet.class);
 *       // Etc.
 *     }
 *   }
 * }
 * </pre>
 * <p>
 * This filter is thread safe and allows you to create injectors concurrently
 * and deploy multiple guice-persist modules within the same injector, or even
 * multiple injectors with persist modules withing the same JVM or web app.
 * <p>
 * This filter requires the Guice Servlet extension.
 * 
 * @author Dhanji R. Prasanna (dhanji@gmail.com)
 * 
 *         (Custom PersistFilter.class modified for kune needs without
 *         persistence start/stop)
 */

@Singleton
public class CustomPersistFilter implements Filter {
  // private final PersistService persistService;
  // private final UnitOfWork unitOfWork;

  @Inject
  public CustomPersistFilter(final UnitOfWork unitOfWork, final PersistService persistService) {
    // this.unitOfWork = unitOfWork;
    // this.persistService = persistService;
  }

  @Override
  public void destroy() {
    // Commented in kune
    // persistService.stop();
  }

  @Override
  @KuneTransactional
  public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse,
      final FilterChain filterChain) throws IOException, ServletException {
    // Commented in kune (using @KuneTransactional)
    // unitOfWork.begin();
    // try {
    filterChain.doFilter(servletRequest, servletResponse);
    // } finally {
    // unitOfWork.end();
    // }
  }

  @Override
  public void init(final FilterConfig filterConfig) throws ServletException {
    // Commented in kune (because if not this is started twice)
    // persistService.start();
  }
}