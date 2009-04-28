package org.ourproject.kune.platf.client.ui.rte.insertimg;

import org.cobogw.gwt.user.client.CSS;
import org.ourproject.kune.platf.client.ui.rte.insertlink.LinkInfo;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;

public class ImageInfo {

    public static final boolean DEF_WRAP_VALUE = true;
    public static final boolean DEF_CLICK_ORIGINAL_VALUE = false;

    public static final String SIZE_ORIGINAL = "original";
    public static final String SIZE_FIT = "fit";
    public static final String SIZE_XS = "xs";
    public static final String SIZE_S = "s";
    public static final String SIZE_M = "m";
    public static final String SIZE_L = "l";
    public static final String SIZE_XL = "xl";

    public static String[][] sizes = { new String[] { SIZE_ORIGINAL, "Original image size", "" },
            new String[] { SIZE_FIT, "Fit page width", "100%" },
            new String[] { SIZE_XS, "Thumbnail, up to 160 pixels wide", "160px" },
            new String[] { SIZE_S, "Small, up to 320 pixels wide", "320px" },
            new String[] { SIZE_M, "Medium, up to 640 pixels wide", "640px" },
            new String[] { SIZE_L, "Large, up to 1024 pixels wide", "1024px" },
            new String[] { SIZE_XL, "Extra large, up to 1600 pixels wide", "1600px" } };

    private String src;
    private boolean wraptext;
    private boolean clickOriginal;
    private String position;
    private String size;

    public ImageInfo(final String src, final boolean wraptext, final boolean clickOriginal, final String position,
            final String size) {
        this.src = src;
        this.wraptext = wraptext;
        this.clickOriginal = clickOriginal;
        this.setPosition(position);
        this.setSize(size);
    }

    public boolean getClickOriginal() {
        return clickOriginal;
    }

    public Element getElement() {
        Element imgElement = getImageElement();
        if (clickOriginal) {
            com.google.gwt.user.client.Element anchor = DOM.createAnchor();
            DOM.setElementProperty(anchor, LinkInfo.HREF, src);
            DOM.setElementProperty(anchor, LinkInfo.TARGET, LinkInfo._BLANK);
            anchor.setInnerHTML(imgElement.getString());
            return anchor;
        } else {
            return imgElement;
        }
    }

    public String getPosition() {
        return position;
    }

    public String getSize() {
        return size;
    }

    public String getSrc() {
        return src;
    }

    public boolean isWraptext() {
        return wraptext;
    }

    public void setClickOriginal(final boolean clickOriginal) {
        this.clickOriginal = clickOriginal;
    }

    public void setPosition(final String position) {
        this.position = position;
    }

    public void setSize(final String size) {
        this.size = size;
    }

    public void setSrc(final String src) {
        this.src = src;
    }

    public void setWraptext(final boolean wraptext) {
        this.wraptext = wraptext;
    }

    @Override
    public String toString() {
        Element element = getElement();
        return element.getString();
    }

    private Element getImageElement() {
        com.google.gwt.user.client.Element img = DOM.createImg();
        DOM.setElementProperty(img, "src", src);
        CSS.setProperty(img, CSS.A.WIDTH, getStyleSize());
        return ContentPosition.getElementPosition(img, wraptext, position);
    }

    private String getStyleSize() {
        for (String[] current : sizes) {
            if (current[0].equals(size)) {
                return current[2];
            }
        }
        return "";
    }
}
