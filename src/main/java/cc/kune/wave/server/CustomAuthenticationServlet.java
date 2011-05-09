/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package cc.kune.wave.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.security.Principal;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.Configuration;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eclipse.jetty.util.MultiMap;
import org.eclipse.jetty.util.UrlEncoded;
import org.waveprotocol.box.server.CoreSettings;
import org.waveprotocol.box.server.authentication.HttpRequestBasedCallbackHandler;
import org.waveprotocol.box.server.authentication.ParticipantPrincipal;
import org.waveprotocol.box.server.authentication.SessionManager;
import org.waveprotocol.box.server.gxp.AuthenticationPage;
import org.waveprotocol.wave.model.wave.InvalidParticipantAddress;
import org.waveprotocol.wave.model.wave.ParticipantId;
import org.waveprotocol.wave.util.logging.Log;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.gxp.base.GxpContext;
import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * A servlet for authenticating a user's password and giving them a token via a
 * cookie.
 * 
 * @author josephg@gmail.com (Joseph Gentle)
 */
public class CustomAuthenticationServlet extends HttpServlet {
    private static final String DEFAULT_REDIRECT_URL = "/";
    private static final Log LOG = Log.get(CustomAuthenticationServlet.class);
    public static final String RESPONSE_STATUS_FAILED = "FAILED";
    public static final String RESPONSE_STATUS_NONE = "NONE";

    public static final String RESPONSE_STATUS_SUCCESS = "SUCCESS";

    private final Configuration configuration;
    private final String domain;
    private final SessionManager sessionManager;

    @Inject
    public CustomAuthenticationServlet(final Configuration configuration, final SessionManager sessionManager,
            @Named(CoreSettings.WAVE_SERVER_DOMAIN) final String domain) {
        Preconditions.checkNotNull(configuration, "Configuration is null");
        Preconditions.checkNotNull(sessionManager, "Session manager is null");
        this.configuration = configuration;
        this.sessionManager = sessionManager;
        this.domain = domain.toLowerCase();
    }

    /**
     * On GET, present a login form if the user isn't authenticated.
     */
    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        // If the user is already logged in, we'll try to redirect them
        // immediately.
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        final HttpSession session = req.getSession(false);
        final ParticipantId user = sessionManager.getLoggedInUser(session);

        if (user != null) {
            redirectLoggedInUser(req, resp);
        } else {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("text/html;charset=utf-8");
            AuthenticationPage.write(resp.getWriter(), new GxpContext(req.getLocale()), domain, "",
                    RESPONSE_STATUS_NONE);
        }
    }

    /**
     * The POST request should have all the fields required for authentication.
     */
    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        LoginContext context;
        try {
            context = login(req.getReader());
        } catch (final LoginException e) {
            final String message = "The username or password you entered is incorrect.";
            final String responseType = RESPONSE_STATUS_FAILED;
            LOG.info("User authentication failed: " + e.getLocalizedMessage());
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            AuthenticationPage.write(resp.getWriter(), new GxpContext(req.getLocale()), domain, message, responseType);
            return;
        }

        final Subject subject = context.getSubject();

        ParticipantId loggedInAddress;
        try {
            loggedInAddress = getLoggedInUser(subject);
        } catch (final InvalidParticipantAddress e1) {
            throw new IllegalStateException("The user provided valid authentication information, but the username"
                    + " isn't a valid user address.");
        }

        if (loggedInAddress == null) {
            try {
                context.logout();
            } catch (final LoginException e) {
                // Logout failed. Absorb the error, since we're about to throw
                // an
                // illegal state exception anyway.
            }
            throw new IllegalStateException("The user provided valid authentication information, but we don't "
                    + "know how to map their identity to a wave user address.");
        }

        final HttpSession session = req.getSession(true);
        sessionManager.setLoggedInUser(session, loggedInAddress);
        // The context needs to be notified when the user logs out.
        session.setAttribute("context", context);
        LOG.info("Authenticated user " + loggedInAddress);

        redirectLoggedInUser(req, resp);
    }

    /**
     * Get the participant id of the given subject.
     * 
     * The subject is searched for compatible principals. When other
     * authentication types are added, this method will need to be updated to
     * support their principal types.
     * 
     * @throws InvalidParticipantAddress
     *             The subject's address is invalid
     */
    private ParticipantId getLoggedInUser(final Subject subject) throws InvalidParticipantAddress {
        String address = null;

        for (final Principal p : subject.getPrincipals()) {
            // TODO(josephg): When we support other authentication types (LDAP,
            // etc),
            // this method will need to read the address portion out of the
            // other principal types.
            if (p instanceof ParticipantPrincipal) {
                address = ((ParticipantPrincipal) p).getName();
                break;
            }
        }

        return address == null ? null : ParticipantId.of(address);
    }

    @SuppressWarnings("unchecked")
    public LoginContext login(final BufferedReader body) throws IOException, LoginException {
        try {
            final Subject subject = new Subject();

            String parametersLine = body.readLine();
            // Throws UnsupportedEncodingException.
            final byte[] utf8Bytes = parametersLine.getBytes("UTF-8");

            final CharsetDecoder utf8Decoder = Charset.forName("UTF-8").newDecoder();
            utf8Decoder.onMalformedInput(CodingErrorAction.IGNORE);
            utf8Decoder.onUnmappableCharacter(CodingErrorAction.IGNORE);

            // Throws CharacterCodingException.
            final CharBuffer parsed = utf8Decoder.decode(ByteBuffer.wrap(utf8Bytes));
            parametersLine = parsed.toString();

            final MultiMap<String> parameters = new UrlEncoded(parametersLine);
            final CallbackHandler callbackHandler = new HttpRequestBasedCallbackHandler(parameters);

            final LoginContext context = new LoginContext("Wave", subject, callbackHandler, configuration);

            // If authentication fails, login() will throw a LoginException.
            context.login();
            return context;
        } catch (final CharacterCodingException cce) {
            throw new LoginException("Character coding exception (not utf-8): " + cce.getLocalizedMessage());
        } catch (final UnsupportedEncodingException uee) {
            throw new LoginException("ad character encoding specification: " + uee.getLocalizedMessage());
        }
    }

    /**
     * Redirect the user back to DEFAULT_REDIRECT_URL, unless a custom redirect
     * URL has been specified in the query string; in which case redirect there.
     * 
     * Only redirects to local URLs are allowed.
     * 
     * @throws IOException
     */
    private void redirectLoggedInUser(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        Preconditions.checkState(sessionManager.getLoggedInUser(req.getSession(false)) != null,
                "The user is not logged in");
        final String query = req.getQueryString();

        // Not using req.getParameter() for this because calling that method
        // might parse the password
        // sitting in POST data into a String, where it could be read by another
        // process after the
        // string is garbage collected.
        if (query == null || !query.startsWith("r=")) {
            resp.sendRedirect(DEFAULT_REDIRECT_URL);
            return;
        }

        final String encoded_url = query.substring("r=".length());
        final String path = URLDecoder.decode(encoded_url, "UTF-8");

        // The URL must not be an absolute URL to prevent people using this as a
        // generic redirection service.
        URI uri;
        try {
            uri = new URI(path);
        } catch (final URISyntaxException e) {
            // The redirect URL is invalid.
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        if (Strings.isNullOrEmpty(uri.getHost()) == false) {
            // The URL includes a host component. Disallow it.
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } else {
            resp.sendRedirect(path);
        }
    }
}
