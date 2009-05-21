package org.ourproject.kune.platf.client.ui.rte.insertlink;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;

public class LinkInfo {
    public static final String HREF = "href";
    public static final String TITLE = "title";
    public static final String TARGET = "target";
    public static final String _BLANK = "_blank";
    private static final String NO_TARGET = null;

    public static LinkInfo parse(final Element element) {
        final String target = element.getAttribute(TARGET);
        return new LinkInfo(element.getInnerText(), element.getTitle(), element.getAttribute(HREF), target != null
                && target.equals(_BLANK));
    }

    public static LinkInfo parse(final org.xwiki.gwt.dom.client.Element element) {
        final String target = element.getAttribute(TARGET);
        return new LinkInfo(element.getInnerText(), element.getTitle(), element.getAttribute(HREF), target != null
                && target.equals(_BLANK));
    }

    private String text;
    private String title;
    private String href;
    private String target;

    public LinkInfo(final String text) {
        this(text, null, "", false);
    }

    public LinkInfo(final String text, final String href) {
        this.text = text;
        this.href = href;
    }

    public LinkInfo(final String text, final String title, final String href, final boolean inNewWindow) {
        this.text = text;
        this.href = href;
        this.title = title;
        setInNewWindowImpl(inNewWindow);
    }

    public Element getElement() {
        final Element anchor = DOM.createAnchor();
        final com.google.gwt.user.client.Element element = (com.google.gwt.user.client.Element) anchor;
        DOM.setElementProperty(element, HREF, href);
        if (getTarget() != NO_TARGET) {
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
        if (target.equals(NO_TARGET)) {
            return false;
        } else {
            return target.equals(_BLANK);
        }
    }

    public void setHref(final String href) {
        this.href = href;
    }

    public void setInNewWindow(final boolean inNewWindow) {
        setInNewWindowImpl(inNewWindow);
    }

    public void setTarget(final String target) {
        setTargetImpl(target);
    }

    public void setText(final String text) {
        this.text = text;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        final Element anchor = getElement();
        return anchor.getString();
    }

    private void setInNewWindowImpl(final boolean inNewWindow) {
        if (inNewWindow) {
            setTargetImpl(_BLANK);
        } else {
            setTargetImpl(NO_TARGET);
        }
    }

    private void setTargetImpl(final String target) {
        this.target = target;
    }

}
