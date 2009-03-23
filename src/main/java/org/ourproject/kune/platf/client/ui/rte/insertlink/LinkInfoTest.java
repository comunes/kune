package org.ourproject.kune.platf.client.ui.rte.insertlink;

import org.xwiki.gwt.dom.client.Element;
import org.xwiki.gwt.dom.client.Range;
import org.xwiki.gwt.dom.client.Selection;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Timer;

public class LinkInfoTest extends AbstractRichTextAreaTest {

    private static final String TEXT = "somelink";
    private static final String HREF = "http://example.com";
    private static final String TITLE = "on over";
    private static final String _BLANK = "_blank";

    public void doTestSimpleLink() {
        String html = createHtmlLink(HREF, TITLE, _BLANK, TEXT);
        rta.setHTML(html);
        Log.debug(rta.getHTML());

        Range range = rta.getDocument().createRange();
        range.setStart(getBody().getFirstChild(), 1);
        range.setEnd(getBody().getFirstChild(), 1);

        Selection selection = rta.getDocument().getSelection();
        selection.removeAllRanges();
        selection.addRange(range);

        Element selectedAnchor = LinkExecutableUtils.getSelectedAnchor(rta);

        Log.info(selectedAnchor.getString());

        LinkInfo linkParsed = LinkInfo.parse(getBody().getFirstChildElement());
        assertEquals(TEXT, linkParsed.getText());
        assertEquals(HREF, linkParsed.getHref());
        assertEquals(TITLE, linkParsed.getTitle());
        assertEquals(_BLANK, linkParsed.getTarget());
    }

    @Override
    public String getModuleName() {
        return "org.ourproject.kune.app.Kune";
    }

    public void testPlainTextSelectionWithoutModification() {
        delayTestFinish(FINISH_DELAY);
        (new Timer() {
            @Override
            public void run() {
                doTestSimpleLink();
                finishTest();
            }
        }).schedule(START_DELAY);
    }

    private String createHtmlLink(String href, String title, String target, String text) {
        return "<a title=\"" + title + "\" href=\"" + href + "\" target=\"" + target + "\">" + text + "</a>";
    }

}
