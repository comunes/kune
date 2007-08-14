package org.ourproject.kune.platf.server.manager;


public class AccessListManagerTest {

    // private AccessListsManager accessListsManager;
    //
    // @Before
    // public void createSession() {
    // accessListsManager = new AccessListsManagerDefault();
    // }
    //
    // @Test
    // public void getAccessListFromContents() {
    // ContentDescriptor contentDesc = new ContentDescriptor();
    // AccessLists accessLists = new AccessLists();
    // contentDesc.setAccessLists(accessLists);
    //
    // AccessLists response = accessListsManager.get(contentDesc);
    // assertSame(accessLists, response);
    // }
    //
    // @Test
    // public void getAccessListFromSocialNet() {
    // ContentDescriptor contentDesc = new ContentDescriptor();
    // Folder folder = TestDomainHelper.createFolderWithIdAndGroupAndTool(1,
    // "group", "tool");
    // Group group1 = TestDomainHelper.createGroup(1);
    // Group group2 = TestDomainHelper.createGroup(2);
    // Group group3 = TestDomainHelper.createGroup(3);
    // Group group4 = TestDomainHelper.createGroup(3);
    // ArrayList<Group> adminList = new ArrayList<Group>();
    // ArrayList<Group> collabList = new ArrayList<Group>();
    // ArrayList<Group> viewerList = new ArrayList<Group>();
    // ArrayList<Group> pendingList = new ArrayList<Group>();
    // adminList.add(group1);
    // adminList.add(group2);
    // collabList.add(group2);
    // collabList.add(group3);
    // viewerList.add(group3);
    // viewerList.add(group1);
    // pendingList.add(group4);
    // SocialNetwork socialNetwork = new SocialNetwork(new GroupList(adminList),
    // new GroupList(collabList),
    // new GroupList(viewerList), new GroupList(pendingList));
    //
    // folder.setOwner(group1);
    // contentDesc.setFolder(folder);
    // group1.setSocialNetwork(socialNetwork);
    //
    // AccessLists response = accessListsManager.get(contentDesc);
    // assertEquals(adminList, response.getAdmin());
    // assertEquals(collabList, response.getEdit());
    // assertEquals(viewerList, response.getView());
    // }

}
