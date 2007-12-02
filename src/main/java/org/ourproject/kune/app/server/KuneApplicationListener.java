/**
 * 
 */
package org.ourproject.kune.app.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.rack.filters.ApplicationListener;

import com.google.inject.Inject;

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