package org.ourproject.kune.platf.client.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import cc.kune.common.client.utils.TextUtils;

public class TextUtilsTest {
    @Test
    public void dontRemoveLastSlashIfNoExists() {
        final String text = "ab";
        assertEquals("ab", TextUtils.removeLastSlash(text));
    }

    @Test
    public void dontRemoveOtherSlashIfExists() {
        final String text = "/ab";
        assertEquals("/ab", TextUtils.removeLastSlash(text));
    }

    @Test
    public void matchDemoEmail() {
        final String email = "test100@emitedemo.ourproject.org";
        assertTrue(email.matches(TextUtils.EMAIL_REGEXP));
    }

    @Test
    public void matchLocalhostEmail() {
        final String email = "me@localhost";
        assertTrue(email.matches(TextUtils.EMAIL_REGEXP));
    }

    @Test
    public void matchSimpleEmail() {
        final String email = "me@example.com";
        assertTrue(email.matches(TextUtils.EMAIL_REGEXP));
    }

    @Test
    public void matchUrlWithHttp() {
        final String gnuUrl = "http://gnu.org";
        assertTrue(gnuUrl.matches(TextUtils.URL_REGEXP));
    }

    @Test
    public void matchUrlWithoutHttp() {
        final String gnuUrl = "gnu.org";
        assertTrue(!gnuUrl.matches(TextUtils.URL_REGEXP));
    }

    @Test
    public void notMatchWrongUrl() {
        final String someWrong = "some@email.com";
        assertTrue(!someWrong.matches(TextUtils.URL_REGEXP));
    }

    @Test
    public void removeLastSlashIfExists() {
        final String text = "/";
        assertEquals("", TextUtils.removeLastSlash(text));
    }

    @Test
    public void removeOnlytLastSlashIfExists() {
        final String text = "/ab/cd/";
        assertEquals("/ab/cd", TextUtils.removeLastSlash(text));
    }
}
