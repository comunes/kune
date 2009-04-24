package org.ourproject.kune.platf.client.ui.rte.insertmedia;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class ExternalMediaDescriptorTest {

    private ExternalMediaDescriptor ytmedia;
    private ExternalMediaDescriptor btmedia;
    private ExternalMediaDescriptor gvmedia;

    @Before
    public void before() {
        ytmedia = new ExternalMediaDescriptor(
                "youtube",
                "http://youtube.com",
                ".*youtube.*",
                ".*youtube.com/watch.*[\\?&]v=(.*)",
                "<object width=\"###WIDTH###\" height=\"###HEIGHT###\"><param name=\"movie\" value=\"http://youtube.com/v/###URL###\" /><param name=\"wmode\" value=\"transparent\" /><embed src=\"http://youtube.com/v/###URL###\" type=\"application/x-shockwave-flash\" width=\"###WIDTH###\" height=\"###HEIGHT###\" wmode=\"transparent\"></embed></object>",
                480, 385);
        btmedia = new ExternalMediaDescriptor(
                "blip.tv",
                "http://blip.tv",
                ".*blip\\.tv.*",
                ".*blip.tv/file/([0-9]+).*",
                "<embed src=\"http://blip.tv/play/###URL###\" type=\"application/x-shockwave-flash\" width=\"###WIDTH###\" height=\"###HEIGHT###\" allowscriptaccess=\"always\" allowfullscreen=\"true\"></embed>",
                480, 385);
        gvmedia = new ExternalMediaDescriptor(
                "google video",
                "http://video.google.com/",
                ".*video\\.google.*",
                ".*video\\.google\\.com/videoplay.*docid=([0-9]+).*",
                "<embed id=\"VideoPlayback\" src=\"http://video.google.com/googleplayer.swf?docid=###URL###&hl=es&fs=true\" style=\"width:###WIDTH###;height:###HEIGHT###\" allowFullScreen=\"true\" allowScriptAccess=\"always\" type=\"application/x-shockwave-flash\"> </embed>",
                400, 326);

    }

    @Test
    public void getIds() {
        assertEquals("v2aKo6la7J4", ytmedia.getId("http://www.youtube.com/watch?v=v2aKo6la7J4"));
        assertEquals("v2aKo6la7J4", ytmedia.getId("http://de.youtube.com/watch?v=v2aKo6la7J4"));
        assertEquals("v2aKo6la7J4", ytmedia.getId("http://www.youtube.com/watch?gl=ES&hl=es&v=v2aKo6la7J4"));
        assertEquals("v2aKo6la7J4", ytmedia.getId("http://youtube.com/watch?gl=ES&hl=es&v=v2aKo6la7J4"));

        assertEquals("1646241", btmedia.getId("http://vjrj.blip.tv/file/1646241/"));
        assertEquals("1646241",
                btmedia.getId("http://blip.tv/file/1646241?utm_source=featured_ep&utm_medium=featured_ep"));
        assertEquals("1646241", btmedia.getId("http://blip.tv/file/1646241"));

        assertEquals(
                "7360794370207355739",
                gvmedia.getId("http://video.google.com/videoplay?ei=VBPxSbXDLob2-Aabu9B-&q=chomsky&docid=7360794370207355739"));
        assertEquals(
                "7360794370207355739",
                gvmedia.getId("http://video.google.com/videoplay?docid=7360794370207355739&ei=VBPxSbXDLob2-Aabu9B-&q=chomsky"));
        assertEquals("7360794370207355739",
                gvmedia.getId("http://video.google.com/videoplay?docid=7360794370207355739"));
    }

    @Test
    public void testGeEmb() {
        String result = ytmedia.getEmbed("http://www.youtube.com/watch?v=v2aKo6la7J4");
        assertFalse(result.matches(ExternalMediaDescriptor.HEIGHT));
        assertFalse(result.matches(ExternalMediaDescriptor.WIDTH));
        assertFalse(result.matches(ExternalMediaDescriptor.URL));
        assertTrue(result.matches(".*v2aKo6la7J4.*"));
    }

    @Test
    public void testMatch() {
        assertTrue(ytmedia.is("sdfklasdfayoutubekasdf"));
    }

    @Test
    public void testNoMatch() {
        assertFalse(ytmedia.is("sdfklasdfakasdf"));
    }

}
