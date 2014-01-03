package cc.kune.core.server.rack.filters.rest;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The Class CORSServletFilter will we used in the future to filter some
 * cross-domain servlet calls
 */
public class CORSServletFilter extends AbstractCustomCORSFilter {

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.rack.filters.rest.AbstractCustomCORSFilter#customDoFilter
   * (javax.servlet.http.HttpServletRequest,
   * javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
   */
  @Override
  protected void customDoFilter(final HttpServletRequest request, final HttpServletResponse response,
      final FilterChain chain) throws IOException, ServletException {
    chain.doFilter(request, response);
  }
}
