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
import java.util.Locale;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.waveprotocol.box.server.CoreSettings;
import org.waveprotocol.box.server.account.HumanAccountDataImpl;
import org.waveprotocol.box.server.authentication.HttpRequestBasedCallbackHandler;
import org.waveprotocol.box.server.authentication.PasswordDigest;
import org.waveprotocol.box.server.gxp.UserRegistrationPage;
import org.waveprotocol.box.server.persistence.AccountStore;
import org.waveprotocol.box.server.persistence.PersistenceException;
import org.waveprotocol.box.server.rpc.AuthenticationServlet;
import org.waveprotocol.wave.model.wave.InvalidParticipantAddress;
import org.waveprotocol.wave.model.wave.ParticipantId;
import org.waveprotocol.wave.util.logging.Log;

import com.google.gxp.base.GxpContext;
import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * The user registration servlet allows new users to register accounts.
 * 
 * @author josephg@gmail.com (Joseph Gentle)
 */
public class CustomUserRegistrationServlet extends HttpServlet {
    private final AccountStore accountStore;
    private final String domain;

    private final Log LOG = Log.get(CustomUserRegistrationServlet.class);

    @Inject
    public CustomUserRegistrationServlet(final AccountStore accountStore,
            @Named(CoreSettings.WAVE_SERVER_DOMAIN) final String domain) {
        this.accountStore = accountStore;
        this.domain = domain;
    }

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        writeRegistrationPage("", AuthenticationServlet.RESPONSE_STATUS_NONE, req.getLocale(), resp);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");

        final String username = req.getParameter(HttpRequestBasedCallbackHandler.ADDRESS_FIELD);
        String passwd = req.getParameter(HttpRequestBasedCallbackHandler.PASSWORD_FIELD);
        if (passwd == null) {
            // Register the user with an empty password.
            passwd = "";
        }
        final PasswordDigest passwordDigest = new PasswordDigest(passwd.toCharArray());
        String message = tryCreateUser(username, passwordDigest);
        String responseType = AuthenticationServlet.RESPONSE_STATUS_SUCCESS;

        if (message != null) {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            responseType = AuthenticationServlet.RESPONSE_STATUS_FAILED;
        } else {
            message = "Registration complete.";
            resp.setStatus(HttpServletResponse.SC_OK);
        }

        writeRegistrationPage(message, responseType, req.getLocale(), resp);
    }

    /**
     * Try to create a user with the provided username and password. On error,
     * returns a string containing an error message. On success, returns null.
     */
    public String tryCreateUser(final String username, final PasswordDigest passwordDigest) {
        String lusername = username;
        // final String message = null;
        ParticipantId id = null;

        try {
            // First, some cleanup on the parameters.
            if (lusername == null) {
                return "Username portion of address cannot be less than 2 characters";
            }
            lusername = lusername.trim().toLowerCase();
            if (lusername.contains(ParticipantId.DOMAIN_PREFIX)) {
                id = ParticipantId.of(lusername);
            } else {
                id = ParticipantId.of(lusername + ParticipantId.DOMAIN_PREFIX + domain);
            }
            if (id.getAddress().indexOf("@") < 2) {
                return "Username portion of address cannot be less than 2 characters";
            }
            final String[] usernameSplit = id.getAddress().split("@");
            if (usernameSplit.length != 2 || !usernameSplit[0].matches("[\\w\\.]+")) {
                return "Only letters (a-z), numbers (0-9), and periods (.) are allowed in Username";
            }
            if (!id.getDomain().equals(domain)) {
                return "You can only create users at the " + domain + " domain";
            }
        } catch (final InvalidParticipantAddress e) {
            return "Invalid username";
        }

        try {
            if (accountStore.getAccount(id) != null) {
                return "An account with this name already exists";
            }
        } catch (final PersistenceException e) {
            LOG.severe("Failed to retreive account data for " + id, e);
            return "An unexpected error occured while trying to retrieve account status";
        }

        final HumanAccountDataImpl account = new HumanAccountDataImpl(id, passwordDigest);
        try {
            accountStore.putAccount(account);
        } catch (final PersistenceException e) {
            LOG.severe("Failed to create new account for " + id, e);
            return "An unexpected error occured while trying to create the account";
        }

        return null;
    }

    private void writeRegistrationPage(final String message, final String responseType, final Locale locale,
            final HttpServletResponse dest) throws IOException {
        dest.setCharacterEncoding("UTF-8");
        dest.setContentType("text/html;charset=utf-8");
        UserRegistrationPage.write(dest.getWriter(), new GxpContext(locale), domain, message, responseType);
    }
}
