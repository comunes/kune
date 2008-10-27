package org.ourproject.kune.platf.client.ui;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

public class KuneStringUtilsTest {

    @Test
    public void testTagNull() {
        final String tagsString = null;
        final ArrayList<String> tags = TextUtils.splitTags(tagsString);
        assertEquals(0, tags.size());
    }

    @Test
    public void testTagStripsSimple() {
        final String tagsString = "ab cd";
        final ArrayList<String> tags = TextUtils.splitTags(tagsString);
        assertEquals("ab", tags.get(0));
        assertEquals("cd", tags.get(1));
    }

    @Test
    public void testTagStripsWithCommas() {
        final String tagsString = "ab,cd";
        final ArrayList<String> tags = TextUtils.splitTags(tagsString);
        assertEquals("ab", tags.get(0));
        assertEquals("cd", tags.get(1));
    }

    @Test
    public void testTagStripsWithCommasAndSpaces() {
        final String tagsString = "ab, cd";
        final ArrayList<String> tags = TextUtils.splitTags(tagsString);
        assertEquals("ab", tags.get(0));
        assertEquals("cd", tags.get(1));
    }

    @Test
    public void testTagStripsWithQuotes() {
        final String tagsString = "ab \"cd\"";
        final ArrayList<String> tags = TextUtils.splitTags(tagsString);
        assertEquals("ab", tags.get(0));
        assertEquals("cd", tags.get(1));
    }

    @Test
    public void testTagStripsWithSpaces() {
        final String tagsString = "    ab       cd    ";
        final ArrayList<String> tags = TextUtils.splitTags(tagsString);
        assertEquals("ab", tags.get(0));
        assertEquals("cd", tags.get(1));
    }

}
