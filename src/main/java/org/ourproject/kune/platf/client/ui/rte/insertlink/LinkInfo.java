package org.ourproject.kune.platf.client.ui.rte.insertlink;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;

public class LinkInfo {
    public static final String HREF = "href";
    public static final String TITLE = "title";
    public static final String TARGET = "target";
    public static final String _BLANK = "_blank";

    public static LinkInfo parse(Element element) {
        String target = element.getAttribute(TARGET);
        return new LinkInfo(element.getInnerText(), element.getTitle(), element.getAttribute(HREF), target != null
                && target.equals(_BLANK));
    }

    public static LinkInfo parse(org.xwiki.gwt.dom.client.Element element) {
        String target = element.getAttribute(TARGET);
        return new LinkInfo(element.getInnerText(), element.getTitle(), element.getAttribute(HREF), target != null
                && target.equals(_BLANK));
    }

    private String text;
    private String title;
    private String href;
    private String target;

    public LinkInfo(String text) {
        this(text, null, "", false);
    }

    public LinkInfo(String text, String href) {
        this.text = text;
        this.href = href;
    }

    public LinkInfo(String text, String title, String href, boolean inNewWindow) {
        this.text = text;
        this.href = href;
        this.title = title;
        if (inNewWindow) {
            this.setTarget(_BLANK);
        }
    }

    public Element getElement() {
        Element anchor = DOM.createAnchor();
        com.google.gwt.user.client.Element element = (com.google.gwt.user.client.Element) anchor;
        DOM.setElementProperty(element, HREF, href);
        if (getTarget() != null) {
            DOM.setElementProperty(element, TARGET, getTarget());
        }
        if (title != null) {
            DOM.setElementAttribute(element, TITLE, title);
        }
        DOM.setInnerText(element, text);
        return anchor;
    }

    public String getHref() {
        return href;
    }

    public String getTarget() {
        return target;
    }

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }

    public boolean inSameWindow() {
        if (target != null) {
            return target.equals(_BLANK);
        } else {
            return false;
        }
    }

    public void setHref(String href) {
        this.href = href;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        Element anchor = getElement();
        return anchor.getString();
    }

}
