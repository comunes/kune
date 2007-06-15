package org.ourproject.kune.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ourproject.kune.server.jcr.RepositoryProvider;

public class RepositoryServlet extends HttpServlet{
	
	private RepositoryProvider provider;

	@Override
	public void init() throws ServletException {
		super.init();
		this.provider = new RepositoryProvider();
		this.provider.startup();
		getServletContext().setAttribute(RepositoryProvider.class.getName(), provider);
	}


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doGet(req, resp);
	}

	private static final long serialVersionUID = 1L;

}
