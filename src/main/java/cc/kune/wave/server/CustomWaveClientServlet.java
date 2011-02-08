/**
 * Copyright 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package cc.kune.wave.server;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;
import org.waveprotocol.box.common.SessionConstants;
import org.waveprotocol.box.server.CoreSettings;
import org.waveprotocol.box.server.authentication.SessionManager;
import org.waveprotocol.box.server.gxp.TopBar;
import org.waveprotocol.box.server.gxp.WaveClientPage;
import org.waveprotocol.box.server.rpc.BaseUrlHelper;
import org.waveprotocol.box.server.util.RandomBase64Generator;
import org.waveprotocol.wave.client.util.ClientFlagsBase;
import org.waveprotocol.wave.common.bootstrap.FlagConstants;
import org.waveprotocol.wave.model.wave.ParticipantId;
import org.waveprotocol.wave.util.logging.Log;

import com.google.common.collect.Maps;
import com.google.gxp.base.GxpContext;
import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * The HTTP servlet for serving a wave client along with content generated on
 * the server.
 * 
 * @author kalman@google.com (Benjamin Kalman)
 */
public class CustomWaveClientServlet extends HttpServlet {

    private static final HashMap<String, String> FLAG_MAP = Maps.newHashMap();

    private static final Log LOG = Log.get(CustomWaveClientServlet.class);
    static {
        // __NAME_MAPPING__ is a map of name to obfuscated id
        for (int i = 0; i < FlagConstants.__NAME_MAPPING__.length; i += 2) {
            FLAG_MAP.put(FlagConstants.__NAME_MAPPING__[i], FlagConstants.__NAME_MAPPING__[i + 1]);
        }
    }

    private final String baseUrl;
    private final String domain;
    private final SessionManager sessionManager;
    private final Boolean useSocketIO;

    /**
     * Creates a servlet for the wave client.
     */
    @Inject
    public CustomWaveClientServlet(@Named(CoreSettings.WAVE_SERVER_DOMAIN) final String domain,
            @Named(CoreSettings.USE_SOCKETIO) final Boolean useSocketIO,
            @Named(CoreSettings.HTTP_BASE_URL) final String baseUrl, final SessionManager sessionManager) {
        this.domain = domain;
        this.useSocketIO = useSocketIO;
        this.baseUrl = BaseUrlHelper.removeLastSlash(baseUrl);
        this.sessionManager = sessionManager;
    }

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        final ParticipantId id = sessionManager.getLoggedInUser(request.getSession(false));

        // Eventually, it would be nice to show users who aren't logged in the
        // public waves.
        // However, public waves aren't implemented yet. For now, we'll just
        // redirect users
        // who haven't signed in to the sign in page.
        // COMMENTED (vjrj)
        if (id == null) {
            response.sendRedirect(baseUrl + sessionManager.getLoginUrl(baseUrl + "/"));
            return;
        }

        String username = null;
        String userDomain = null;
        if (id != null) {
            final String[] parts = id.getAddress().split("@");
            username = parts[0];
            userDomain = id.getDomain();
        }

        try {
            WaveClientPage.write(response.getWriter(), new GxpContext(request.getLocale()),
                    getSessionJson(request.getSession(false)), getClientFlags(request),
                    TopBar.getGxpClosure(username, userDomain, baseUrl), useSocketIO, baseUrl);
        } catch (final IOException e) {
            LOG.warning("Failed to write GXP for request " + request, e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private JSONObject getClientFlags(final HttpServletRequest request) {
        try {
            final JSONObject ret = new JSONObject();

            final Enumeration<?> iter = request.getParameterNames();
            while (iter.hasMoreElements()) {
                final String name = (String) iter.nextElement();
                final String value = request.getParameter(name);

                if (FLAG_MAP.containsKey(name)) {
                    // Set using the correct type of data in the json using
                    // reflection
                    try {
                        final Method getter = ClientFlagsBase.class.getMethod(name);
                        final Class<?> retType = getter.getReturnType();

                        if (retType.equals(String.class)) {
                            ret.put(FLAG_MAP.get(name), value);
                        } else if (retType.equals(Integer.class)) {
                            ret.put(FLAG_MAP.get(name), Integer.parseInt(value));
                        } else if (retType.equals(Boolean.class)) {
                            ret.put(FLAG_MAP.get(name), Boolean.parseBoolean(value));
                        } else if (retType.equals(Float.class)) {
                            ret.put(FLAG_MAP.get(name), Float.parseFloat(value));
                        } else if (retType.equals(Double.class)) {
                            ret.put(FLAG_MAP.get(name), Double.parseDouble(value));
                        } else {
                            // Flag exists, but its type is unknown, so it can
                            // not be
                            // properly encoded in JSON.
                            LOG.warning("Ignoring flag [" + name + "] with unknown return type: " + retType);
                        }

                        // Ignore the flag on any exception
                    } catch (final SecurityException ex) {
                    } catch (final NoSuchMethodException ex) {
                        LOG.warning("Failed to find the flag [" + name + "] in ClientFlagsBase.");
                    } catch (final NumberFormatException ex) {
                    }
                }
            }

            return ret;
        } catch (final JSONException ex) {
            LOG.severe("Failed to create flags JSON");
            return new JSONObject();
        }
    }

    private JSONObject getSessionJson(final HttpSession session) {
        try {
            final ParticipantId user = sessionManager.getLoggedInUser(session);
            final String address = (user != null) ? user.getAddress() : null;

            // TODO(zdwang): Figure out a proper session id rather than
            // generating a
            // random number
            final String sessionId = (new RandomBase64Generator()).next(10);

            return new JSONObject().put(SessionConstants.DOMAIN, domain).putOpt(SessionConstants.ADDRESS, address).putOpt(
                    SessionConstants.ID_SEED, sessionId);
        } catch (final JSONException e) {
            LOG.severe("Failed to create session JSON");
            return new JSONObject();
        }
    }
}
