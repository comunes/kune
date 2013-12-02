package cc.kune.core.server.rack.filters.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cc.kune.core.server.rack.RackServletFilter;
import cc.kune.core.server.rack.utils.RackHelper;

import com.google.inject.Inject;
import com.google.inject.Injector;

public class CORSServiceFilter extends AbstractCustomCORSFilter {
  private static final Log LOG = LogFactory.getLog(CORSServiceFilter.class);
  protected ServletContext ctx;
  private final Pattern pattern;

  private final Class<?> serviceClass;

  @Inject
  private TransactionalServiceExecutor transactionalFilter;

  public CORSServiceFilter(final String pattern, final Class<?> serviceClass) {
    this.serviceClass = serviceClass;
    this.pattern = Pattern.compile(pattern);
  }

  @Override
  protected void customDoFilter(final HttpServletRequest request, final HttpServletResponse response)
      throws IOException {
    final boolean cors = (Boolean) request.getAttribute("cors.isCorsRequest");

    // This part is similar to RESTServiceFilter
    final String methodName = RackHelper.getMethodName(request, pattern);
    final ParametersAdapter parameters = new ParametersAdapter(request);
    LOG.debug((cors ? "" : "NO ") + "CORS METHOD: '" + methodName + "' on: "
        + serviceClass.getSimpleName());

    response.setCharacterEncoding("utf-8");

    // See: http://software.dzhuvinov.com/cors-filter-tips.html
    response.setContentType("text/plain");

    final String output = transactionalFilter.doService(serviceClass, methodName, parameters,
        getInstance(serviceClass));
    if (output != null) {
      final PrintWriter writer = response.getWriter();
      writer.print(output);
      writer.flush();
    } else {
      // Is not for us!!!
    }
  }

  @Override
  public void destroy() {
  }

  private Injector getInjector() {
    return (Injector) ctx.getAttribute(RackServletFilter.INJECTOR_ATTRIBUTE);
  }

  public <T> T getInstance(final Class<T> type) {
    return getInjector().getInstance(type);
  }

  @Override
  public void init(final FilterConfig filterConfig) throws ServletException {
    super.init(filterConfig);
    this.ctx = filterConfig.getServletContext();
    getInjector().injectMembers(this);
  }

}
