/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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
package cc.kune.core.server.integration.site;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import cc.kune.core.client.errors.AccessViolationException;
import cc.kune.core.client.errors.EmailAddressInUseException;
import cc.kune.core.client.errors.GroupLongNameInUseException;
import cc.kune.core.client.errors.GroupShortNameInUseException;
import cc.kune.core.client.errors.SessionExpiredException;
import cc.kune.core.client.errors.WrongCurrentPasswordException;
import cc.kune.core.client.rpcservices.ContentService;
import cc.kune.core.client.rpcservices.UserService;
import cc.kune.core.server.integration.IntegrationTest;
import cc.kune.core.server.integration.IntegrationTestHelper;
import cc.kune.core.server.manager.I18nLanguageManager;
import cc.kune.core.server.mapper.KuneMapper;
import cc.kune.core.server.properties.KuneBasicProperties;
import cc.kune.core.server.rpc.SocialNetworkRPC;
import cc.kune.core.server.users.UserInfo;
import cc.kune.core.server.users.UserInfoService;
import cc.kune.core.shared.domain.UserSNetVisibility;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.I18nCountryDTO;
import cc.kune.core.shared.dto.I18nLanguageDTO;
import cc.kune.core.shared.dto.I18nLanguageSimpleDTO;
import cc.kune.core.shared.dto.SocialNetworkDataDTO;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.core.shared.dto.StateContentDTO;
import cc.kune.core.shared.dto.SubscriptionMode;
import cc.kune.core.shared.dto.TimeZoneDTO;
import cc.kune.core.shared.dto.UserDTO;
import cc.kune.core.shared.dto.UserInfoDTO;
import cc.kune.domain.Group;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class UserServiceTest.
 * 
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class UserServiceTest extends IntegrationTest {

  /** The content service. */
  @Inject
  ContentService contentService;

  /** The country. */
  private I18nCountryDTO country;

  /** The i18n lang manager. */
  @Inject
  I18nLanguageManager i18nLangManager;

  /** The lang. */
  private I18nLanguageDTO lang;

  /** The mapper. */
  @Inject
  KuneMapper mapper;

  /** The properties. */
  @Inject
  KuneBasicProperties properties;

  /** The simple lang. */
  private I18nLanguageSimpleDTO simpleLang;

  /** The sn. */
  @Inject
  SocialNetworkRPC sn;

  /** The timezone. */
  private TimeZoneDTO timezone;

  /** The user info service. */
  @Inject
  UserInfoService userInfoService;

  /** The user service. */
  @Inject
  UserService userService;

  /**
   * Assert equal group lists.
   * 
   * @param listDTO
   *          the list dto
   * @param list
   *          the list
   */
  private void assertEqualGroupLists(final Set<GroupDTO> listDTO, final Set<Group> list) {
    assertEquals(listDTO.size(), list.size());
    final Iterator<Group> ite = list.iterator();
    for (final GroupDTO groupDTO : listDTO) {
      final GroupDTO d = groupDTO;
      final Group l = ite.next();
      assertNotNull(d);
      assertNotNull(l);
      final GroupDTO map = mapper.map(l, GroupDTO.class);
      assertEquals(map.getShortName(), d.getShortName());
    }
  }

  /**
   * Creates the user existing email fails.
   * 
   * @throws Exception
   *           the exception
   */
  @Test(expected = EmailAddressInUseException.class)
  public void createUserExistingEmailFails() throws Exception {
    assertNull(session.getUser().getId());
    final UserDTO user = new UserDTO("test2", "test2", "123456", properties.getAdminEmail(), lang,
        country, timezone, null, true, SubscriptionMode.manual, "blue");
    userService.createUser(user, false);
  }

  /**
   * Creates the user existing long name fails.
   * 
   * @throws Exception
   *           the exception
   */
  @Test(expected = GroupLongNameInUseException.class)
  public void createUserExistingLongNameFails() throws Exception {
    assertNull(session.getUser().getId());
    final UserDTO user = new UserDTO("test", properties.getAdminUserName(), "123456",
        "example1234@example.com", lang, country, timezone, null, true, SubscriptionMode.manual, "blue");
    userService.createUser(user, false);
  }

  /**
   * Creates the user existing short name fails.
   * 
   * @throws Exception
   *           the exception
   */
  @Test(expected = GroupShortNameInUseException.class)
  public void createUserExistingShortNameFails() throws Exception {
    assertNull(session.getUser().getId());
    final UserDTO user = new UserDTO(properties.getAdminShortName(), "test", "123456",
        "example1234@example.com", lang, country, timezone, null, true, SubscriptionMode.manual, "blue");
    userService.createUser(user, false);
  }

  /**
   * Creates the user should permit edit of self homepage.
   * 
   * @throws Exception
   *           the exception
   */
  @Test
  public void createUserShouldPermitEditOfSelfHomepage() throws Exception {
    assertNull(session.getUser().getId());
    final UserDTO user = new UserDTO("test", "test", "123456", "example1234@example.com", lang, country,
        timezone, null, true, SubscriptionMode.manual, "blue");
    userService.createUser(user, true);
    doLogin("test", "123456");
    final StateAbstractDTO homepage = contentService.getContent(getHash(), new StateToken("test"));
    assertTrue(homepage.getGroupRights().isAdministrable());
    assertTrue(homepage.getGroupRights().isEditable());
    assertTrue(((StateContentDTO) homepage).getContentRights().isAdministrable());
    assertTrue(((StateContentDTO) homepage).getContentRights().isEditable());
    assertTrue(((StateContentDTO) homepage).getContainerRights().isAdministrable());
    assertTrue(((StateContentDTO) homepage).getContainerRights().isEditable());
  }

  /**
   * Inits the.
   */
  @Before
  public void init() {
    new IntegrationTestHelper(true, this);
    lang = new I18nLanguageDTO();
    lang.setCode("en");
    simpleLang = new I18nLanguageSimpleDTO(lang.getCode(), lang.getEnglishName());
    country = new I18nCountryDTO();
    timezone = new TimeZoneDTO();
    lang.setCode("en");
    country.setCode("GB");
    timezone.setId("GMT");
  }

  /**
   * Test reload user info not logged.
   * 
   * @throws Exception
   *           the exception
   */
  @Test(expected = SessionExpiredException.class)
  public void testReloadUserInfoNotLogged() throws Exception {
    assertNull(session.getUser().getId());
    userService.reloadUserInfo("AndOldUserHash");
  }

  /**
   * Test set visibility.
   * 
   * @throws Exception
   *           the exception
   */
  @Test
  public void testSetVisibility() throws Exception {
    assertNull(session.getUser().getId());
    doLogin();
    assertNotNull(session.getUser().getId());
    for (final UserSNetVisibility visibility : UserSNetVisibility.values()) {
      userService.setBuddiesVisibility(token, session.getUser().getStateToken(), visibility);
      final SocialNetworkDataDTO snRetrieved = sn.getSocialNetwork(token,
          session.getUser().getStateToken());
      assertEquals(visibility, snRetrieved.getUserBuddiesVisibility());
      assertEquals(visibility, session.getUser().getSNetVisibility());
    }
  }

  /**
   * Test site change incorrect passwd must fail.
   * 
   * @throws Exception
   *           the exception
   */
  @Test(expected = WrongCurrentPasswordException.class)
  public void testSiteChangeIncorrectPasswdMustFail() throws Exception {
    assertNull(session.getUser().getId());
    doLogin(properties.getAdminShortName(), properties.getAdminPassword());
    assertNotNull(session.getUser().getId());
    userService.changePasswd(session.getHash(), "otherpasswd", "kkkkkk");
  }

  /**
   * Test site change passwd.
   * 
   * @throws Exception
   *           the exception
   */
  @Test
  public void testSiteChangePasswd() throws Exception {
    assertNull(session.getUser().getId());
    doLogin(properties.getAdminShortName(), properties.getAdminPassword());
    assertNotNull(session.getUser().getId());
    userService.changePasswd(session.getHash(), properties.getAdminPassword(), "kkkkkk");
    doLogout();
    doLogin(properties.getAdminShortName(), "kkkkkk");
    userService.changePasswd(session.getHash(), "kkkkkk", properties.getAdminPassword());
    doLogout();
  }

  /**
   * Test site email login.
   * 
   * @throws Exception
   *           the exception
   */
  @Test
  public void testSiteEmailLogin() throws Exception {
    assertNull(session.getUser().getId());
    userService.login(properties.getAdminEmail(), properties.getAdminPassword(), token);
    assertNotNull(session.getUser().getId());
  }

  /**
   * Test site name login.
   * 
   * @throws Exception
   *           the exception
   */
  @Test
  public void testSiteNameLogin() throws Exception {
    assertNull(session.getUser().getId());
    userService.login(properties.getAdminShortName(), properties.getAdminPassword(), token);
    assertNotNull(session.getUser().getId());
  }

  /**
   * Test user info.
   * 
   * @throws Exception
   *           the exception
   */
  @Test
  public void testUserInfo() throws Exception {
    doLogin();
    final UserInfo userInfo = userInfoService.buildInfo(session.getUser(), session.getHash());

    final UserInfoDTO userInfoDTO = mapper.map(userInfo, UserInfoDTO.class);
    assertEquals(userInfo.getName(), userInfoDTO.getName());
    assertEquals(userInfo.getChatName(), userInfoDTO.getChatName());
    final Set<Group> adminsGroup = userInfo.getGroupsIsAdmin();
    final Set<GroupDTO> adminsGroupDTO = userInfoDTO.getGroupsIsAdmin();
    assertEqualGroupLists(adminsGroupDTO, adminsGroup);
  }

  /**
   * Update another user fails.
   * 
   * @throws Exception
   *           the exception
   */
  @Test(expected = AccessViolationException.class)
  public void updateAnotherUserFails() throws Exception {
    assertNull(session.getUser().getId());
    final UserDTO user = new UserDTO("test", "test", "123456", "example1234@example.com", lang, country,
        timezone, null, true, SubscriptionMode.manual, "blue");
    userService.createUser(user, false);
    // do login as admin
    doLogin();
    userService.updateUser(getHash(), user, simpleLang);
  }

  /**
   * Updated user.
   * 
   * @throws Exception
   *           the exception
   */
  @Test
  public void updatedUser() throws Exception {
    updateUser("test2", "test2", "example1234-2@example.com");
  }

  /**
   * Updated user existing email fails.
   * 
   * @throws Exception
   *           the exception
   */
  @Test(expected = EmailAddressInUseException.class)
  public void updatedUserExistingEmailFails() throws Exception {
    updateUser("test", "test", properties.getAdminEmail());
  }

  /**
   * Updated user existing short name fails.
   * 
   * @throws Exception
   *           the exception
   */
  @Ignore
  @Test(expected = GroupShortNameInUseException.class)
  public void updatedUserExistingShortNameFails() throws Exception {
    // We don't allow shortName change because we cannot change the name of a
    // wave account (and waves)
    updateUser(properties.getAdminShortName(), "test", "example1234@example.com");
  }

  /**
   * Update user.
   * 
   * @param shortName
   *          the short name
   * @param longName
   *          the long name
   * @param email
   *          the email
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  private void updateUser(final String shortName, final String longName, final String email)
      throws IOException {
    assertNull(session.getUser().getId());
    final UserDTO user = new UserDTO("test", "test", "123456", "example1234@example.com", lang, country,
        timezone, null, true, SubscriptionMode.manual, "blue");
    userService.createUser(user, false);
    doLogin("test", "123456");
    final UserDTO userChanged = new UserDTO(shortName, longName, "123456", email, lang, country,
        timezone, null, true, SubscriptionMode.manual, "blue");
    userChanged.setId(session.getUser().getId());
    userService.updateUser(getHash(), userChanged, simpleLang);
  }

  /**
   * Update user existing long name fails.
   * 
   * @throws Exception
   *           the exception
   */
  @Test(expected = GroupLongNameInUseException.class)
  public void updateUserExistingLongNameFails() throws Exception {
    updateUser("test", properties.getAdminUserName(), "example1234@example.com");
  }

}
