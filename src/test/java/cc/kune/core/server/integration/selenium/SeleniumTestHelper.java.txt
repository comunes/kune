package org.ourproject.kune.platf.integration.selenium;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.ourproject.kune.platf.server.ServerException;

import com.google.gwt.user.client.ui.UIObject;
import com.thoughtworks.selenium.DefaultSelenium;

public class SeleniumTestHelper {

    protected static DefaultSelenium selenium;
    private static boolean mustStopFinally = true;

    @AfterClass
    public static void afterClass() {
        if (mustStopFinally) {
            selenium.stop();
        }
    }

    /**
     * 
     * If you get and null in ./content/recorder.js line 74 running test, this
     * happens when you compile gwt with PRETTY instead of OBF. See:
     * 
     * <pre>
     * http://groups.google.com/group/Google-Web-Toolkit/browse_thread/thread/5d6a9c448a82b916/af62e5877237b107?lnk=raot
     * </pre>
     * 
     * <pre>
     * http://code.google.com/p/google-web-toolkit/issues/detail?id=2861
     * </pre>
     * 
     * @param url
     * @return
     * @throws Exception
     */
    @BeforeClass
    public static void beforeClass() {
        // ff3 hangs: http://jira.openqa.org/browse/SRC-225
        // as a workarount use ff2:
        //
        // return new DefaultSelenium("localhost", 4441,
        // "*firefox /usr/lib/firefox-3.0.3/firefox", url);

        // this is a problem... platform dependence ...
        selenium = new DefaultSelenium("localhost", 4441, "*chrome /usr/lib/firefox/firefox-2-bin",
                "http://localhost:8080/");
        selenium.start();
    }

    public static void setMustStopFinally(final boolean mustStopFinally) {
        SeleniumTestHelper.mustStopFinally = mustStopFinally;
    }

    protected void click(final String id) {
        selenium.click(id);
    }

    protected void clickOnPushButton(final String id) {
        selenium.mouseOver(id);
        selenium.mouseDown(id);
        selenium.mouseUp(id);
    }

    protected void fail(final String message) throws Exception {
        throw new ServerException(message);
    }

    /**
     * Returns the debug id with the gwt DEBUG_ID_PREFIX
     * 
     * @param id
     * @return
     */
    protected String gid(final String id) {
        return UIObject.DEBUG_ID_PREFIX + id;
    }

    protected String linkId(final String link) {
        return "link=" + link;
    }

    protected void open(final String url) {
        try {
            selenium.open(url);
        } catch (final UnsupportedOperationException e) {
            throw new ServerException(
                    "Seems that selenium server is not running; run before: 'mvn selenium:start-server' ", e);
        }
    }

    protected void setSpeed(final int milliseconds) {
        selenium.setSpeed("" + milliseconds);
    }

    protected void type(final String id, final String text) {
        selenium.type(id, text);
    }

    protected void wait(final int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (final InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    protected void waitForTextInside(final String id, final String text) throws Exception {
        for (int second = 0;; second++) {
            if (second >= 60) {
                fail("timeout");
            }
            try {
                final String selText = selenium.getText(id);
                if (selText.indexOf(text) >= 0) {
                    break;
                }
            } catch (final Exception e) {
            }
            Thread.sleep(1000);
        }
    }

    protected void waitForTextRegExp(final String id, final String text) throws Exception {
        for (int second = 0;; second++) {
            if (second >= 60) {
                fail("timeout");
            }
            try {
                if (selenium.getText(id).matches(text)) {
                    break;
                }
            } catch (final Exception e) {
            }
            Thread.sleep(1000);
        }
    }
}
