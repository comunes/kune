package org.ourproject.kune.platf.client.ui.rte.insertlink;

import com.allen_sauer.gwt.log.client.Log;
import com.xpn.xwiki.wysiwyg.client.dom.Range;
import com.xpn.xwiki.wysiwyg.client.dom.Selection;

public class LinkInfoTest extends AbstractRichTextAreaTest {

    private static final String TEXT = "somelink";
    private static final String HREF = "http://example.com";
    private static final String TITLE = "on over";
    private static final String _BLANK = "_blank";

    @Override
    public String getModuleName() {
        return "org.ourproject.kune.app.Kune";
    }

    public void testSimpleLink() {
        // String html = createHtmlLink(HREF, TITLE, _BLANK, TEXT);
        rta.setHTML(TEXT);
        Log.debug(rta.getHTML());

        Range range = rta.getDocument().createRange();
        range.setStart(getBody().getFirstChild(), 1);
        range.setEnd(getBody().getFirstChild(), 3);
        String selectedText = "om";

        Selection selection = rta.getDocument().getSelection();
        selection.removeAllRanges();
        selection.addRange(range);
        assertEquals(selectedText, selection.toString());
        //
        // LinkInfo linkParsed =
        // LinkInfo.parse(getBody().getFirstChildElement());
        // assertEquals(TEXT, linkParsed.getText());
        // assertEquals(HREF, linkParsed.getUrl());
        // assertEquals(TITLE, linkParsed.getTitle());
        // assertEquals(false, linkParsed.isInNewWindow());
    }

    private String createHtmlLink(String href, String title, String target, String text) {
        return "<a title=\"" + title + "\" href=\"" + href + "\" target=\"" + target + "\">" + text + "</a>";
    }
}
