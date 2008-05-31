package org.ourproject.kune.platf.integration.site;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.client.dto.I18nCountryDTO;
import org.ourproject.kune.platf.client.dto.I18nLanguageDTO;
import org.ourproject.kune.platf.client.dto.LinkDTO;
import org.ourproject.kune.platf.client.dto.TimeZoneDTO;
import org.ourproject.kune.platf.client.dto.UserDTO;
import org.ourproject.kune.platf.client.dto.UserInfoDTO;
import org.ourproject.kune.platf.client.errors.EmailAddressInUseException;
import org.ourproject.kune.platf.client.errors.GroupNameInUseException;
import org.ourproject.kune.platf.client.errors.SessionExpiredException;
import org.ourproject.kune.platf.integration.IntegrationTest;
import org.ourproject.kune.platf.integration.IntegrationTestHelper;
import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.manager.I18nLanguageManager;
import org.ourproject.kune.platf.server.mapper.Mapper;
import org.ourproject.kune.platf.server.properties.DatabaseProperties;
import org.ourproject.kune.platf.server.users.Link;
import org.ourproject.kune.platf.server.users.UserInfo;
import org.ourproject.kune.platf.server.users.UserInfoService;
import org.ourproject.kune.workspace.client.sitebar.rpc.UserService;

import com.calclab.emite.client.im.roster.RosterManager.SubscriptionMode;
import com.google.inject.Inject;

public class UserServiceTest extends IntegrationTest {
    @Inject
    UserSession session;
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
        userService.createUser(user);
    }

    @Test(expected = GroupNameInUseException.class)
    public void createUserExistingNameFails() throws Exception {
        assertNull(session.getUser().getId());
        final UserDTO user = new UserDTO("test", properties.getAdminShortName(), "123456", "example@example.com", lang,
                country, timezone, null, true, SubscriptionMode.manual, "blue");
        userService.createUser(user);
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
        final List<Link> adminsGroup = userInfo.getGroupsIsAdmin();
        final List<LinkDTO> adminsGroupDTO = userInfoDTO.getGroupsIsAdmin();
        assertEqualListsLink(adminsGroupDTO, adminsGroup);
    }

    private void assertEqualListsLink(final List<LinkDTO> listDTO, final List<Link> list) {
        assertEquals(listDTO.size(), list.size());
        for (int i = 0; i < listDTO.size(); i++) {
            final Object object = listDTO.get(i);
            assertEquals(LinkDTO.class, object.getClass());
            final LinkDTO d = (LinkDTO) object;
            final Link l = list.get(i);
            assertNotNull(d);
            assertNotNull(l);
            final LinkDTO map = mapper.map(l, LinkDTO.class);
            assertEquals(map.getShortName(), d.getShortName());
            assertEquals(map.getLink(), d.getLink());
        }
    }

}
