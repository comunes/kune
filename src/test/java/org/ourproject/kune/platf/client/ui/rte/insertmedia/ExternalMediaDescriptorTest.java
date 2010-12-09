package org.ourproject.kune.platf.client.ui.rte.insertmedia;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import cc.kune.core.shared.dto.ExtMediaDescripDTO;

public class ExternalMediaDescriptorTest {

    private ExtMediaDescripDTO ytmedia;
    private ExtMediaDescripDTO btmedia;
    private ExtMediaDescripDTO gvmedia;
    private ExtMediaDescripDTO yvmedia;
    private ExtMediaDescripDTO vimedia;
    private ExtMediaDescripDTO dmmedia;

    @Before
    public void before() {
        ytmedia = new ExtMediaDescripDTO(
                "youtube",
                "http://youtube.com",
                ".*youtube.*",
                ".*youtube.com/watch.*[\\?&]v=(.*)",
                "<object width=\"###WIDTH###\" height=\"###HEIGHT###\"><param name=\"movie\" value=\"http://youtube.com/v/###URL###\" /><param name=\"wmode\" value=\"transparent\" /><embed src=\"http://youtube.com/v/###URL###\" type=\"application/x-shockwave-flash\" width=\"###WIDTH###\" height=\"###HEIGHT###\" wmode=\"transparent\"></embed></object>",
                480, 385);
        btmedia = new ExtMediaDescripDTO(
                "blip.tv",
                "http://blip.tv",
                ".*blip\\.tv.*",
                ".*blip.tv/file/([0-9]+).*",
                "<embed src=\"http://blip.tv/play/###URL###\" type=\"application/x-shockwave-flash\" width=\"###WIDTH###\" height=\"###HEIGHT###\" allowscriptaccess=\"always\" allowfullscreen=\"true\"></embed>",
                480, 385);
        gvmedia = new ExtMediaDescripDTO(
                "google video",
                "http://video.google.com/",
                ".*video\\.google.*",
                ".*video\\.google\\.com/videoplay.*docid=([0-9]+).*",
                "<embed id=\"VideoPlayback\" src=\"http://video.google.com/googleplayer.swf?docid=###URL###&hl=es&fs=true\" style=\"width:###WIDTH###;height:###HEIGHT###\" allowFullScreen=\"true\" allowScriptAccess=\"always\" type=\"application/x-shockwave-flash\"> </embed>",
                400, 326);
        yvmedia = new ExtMediaDescripDTO(
                "yahoo video",
                "http://video.yahoo.com/",
                ".*video\\.yahoo.*",
                ".*video.yahoo.com/watch/(.*)",
                "<object width=\"###WIDTH###\" height=\"###HEIGHT###\"><param name=\"movie\" value=\"http://d.yimg.com/static.video.yahoo.com/yep/YV_YEP.swf?ver=2.2.30\" /><param name=\"allowFullScreen\" value=\"true\" /><param name=\"AllowScriptAccess\" VALUE=\"always\" /><param name=\"bgcolor\" value=\"#000000\" /><param name=\"flashVars\" value=\"id=###URL###&embed=1\" /><embed src=\"http://d.yimg.com/static.video.yahoo.com/yep/YV_YEP.swf?ver=2.2.30\" type=\"application/x-shockwave-flash\" width=\"###WIDTH###\" height=\"###HEIGHT###\" allowFullScreen=\"true\" AllowScriptAccess=\"always\" bgcolor=\"#000000\" flashVars=\"id=###URL###&embed=1\" ></embed></object>",
                512, 322);
        vimedia = new ExtMediaDescripDTO(
                "vimeo",
                "http://vimeo.com",
                ".*vimeo\\.com/.*",
                ".*vimeo\\.com/(.*)",
                "<object width=\"###WIDTH###\" height=\"###HEIGHT###\"><param name=\"allowfullscreen\" value=\"true\" /><param name=\"allowscriptaccess\" value=\"always\" /><param name=\"movie\" value=\"http://vimeo.com/moogaloop.swf?clip_id=###URL###&amp;server=vimeo.com&amp;show_title=1&amp;show_byline=1&amp;show_portrait=0&amp;color=00ADEF&amp;fullscreen=1\" /><embed src=\"http://vimeo.com/moogaloop.swf?clip_id=###URL###&amp;server=vimeo.com&amp;show_title=1&amp;show_byline=1&amp;show_portrait=0&amp;color=00ADEF&amp;fullscreen=1\" type=\"application/x-shockwave-flash\" allowfullscreen=\"true\" allowscriptaccess=\"always\" width=\"###WIDTH###\" height=\"###HEIGHT###\"></embed></object><br />",
                400, 342);
        dmmedia = new ExtMediaDescripDTO(
                "Daylymotion",
                "http://www.dailymotion.com/",
                ".*dailymotion\\.com.*",
                ".*www\\.dailymotion\\.com.*video/([^_]+).*$",
                "<object type=\"application/x-shockwave-flash\" data=\"http://www.dailymotion.com/swf/###URL###\" width=\"###WIDTH###\" height=\"###HEIGHT###\"><param name=\"movie\" value=\"http://www.dailymotion.com/swf/###URL###\" /><param name=\"wmode\" value=\"transparent\" /></object>",
                420, 339);
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

        assertEquals("1626663/5503240", yvmedia.getId("http://video.yahoo.com/watch/1626663/5503240"));

        assertEquals("1650316", vimedia.getId("http://vimeo.com/1650316"));

        assertEquals(
                "x74yqa",
                dmmedia.getId("http://www.dailymotion.com/relevance/search/free+software/video/x74yqa_stephen-fry-free-software_tech"));

        // assertEquals("", gvmedia.getId(""));
    }

    @Test
    public void testGeEmb() {
        String result = ytmedia.getEmbed("http://www.youtube.com/watch?v=v2aKo6la7J4");
        assertFalse(result.matches(ExtMediaDescripDTO.HEIGHT));
        assertFalse(result.matches(ExtMediaDescripDTO.WIDTH));
        assertFalse(result.matches(ExtMediaDescripDTO.URL));
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
