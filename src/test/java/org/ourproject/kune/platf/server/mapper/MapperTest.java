package org.ourproject.kune.platf.server.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.client.dto.CommentDTO;
import org.ourproject.kune.platf.client.dto.ContainerDTO;
import org.ourproject.kune.platf.client.dto.ContainerSimpleDTO;
import org.ourproject.kune.platf.client.dto.ContentSimpleDTO;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.GroupListDTO;
import org.ourproject.kune.platf.client.dto.LicenseDTO;
import org.ourproject.kune.platf.client.dto.LinkDTO;
import org.ourproject.kune.platf.client.dto.SocialNetworkDataDTO;
import org.ourproject.kune.platf.client.dto.SocialNetworkVisibilityDTO;
import org.ourproject.kune.platf.client.dto.StateContentDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.dto.UserBuddiesVisibilityDTO;
import org.ourproject.kune.platf.server.TestDomainHelper;
import org.ourproject.kune.platf.server.TestHelper;
import org.ourproject.kune.platf.server.access.AccessRights;
import org.ourproject.kune.platf.server.domain.BasicMimeType;
import org.ourproject.kune.platf.server.domain.Comment;
import org.ourproject.kune.platf.server.domain.Container;
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.GroupList;
import org.ourproject.kune.platf.server.domain.GroupListMode;
import org.ourproject.kune.platf.server.domain.License;
import org.ourproject.kune.platf.server.domain.Revision;
import org.ourproject.kune.platf.server.domain.SocialNetwork;
import org.ourproject.kune.platf.server.domain.SocialNetworkData;
import org.ourproject.kune.platf.server.domain.SocialNetworkVisibility;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.domain.UserBuddiesVisibility;
import org.ourproject.kune.platf.server.manager.GroupManager;
import org.ourproject.kune.platf.server.sn.ParticipationData;
import org.ourproject.kune.platf.server.sn.UserBuddiesData;
import org.ourproject.kune.platf.server.state.StateContent;

import com.google.inject.Inject;

public class MapperTest {
    private static final String TESTTOOL = "docs";
    private static final String TESTGROUPSHORTNAME = "grouptest";
    @Inject
    Mapper mapper;
    @Inject
    GroupManager groupManager;

    @Test
    public void groupHasLogo() {
        final Group group = new Group("test", "this is a test");
        GroupDTO groupDTO = mapper.map(group, GroupDTO.class);
        assertFalse(group.hasLogo());
        assertFalse(groupDTO.hasLogo());
        group.setLogo(new byte[2]);
        group.setLogoMime(new BasicMimeType("image/png"));
        groupDTO = mapper.map(group, GroupDTO.class);
        assertTrue(group.hasLogo());
        assertTrue(groupDTO.hasLogo());
    }

    @Before
    public void inject() {
        TestHelper.inject(this);
    }

    @Test
    public void testCommentMapper() {
        final Content d = createDefContent();
        final Comment comment = new Comment();
        comment.setContent(d);
        comment.setText("Some text");
        final User user = new User();
        comment.addPositiveVoter(user);
        comment.addPositiveVoter(user);
        CommentDTO commentDTO = mapper.map(comment, CommentDTO.class);
        assertEquals(0, commentDTO.getNegativeVotersCount());
        assertEquals(0, commentDTO.getAbuseInformersCount());
        assertEquals(1, commentDTO.getPositiveVotersCount());
        comment.addNegativeVoter(user);
        comment.addNegativeVoter(user);
        commentDTO = mapper.map(comment, CommentDTO.class);
        assertEquals(1, commentDTO.getNegativeVotersCount());
        assertEquals(0, commentDTO.getAbuseInformersCount());
        assertEquals(0, commentDTO.getPositiveVotersCount());
        comment.addAbuseInformer(user);
        comment.addAbuseInformer(user);
        commentDTO = mapper.map(comment, CommentDTO.class);
        assertEquals(1, commentDTO.getNegativeVotersCount());
        assertEquals(1, commentDTO.getAbuseInformersCount());
        assertEquals(0, commentDTO.getPositiveVotersCount());
        final Comment childComment = new Comment();
        childComment.setContent(d);
        childComment.setParent(comment);
        comment.getChilds().add(childComment);
        commentDTO = mapper.map(comment, CommentDTO.class);
        final CommentDTO childCommentDTO = mapper.map(childComment, CommentDTO.class);
        assertEquals(1, comment.getChilds().size());
        assertEquals(commentDTO, childCommentDTO.getParent());
    }

    @Test
    public void testContentDescriptorMapping() {
        final Content d = createDefContent();
        final StateToken expectedToken = new StateToken(TESTGROUPSHORTNAME, TESTTOOL, "1", "1");
        assertEquals(expectedToken, d.getStateToken());

        final ContentSimpleDTO dto = mapper.map(d, ContentSimpleDTO.class);
        assertEquals(1, (long) dto.getId());
        assertEquals("title", dto.getTitle());
        assertEquals(expectedToken, dto.getStateToken());
    }

    @Test
    public void testContentDescriptorToLinkMapping() {
        final Content d = createDefContent();

        final LinkDTO dto = mapper.map(d, LinkDTO.class);
        assertEquals("title", dto.getLongName());
        assertEquals(TESTGROUPSHORTNAME, dto.getShortName());
        assertEquals("grouptest.docs.1.1", dto.getLink());
    }

    @Test
    public void testContentMapping() {
        final StateContent c = new StateContent();
        c.setContentRights(new AccessRights(true, true, true));
        final Group groupAdmins = TestDomainHelper.createGroup(1);
        final Group groupEdit = TestDomainHelper.createGroup(2);
        final Group groupView = TestDomainHelper.createGroup(3);
        c.setAccessLists(TestDomainHelper.createAccessLists(groupAdmins, groupEdit, groupView));
        c.setRate(10.2D);
        c.setRateByUsers(3);
        c.setCurrentUserRate(null);

        final StateContentDTO dto = mapper.map(c, StateContentDTO.class);
        assertEquals(c.getContentRights().isAdministrable(), dto.getContentRights().isAdministrable());

        assertValidAccessListsMapping(c.getAccessLists().getAdmins(), dto.getAccessLists().getAdmins());
        assertValidAccessListsMapping(c.getAccessLists().getEditors(), dto.getAccessLists().getEditors());
        assertValidAccessListsMapping(c.getAccessLists().getViewers(), dto.getAccessLists().getViewers());

        assertEquals(dto.getRate(), c.getRate());
        assertEquals(dto.getRateByUsers(), c.getRateByUsers());
        assertEquals(dto.getCurrentUserRate(), c.getCurrentUserRate());
    }

    @Test
    public void testFolderMapping() {
        final Container container = createDefContainer();
        final StateToken expectedToken = new StateToken(TESTGROUPSHORTNAME, TESTTOOL, 1L);
        assertEquals(expectedToken, container.getStateToken());
        container.addChild(createDefContainer());
        container.addChild(createDefContainer());
        container.addContent(createDefContent());
        container.addContent(createDefContent());
        container.addContent(createDefContent());
        final Container containerChild = createDefContainer();
        container.addChild(containerChild);
        final List<Container> absolutePathChild = new ArrayList<Container>();
        absolutePathChild.add(container);
        containerChild.setAbsolutePath(absolutePathChild);

        final ContainerDTO dto = mapper.map(container, ContainerDTO.class);
        assertEquals(3, dto.getChilds().size());
        assertEquals(3, dto.getContents().size());
        assertTrue(dto.getContents().get(0) instanceof ContentSimpleDTO);
        assertTrue(dto.getChilds().get(0) instanceof ContainerSimpleDTO);
        assertEquals(new StateToken(TESTGROUPSHORTNAME, TESTTOOL),
                dto.getChilds().get(0).getStateToken().copy().clearFolder());
        assertEquals(expectedToken, dto.getContents().get(0).getStateToken().copy().clearDocument());
        assertEquals(expectedToken, dto.getStateToken());

        final ContainerDTO dtoChild = mapper.map(containerChild, ContainerDTO.class);
        assertNotNull(dtoChild.getAbsolutePath()[0]);
    }

    @Test
    public void testGroupListMapping() {
        assertMapping(GroupListMode.EVERYONE, GroupListDTO.EVERYONE);
        assertMapping(GroupListMode.NOBODY, GroupListDTO.NOBODY);
        assertMapping(GroupListMode.NORMAL, GroupListDTO.NORMAL);
    }

    @Test
    public void testGroupMapping() {
        final Group group = new Group("shortName", "name");
        final GroupDTO dto = mapper.map(group, GroupDTO.class);
        assertEquals(group.getLongName(), dto.getLongName());
        assertEquals(group.getShortName(), dto.getShortName());
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

    @Test
    public void testMimeMapping() {
        final Content d = createDefContent();
        d.setMimeType(new BasicMimeType("application/pdf"));
        final ContentSimpleDTO contentSimpleDTO = mapper.map(d, ContentSimpleDTO.class);
        assertEquals("application/pdf", contentSimpleDTO.getMimeType().toString());
    }

    @Test
    public void testSnResultMap() {
        final Group group = new Group("test", "this is a test");
        final SocialNetwork sn = new SocialNetwork();
        sn.addAdmin(group);
        final ParticipationData part = new ParticipationData();
        part.setGroupsIsAdmin(sn.getAccessLists().getAdmins().getList());
        final UserBuddiesData budData = new UserBuddiesData();
        final ArrayList<User> buddies = new ArrayList<User>();
        final User user = new User();
        user.setShortName("usertest");
        user.setUserGroup(new Group("test2", "this is test2"));
        buddies.add(user);
        budData.setBuddies(buddies);
        final SocialNetworkData snResult = new SocialNetworkData(SocialNetworkVisibility.onlymembers, sn, part,
                UserBuddiesVisibility.onlyyou, budData, new AccessRights(false, false, true), true, true);
        final SocialNetworkDataDTO map = mapper.map(snResult, SocialNetworkDataDTO.class);
        assertNotNull(map);
        assertEquals(SocialNetworkVisibilityDTO.onlymembers, map.getSocialNetworkVisibility());
        assertEquals(UserBuddiesVisibilityDTO.onlyyou, map.getUserBuddiesVisibility());
        assertEquals("test", map.getGroupMembers().getAccessLists().getAdmins().getList().get(0).getShortName());
        assertEquals("test", map.getUserParticipation().getGroupsIsAdmin().get(0).getShortName());
        assertEquals("usertest", map.getUserBuddies().getBuddies().get(0).getShortName());
        assertFalse(map.getGroupRights().isAdministrable());
        assertFalse(map.getGroupRights().isEditable());
        assertTrue(map.getGroupRights().isVisible());
        assertTrue(map.isMembersVisible());
        assertTrue(map.isBuddiesVisible());
    }

    @Test
    public void testStateTokenInStateMap() {
        final StateToken stateToken = new StateToken(TESTGROUPSHORTNAME, TESTTOOL, "1", "2");
        final StateToken stateTokenMapped = mapper.map(stateToken, StateToken.class);
        assertEquals(stateToken, stateTokenMapped);
        final StateContent state = new StateContent();
        state.setStateToken(stateToken);
        final StateContentDTO stateDTO = mapper.map(state, StateContentDTO.class);
        assertEquals(stateToken, stateDTO.getStateToken());
    }

    @Test
    public void testUserToLinkMappping() {
        final User user = new User("shortName", "longName", "", "", null, null, null);
        final LinkDTO dto = mapper.map(user, LinkDTO.class);
        assertEquals("shortName", dto.getShortName());
        assertEquals("longName", dto.getLongName());
    }

    private void assertMapping(final GroupListMode mode, final String modeName) {
        final GroupList list = new GroupList();
        list.setMode(mode);
        final GroupListDTO dto = mapper.map(list, GroupListDTO.class);
        assertEquals(modeName, dto.getMode());
        final GroupList listBack = mapper.map(dto, GroupList.class);
        assertEquals(mode, listBack.getMode());
    }

    private void assertValidAccessListsMapping(final GroupList groupList, final GroupListDTO groupListDTO) {
        final List<Group> listOrig = groupList.getList();
        final List<GroupDTO> listDto = groupListDTO.getList();
        assertEquals(listDto.size(), listOrig.size());
        for (int i = 0; i < listDto.size(); i++) {
            final Object object = listDto.get(i);
            assertEquals(GroupDTO.class, object.getClass());
            final GroupDTO d = (GroupDTO) object;
            final Group g = listOrig.get(i);
            assertNotNull(d);
            assertNotNull(g);
            final GroupDTO map = mapper.map(g, GroupDTO.class);
            assertEquals(map, d);
        }
    }

    private Container createDefContainer() {
        return createDefContainer(createDeGroup());
    }

    private Container createDefContainer(final Group group) {
        final Container container = new Container();
        container.setId(1L);
        container.setToolName(TESTTOOL);
        container.setOwner(group);
        container.setName("folder");
        return container;
    }

    private Content createDefContent() {
        final Container container = createDefContainer(createDeGroup());
        final Content d = new Content();
        d.setId(1L);
        final Revision revision = new Revision(d);
        revision.setTitle("title");
        d.addRevision(revision);
        d.setContainer(container);
        return d;
    }

    private Group createDeGroup() {
        final Group group = new Group(TESTGROUPSHORTNAME, "This is a group Test");
        return group;
    }
}
