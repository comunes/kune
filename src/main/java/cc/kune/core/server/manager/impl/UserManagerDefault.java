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
package cc.kune.core.server.manager.impl;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.Query;
import org.waveprotocol.box.server.account.AccountData;
import org.waveprotocol.box.server.authentication.PasswordDigest;
import org.waveprotocol.box.server.persistence.AccountStore;
import org.waveprotocol.box.server.persistence.PersistenceException;
import org.waveprotocol.box.server.robots.agent.RobotAgentUtil;
import org.waveprotocol.wave.model.id.InvalidIdException;
import org.waveprotocol.wave.model.id.WaveId;
import org.waveprotocol.wave.model.wave.ParticipantId;
import org.waveprotocol.wave.model.waveref.WaveRef;

import cc.kune.common.shared.utils.SimpleArgCallback;
import cc.kune.common.shared.utils.TextUtils;
import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.client.errors.EmailAddressInUseException;
import cc.kune.core.client.errors.EmailHashExpiredException;
import cc.kune.core.client.errors.EmailHashInvalidException;
import cc.kune.core.client.errors.GroupLongNameInUseException;
import cc.kune.core.client.errors.GroupShortNameInUseException;
import cc.kune.core.client.errors.I18nNotFoundException;
import cc.kune.core.client.errors.UserRegistrationException;
import cc.kune.core.client.errors.WrongCurrentPasswordException;
import cc.kune.core.client.state.SiteTokens;
import cc.kune.core.client.state.TokenUtils;
import cc.kune.core.server.i18n.I18nTranslationServiceMultiLang;
import cc.kune.core.server.manager.GroupManager;
import cc.kune.core.server.manager.I18nCountryManager;
import cc.kune.core.server.manager.I18nLanguageManager;
import cc.kune.core.server.manager.SearchResult;
import cc.kune.core.server.manager.UserManager;
import cc.kune.core.server.notifier.NotificationService;
import cc.kune.core.server.persist.DataSourceKune;
import cc.kune.core.server.properties.ChatProperties;
import cc.kune.core.server.properties.KuneBasicProperties;
import cc.kune.core.server.xmpp.RosterItem;
import cc.kune.core.server.xmpp.XmppManager;
import cc.kune.core.server.xmpp.XmppRosterProvider;
import cc.kune.core.shared.domain.UserSNetVisibility;
import cc.kune.core.shared.dto.I18nLanguageSimpleDTO;
import cc.kune.core.shared.dto.UserDTO;
import cc.kune.domain.Group;
import cc.kune.domain.I18nCountry;
import cc.kune.domain.I18nLanguage;
import cc.kune.domain.User;
import cc.kune.domain.UserBuddiesData;
import cc.kune.domain.finders.UserFinder;
import cc.kune.wave.server.CustomUserRegistrationServlet;
import cc.kune.wave.server.ParticipantUtils;
import cc.kune.wave.server.kspecific.KuneWaveService;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class UserManagerDefault extends DefaultManager<User, Long> implements UserManager {

  public static final Log LOG = LogFactory.getLog(UserManagerDefault.class);
  private final ChatProperties chatProperties;
  private final I18nCountryManager countryManager;
  private final GroupManager groupManager;
  private final I18nTranslationServiceMultiLang i18n;
  private final KuneWaveService kuneWaveManager;
  private final I18nLanguageManager languageManager;
  private final NotificationService notifyService;
  private final ParticipantUtils participantUtils;
  private final KuneBasicProperties properties;
  private final UserFinder userFinder;
  private final AccountStore waveAccountStore;
  private final CustomUserRegistrationServlet waveUserRegister;
  private final XmppManager xmppManager;
  private final XmppRosterProvider xmppRoster;

  @Inject
  public UserManagerDefault(@DataSourceKune final Provider<EntityManager> provider,
      final UserFinder finder, final I18nLanguageManager languageManager,
      final I18nCountryManager countryManager, final XmppManager xmppManager,
      final ChatProperties chatProperties, final I18nTranslationServiceMultiLang i18n,
      final CustomUserRegistrationServlet waveUserRegister, final AccountStore waveAccountStore,
      final KuneWaveService kuneWaveManager, final ParticipantUtils participantUtils,
      final KuneBasicProperties properties, final GroupManager groupManager,
      final NotificationService notifyService, final XmppRosterProvider xmppRoster) {
    super(provider, User.class);
    this.userFinder = finder;
    this.languageManager = languageManager;
    this.countryManager = countryManager;
    this.xmppManager = xmppManager;
    this.chatProperties = chatProperties;
    this.i18n = i18n;
    this.waveUserRegister = waveUserRegister;
    this.waveAccountStore = waveAccountStore;
    this.kuneWaveManager = kuneWaveManager;
    this.participantUtils = participantUtils;
    this.properties = properties;
    this.groupManager = groupManager;
    this.notifyService = notifyService;
    this.xmppRoster = xmppRoster;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.UserManager#askForEmailConfirmation(cc.kune
   * .domain.User, cc.kune.core.server.manager.impl.EmailConfirmationType)
   * 
   * More info: http://en.wikipedia.org/wiki/Self-service_password_reset
   * http://en.wikipedia.org/wiki/Password_notification_e-mail
   * http://stackoverflow
   * .com/questions/1102781/best-way-for-a-forgot-password-implementation
   */
  @Override
  public void askForEmailConfirmation(final User user, final EmailConfirmationType type)
      throws DefaultException {
    user.setEmailCheckDate(System.currentTimeMillis());
    final String hash = UUID.randomUUID().toString();
    user.setEmailConfirmHash(hash);
    persist(user);

    switch (type) {
    case emailVerification:
      notifyService.sendEmailToWithLink(user, "Please verify your email",
          "Please click in the following link to verify your email at %s:",
          TokenUtils.addRedirect(SiteTokens.VERIFY_EMAIL, hash));
      break;
    case passwordReset:
      notifyService.sendEmailToWithLink(
          user,
          "Verify password reset",
          "You are receiving this email because a request has been made to change the password associated with this email address in %s.<br><br>"
              + "If this was a mistake, just ignore this email and nothing will happen.<br><br>"
              + "If you would like to reset the password for this account simply click on the link below or paste it into the url field on your favorite browser:",
          TokenUtils.addRedirect(SiteTokens.RESET_PASSWD, hash));
    default:
      break;
    }
  }

  @Override
  public User changePasswd(final Long userId, final String oldPassword, final String newPassword,
      final boolean checkOldPasswd) {
    final User user = find(userId);
    final ParticipantId participantId = participantUtils.of(user.getShortName());
    if (checkOldPasswd) {
      // Check oldPasswd
      AccountData account;
      try {
        account = waveAccountStore.getAccount(participantId);
        if (TextUtils.notEmpty(oldPassword) && account != null
            && !account.asHuman().getPasswordDigest().verify(oldPassword.toCharArray())) {
          throw new WrongCurrentPasswordException();
        }
      } catch (final PersistenceException e) {
        thowExceptionChangingPasswd(e);
      }
    }
    try {
      // Wave change passwd
      RobotAgentUtil.changeUserPassword(newPassword, participantId, waveAccountStore);
    } catch (final IllegalArgumentException e) {
      thowExceptionChangingPasswd(e);
    } catch (final PersistenceException e) {
      thowExceptionChangingPasswd(e);
    }
    // Kune db change passwd
    final PasswordDigest newPasswordDigest = new PasswordDigest(newPassword.toCharArray());
    user.setSalt(newPasswordDigest.getSalt());
    user.setDiggest(newPasswordDigest.getDigest());
    return persist(user);
  }

  private void checkIfEmailAreInUse(final String email) {
    if (userFinder.countByEmail(email) != 0) {
      throw new EmailAddressInUseException();
    }
  }

  private void checkIfLongNameAreInUse(final String longName) {
    if (userFinder.countByLongName(longName) != 0) {
      throw new GroupLongNameInUseException();
    }
    groupManager.checkIfLongNameAreInUse(longName);
  }

  private void checkIfNamesAreInUse(final String shortName, final String longName, final String email) {
    checkIfShortNameAreInUse(shortName);
    checkIfLongNameAreInUse(longName);
    checkIfEmailAreInUse(email);
  }

  private void checkIfShortNameAreInUse(final String shortName) {
    if (userFinder.countByShortName(shortName) != 0) {
      throw new GroupShortNameInUseException();
    }
    groupManager.checkIfShortNameAreInUse(shortName);
  }

  @Override
  public void clearPasswordHash(final User user) {
    user.setEmailVerified(true);
    user.setEmailCheckDate(0l);
    user.setEmailConfirmHash(null);
  }

  @Override
  public User createUser(final String shortName, final String longName, final String email,
      final String passwd, final String langCode, final String countryCode, final String timezone,
      final boolean wantPersonalHomepage) throws I18nNotFoundException {
    I18nLanguage language;
    I18nCountry country;
    TimeZone tz;
    checkIfNamesAreInUse(shortName, longName, email);
    try {
      language = findLanguage(langCode);
      country = countryManager.findByCode(countryCode);
      tz = TimeZone.getTimeZone(timezone);
    } catch (final NoResultException e) {
      throw new I18nNotFoundException();
    }
    final PasswordDigest passwdDigest = new PasswordDigest(passwd.toCharArray());

    try {
      createWaveAccount(shortName, passwdDigest);
    } catch (final UserRegistrationException e) {
      throw e;
    }
    WaveRef welcome = null;
    final User user = new User(shortName, longName, email, passwdDigest.getDigest(),
        passwdDigest.getSalt(), language, country, tz);

    final String defWave = properties.getWelcomewave();
    groupManager.createUserGroup(user, wantPersonalHomepage);
    try {
      try {
        if (!TextUtils.empty(defWave)) {
          final WaveId copyWaveId = WaveId.ofChecked(participantUtils.getDomain(), defWave);
          welcome = kuneWaveManager.createWave(
              ContentConstants.WELCOME_WAVE_CONTENT_TITLE.replaceAll("\\[%s\\]",
                  properties.getDefaultSiteName()), "", WaveRef.of(copyWaveId),
              new SimpleArgCallback<WaveRef>() {
                @Override
                public void onCallback(final WaveRef arg) {
                  // Is this necessary? try to remove (used when we were setting
                  // the def
                  // content
                  // contentManager.save(userGroup.getDefaultContent());
                  askForEmailConfirmation(user, EmailConfirmationType.emailVerification);
                }
              }, null, participantUtils.of(properties.getAdminShortName()),
              participantUtils.of(shortName));
        }
      } catch (final InvalidIdException e) {
        LOG.error("Cannot create a welcome wave", e);
      }
      return user;
    } catch (final RuntimeException e) {
      try {
        if (welcome != null) {
          kuneWaveManager.delParticipants(welcome, properties.getAdminShortName(), shortName);
        }
        // Try to remove wave account
        waveAccountStore.removeAccount(participantUtils.of(shortName));
      } catch (final Exception e2) {
        throw e;
      }
      throw e;
    }
  }

  @Override
  public void createWaveAccount(final String shortName, final PasswordDigest passwdDigest) {
    final String msg = waveUserRegister.tryCreateUser(shortName, passwdDigest);
    if (msg != null) {
      throw new UserRegistrationException(msg);
    }
  }

  @Override
  public User find(final Long userId) {
    try {
      return userFinder.findById(userId);
    } catch (final NoResultException e) {
      return User.UNKNOWN_USER;
    }
  }

  @Override
  public User findByShortname(final String shortName) {
    return userFinder.findByShortName(shortName);
  }

  private I18nLanguage findLanguage(final String langCode) {
    return languageManager.findByCode(langCode);
  }

  public List<User> getAll() {
    return userFinder.getAll();
  }

  @Override
  public UserBuddiesData getUserBuddies(final String shortName) {
    // XEP-133 get roster by admin part is not implemented in openfire
    // also access to the openfire db is not easy with hibernate (the use of
    // two db at the same time). This compromise solution is server
    // independent.
    // In the future cache this.
    final String domain = "@" + chatProperties.getDomain();
    final UserBuddiesData buddiesData = new UserBuddiesData();

    final User user = userFinder.findByShortName(shortName);
    Collection<RosterItem> roster;
    roster = xmppRoster.getRoster(user.getShortName());
    for (final RosterItem entry : roster) {
      if (entry.getSubStatus() == 3) {
        // only show buddies with subscription 'both'
        final String jid = entry.getJid();
        final int index = jid.indexOf(domain);
        if (index > 0) {
          // local user
          try {
            final String username = jid.substring(0, index);
            final User buddie = userFinder.findByShortName(username);
            buddiesData.getBuddies().add(buddie);
          } catch (final NoResultException e) {
            // No existent buddie, skip
          }
        } else {
          // ext user (only count)
          buddiesData.setOtherExtBuddies(buddiesData.getOtherExtBuddies() + 1);
        }
      }
    }
    return buddiesData;
  }

  @Override
  public User login(final String nickOrEmail, final String passwd) {
    User user;
    try {
      user = userFinder.findByShortName(nickOrEmail);
    } catch (final NoResultException e) {
      try {
        user = userFinder.findByEmail(nickOrEmail);
      } catch (final NoResultException e2) {
        return null;
      }
    }
    if (PasswordDigest.from(user.getSalt(), user.getDiggest()).verify(passwd.toCharArray())) {
      final I18nLanguage lang = user.getLanguage();
      if (user.getLastLogin() == null) {
        final String userName = user.getShortName();
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
          @Override
          public void run() {
            // FIXME: Use notifyService!
            xmppManager.sendMessage(
                userName,
                i18n.tWithNT(
                    lang,
                    "This is the chat window. "
                        + "Here you can communicate with other users of [%s] but also with other users with compatible accounts (like gmail accounts). "
                        + "Just add some buddy and start to chat.", "",
                    i18n.tWithNT(lang, properties.getSiteCommonName(), "")));
          }
        }, 5000);
      }
      user.setLastLogin(System.currentTimeMillis());
      return user;
    } else {
      return null;
    }
  }

  @Override
  public SearchResult<User> search(final String search) {
    return this.search(search, null, null);
  }

  @Override
  public SearchResult<User> search(final String search, final Integer firstResult,
      final Integer maxResults) {
    final MultiFieldQueryParser parser = new MultiFieldQueryParser(new String[] { "name", "shortName" },
        new StandardAnalyzer());
    Query query;
    try {
      query = parser.parse(search);
    } catch (final ParseException e) {
      throw new ServerManagerException("Error parsing search", e);
    }
    return super.search(query, firstResult, maxResults);
  }

  @Override
  public void setSNetVisibility(final User user, final UserSNetVisibility visibility) {
    user.setSNetVisibility(visibility);
    persist(user);
  }

  private void thowExceptionChangingPasswd(final Exception e) {
    throw new DefaultException("Error changing user passwd", e);
  }

  @Override
  public User update(final Long userId, final UserDTO userDTO, final I18nLanguageSimpleDTO lang) {
    final User user = find(userId);
    // final String shortName = userDTO.getShortName();
    final String longName = userDTO.getName();
    final String email = userDTO.getEmail();
    final Group userGroup = user.getUserGroup();
    user.setEmailNotifFreq(userDTO.getEmailNotifFreq());
    // We don't allow to change shortName because we cannot change shotNames in
    // wave accounts
    // if (!shortName.equals(user.getShortName())) {
    // checkIfShortNameAreInUse(shortName);
    // user.setShortName(shortName);
    // userGroup.setShortName(shortName);
    // }
    if (longName != null && !longName.equals(user.getName())) {
      checkIfLongNameAreInUse(longName);
      user.setName(longName);
      userGroup.setLongName(longName);
    }
    if (email != null && !email.equals(user.getEmail())) {
      checkIfEmailAreInUse(email);
      user.setEmail(email);
      user.setEmailVerified(false);
      askForEmailConfirmation(user, EmailConfirmationType.emailVerification);
    }
    user.setLanguage(findLanguage(lang.getCode()));
    persist(user);
    groupManager.persist(userGroup);
    return user;
  }

  @Override
  public void verifyPasswordHash(final Long userId, final String emailReceivedHash, final long period)
      throws EmailHashInvalidException, EmailHashExpiredException {
    final User user = find(userId);
    final Date on = new Date(user.getEmailCheckDate() + period);

    final Date now = new Date();
    if (on.before(now)) {
      throw new EmailHashExpiredException();
    }
    final String emailConfirmHash = user.getEmailConfirmHash();
    if (emailReceivedHash != null && emailConfirmHash != null
        && emailReceivedHash.equals(emailConfirmHash)) {
      clearPasswordHash(user);
      persist(user);
    } else {
      throw new EmailHashInvalidException();
    }
  }
}
