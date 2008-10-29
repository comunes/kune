package org.ourproject.kune.platf.integration.selenium;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.google.gwt.user.client.ui.UIObject;
import com.thoughtworks.selenium.DefaultSelenium;

public class SeleniumTestHelper {

    protected static DefaultSelenium selenium;
    private static boolean mustStopFinally = true;

    @AfterClass
    public static void afterClass() throws Exception {
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

    public static void setMustStopFinally(boolean mustStopFinally) {
        SeleniumTestHelper.mustStopFinally = mustStopFinally;
    }

    protected void click(String id) {
        selenium.click(id);
    }

    protected void clickOnPushButton(String id) {
        selenium.mouseOver(id);
        selenium.mouseDown(id);
        selenium.mouseUp(id);
    }

    protected void fail(String message) throws Exception {
        throw new Exception(message);
    }

    /**
     * Returns the debug id with the gwt DEBUG_ID_PREFIX
     * 
     * @param id
     * @return
     */
    protected String gid(String id) {
        return UIObject.DEBUG_ID_PREFIX + id;
    }

    protected String linkId(String link) {
        return "link=" + link;
    }

    protected void open(String url) {
        try {
            selenium.open(url);
        } catch (final UnsupportedOperationException e) {
            System.err.println("Seems that selenium server is not running; run before: 'mvn selenium:start-server' ");
        }
    }

    protected void setSpeed(int milliseconds) {
        selenium.setSpeed("" + milliseconds);
    }

    protected void type(String id, String text) {
        selenium.type(id, text);
    }

    protected void wait(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    protected void waitForTextInside(String id, String text) throws Exception {
        for (int second = 0;; second++) {
            if (second >= 60) {
                fail("timeout");
            }
            try {
                String selText = selenium.getText(id);
                if (selText.indexOf(text) >= 0) {
                    break;
                }
            } catch (Exception e) {
            }
            Thread.sleep(1000);
        }
    }

    protected void waitForTextRegExp(String id, String text) throws Exception {
        for (int second = 0;; second++) {
            if (second >= 60) {
                fail("timeout");
            }
            try {
                if (selenium.getText(id).matches(text)) {
                    break;
                }
            } catch (Exception e) {
            }
            Thread.sleep(1000);
        }
    }
}
