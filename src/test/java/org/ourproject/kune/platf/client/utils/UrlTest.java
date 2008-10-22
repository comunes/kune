package org.ourproject.kune.platf.client.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UrlTest {

    @Test
    public void testBooleanFalse() {
        assertEquals("test?param1=value1&param2=false", new Url("test", new UrlParam("param1", "value1"), new UrlParam(
                "param2", false)).toString());
    }

    @Test
    public void testBooleanTrue() {
        assertEquals("test?param1=value1&param2=true", new Url("test", new UrlParam("param1", "value1"), new UrlParam(
                "param2", true)).toString());
    }

    @Test
    public void testOneParam() {
        assertEquals("test?param1=value1", new Url("test", new UrlParam("param1", "value1")).toString());
    }

    @Test
    public void testOnlyBase() {
        assertEquals("test", new Url("test").toString());
    }

    @Test
    public void testThreeParams() {
        assertEquals("test?param1=value1&param2=value2&param3=value3",
                new Url("test", new UrlParam("param1", "value1"), new UrlParam("param2", "value2"), new UrlParam(
                        "param3", "value3")).toString());
    }

    @Test
    public void testTwoParams() {
        assertEquals("test?param1=value1&param2=value2", new Url("test", new UrlParam("param1", "value1"),
                new UrlParam("param2", "value2")).toString());
    }
}
