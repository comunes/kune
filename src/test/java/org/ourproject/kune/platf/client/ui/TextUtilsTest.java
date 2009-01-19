package org.ourproject.kune.platf.client.ui;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TextUtilsTest {
    @Test
    public void matchDemoEmail() {
        String email = "test100@emitedemo.ourproject.org";
        assertTrue(email.matches(TextUtils.EMAIL_REGEXP));
    }

    @Test
    public void matchLocalhostEmail() {
        String email = "me@localhost";
        assertTrue(email.matches(TextUtils.EMAIL_REGEXP));
    }

    @Test
    public void matchSimpleEmail() {
        String email = "me@example.com";
        assertTrue(email.matches(TextUtils.EMAIL_REGEXP));
    }

    @Test
    public void matchUrlWithHttp() {
        String gnuUrl = "http://gnu.org";
        assertTrue(gnuUrl.matches(TextUtils.URL_REGEXP));
    }

    @Test
    public void matchUrlWithoutHttp() {
        String gnuUrl = "gnu.org";
        assertTrue(!gnuUrl.matches(TextUtils.URL_REGEXP));
    }

    @Test
    public void notMatchWrongUrl() {
        String someWrong = "some@email.com";
        assertTrue(!someWrong.matches(TextUtils.URL_REGEXP));
    }
}
