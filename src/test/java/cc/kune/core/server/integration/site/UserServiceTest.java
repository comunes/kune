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
package cc.kune.core.server.integration.site;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
import cc.kune.core.server.mapper.Mapper;
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
import com.google.inject.persist.UnitOfWork;

public class UserServiceTest extends IntegrationTest {
  @Inject
  ContentService contentService;
  private I18nCountryDTO country;
  @Inject
  I18nLanguageManager i18nLangManager;
  private I18nLanguageDTO lang;
  @Inject
  Mapper mapper;
  @Inject
  KuneBasicProperties properties;
  private I18nLanguageSimpleDTO simpleLang;
  @Inject
  SocialNetworkRPC sn;
  private TimeZoneDTO timezone;
  @Inject
  UnitOfWork unitOfWork;
  @Inject
  UserInfoService userInfoService;
  @Inject
  UserService userService;

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

  @Test(expected = EmailAddressInUseException.class)
  public void createUserExistingEmailFails() throws Exception {
    assertNull(session.getUser().getId());
    final UserDTO user = new UserDTO("test2", "test2", "123456", properties.getAdminEmail(), lang,
        country, timezone, null, true, SubscriptionMode.manual, "blue");
    userService.createUser(user, false);
  }

  @Test(expected = GroupLongNameInUseException.class)
  public void createUserExistingLongNameFails() throws Exception {
    assertNull(session.getUser().getId());
    final UserDTO user = new UserDTO("test", properties.getAdminUserName(), "123456",
        "example1234@example.com", lang, country, timezone, null, true, SubscriptionMode.manual, "blue");
    userService.createUser(user, false);
  }

  @Test(expected = GroupShortNameInUseException.class)
  public void createUserExistingShortNameFails() throws Exception {
    assertNull(session.getUser().getId());
    final UserDTO user = new UserDTO(properties.getAdminShortName(), "test", "123456",
        "example1234@example.com", lang, country, timezone, null, true, SubscriptionMode.manual, "blue");
    userService.createUser(user, false);
  }

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

  @Before
  public void init() {
    new IntegrationTestHelper(this);
    lang = new I18nLanguageDTO();
    lang.setCode("en");
    simpleLang = new I18nLanguageSimpleDTO(lang.getCode(), lang.getEnglishName());
    country = new I18nCountryDTO();
    timezone = new TimeZoneDTO();
    lang.setCode("en");
    country.setCode("GB");
    timezone.setId("GMT");
  }

  @Test(expected = SessionExpiredException.class)
  public void testReloadUserInfoNotLogged() throws Exception {
    assertNull(session.getUser().getId());
    userService.reloadUserInfo("AndOldUserHash");
  }

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

  @Test(expected = WrongCurrentPasswordException.class)
  public void testSiteChangeIncorrectPasswdMustFail() throws Exception {
    assertNull(session.getUser().getId());
    doLogin(properties.getAdminShortName(), properties.getAdminPassword());
    assertNotNull(session.getUser().getId());
    userService.changePasswd(session.getHash(), "otherpasswd", "kkkkkk");
  }

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

  @Test
  public void testSiteEmailLogin() throws Exception {
    assertNull(session.getUser().getId());
    userService.login(properties.getAdminEmail(), properties.getAdminPassword(), token);
    assertNotNull(session.getUser().getId());
  }

  @Test
  public void testSiteNameLogin() throws Exception {
    assertNull(session.getUser().getId());
    userService.login(properties.getAdminShortName(), properties.getAdminPassword(), token);
    assertNotNull(session.getUser().getId());
  }

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

  @Test
  public void updatedUser() throws Exception {
    updateUser("test2", "test2", "example1234-2@example.com");
  }

  @Test(expected = EmailAddressInUseException.class)
  public void updatedUserExistingEmailFails() throws Exception {
    updateUser("test", "test", properties.getAdminEmail());
  }

  @Ignore
  @Test(expected = GroupShortNameInUseException.class)
  public void updatedUserExistingShortNameFails() throws Exception {
    // We don't allow shortName change because we cannot change the name of a
    // wave account (and waves)
    updateUser(properties.getAdminShortName(), "test", "example1234@example.com");
  }

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

  @Test(expected = GroupLongNameInUseException.class)
  public void updateUserExistingLongNameFails() throws Exception {
    updateUser("test", properties.getAdminUserName(), "example1234@example.com");
  }

}
