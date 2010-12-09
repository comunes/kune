package org.ourproject.kune.platf.integration.site;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.integration.IntegrationTest;
import org.ourproject.kune.platf.integration.IntegrationTestHelper;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.manager.I18nLanguageManager;
import org.ourproject.kune.platf.server.mapper.Mapper;
import org.ourproject.kune.platf.server.properties.DatabaseProperties;
import org.ourproject.kune.platf.server.users.UserInfo;
import org.ourproject.kune.platf.server.users.UserInfoService;

import cc.kune.core.client.errors.EmailAddressInUseException;
import cc.kune.core.client.errors.GroupNameInUseException;
import cc.kune.core.client.errors.SessionExpiredException;
import cc.kune.core.client.rpcservices.UserService;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.I18nCountryDTO;
import cc.kune.core.shared.dto.I18nLanguageDTO;
import cc.kune.core.shared.dto.SubscriptionMode;
import cc.kune.core.shared.dto.TimeZoneDTO;
import cc.kune.core.shared.dto.UserDTO;
import cc.kune.core.shared.dto.UserInfoDTO;

import com.google.inject.Inject;

public class UserServiceTest extends IntegrationTest {
    @Inject
    DatabaseProperties properties;
    @Inject
    UserService userService;
    @Inject
    UserInfoService userInfoService;
    @Inject
    Mapper mapper;
    @Inject
    I18nLanguageManager i18nLangManager;
    private I18nLanguageDTO lang;
    private I18nCountryDTO country;
    private TimeZoneDTO timezone;

    @Test(expected = EmailAddressInUseException.class)
    public void createUserExistingEmailFails() throws Exception {
        assertNull(session.getUser().getId());
        final UserDTO user = new UserDTO("test", "test", "123456", properties.getAdminEmail(), lang, country, timezone,
                null, true, SubscriptionMode.manual, "blue");
        userService.createUser(user, false);
    }

    @Test(expected = GroupNameInUseException.class)
    public void createUserExistingNameFails() throws Exception {
        assertNull(session.getUser().getId());
        final UserDTO user = new UserDTO("test", properties.getAdminShortName(), "123456", "example1234@example.com",
                lang, country, timezone, null, true, SubscriptionMode.manual, "blue");
        userService.createUser(user, false);
    }

    @Before
    public void init() {
        new IntegrationTestHelper(this);
        lang = new I18nLanguageDTO();
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
    public void testSiteEmailLogin() throws Exception {
        assertNull(session.getUser().getId());
        userService.login(properties.getAdminEmail(), properties.getAdminPassword());
        assertNotNull(session.getUser().getId());
    }

    @Test
    public void testSiteNameLogin() throws Exception {
        assertNull(session.getUser().getId());
        userService.login(properties.getAdminShortName(), properties.getAdminPassword());
        assertNotNull(session.getUser().getId());
    }

    @Test
    public void testUserInfo() throws Exception {
        doLogin();
        final UserInfo userInfo = userInfoService.buildInfo(session.getUser(), session.getHash());

        final UserInfoDTO userInfoDTO = mapper.map(userInfo, UserInfoDTO.class);
        assertEquals(userInfo.getName(), userInfoDTO.getName());
        assertEquals(userInfo.getChatName(), userInfoDTO.getChatName());
        assertEquals(userInfo.getChatPassword(), userInfoDTO.getChatPassword());
        final List<Group> adminsGroup = userInfo.getGroupsIsAdmin();
        final List<GroupDTO> adminsGroupDTO = userInfoDTO.getGroupsIsAdmin();
        assertEqualGroupLists(adminsGroupDTO, adminsGroup);
    }

    private void assertEqualGroupLists(final List<GroupDTO> listDTO, final List<Group> list) {
        assertEquals(listDTO.size(), list.size());
        for (int i = 0; i < listDTO.size(); i++) {
            final Object object = listDTO.get(i);
            assertEquals(GroupDTO.class, object.getClass());
            final GroupDTO d = (GroupDTO) object;
            final Group l = list.get(i);
            assertNotNull(d);
            assertNotNull(l);
            final GroupDTO map = mapper.map(l, GroupDTO.class);
            assertEquals(map.getShortName(), d.getShortName());
        }
    }

}
