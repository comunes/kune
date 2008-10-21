package org.ourproject.kune.platf.server.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class GroupListTest {
    private GroupList list;
    private Group includedGroup;

    @Before
    public void createList() {
        list = new GroupList();
        includedGroup = new Group("one", "group");
        list.add(includedGroup);
    }

    @Test
    public void testModeNormal() {
        list.setMode(GroupListMode.NORMAL);
        assertTrue(list.includes(includedGroup));
        assertFalse(list.includes(new Group("other", "group")));
        assertFalse(list.includes(Group.NO_GROUP));
    }

    @Test
    public void testModeNobody() {
        list.setMode(GroupListMode.NOBODY);
        assertFalse(list.includes(includedGroup));
        assertFalse(list.includes(new Group("other", "group")));
        assertFalse(list.includes(Group.NO_GROUP));
    }

    @Test
    public void testModeEverybody() {
        list.setMode(GroupListMode.EVERYONE);
        assertTrue(list.includes(includedGroup));
        assertTrue(list.includes(new Group("other", "group")));
        assertTrue(list.includes(Group.NO_GROUP));
    }

}
