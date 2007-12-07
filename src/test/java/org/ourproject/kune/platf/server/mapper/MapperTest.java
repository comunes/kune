package org.ourproject.kune.platf.server.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.client.dto.ContainerDTO;
import org.ourproject.kune.platf.client.dto.ContentDTO;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.GroupListDTO;
import org.ourproject.kune.platf.client.dto.LicenseDTO;
import org.ourproject.kune.platf.server.TestDomainHelper;
import org.ourproject.kune.platf.server.TestHelper;
import org.ourproject.kune.platf.server.access.AccessRights;
import org.ourproject.kune.platf.server.domain.Container;
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.GroupList;
import org.ourproject.kune.platf.server.domain.GroupListMode;
import org.ourproject.kune.platf.server.domain.License;
import org.ourproject.kune.platf.server.domain.Revision;
import org.ourproject.kune.platf.server.manager.GroupManager;
import org.ourproject.kune.platf.server.state.State;
import org.ourproject.kune.platf.server.users.UserInfoService;
import org.ourproject.kune.workspace.client.dto.StateDTO;

import com.google.inject.Inject;

public class MapperTest {
    @Inject
    Mapper mapper;
    @Inject
    UserInfoService userInfoService;
    @Inject
    GroupManager groupManager;

    @Before
    public void inject() {
        TestHelper.inject(this);
    }

    @Test
    public void testContentMapping() {
        final State c = new State();
        c.setContentRights(new AccessRights(true, true, true));
        final Group groupAdmins = TestDomainHelper.createGroup(1);
        final Group groupEdit = TestDomainHelper.createGroup(2);
        final Group groupView = TestDomainHelper.createGroup(3);
        c.setAccessLists(TestDomainHelper.createAccessLists(groupAdmins, groupEdit, groupView));
        c.setRate(10.2d);
        c.setRateByUsers(3l);
        c.setCurrentUserRate(null);

        final StateDTO dto = mapper.map(c, StateDTO.class);
        assertEquals(c.getContentRights().isAdministrable(), dto.getContentRights().isAdministrable());

        assertValidAccessListsMapping(c.getAccessLists().getAdmins(), dto.getAccessLists().getAdmins());
        assertValidAccessListsMapping(c.getAccessLists().getEditors(), dto.getAccessLists().getEditors());
        assertValidAccessListsMapping(c.getAccessLists().getViewers(), dto.getAccessLists().getViewers());

        assertEquals(dto.getRate(), c.getRate());
        assertEquals(dto.getRateByUsers(), c.getRateByUsers());
        assertEquals(dto.getCurrentUserRate(), c.getCurrentUserRate());
    }

    private void assertValidAccessListsMapping(final GroupList groupList, final GroupListDTO groupListDTO) {
        final List listOrig = groupList.getList();
        final List listDto = groupListDTO.getList();
        assertEquals(listDto.size(), listOrig.size());
        for (int i = 0; i < listDto.size(); i++) {
            final Object object = listDto.get(i);
            assertEquals(GroupDTO.class, object.getClass());
            final GroupDTO d = (GroupDTO) object;
            final Group g = (Group) listOrig.get(i);
            assertNotNull(d);
            assertNotNull(g);
            final GroupDTO map = mapper.map(g, GroupDTO.class);
            assertEquals(map, d);
        }
    }

    @Test
    public void testGroupListMapping() {
        assertMapping(GroupListMode.EVERYONE, GroupListDTO.EVERYONE);
        assertMapping(GroupListMode.NOBODY, GroupListDTO.NOBODY);
        assertMapping(GroupListMode.NORMAL, GroupListDTO.NORMAL);
    }

    private void assertMapping(final GroupListMode mode, final String modeName) {
        final GroupList list = new GroupList();
        list.setMode(mode);
        final GroupListDTO dto = mapper.map(list, GroupListDTO.class);
        assertEquals(modeName, dto.getMode());
        final GroupList listBack = mapper.map(dto, GroupList.class);
        assertEquals(mode, listBack.getMode());
    }

    @Test
    public void testGroupMapping() {
        final Group group = new Group("shortName", "name");
        final GroupDTO dto = mapper.map(group, GroupDTO.class);
        assertEquals(group.getLongName(), dto.getLongName());
        assertEquals(group.getShortName(), dto.getShortName());
    }

    @Test
    public void testContentDescriptorMapping() {
        final Content d = new Content();
        d.setId(1l);
        final Revision revision = new Revision();
        revision.getData().setTitle("title");
        d.addRevision(revision);

        final ContentDTO dto = mapper.map(d, ContentDTO.class);
        assertEquals(1l, dto.getId());
        assertEquals("title", dto.getTitle());
    }

    @Test
    public void testFolderMapping() {
        final Container container = new Container();
        container.addChild(new Container());
        container.addChild(new Container());
        container.addContent(new Content());
        container.addContent(new Content());
        container.addContent(new Content());

        final ContainerDTO dto = mapper.map(container, ContainerDTO.class);
        assertEquals(2, dto.getChilds().size());
        assertEquals(3, dto.getContents().size());
        assertTrue(dto.getContents().get(0) instanceof ContentDTO);
        assertTrue(dto.getChilds().get(0) instanceof ContainerDTO);
    }

    @Test
    public void testLicenseMapping() {
        final License licenseCC = new License("by-nc-nd", "Creative Commons Attribution-NonCommercial-NoDerivs", "cc1",
                "http://creativecommons.org/licenses/by-nc-nd/3.0/", true, false, false, "cc2", "cc3");

        final License licenseNotCC = new License("gfdl", "GNU Free Documentation License", "nocc1",
                "http://www.gnu.org/copyleft/fdl.html", false, true, false, "nocc2", "nocc3");

        final LicenseDTO dtoCC = mapper.map(licenseCC, LicenseDTO.class);
        final LicenseDTO dtoNotCC = mapper.map(licenseNotCC, LicenseDTO.class);

        assertEquals("by-nc-nd", dtoCC.getShortName());
        assertEquals("gfdl", dtoNotCC.getShortName());
        assertEquals("Creative Commons Attribution-NonCommercial-NoDerivs", dtoCC.getLongName());
        assertEquals("GNU Free Documentation License", dtoNotCC.getLongName());
        assertEquals("http://creativecommons.org/licenses/by-nc-nd/3.0/", dtoCC.getUrl());
        assertEquals("http://www.gnu.org/copyleft/fdl.html", dtoNotCC.getUrl());
        assertTrue(dtoCC.isCC());
        assertFalse(dtoNotCC.isCC());
        assertFalse(dtoCC.isCopyleft());
        assertTrue(dtoNotCC.isCopyleft());
        assertFalse(dtoCC.isDeprecated());
        assertFalse(dtoNotCC.isDeprecated());
        assertEquals("cc1", dtoCC.getDescription());
        assertEquals("cc2", dtoCC.getRdf());
        assertEquals("cc3", dtoCC.getImageUrl());
        assertEquals("nocc1", dtoNotCC.getDescription());
        assertEquals("nocc2", dtoNotCC.getRdf());
        assertEquals("nocc3", dtoNotCC.getImageUrl());
    }
}
