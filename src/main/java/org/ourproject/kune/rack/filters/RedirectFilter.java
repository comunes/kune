package org.ourproject.kune.rack.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class RedirectFilter implements Filter {
	private final String redirect;

	public RedirectFilter(String redirect) {
		this.redirect = redirect;
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {

		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.sendRedirect(redirect);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void destroy() {
	}

}
