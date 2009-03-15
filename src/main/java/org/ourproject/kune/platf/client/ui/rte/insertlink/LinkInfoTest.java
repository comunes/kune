package org.ourproject.kune.platf.client.ui.rte.insertlink;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.Hyperlink;

public class LinkInfoTest extends GWTTestCase {

    private static final String TEXT = "some link";
    private static final String HREF = "http://example.com";
    private static final String TITLE = "on over";

    @Override
    public String getModuleName() {
        return "org.ourproject.kune.app.Kune";
    }

    public void testSimpleLink() {
        Hyperlink link = new Hyperlink(TEXT, HREF);
        link.setTitle(TITLE);
        Log.info(link.getHTML());
        LinkInfo linkParsed = LinkInfo.parse(link.getElement());
        assertEquals(TEXT, linkParsed.getText());
        assertEquals(HREF, linkParsed.getUrl());
        assertEquals(TITLE, linkParsed.getTitle());
        assertEquals(false, linkParsed.isInNewWindow());
    }
}
