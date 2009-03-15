package org.ourproject.kune.platf.client.ui.rte.insertlink;

import com.google.gwt.dom.client.Element;

public class LinkInfo {

    private static final String HREF = "href";
    // private static final String TITLE = "title";
    private static final String TARGET = "target";
    private static final String _BLANK = "_blank";

    public static LinkInfo parse(Element element) {
        String target = element.getAttribute(TARGET);
        return new LinkInfo(element.getInnerText(), element.getTitle(), element.getAttribute(HREF), target != null
                && target.equals(_BLANK));
    }

    private String text;
    private String title;
    private String url;

    private boolean inNewWindow;

    public LinkInfo(String text, String title, String url, boolean inNewWindow) {
        this.text = text;
        this.title = title;
        this.url = url;
        this.inNewWindow = inNewWindow;
    }

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public boolean isInNewWindow() {
        return inNewWindow;
    }

    public void setInNewWindow(boolean inNewWindow) {
        this.inNewWindow = inNewWindow;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
